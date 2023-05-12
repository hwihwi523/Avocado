import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { HYDRATE } from "next-redux-wrapper";
import { Product, ProductDetail, ProductReview } from "./productSlice";
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

// /merchandises/{merchandise_id}?user_id={user_id}
interface ProductDetailResponse {
  message: string;
  data: ProductDetail;
}

// /merchandises/{merchandise_id}/reviews
interface ProductReviewsResponse {
  message: string;
  data: ProductReview[];
}

// /merchandises/{merchandise_id}/reviews
interface RegistProductReviewRequest {
  productId: number;
  score: number;
  content: string;
}

interface RegistProductReviewResponse {
  message: string;
}

// /merchandises/{merchandise_id}/reviews
interface RemoveProductReviewRequest {
  productId: number;
  reviewId: number;
}

interface RemoveProductReviewResponse {
  message: string;
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
    getProductDetail: builder.query<ProductDetail, string>({
      query: (id) => `/merchandises/${id}`,
      transformResponse: (response: ProductDetailResponse) => {
        // response 변환
        return response.data;
      },
    }),
    getProductReviews: builder.query<ProductReview[], string>({
      query: (id) => `/merchandises/${id}/reviews`,
      transformResponse: (response: ProductReviewsResponse) => {
        return response.data;
      },
    }),
    registProductReview: builder.mutation<
      RegistProductReviewResponse,
      RegistProductReviewRequest
    >({
      query: (payload) => ({
        url: `/merchandises/${payload.productId}/reviews`,
        method: "POST",
        body: {
          score: payload.score,
          content: payload.content,
        },
      }),
    }),
    removeProductReview: builder.mutation<
      RemoveProductReviewResponse,
      RemoveProductReviewRequest
    >({
      query: (payload) => ({
        url: `/mkerchandises/${payload.productId}/reviews`,
        method: "DELETE",
        body: {
          review_id: payload.reviewId,
        },
      }),
    }),
  }),
});

export const {
  useGetProductListQuery,
  useGetProductDetailQuery,
  useGetProductReviewsQuery,
  useRegistProductReviewMutation,
  useRemoveProductReviewMutation,
} = productApi;
