import type { Metadata } from "next";

import { CopilotKit } from "@copilotkit/react-core";
import "./globals.css";
import "@copilotkit/react-ui/styles.css";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

export const metadata: Metadata = {
  title: "EPF Agent",
  description: "",
};
const debug = ("true" == process.env.DEBUG);
export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const session = await getServerSession(authOptions);
  console.log("[BEGIN]RootLayout");
  console.log("session.user.id:%s", session?.user.id);
  console.log("session.expires:%s", session?.expires);
  console.log("[END]RootLayout");
  return (
    <html lang="en">
      <body className={"antialiased"}>
        <CopilotKit runtimeUrl="/api/copilotkit" agent="epf-agent" showDevConsole={debug} threadId={session?.user.id}>
          {children}
        </CopilotKit>
      </body>
    </html>
  );
}
