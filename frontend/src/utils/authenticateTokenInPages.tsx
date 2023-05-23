import { IncomingMessage, ServerResponse } from "http";
import jwt from "jsonwebtoken";
import { DecodedToken } from "../features/auth/authApi";
import { clearAuth } from "../features/auth/authSlice";
import { AppStore } from "../features/store";
import authenticateMemberInPages from "./authenticateMemberInPages";

// 서버
export async function authenticateTokenInPages(
  {
    req,
    res,
  }: {
    req: IncomingMessage & {
      cookies: Partial<{
        [key: string]: string;
      }>;
    };
    res: ServerResponse<IncomingMessage>;
  },
  store: AppStore
) {
  // 쿠키를 받아와 로그인 상태인지 확인
  let cookie = req?.headers.cookie;
  let accessToken = cookie
    ?.split(";")
    .find((c) => c.trim().startsWith("ACCESS_TOKEN="))
    ?.split("=")[1];
  let refreshToken = cookie
    ?.split(";")
    .find((c) => c.trim().startsWith("REFRESH_TOKEN="))
    ?.split("=")[1];
  console.log("SERVER_ACCESS_TOKEN:", accessToken);
  console.log("SERVER_REFRESH_TOKEN:", refreshToken);
  // 엑세스 토큰이 없다면(만료되었다면) refresh 요청하여, 쿠키 재구성
  if (!accessToken) {
    // 리프레시 토큰이 있다면 accessToken 재발급 요청
    if (refreshToken) {
      try {
        const response = await fetch(`${process.env.API_URL}/member/refresh`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${refreshToken}`,
          },
        });
        const data = await response.json();
        // 받아온 데이터에서 토큰을 가져와 쿠키를 최신화하고, 로그인 상태 설정
        accessToken = data.access_token as string;
        refreshToken = data.refresh_token as string;
        console.log(accessToken);
        const decodedAccessToken = jwt.decode(accessToken) as DecodedToken;
        const decodedRefreshToken = jwt.decode(refreshToken) as DecodedToken;
        res.setHeader("Set-Cookie", [
          `ACCESS_TOKEN=${accessToken}; Max-Age=${
            decodedAccessToken.exp - Math.floor(Date.now() / 1000)
          }; Path=/`,
          `REFRESH_TOKEN=${refreshToken}; Max-Age=${
            decodedRefreshToken.exp - Math.floor(Date.now() / 1000)
          }; Path=/`,
        ]);
        // refreshToken을 기반으로 스토어에 로그인 상태 인증
        console.log("TEST:", accessToken);
        authenticateMemberInPages(store, refreshToken, res);
      } catch (error) {
        // 요청 실패 시 로그인 상태 해제(쿠키, 스토어) 후..
        // 서버단에서의 쿠키 조작을 위해 res 이용
        store.dispatch(clearAuth());
        res.setHeader("Set-Cookie", "ACCESS_TOKEN=;Max-Age=0");
        res.setHeader("Set-Cookie", "REFRESH_TOKEN=;Max-Age=0");
        console.log("SERVER_TOKEN_REFRESH_ERRROR: ", error);
      }
    } else {
      // 리프레시 토큰이 없다면 아무일 없음
      console.log("SERVER_NO_LOGIN_STATE");
    }
  } else {
    // 엑세스 토큰이 있을 때
    if (refreshToken) {
      authenticateMemberInPages(store, refreshToken, res);
    } else {
      // 리프레시 토큰이 없다면 마저 로그아웃 처리
      store.dispatch(clearAuth());
      res.setHeader("Set-Cookie", "ACCESS_TOKEN=;Max-Age=0");
    }
  }
}
