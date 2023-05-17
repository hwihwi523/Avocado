import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { createApi } from "@reduxjs/toolkit/dist/query/react";
import { HYDRATE } from "next-redux-wrapper";
import {
  StatisticDataForPersonalRecommendation,
  StatisticDataForProductDetail,
  StatisticDataForProvider,
} from "./statisticSlice";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

interface StatisticBaseResponse {
  message: string;
  data: any;
}

interface GetStatisticDataForProductDetailResponse
  extends StatisticBaseResponse {
  data: StatisticDataForProductDetail;
}

interface GetStatisticDataForPersonalRecommendationResponse
  extends StatisticBaseResponse {
  data: StatisticDataForPersonalRecommendation;
}

interface GetStatisticDataForProviderResponse extends StatisticBaseResponse {
  data: StatisticDataForProvider;
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
      }),
    }),
    getStatisticDataForProvider: builder.query<
      GetStatisticDataForProviderResponse,
      void
    >({
      query: () => `/provider`,
    }),
  }),
});

export const {
  useGetStatisticDataForProductDetailQuery,
  useGetStatisticDataForPersonalRecommendationQuery,
  useGetStatisticDataForProviderQuery,
} = statisticApi;
