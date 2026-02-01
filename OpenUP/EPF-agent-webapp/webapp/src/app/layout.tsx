import type { Metadata } from "next";

import { CopilotKit } from "@copilotkit/react-core";
import "./globals.css";
import "@copilotkit/react-ui/styles.css";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { SessionProvider } from "next-auth/react";

export const metadata: Metadata = {
  title: "EPF Agent",
  description: "",
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const session = await getServerSession(authOptions);
  console.log("[BEGIN]RootLayout");
  console.log("session.expires:%s", session != null ? session.expires : undefined);
  console.log("[END]RootLayout");
  return (
    <html lang="en">
      <body className={"antialiased"}>
        <SessionProvider>
          <CopilotKit runtimeUrl="/api/copilotkit" agent="epf-agent">
            {children}
          </CopilotKit>
        </SessionProvider>
      </body>
    </html>
  );
}
