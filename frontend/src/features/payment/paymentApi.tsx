import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { createApi } from "@reduxjs/toolkit/query/react";
import { HYDRATE } from "next-redux-wrapper";
import { ProductForPayment } from "./paymentSlice";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export interface StartPaymentRequest {
  total_price: number;
  success_url: string;
  fail_url: string;
  merchandises: ProductForPayment[];
}

interface StartPaymentResponse {
  message: string;
  data: {
    next_redirect_mobile_url: string; // 바로 카카오톡으로 연결돼서 PC로는 결제 불가
    next_redirect_pc_url: string; // QR 코드가 표시되고 스캔해서 결제
  };
}

export const paymentApi = createApi({
  reducerPath: "paymentApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL + "/payment" }),
  extractRehydrationInfo(action, { reducerPath }) {
    if (action.type === HYDRATE) {
      return action.payload[reducerPath];
    }
  },
  tagTypes: [],
  endpoints: (builder) => ({
    startPayment: builder.mutation<StartPaymentResponse, StartPaymentRequest>({
      query: (body) => ({
        url: "/ready",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const { useStartPaymentMutation } = paymentApi;
