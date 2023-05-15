// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { createApi } from "@reduxjs/toolkit/query/react";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
const API_URL = process.env.NEXT_PUBLIC_API_URL;

export type ProductItem = {
  brand_name: string;
  merchandise_id: number;
  merchandise_category: string;
  price: number;
  discounted_price: number;
  mbti: number | null;
  personal_color: number | null;
  age_group: string | number | null;
  image_url: string;
  score: number;
};

export type ResponseProductListType = {
  message: string;
  is_last_page: boolean;
  last_id: number;
  last_date: null; // 이게 뭐지?
  data: ProductItem[];
};

export type RequestProductListType = {
  provider_id: number;
  category: string | null; //
  last: number | null; // 받아온 last_id 넘겨주면됨
  size: number;
};

export const sellerApi = createApi({
  reducerPath: "sellerApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL! + "/merchandise" }),
  tagTypes: ["seller"],
  endpoints: (build) => ({
    //카테고리별 제품 리스트
    getProductListByCategory: build.query<
      ResponseProductListType,
      RequestProductListType
    >({
      query: (params) => ({
        url: "/",
        method: "GET",
        params,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.data.map(
                ({ merchandise_id }) =>
                  ({ type: "seller", id: merchandise_id } as const)
              ),
              { type: "seller", id: "LIST" },
            ]
          : [{ type: "seller", id: "LIST" }],
    }),

    // mutation 예제
    // //제품 삭제
    // removeCommercial: build.mutation<ResponseType, number>({
    //   query: (commercial_id: number) => ({
    //     url: "/ads",
    //     method: "DELETE",
    //     params: {
    //       commercial_id,
    //     },
    //   }),
    //   invalidatesTags: [{ type: "commercials", id: "L" }],
    // }),
  }),
});

export const { useGetProductListByCategoryQuery } = sellerApi;
