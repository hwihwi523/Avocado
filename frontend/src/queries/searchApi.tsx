// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const API_URL = process.env.NEXT_PUBLIC_SEARCH_API_URL;



type Recommands = { name: string };

type PostsList =  {
  "id": number,
  "group_id": number,
  "name": string
  "imgurl": string,
  "inventory": number,
  "created_at": string,
  "updated_at": string
  "type_kor": string,
  "type_eng": string
  "age_gender_group_kor": string
  "age_gender_group_eng": string
  "usage_kor": string
  "usage_eng": string
  "price": number
  "discounted_price":number
  "season": string,
  "store_name": string
  "fashion_year": number
  "color_kor": string
  "color_eng": string
  "sub_color1_kor": string
  "sub_color1_eng": string
  "sub_color2_kor":string
  "sub_color2_eng": string
  "total_score": number,
  "review_count": number,
  "category_kor": string,
  "category_eng": string
}




export const searchApi = createApi({
  reducerPath: "searchApi", //이게 뭐지..?
  baseQuery: fetchBaseQuery({ baseUrl: API_URL }),
  tagTypes: ["products","recommands"],
  endpoints: (build) => ({
    getProductList: build.query<PostsList[], string>({
      query: (keyword) => ({
        url:"/products",
        method:"GET",
        params:{
          keyword,
        }
      }),
      //테그 설정
      providesTags: (result) =>
        result
          ? //성공시 설정할 태그
            [
              ...result.map(({ id }) => ({ type: "products", id } as const)),
              { type: "products", id: "LIST" },
            ]
          : // 실패할시 설정할 태그들 
            [{ type: "products", id: "LIST" }],
    }),
   
    getRecommands: build.query<Recommands[], string>({
      query: (keyword) => ({
        url:'/recommands',
        method:"GET",
        params:{keyword}
      }),
      providesTags: (result) =>
        result
          ? // 성공시 설정할 태그
            [
              ...result.map(({ name }) => ({ type: "recommands", name } as const)),
              { type: "recommands", id: "LIST" },
            ]
          : // 실패시 설정할 태그
            [{ type: "recommands", id: "LIST" }],
    }),
  }),
});

export const {
useGetRecommandsQuery,
useGetProductListQuery
} = searchApi;
