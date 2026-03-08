import { type DefaultSession } from "next-auth"
import { DefaultJWT } from "next-auth/jwt"

declare module "next-auth" {
  interface Session extends DefaultSession {
    user: {
      id?: string
    } & DefaultSession["user"]
  }
}

declare module "next-auth/jwt" {
  interface JWT extends Record<string, unknown>, DefaultJWT {
    accessToken?: string;
    refreshToken?: string;
    expiresAt?: number;
  }
}