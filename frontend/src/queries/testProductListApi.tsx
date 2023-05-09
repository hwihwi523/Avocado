import {
  BaseQueryFn,
  createApi,
  fetchBaseQuery,
} from "@reduxjs/toolkit/query/react";
import { HYDRATE } from "next-redux-wrapper";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export interface TestProduct {
  api_featured_image: string;
  brand: string;
  category: string;
  created_at: string;
  currency: null | string;
  description: string;
  id: number;
  image_link: string;
  name: string;
  price: string;
  price_sign: null | string;
  product_api_url: string;
  product_colors: any[];
  product_link: string;
  product_type: string;
  rating: number;
  tag_list: string[];
  updated_at: string;
  website_link: string;
}

interface TestProductsApiResponse {
  data: TestProduct[];
}

export const testProductListApi = createApi({
  reducerPath: "testProductsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: API_URL,
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
    getTestProducts: builder.query<TestProduct[], void>({
      query: () => "",
    }),
  }),
});

// Export hooks for usage in functional components
export const {
  useGetTestProductsQuery,
  util: { getRunningQueriesThunk },
} = testProductListApi;

// export endpoints for use in SSR
export const { getTestProducts } = testProductListApi.endpoints;
