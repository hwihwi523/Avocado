import { fetchBaseQuery } from "@reduxjs/toolkit/dist/query";
import { appCookies } from "../pages/_app";

// 각 API 요청 시 token을 헤더에 담아 보내기 위한 커스텀 함수(로그인 외 RTK Query 작성 시 사용)
export function customFetchBaseQuery(option: { baseUrl: string }) {
  return fetchBaseQuery({
    baseUrl: option.baseUrl,
    prepareHeaders: async (headers) => {
      const accessToken = appCookies.get("ACCESS_TOKEN");
      if (accessToken) {
        return {
          ...headers,
          Authorization: `Bearer ${accessToken}`,
        };
      }
      // 토큰 검증
      const response = await fetch("");

      return headers;
    },
  });
}
