
import "./globals.css";
import "@copilotkit/react-core/v2/styles.css";

import { CopilotKit } from "@copilotkit/react-core/v2";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

const debug = ("true" == process.env.DEBUG);

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const session = await getServerSession(authOptions);
  return (
    <html lang="en" suppressHydrationWarning>
      <head>
        <title>EPF Agent</title>
        <link
          rel="icon"
          type="image/svg+xml"
          href="/copilotkit-logo-mark.svg"
        />
        {/*
          Set the theme class BEFORE first paint to avoid a white→dark flash.
          ThemeProvider applies the theme in a useEffect (post-hydration), so
          without this the page paints unthemed (light) first, then flips. This
          blocking inline script matches ThemeProvider's "system" default so
          there's no flash and no class mismatch when the provider re-applies.
        */}
        <script
          dangerouslySetInnerHTML={{
            __html:
              "(function(){try{var d=window.matchMedia('(prefers-color-scheme: dark)').matches;document.documentElement.classList.add(d?'dark':'light');}catch(e){}})();",
          }}
        />
      </head>
      {/*
        suppressHydrationWarning: browser extensions (e.g. Grammarly) inject
        attributes like data-gr-ext-installed onto <body> before React hydrates,
        which would otherwise surface as a hydration mismatch on first load.
        This only relaxes the check for <body>'s own attributes (one level deep);
        everything rendered inside <body> is still fully hydration-checked.
      */}
      <body className={`antialiased`} suppressHydrationWarning>
        <CopilotKit
          runtimeUrl="/agent/api/copilotkit"
          inspectorDefaultAnchor={{ horizontal: "right", vertical: "top" }}
          a2ui={{ catalog: demonstrationCatalog }}
          openGenerativeUI={{}}
          useSingleEndpoint={false}
          publicLicenseKey={process.env.COPILOTKIT_PUBLIC_LICENSE_KEY}
          showDevConsole={debug}
          threadId={session?.user.id}
        >
          {children}
        </CopilotKit>
      </body>
    </html>
  );
}
