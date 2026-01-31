import NextAuth, { AuthOptions } from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak";

const debug = ("true" == process.env.DEBUG);

export const authOptions: AuthOptions = {
  providers: [
    KeycloakProvider({
      clientId: process.env.CLIENT_ID!,
      clientSecret: process.env.CLIENT_SECRET!,
      issuer: process.env.ISSUER,
      httpOptions: {
        timeout: process.env.AUTH_REQUEST_TIMEOUT? parseInt(process.env.AUTH_REQUEST_TIMEOUT) : undefined
      }
    })
  ],
  session: {
    strategy: "jwt",
  },
  jwt: {
    maxAge: process.env.JWT_MAX_AGE? parseInt(process.env.JWT_MAX_AGE) : undefined
  },
  callbacks: {
    async session({ session, token, user }) {
      if(debug){
        console.log("[BEGIN]session");
        console.log("session._accessToken:%s", session._accessToken);
        console.log("token.accessToken:%s", token.accessToken);
        console.log("[END]session");
      }
      return session
    },
    async jwt({ token, user, account, profile }) {
      if(debug){
        console.log("[BEGIN]jwt");
        console.log("token._accessToken:%s", token._accessToken);
        console.log("account.accessToken:%s", account?.access_token);
        console.log("account.expires_at:%s", account?.expires_at);
        console.log("[END]jwt");
      }
      if(account){
        token.accessToken = account?.access_token;
      }
      return token
    }
  }
}
const handler = NextAuth(authOptions)

export { handler as GET, handler as POST }