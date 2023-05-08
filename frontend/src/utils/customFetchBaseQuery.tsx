import { fetchBaseQuery } from "@reduxjs/toolkit/dist/query";
import { appCookies } from "../pages/_app";
import jwt from "jsonwebtoken";
import { DecodedToken } from "../features/auth/authApi";
import { setToken } from "./tokenManager";

const API_URL = process.env.NEXT_PUBLIC_MEMBER_API_URL
  ? process.env.NEXT_PUBLIC_MEMBER_API_URL
  : "";
const SECRET = process.env.JWT_SECRET ? process.env.JWT_SECRET : "";

interface RefreshResponse {
  access_token: string;
  refresh_token: string;
}

// 각 API 요청 시 token을 헤더에 담아 보내기 위한 커스텀 함수(검증이 필요한 경우 사용)
export function customFetchBaseQuery(option: { baseUrl: string }) {
  return fetchBaseQuery({
    baseUrl: option.baseUrl,
    prepareHeaders: async (headers) => {
      let token = appCookies.get("ACCESS_TOKEN");
      if (token) {
        // 토큰 파싱(유효기간 구하기)
        const secret = SECRET;
        let exp = 0;
        try {
          const decoded = jwt.verify(token, secret);
          const decodedToken = decoded as DecodedToken;
          exp = decodedToken.exp;
          console.log("JWT_TOKEN_PARSING_EXP:", exp);
        } catch (err) {
          console.log("JWT_TOKEN_PARSING_ERR:", err);
        }
        const isExpired = new Date(exp).getTime() <= new Date().getTime();
        if (isExpired) {
          let refreshToken = appCookies.get("REFRESH_TOKEN");
          // 새로운 access token 가져오기
          const response = await fetch(API_URL + "/tokens/refresh", {
            method: "GET",
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          });
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
            // 요청이 성공했을 경우 토큰들을 set
            setToken("ACCESS_TOKEN", newTokens.access_token, accessExp);
            setToken("REFRESH_TOKEN", newTokens.refresh_token, refreshExp);
            token = appCookies.get("ACCESS_TOKEN");
          } else {
            // 요청이 실패하면 로그아웃시키기
          }
        }
        return {
          ...headers,
          Authorization: `Bearer ${token}`,
        };
      }
      // 토큰 검증
      const response = await fetch("");

      return headers;
    },
  });
}
