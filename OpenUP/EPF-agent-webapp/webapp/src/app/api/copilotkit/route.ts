import {
  CopilotRuntime,
  ExperimentalEmptyAdapter,
  copilotRuntimeNextJSAppRouterEndpoint,
} from "@copilotkit/runtime";
import { LangGraphHttpAgent } from "@ag-ui/langgraph"
import { NextRequest } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getToken } from "next-auth/jwt";
 
const debug = ("true" == process.env.DEBUG);
// 1. You can use any service adapter here for multi-agent support. We use
//    the empty adapter since we're only using one agent.
const serviceAdapter = new ExperimentalEmptyAdapter();

// 2. Create the CopilotRuntime instance and utilize the LangGraph AG-UI
//    integration to setup the connection.
/*const runtime = new CopilotRuntime({
  agents: {
    sample_agent: new LangGraphAgent({
      deploymentUrl:
        process.env.LANGGRAPH_DEPLOYMENT_URL || "http://localhost:8123",
      graphId: "sample_agent",
      langsmithApiKey: process.env.LANGSMITH_API_KEY || "",
    }),
  },
});*/

// 3. Build a Next.js API route that handles the CopilotKit runtime requests.
export const POST = async (req: NextRequest) => {
  
  const session = await getServerSession(authOptions);
  const jwt = await getToken({ req : req });
  const runtime = new CopilotRuntime({
    agents: {
      "epf-agent": new LangGraphHttpAgent({
        url: process.env.EPF_AGENT_URL || "http://localhost:8123",
        headers: { "Authorization": "Bearer " + jwt?.accessToken },
        threadId: jwt?.sub,
        debug: debug
      }),
    }
  });
  if(debug){
    console.log("[BEGIN]POST");
    console.log("session._accessToken:%s", session?._accessToken);
    console.log("session.expires:%s", session?.expires);
    console.log("jwt.accessToken:%s", jwt?.accessToken);
    console.log("[END]POST");
  }
  const { handleRequest } = copilotRuntimeNextJSAppRouterEndpoint({
    runtime,
    serviceAdapter,
    endpoint: "/api/copilotkit",
  });

  return handleRequest(req);
};
