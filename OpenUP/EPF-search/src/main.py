import os
from typing import Annotated, Any
from fastapi import Depends, FastAPI, HTTPException, Request
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
import jwt
from jwt import PyJWKClient
from jwt.exceptions import PyJWTError
from starlette.status import HTTP_403_FORBIDDEN
from langchain_openai import OpenAIEmbeddings
import uvicorn
from turbovec.langchain import TurboQuantVectorStore
from langchain_core.documents.base import Document
from opentelemetry.instrumentation import auto_instrumentation
from aiocache import Cache, cached
from dotenv import load_dotenv

load_dotenv()
auto_instrumentation.initialize()

LOG_LEVEL = os.environ.get("LOG_LEVEL", "info")
APP = FastAPI()
HOSTNAME = os.environ.get("HOSTNAME", "0.0.0.0")
PORT = int(os.environ.get("PORT", "9198"))

SECURITY = HTTPBearer()
JWK_CLIENT = PyJWKClient(uri=os.environ["JWT_KEY_URL"])

CACHE = Cache(Cache.MEMORY)

async def authenticate(credentials: HTTPAuthorizationCredentials) -> Any:
    claims: Any = None
    try:
        key = JWK_CLIENT.get_signing_key_from_jwt(token=credentials.credentials)
        claims = jwt.decode(jwt=credentials.credentials, key=key, issuer=os.environ["JWT_ISSUER"])
    except PyJWTError as ex:
        raise HTTPException(status_code=HTTP_403_FORBIDDEN, detail=ex.args)
    return claims

def get_organization(claims: Any) -> str:
    claims_dict: dict[str, Any] = claims
    if "organization" in claims_dict.keys():
        organization_claim: dict[str, Any] = claims_dict["organization"]
        organization_name: str = list(organization_claim.keys())[0]
        organization: str = organization_claim.get(organization_name)["id"]
        return organization
    raise HTTPException(status_code=HTTP_403_FORBIDDEN)

def organization_key_builder(func, *args, **kwargs):
    organization = kwargs.get("organization") or args[0]
    return organization

@cached(key_builder=organization_key_builder)
def get_embeddings(organization: str) -> OpenAIEmbeddings:
    return OpenAIEmbeddings(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

@cached(key_builder=organization_key_builder)
def get_vector_store(organization: str) -> TurboQuantVectorStore:
    embeddings = get_embeddings(organization=organization)
    return TurboQuantVectorStore(embedding=embeddings)

@APP.get("/similarity")
async def similarity_search(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)], query: str, k: int = 4):
    claims = await authenticate(credentials=credentials)
    organization: str = get_organization(claims=claims)
    vector_store: TurboQuantVectorStore = get_vector_store(organization)
    return await vector_store.asimilarity_search(query=query, k=k)

@APP.get("/mmr")
async def max_marginal_relevance_search(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)], query: str, k: int = 4, fetch_k: int = 20, lambda_mult: float = 0.5):
    claims = await authenticate(credentials=credentials)
    organization: str = get_organization(claims=claims)
    vector_store: TurboQuantVectorStore = get_vector_store(organization)
    return await vector_store.amax_marginal_relevance_search(query=query, k=k, fetch_k=fetch_k, lambda_mult=lambda_mult)

@APP.patch("documents")
async def add_documents(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)], documents: list[Document]):
    claims = await authenticate(credentials=credentials)
    organization: str = get_organization(claims=claims)
    vector_store: TurboQuantVectorStore = get_vector_store(organization)
    return await vector_store.aadd_documents(documents=documents)

@APP.delete("documents/{id}")
async def delete(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)], request: Request):
    claims = await authenticate(credentials=credentials)
    organization: str = get_organization(claims=claims)
    vector_store: TurboQuantVectorStore = get_vector_store(organization)
    ids: list[str] = request.query_params.getlist(key="id")
    return await vector_store.adelete(ids=ids)

@APP.get("/health")
def health():
    """Health check."""
    return {
        "status": "ok"
    }

if __name__ == "__main__":
    uvicorn.run(app=APP, host=HOSTNAME, port=PORT, log_level=LOG_LEVEL)