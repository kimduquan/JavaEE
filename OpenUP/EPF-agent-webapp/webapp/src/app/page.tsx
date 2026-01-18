"use client";

import { CopilotChat } from "@copilotkit/react-ui";
import { SessionProvider, useSession } from "next-auth/react"

export default function CopilotKitPage() {
  return (
    <main>
      <SessionProvider>
        <CopilotChat />
        <MainContent />
      </SessionProvider>
    </main>
  );
}

function MainContent() {
  useSession({ required: true });

  return (
    <div>
    </div>
  );
}