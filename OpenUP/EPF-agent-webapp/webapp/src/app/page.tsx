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
  /*useInterrupt({
    render: ({ event, resolve }) => {
      resolve("reject")
      return (<div>{event.name}</div>);
    }
  })*/
  if(session.status == "authenticated"){
    return (
      <CopilotChat threadId={session.data.user.id} />
    );
  }
  return (
      <p>{session.status}</p>
    );
}