
import "./globals.css";

import { CopilotKit } from "@copilotkit/react-core";
import "@copilotkit/react-core/v2/styles.css";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

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
      <body className={`antialiased`}>
        <CopilotKit publicLicenseKey={process.env.COPILOTKIT_PUBLIC_LICENSE_KEY} runtimeUrl="/agent/api/copilotkit" showDevConsole={debug} threadId={session?.user.id}>
          {children}
        </CopilotKit>
      </body>
    </html>
  );
}
