"use client";

import { CopilotChat } from "@copilotkit/react-core/v2";
import { SessionProvider, useSession } from "next-auth/react";
import { useInterrupt, useDefaultRenderTool } from "@copilotkit/react-core/v2";

const baseUrl = process.env.NEXT_PUBLIC_NEXT_AUTH_BASE_URL || "http://localhost:3000";
const basePath = process.env.NEXT_PUBLIC_NEXT_AUTH_BASE_PATH || "/agent/api/auth";
const refetchInterval = parseInt(process.env.NEXT_PUBLIC_NEXT_AUTH_REFETCH_INTERVAL || "60");

export default function CopilotKitPage() {
  return (
    <main>
      <SessionProvider baseUrl={baseUrl} basePath={basePath} refetchInterval={refetchInterval}>
        <MainContent />
      </SessionProvider>
    </main>
  );
}

function MainContent() {
  const session = useSession({
    required: true,
    onUnauthenticated: () => {
      const url = `${basePath}/signin?${new URLSearchParams({
          error: "SessionRequired",
          callbackUrl: window.location.href,
        })}`
      window.location.href = url;
    }
  });
  console.log("[BEGIN]MainContent");
  console.log("session.status:%s", session.status);
  console.log("session.data.user.id:%s", session.data?.user?.id);
  console.log("session.data.expires:%s", session.data?.expires);
  console.log("[END]MainContent");
  useDefaultRenderTool({
    render: ({name, status, result}) => {
      return (<p>{name}</p>);
    }
  });
  useInterrupt({
  render: ({ event, resolve }) => {
    return (
      <div className="fixed inset-0 bg-gray-900 bg-opacity-75 flex items-center justify-center z-50">
        <div className="bg-white p-6 rounded-lg shadow-xl max-w-md w-full">
          <h2 className="text-xl font-bold mb-4">Agent Intervention Required</h2>
          <p className="mb-4">The agent requires your review before proceeding with a critical step:</p>
          <div className="p-3 border rounded bg-gray-50 mb-6 text-sm">
            <p>Event: <span className="font-mono">{event.name}</span></p>
            <p>Details: {JSON.stringify(event.details)}</p>
          </div>
          <div className="flex justify-end space-x-4">
            <button
              onClick={() => resolve("reject")}
              className="px-4 py-2 border rounded text-sm"
            >
              Reject
            </button>
            <button
              onClick={() => resolve("edit")}
              className="px-4 py-2 border rounded text-sm"
            >
              Edit
            </button>
            <button
              onClick={() => resolve("approve")}
              className="px-4 py-2 bg-blue-600 text-white rounded text-sm hover:bg-blue-700"
            >
              Approve
            </button>
          </div>
        </div>
      </div>
    );
  },
});
  if(session.status == "authenticated"){
    return (
      <CopilotChat threadId={session.data.user.id} />
    );
  }
  return (
      <p>{session.status}</p>
    );
}