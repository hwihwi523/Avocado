import { useEffect } from "react";
import { useRouter } from "next/router";
import { useQueryParams } from "@/src/hooks/useQueryParams";
import { DecodedToken, authApi } from "@/src/features/auth/authApi";
import jwt from "jsonwebtoken";
import { setToken } from "@/src/utils/tokenManager";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function OAuthRedirect() {
  const router = useRouter();
  const queryParams = useQueryParams();

  useEffect(() => {
    // url에서 받은 토큰으로 로그인 처리
    const accessToken = queryParams.get("access_token");
    const refreshToken = queryParams.get("refresh_token");
    const secret = SECRET;
    // 쿠키에 토큰 집어넣기
    if (accessToken && refreshToken) {
      const decodedAccess = jwt.verify(accessToken, secret);
      const decodedRefresh = jwt.verify(refreshToken, secret);
      const decodedAccessToken = decodedAccess as DecodedToken;
      const decodedRefreshToken = decodedRefresh as DecodedToken;
      const accessExp = decodedAccessToken.exp;
      const refreshExp = decodedRefreshToken.exp;
      setToken("ACCESS_TOKEN", accessToken, accessExp);
      setToken("REFRESH_TOKEN", refreshToken, refreshExp);
    }
    // 메인 페이지로 이동
    router.push("/login");
  }, [queryParams, router]);

  return <></>;
}
