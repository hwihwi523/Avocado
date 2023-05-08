import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Member } from "./authSlice";
import { HYDRATE } from "next-redux-wrapper";

const MEMBER_API_URL = process.env.NEXT_PUBLIC_MEMBER_API_URL;

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
    // prepareHeaders: (headers, { getState }) => {
    //   const accessToken = "";
    //   if (accessToken) {
    //     headers.set("authorization", `Bearer ${accessToken}`);
    //   }
    //   return headers;
    // },
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
  }),
});

export const { useLoginMutation, useSellerLoginMutation } = authApi;
