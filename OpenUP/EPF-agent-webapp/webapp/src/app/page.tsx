"use client";

import { CopilotChat } from "@copilotkit/react-ui";
import { SessionProvider } from "next-auth/react"

export default function CopilotKitPage() {
  return (
    <main>
      <SessionProvider>
        <CopilotChat />
      </SessionProvider>
    </main>
  );
}
