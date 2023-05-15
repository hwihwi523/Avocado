import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Member } from "./authSlice";
import { HYDRATE } from "next-redux-wrapper";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";

const API_URL = process.env.NEXT_PUBLIC_API_URL
  ? process.env.NEXT_PUBLIC_API_URL
  : "";

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
  grade?: number;
  picture_url?: string;
  gender?: string;
  age_group?: number;
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

export interface SellerLoginResponse {
  access_token: string;
  refresh_token: string;
  email: string;
  name: string;
}

export interface MemberLoginResponse {
  provider: string;
  url: string;
}

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: customFetchBaseQuery({
    baseUrl: API_URL + "/member",
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
    Login: builder.mutation<MemberLoginResponse, LoginRequest>({
      query: (body) => ({
        url: `/consumer/oauth2/${body.provider}`,
        method: "GET",
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
        url: "/refresh",
        method: "GET",
      }),
    }),
  }),
});

export const { useLoginMutation, useSellerLoginMutation, useRefreshAuthQuery } =
  authApi;
