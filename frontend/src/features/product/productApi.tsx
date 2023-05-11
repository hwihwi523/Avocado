import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { HYDRATE } from "next-redux-wrapper";
import { Product } from "./productSlice";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

//store={store_name}&category={category_id}&last={merchandise_id}&size={size}
interface ProductListRequest {
  store?: string; // store_name
  category?: number; // category_id
  size?: number; // 가져올 수
}

interface ProductListResponse {
  status: string;
  endpointName: string;
  requestId: string;
  originalArgs: Object;
  startedTimeStamp: number;
  data: Data;
}

interface Data {
  content: Product[];
}

export const productApi = createApi({
  reducerPath: "productApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL + "/merchandise" }),
  extractRehydrationInfo(action, { reducerPath }) {
    if (action.type === HYDRATE) {
      return action.payload[reducerPath];
    }
  },
  tagTypes: [],
  endpoints: (builder) => ({
    getProductList: builder.query<ProductListResponse, ProductListRequest>({
      query: (params) => ({
        url: "/merchandises?",
        method: "GET",
        params,
      }),
    }),
  }),
});

export const { useGetProductListQuery } = productApi;
