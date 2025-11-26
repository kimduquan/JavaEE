import {
  CopilotRuntime,
  ExperimentalEmptyAdapter,
  copilotRuntimeNextJSAppRouterEndpoint,
} from "@copilotkit/runtime";

import { LangGraphHttpAgent } from "@ag-ui/langgraph"
import { NextRequest } from "next/server";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { NextResponse } from "next/server";
import { getToken } from "next-auth/jwt";
 
// 1. You can use any service adapter here for multi-agent support. We use
//    the empty adapter since we're only using one agent.
const serviceAdapter = new ExperimentalEmptyAdapter();
 
// 2. Create the CopilotRuntime instance and utilize the LangGraph AG-UI
//    integration to setup the connection.
/*const runtime = new CopilotRuntime({
  agents: {
    "sample_agent": new LangGraphHttpAgent({
      url: process.env.EPF_AGENT_URL || "http://localhost:8123",
    }),
  }
});*/
 
// 3. Build a Next.js API route that handles the CopilotKit runtime requests.
export const POST = async (req: NextRequest) => {
  
  const session = await getServerSession(authOptions);
  if (!session) return new NextResponse("Unauthorized", { status: 401 });

  const token = await getToken({ req : req, raw: true });
  const runtime = new CopilotRuntime({
    agents: {
      "sample_agent": new LangGraphHttpAgent({
        url: process.env.EPF_AGENT_URL || "http://localhost:8123",
        headers: { "Authorization": "Bearer " + token }
      }),
    }
  });
  const { handleRequest } = copilotRuntimeNextJSAppRouterEndpoint({
    runtime, 
    serviceAdapter,
    endpoint: "/api/copilotkit"
  });
  return handleRequest(req);
};