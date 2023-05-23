import { DecodedToken } from "@/src/features/auth/authApi";
import { AppState, useAppSelector, wrapper } from "@/src/features/store";
import { useQueryParams } from "@/src/hooks/useQueryParams";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
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
  const member = useAppSelector((state: AppState) => state.auth.member);

  useEffect(() => {
    if (member) router.replace("/", undefined, { shallow: true });
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
      // 리로드 -> 멤버 로그인 정보 store에 저장

      window.location.reload();
    };

    navigateToLogin();
  }, [queryParams, router]);

  return <></>;
}

export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    return {
      props: {},
    };
  }
);
