import NextAuth, { AuthOptions } from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak";

export const authOptions: AuthOptions = {
  providers: [
    KeycloakProvider({
      clientId: process.env.CLIENT_ID!,
      clientSecret: process.env.CLIENT_SECRET!,
      issuer: process.env.ISSUER,
    })
  ],
  session: {
    strategy: "jwt",
  },
  callbacks: {
    async session({ session, token, user }) {
      return session
    },
    async jwt({ token, user, account, profile }) {
      if(account){
        token.accessToken = account?.access_token;
      }
      return token
    }
  }
}
const handler = NextAuth(authOptions)

export { handler as GET, handler as POST }