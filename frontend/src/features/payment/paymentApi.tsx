import { createApi } from "@reduxjs/toolkit/dist/query";
import { ProductForPayment } from "./paymentSlice";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { HYDRATE } from "next-redux-wrapper";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export interface PaymentRequest {
  total_price: number;
  merchandises: ProductForPayment[];
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
  endpoints: (builder) => ({}),
});

export const {} = paymentApi;
