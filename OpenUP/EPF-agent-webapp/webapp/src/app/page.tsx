"use client";

import { CopilotChat } from "@copilotkit/react-ui";
import { SessionProvider, useSession } from "next-auth/react"

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
  console.log("session.data.expires:%s", session.data != null? session.data.expires : "");
  console.log("[END]MainContent");
  return (
    <div>
        <CopilotChat />
    </div>
  );
}