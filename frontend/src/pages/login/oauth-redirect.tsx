import { DecodedToken } from "@/src/features/auth/authApi";
import { useQueryParams } from "@/src/hooks/useQueryParams";
import { setToken } from "@/src/utils/tokenManager";
import jwt from "jsonwebtoken";
import { useRouter } from "next/router";
import { useEffect } from "react";

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

    // 쿠키에 토큰 집어넣고 메인 페이지로 이동
    const navigateToLogin = async () => {
      if (accessToken && refreshToken) {
        try {
          const decodedAccess = jwt.verify(accessToken, secret);
          const decodedRefresh = jwt.verify(refreshToken, secret);
          const decodedAccessToken = decodedAccess as DecodedToken;
          const decodedRefreshToken = decodedRefresh as DecodedToken;
          const accessExp = decodedAccessToken.exp;
          const refreshExp = decodedRefreshToken.exp;
          setToken("ACCESS_TOKEN", accessToken, accessExp);
          setToken("REFRESH_TOKEN", refreshToken, refreshExp);
        } catch (err) {
          console.error(err);
          // 토큰 검증에 실패했을 때의 처리 로직
        }
      }
      // 메인 페이지로 이동(스택 X)
      router.replace("/", undefined, { shallow: true }).then(() => {
        window.location.reload();
      });
    };

    navigateToLogin();
  }, [queryParams, router]);

  return <></>;
}
