"use client";

import { CopilotChat } from "@copilotkit/react-ui";
import { SessionProvider, useSession } from "next-auth/react";
import { useLangGraphInterrupt, useDefaultTool } from "@copilotkit/react-core";

export default function CopilotKitPage() {
  return (
    <main>
      <SessionProvider>
        <MainContent />
      </SessionProvider>
    </main>
  );
}

function MainContent() {
  const session = useSession({ required: true });
  console.log("[BEGIN]MainContent");
  console.log("session.status:%s", session.status);
  console.log("session.data.user.id:%s", session.data?.user?.id);
  console.log("session.data.expires:%s", session.data?.expires);
  console.log("[END]MainContent");
  useDefaultTool({
    render: ({name, status, args, result}) => {
      return (<p>{name}</p>);
    }
  });
  useLangGraphInterrupt({
    render: ({ event, resolve }) => {
      resolve("reject")
      return (<div>{event.name}</div>);
    }
  })
  if(session.status == "authenticated"){
    return (
      <div>
          <CopilotChat />
      </div>
    );
  }
  return (
      <p>{session.status}</p>
    );
}