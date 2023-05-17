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
import { ProductItem } from "./../statistic/statisticSlice";

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
interface GetProductDetailWithServerRequest {
  productId: string;
  token: string;
}
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

interface GetRecentlyViewResponse extends ProductBaseResponse {
  data: Product[] | [];
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

// /recommend -> 게스트 추천
interface GetGuestRecommendResponse extends ProductBaseResponse {
  data: Content;
}
type Content = {
  content: ProductItem[];
};

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
    getGuestRecommendProductList: builder.query<ProductItem[], void>({
      query: () => ({
        url: "/merchandises/guest",
        params: {
          size: 10,
        },
      }),
      transformResponse: (response: GetGuestRecommendResponse) => {
        return response.data.content;
      },
    }),
    getProductDetail: builder.query<ProductDetail, string>({
      query: (productId) => `/merchandises/${productId}`,
      transformResponse: (response: ProductDetailResponse) => {
        // response 변환
        return response.data;
      },
    }),
    getProductDetailWithServer: builder.query<
      ProductDetail,
      GetProductDetailWithServerRequest
    >({
      query: (body) => ({
        url: `/merchandises/${body.productId}`,
        headers: {
          Authorization: `Bearer ${body.token}`,
        },
      }),
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
    addWishlist: builder.mutation<UpdateWishlistResponse, number>({
      query: (productId) => ({
        url: `/wishlists`,
        method: "POST",
        body: { merchandise_id: productId },
      }),
    }),
    removeWishlist: builder.mutation<UpdateWishlistResponse, number>({
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
      query: () => `/carts`,
      transformResponse: (response: GetCartResponse) => {
        return response.data;
      },
    }),
    addCart: builder.mutation<ProductBaseResponse, AddCartRequest>({
      query: (payload) => ({
        url: `/carts`,
        method: "POST",
        body: payload,
      }),
    }),
    removeCart: builder.mutation<ProductBaseResponse, number>({
      query: (cartId) => ({
        url: `/carts`,
        method: "DELETE",
        body: {
          cart_id: cartId,
        },
      }),
    }),
    getRecentlyViewProductsList: builder.query<GetRecentlyViewResponse, void>({
      query: () => ({
        url: "merchandises/recents",
        method: "GET",
        transformResponse: (response: GetRecentlyViewResponse) => {
          return response.data;
        },
      }),
    }),
  }),
});

export const {
  useGetProductListQuery,
  useGetGuestRecommendProductListQuery,
  useGetProductDetailQuery,
  useGetProductDetailWithServerQuery,
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
  useGetRecentlyViewProductsListQuery,
} = productApi;
