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

// /wishlists -> 조회, 등록, 삭제
interface GetWishlistResponse {
  message: string;
  data: Product[] | [];
}
interface UpdateWishlistResponse {
  message: string;
  data: IsWishlist;
}
type IsWishlist = {
  is_wishlist: boolean;
};

// /carts -> 조회, 등록, 삭제
interface GetCartResponse {
  message: string;
  data: Product[] | [];
}
interface UpdateCartResponse {
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
      providesTags: (result, error, id) => [{ type: "ProductDetail", id }],
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
    getWishlist: builder.query<Product[], void>({
      query: () => `/wishlists`,
      transformResponse: (response: GetWishlistResponse) => {
        return response.data;
      },
    }),
    AddWishlist: builder.mutation<UpdateWishlistResponse, void>({
      query: () => ({
        url: `/wishlists`,
        method: "POST",
      }),
    }),
    RemoveWishlist: builder.mutation<UpdateWishlistResponse, void>({
      query: () => ({
        url: `/wishlists`,
        method: "DELETE",
      }),
    }),
    getCart: builder.query<Product[], void>({
      query: () => `/cart`,
      transformResponse: (response: GetCartResponse) => {
        return response.data;
      },
    }),
    AddCart: builder.mutation<UpdateCartResponse, void>({
      query: () => ({
        url: `/cart`,
        method: "POST",
      }),
    }),
    RemoveCart: builder.mutation<UpdateCartResponse, void>({
      query: () => ({
        url: `/cart`,
        method: "DELETE",
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
  useGetCartQuery,
  useAddCartMutation,
  useRemoveCartMutation,
} = productApi;
