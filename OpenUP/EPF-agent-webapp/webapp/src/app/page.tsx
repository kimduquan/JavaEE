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
  useSession({ required: true });
  
  return (
    <div>
        <CopilotChat />
    </div>
  );
}