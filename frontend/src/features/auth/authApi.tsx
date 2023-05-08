import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Member } from "./authSlice";
import { HYDRATE } from "next-redux-wrapper";

const MEMBER_API_URL = process.env.NEXT_PUBLIC_MEMBER_API_URL;

interface CheckAuthResponse {
  isAuth: boolean;
}

// refresh token 안에 포함된 내용
export interface DecodedToken {
  iss: string; // 발행자
  iat: number; // 발행 시간
  exp: number; // 만료 시간
  type: string; // 일반 사용자 or 판매자
  id: string;
  email: string;
  name: string;
  picture_url?: string;
  gender?: string;
  age?: number;
  height?: number;
  weight?: number;
  mbti_id?: number;
  personal_color_id?: number;
}

interface LoginRequest {
  provider: string;
}

interface SellerLoginRequest {
  email: string;
  password: string;
}

interface SellerLoginResponse {
  access_token: String;
  refresh_token: String;
  email: String;
  name: String;
}

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: MEMBER_API_URL,
  }),
  // next.js에서 rtk 쿼리를 사용하기 위한 hydrate 과정
  extractRehydrationInfo(action, { reducerPath }) {
    if (action.type === HYDRATE) {
      return action.payload[reducerPath];
    }
  },
  // 태그
  tagTypes: [],
  endpoints: (builder) => ({
    Login: builder.mutation<Member, LoginRequest>({
      query: (body) => ({
        url: "",
        method: "POST",
        body,
      }),
    }),
    sellerLogin: builder.mutation<SellerLoginResponse, SellerLoginRequest>({
      query: (body) => ({
        url: "/provider/login",
        method: "POST",
        body,
      }),
    }),
    refreshAuth: builder.query<CheckAuthResponse, void>({
      query: () => ({
        url: "/tokens/refresh",
        method: "GET",
      }),
    }),
  }),
});

export const { useLoginMutation, useSellerLoginMutation, useRefreshAuthQuery } =
  authApi;
