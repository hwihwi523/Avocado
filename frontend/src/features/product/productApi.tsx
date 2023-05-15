import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { HYDRATE } from "next-redux-wrapper";
import {
  Product,
  ProductDetail,
  ProductForCart,
  ProductForWishlist,
  ProductReview,
} from "./productSlice";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { headers } from "next/dist/client/components/headers";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

interface ProductBaseResponse {
  message: string;
  data: any;
}

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

// /wishlists -> 조회, 등록, 삭제
interface GetWishlistRequest {
  token?: string;
}
interface GetWishlistResponse extends ProductBaseResponse {
  data: ProductForWishlist[] | [];
}
interface UpdateWishlistResponse extends ProductBaseResponse {
  data: null;
}
interface GetIsWishlistRequest {
  merchandise_name: string;
}
interface GetIsWishlistResponse extends ProductBaseResponse {
  data: boolean;
}

// /carts -> 조회, 등록, 삭제
interface GetCartResponse extends ProductBaseResponse {
  data: ProductForCart[] | [];
}
interface AddCartRequest {
  merchandise_id: number;
  size: string;
  quantity: number;
}

export const productApi = createApi({
  reducerPath: "productApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL + "/merchandise" }),
  extractRehydrationInfo(action, { reducerPath }) {
    if (action.type === HYDRATE) {
      return action.payload[reducerPath];
    }
  },
  tagTypes: ["ProductList", "ProductDetail", "ProductReviews"],
  endpoints: (builder) => ({
    getProductList: builder.query<ProductListResponse, ProductListRequest>({
      query: (params) => ({
        url: "/merchandises?",
        method: "GET",
        params,
      }),
      providesTags: ["ProductList"],
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
      providesTags: (result, error, id) => [{ type: "ProductReviews", id }],
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
        url: `/merchandises/${payload.productId}/reviews`,
        method: "DELETE",
        body: {
          review_id: payload.reviewId,
        },
      }),
    }),
    getWishlist: builder.query<ProductForWishlist[], void>({
      query: () => ({
        url: `/wishlists`,
        // 서버단에서 토큰을 넣어주기 위해 필요
        // headers: payload.token
        //   ? { Authorization: `Bearer ${payload.token}` }
        //   : undefined,
      }),
      transformResponse: (response: GetWishlistResponse) => {
        return response.data;
      },
    }),
    AddWishlist: builder.mutation<UpdateWishlistResponse, number>({
      query: (productId) => ({
        url: `/wishlists`,
        method: "POST",
        body: { merchandise_id: productId },
      }),
    }),
    RemoveWishlist: builder.mutation<UpdateWishlistResponse, number>({
      query: (productId) => ({
        url: `/wishlists`,
        method: "DELETE",
        body: { merchandise_id: productId },
      }),
    }),
    getIsWishlist: builder.query<GetIsWishlistResponse, GetIsWishlistRequest>({
      query: (params) => ({ url: "/wishlists/exists", method: "GET", params }),
    }),
    getCart: builder.query<ProductForCart[], void>({
      query: () => `/cart`,
      transformResponse: (response: GetCartResponse) => {
        return response.data;
      },
    }),
    AddCart: builder.mutation<ProductBaseResponse, AddCartRequest>({
      query: (payload) => ({
        url: `/cart`,
        method: "POST",
        body: payload,
      }),
    }),
    RemoveCart: builder.mutation<ProductBaseResponse, number>({
      query: (cartId) => ({
        url: `/cart`,
        method: "DELETE",
        body: {
          cart_id: cartId,
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
  useGetWishlistQuery,
  useAddWishlistMutation,
  useRemoveWishlistMutation,
  useGetIsWishlistQuery,
  useGetCartQuery,
  useAddCartMutation,
  useRemoveCartMutation,
} = productApi;
