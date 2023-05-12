import { fetchBaseQuery } from "@reduxjs/toolkit/dist/query";
import { appCookies } from "../pages/_app";
import jwt from "jsonwebtoken";
import { DecodedToken } from "../features/auth/authApi";
import { removeTokenAll, setToken } from "./tokenManager";
import { clearAuth } from "../features/auth/authSlice";
import { useDispatch } from "react-redux";

const API_URL = process.env.NEXT_PUBLIC_API_URL
  ? process.env.NEXT_PUBLIC_API_URL
  : "";
const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export interface RefreshResponse {
  access_token: string;
  refresh_token: string;
}

type AddHeaders = {
  [key: string]: string;
};

// 각 API 요청 시 token을 헤더에 담아 보내기 위한 커스텀 함수(검증이 필요한 경우 사용)
// 브라우저 단에서만 실행되는 로직
export const customFetchBaseQuery = (option: {
  baseUrl: string;
  headers?: AddHeaders;
}) => {
  return fetchBaseQuery({
    prepareHeaders: async (headers) => {
      // 토큰이 존재하면 토큰을 헤더에 넣어서 보내기(만료 시 자동 삭제)
      let token = appCookies.get("ACCESS_TOKEN");
      // accessToken이 존재하지 않으면 refresh 요청 시도
      if (!token) {
        const secret = SECRET;
        let refreshToken = appCookies.get("REFRESH_TOKEN");
        // refresh token이 아직 만료되지 않았다면 요청
        if (refreshToken) {
          // refresh token을 헤더에 담아 요청하여 새로운 tokens 가져오기
          const response = await fetch(API_URL + "/member/refresh", {
            method: "GET",
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          });
          if (!response.ok) {
            // 로그아웃 시키기
            throw new Error("Refresh token request failed");
          }
          // Response에서 본문(body) 추출 후 객체로 변환
          const newTokens: RefreshResponse = await response.json();
          const newAccessToken = newTokens.access_token;
          const newRefreshToken = newTokens.refresh_token;
          let accessExp = 0;
          let refreshExp = 0;
          try {
            const decodedAccess = jwt.verify(newAccessToken, secret);
            const decodedRefresh = jwt.verify(newRefreshToken, secret);
            const decodedAccessToken = decodedAccess as DecodedToken;
            const decodedRefreshToken = decodedRefresh as DecodedToken;
            accessExp = decodedAccessToken.exp;
            refreshExp = decodedRefreshToken.exp;
            console.log("JWT_TOKEN_PARSING_EXP:", [accessExp, refreshExp]);
          } catch (err) {
            console.log("JWT_TOKEN_PARSING_ERR:", err);
          }
          if (response.ok) {
            // 요청이 성공했을 경우 토큰들을 쿠키에 set
            setToken("ACCESS_TOKEN", newTokens.access_token, accessExp);
            setToken("REFRESH_TOKEN", newTokens.refresh_token, refreshExp);
            token = appCookies.get("ACCESS_TOKEN");
            headers.set("Authorization", `Bearer ${token}`);
          } else {
            // 요청이 실패하면 로그아웃시키기
            removeTokenAll();
            clearAuth();
          }
        }
      } else {
        // 만약 accessToken이 존재하면 이 토큰을 헤더에 담아 보내기
        headers.set("Authorization", `Bearer ${token}`);
      }
      // 액세스, 리프레시 토큰 모두 없다면 일반 헤더로 보냄.
      // headers에 지정된 헤더값들을 추가
      const addHeaders = option.headers || {};
      Object.keys(addHeaders).forEach((key) => {
        headers.set(key, addHeaders[key]);
      });
      return headers;
    },
    ...option,
  });
};
