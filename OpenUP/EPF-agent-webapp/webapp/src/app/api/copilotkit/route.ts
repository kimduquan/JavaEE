import {
  CopilotRuntime,
  ExperimentalEmptyAdapter,
  copilotRuntimeNextJSAppRouterEndpoint,
} from "@copilotkit/runtime";
import { LangGraphHttpAgent } from "@copilotkit/runtime/langgraph";
import { NextRequest } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getToken } from "next-auth/jwt";

const debug = ("true" == process.env.DEBUG);
// 1. Define the agent connection to LangGraph
/*const defaultAgent = new LangGraphAgent({
  deploymentUrl: process.env.LANGGRAPH_DEPLOYMENT_URL || "http://localhost:8123",
  graphId: "sample_agent",
  langsmithApiKey: process.env.LANGSMITH_API_KEY || "",
});*/

// 2. Bind in middleware to the agent. For A2UI and MCP Apps.
//defaultAgent.use(...aguiMiddleware)

// 3. Define the route and CopilotRuntime for the agent
export const POST = async (req: NextRequest) => {
  const session = await getServerSession(authOptions);
  const jwt = await getToken({ req : req });
  if(debug){
    console.log("[BEGIN]POST");
    console.log("session.user.id:%s", session?.user.id);
    console.log("session.expires:%s", session?.expires);
    console.log("jwt.accessToken:%s", jwt?.accessToken);
    console.log("jwt.sub:%s", jwt?.sub);
    console.log("[END]POST");
  }
  const { handleRequest } = copilotRuntimeNextJSAppRouterEndpoint({
    endpoint: "/agent/api/copilotkit",
    serviceAdapter: new ExperimentalEmptyAdapter(),
    runtime: new CopilotRuntime({
      agents: {
        default: new LangGraphHttpAgent({
          url: process.env.EPF_AGENT_URL || "http://localhost:8123",
          headers: { "Authorization": "Bearer " + jwt?.accessToken },
          threadId: jwt?.sub,
          debug: debug
        }),
      },
    }),
  });

  return handleRequest(req);
};
