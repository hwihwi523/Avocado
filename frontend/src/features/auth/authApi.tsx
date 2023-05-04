import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Member, authSlice } from "./authSlice";
import { HYDRATE } from "next-redux-wrapper";

const MEMBER_API_URL = process.env.NEXT_PUBLIC_MEMBER_API_URL;

interface LoginRequest {
  provider: string;
}

interface SellerLoginRequest {
  email: string;
  password: string;
}

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({ baseUrl: MEMBER_API_URL }),
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
    sellerLogin: builder.mutation<Member, SellerLoginRequest>({
      query: (body) => ({
        url: "/provider/login",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const { useLoginMutation, useSellerLoginMutation } = authApi;
