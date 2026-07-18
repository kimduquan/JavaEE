import NextAuth, { AuthOptions } from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak";

const debug = ("true" == process.env.DEBUG);
const wellKnown = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/.well-known/openid-configuration`: undefined;
const jwksEndpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/certs`: undefined;
const tokenEndpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/token`: undefined;
const userInfoEndpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/userinfo`: undefined;
const maxAge = process.env.JWT_MAX_AGE? parseInt(process.env.JWT_MAX_AGE) : undefined;
const updateAge = process.env.JWT_UPDATE_AGE? parseInt(process.env.JWT_UPDATE_AGE) : undefined;

export const authOptions: AuthOptions = {
  providers: [
    KeycloakProvider({
      clientId: process.env.CLIENT_ID!,
      clientSecret: process.env.CLIENT_SECRET!,
      issuer: process.env.ISSUER,
      httpOptions: {
        timeout: process.env.AUTH_REQUEST_TIMEOUT? parseInt(process.env.AUTH_REQUEST_TIMEOUT) : undefined
      },
      wellKnown: wellKnown,
      jwks_endpoint: jwksEndpoint,
      token: tokenEndpoint,
      userinfo: userInfoEndpoint,
    })
  ],
  session: {
    strategy: "jwt",
    maxAge: maxAge,
    updateAge: updateAge,
  },
  jwt: {
    maxAge: maxAge,
  },
  callbacks: {
    async session({ session, token, user }) {
      if(debug){
        console.log("[BEGIN]session");
        console.log("token.sub:%s", token.sub);
        console.log("session.expires:%s", session.expires);
        console.log("[END]session");
      }
      session.user.id = token.sub
      return session
    },
    async jwt({ token, user, account, profile }) {
      if(debug){
        console.log("[BEGIN]jwt");
        console.log("token.sub:%s", token?.sub);
        console.log("account.userId:%s", account?.userId);
        console.log("account.expires_at:%s", account?.expires_at);
        console.log("[END]jwt");
      }
      if(account){
        token.accessToken = account?.access_token;
        token.refreshToken = account?.refresh_token;
        token.expiresAt = account?.expires_at;
      }
      else if(token.refreshToken && token.expiresAt && Date.now() >= (token.expiresAt * 1000 - (updateAge || 0) * 1000)){
        const tokenInfoRes = await fetch(tokenEndpoint!, {
            method: "POST",
            body: new URLSearchParams({
              client_id: process.env.CLIENT_ID!,
              client_secret: process.env.CLIENT_SECRET!,
              grant_type: "refresh_token",
              refresh_token: token.refreshToken!,
            }),
          });
        const tokenInfoJson = await tokenInfoRes.json()
        if(tokenInfoRes.ok){
          const tokenInfo = tokenInfoJson as {
            access_token: string
            expires_in: number
            refresh_token?: string
          }
          token.accessToken = tokenInfo.access_token;
          token.expiresAt = Math.floor(Date.now() / 1000 + tokenInfo.expires_in);
          token.refreshToken = tokenInfo.refresh_token? tokenInfo.refresh_token : token.refreshToken
        }
        else{
          throw tokenInfoJson
        }
      }
      return token
    }
  },
  debug: debug,
}
const handler = NextAuth(authOptions)

export { handler as GET, handler as POST }