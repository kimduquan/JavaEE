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
  callbacks: {
    async session({ session, token, user }) {
      if(debug){
        console.log("session:%s\n%s\n%s", session._accessToken, token.accessToken, user.id);
      }
      return session
    },
    async jwt({ token, user, account, profile }) {
      if(debug){
        console.log("jwt:%s\n%s\n%s\n%s", token.accessToken, user.id, account?.access_token, profile?.name);
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