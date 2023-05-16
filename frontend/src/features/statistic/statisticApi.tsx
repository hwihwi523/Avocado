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
      GetStatisticDataForProductDetailResponse,
      number
    >({
      query: (productId) => `/merchandises/${productId}`,
    }),
    getStatisticDataForPersonalRecoimmendation: builder.query<
      GetStatisticDataForPersonalRecommendationResponse,
      void
    >({
      query: () => `/recommend`,
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
  useGetStatisticDataForPersonalRecoimmendationQuery,
  useGetStatisticDataForProviderQuery,
} = statisticApi;
