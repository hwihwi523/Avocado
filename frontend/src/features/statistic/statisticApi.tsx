import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { createApi } from "@reduxjs/toolkit/dist/query/react";
import { HYDRATE } from "next-redux-wrapper";
import {
  StatisticDataForPersonalRecommendation,
  StatisticDataForProductDetail,
  StatisticDataForProvider,
  StatisticDataForContumer,
} from "./statisticSlice";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

interface StatisticBaseResponse {
  message: string;
  data: any;
}

//상품 디테일 화면 통계
interface GetStatisticDataForProductDetailResponse
  extends StatisticBaseResponse {
  data: StatisticDataForProductDetail;
}

//메인페이지 추천 상품
interface GetStatisticDataForPersonalRecommendationResponse
  extends StatisticBaseResponse {
  data: StatisticDataForPersonalRecommendation;
}

//판매자 통계
interface GetStatisticDataForProviderResponse extends StatisticBaseResponse {
  data: StatisticDataForProvider;
}

//구매자 통계
interface GetStatisticDataForConsumerResponse extends StatisticBaseResponse {
  data: StatisticDataForContumer;
}

export const statisticApi = createApi({
  reducerPath: "statisticApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL + "/statistics" }),
  extractRehydrationInfo(action, { reducerPath }) {
    if (action.type === HYDRATE) {
      return action.payload[reducerPath];
    }
  },
  tagTypes: [],
  keepUnusedDataFor: 5 * 60 * 1000,
  endpoints: (builder) => ({
    getStatisticDataForProductDetail: builder.query<
      StatisticDataForProductDetail,
      number
    >({
      query: (productId) => `/merchandises/${productId}`,
      transformResponse: (
        response: GetStatisticDataForProductDetailResponse
      ) => {
        // response 변환
        return response.data;
      },
    }),
    getStatisticDataForPersonalRecommendation: builder.query<
      GetStatisticDataForPersonalRecommendationResponse,
      string
    >({
      // query: () => `/recommend`,
      query: (accessToken) => ({
        url: "/recommend",
        method: "GET",
        // 서버단에서 토큰을 넣어주기 위해 필요
        headers: accessToken
          ? { Authorization: `Bearer ${accessToken}` }
          : undefined,
        transformResponse: (
          response: GetStatisticDataForProductDetailResponse
        ) => {
          // response 변환
          return response.data;
        },
      }),
    }),
    getStatisticDataForProvider: builder.query<
      GetStatisticDataForProviderResponse,
      void
    >({
      query: () => ({
        url: "/provider",
        method: "GET",
      }),
    }),

    getStatisticDataForConsumer: builder.query<
      GetStatisticDataForConsumerResponse,
      string
    >({
      query: (accessToken) => ({
        url: "/consumer",
        method: "GET",
        // 서버단에서 토큰을 넣어주기 위해 필요
        headers: accessToken
          ? { Authorization: `Bearer ${accessToken}` }
          : undefined,
      }),
    }),
  }),
});

export const {
  useGetStatisticDataForProductDetailQuery,
  useGetStatisticDataForPersonalRecommendationQuery,
  useGetStatisticDataForProviderQuery,
  useLazyGetStatisticDataForConsumerQuery,
} = statisticApi;
