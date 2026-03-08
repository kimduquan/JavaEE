import NextAuth, { AuthOptions } from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak";

const debug = ("true" == process.env.DEBUG);
const wellKnown = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/.well-known/openid-configuration`: undefined;
const jwks_endpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/certs`: undefined;
const token_endpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/token`: undefined;
const userinfo_endpoint = process.env.OIDC_PROVIDER_URL? `${process.env.OIDC_PROVIDER_URL}/protocol/openid-connect/userinfo`: undefined;

export const authOptions: AuthOptions = {
  providers: [
    KeycloakProvider({
      clientId: process.env.CLIENT_ID!,
      clientSecret: process.env.CLIENT_SECRET!,
      issuer: process.env.ISSUER,
      httpOptions: {
        timeout: process.env.AUTH_REQUEST_TIMEOUT? parseInt(process.env.AUTH_REQUEST_TIMEOUT) : undefined
      },
      wellKnown: wellKnown,
      jwks_endpoint: jwks_endpoint,
      token: token_endpoint,
      userinfo: userinfo_endpoint,
    })
  ],
  session: {
    strategy: "jwt",
    maxAge: process.env.JWT_MAX_AGE? parseInt(process.env.JWT_MAX_AGE) : undefined,
    updateAge: 10,
  },
  jwt: {
    maxAge: process.env.JWT_MAX_AGE? parseInt(process.env.JWT_MAX_AGE) : undefined,
  },
  callbacks: {
    async session({ session, token, user }) {
      if(debug){
        console.log("[BEGIN]session");
        console.log("token.sub:%s", token.sub);
        console.log("token.accessToken:%s", token.accessToken);
        console.log("session.expires:%s", session.expires);
        console.log("[END]session");
      }
      session.user.id = token.sub
      return session
    },
    async jwt({ token, user, account, profile }) {
      if(debug){
        console.log("[BEGIN]jwt");
        console.log("token.sub:%s", token?.sub);
        console.log("account.userId:%s", account?.userId);
        console.log("account.accessToken:%s", account?.access_token);
        console.log("account.expires_at:%s", account?.expires_at);
        console.log("[END]jwt");
      }
      if(account){
        token.accessToken = account?.access_token;
        token.refreshToken = account?.refresh_token;
        token.expiresAt = account?.expires_at;
      }
      return token
    }
  },
  debug: debug,
}
const handler = NextAuth(authOptions)

export { handler as GET, handler as POST }