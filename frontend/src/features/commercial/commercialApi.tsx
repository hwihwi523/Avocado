// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { createApi } from "@reduxjs/toolkit/query/react";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
const API_URL = process.env.NEXT_PUBLIC_API_URL;

//광고 신청 타입
export type addCommercialRequestType = {
  merchandise_id: number;
  merchandise_name: string;
  mbti_id: number;
  personal_color_id: number;
  commercial_type_id: number; //0 팝업, 1 메인 캐러셀
  age: number;
  gender: string; // F M
  file: File; //광고 이미지
};

//응답
export type ResponseType = {
  msg: string;
};

//광고 요청하는 타입
export type exposeCommercialRequestType = {
  age: number;
  gender: string;
  mbti_id?: number;
  personal_color_id?: number;
};

//광고
export type commercialItem = {
  imgurl: string;
  merchandise_id: number;
};

export type commercialList = {
  msg: string;
  data: {
    popup: commercialItem;
    carousel_list: commercialItem[];
  };
};

export type analyseItem = {
  date: string;
  exposure_cnt: number;
  click_cnt: number;
  purshase_amount: number;
  quantity: number;
};

export type analysesList = {
  data: analyseItem[];
};

export type myCommercialItem = {
  id: number;
  age: number;
  gender: string;
  imgurl: string;
  merchandiseName: string;
  commercialTypeId: number;
  mbtiId: number;
  personalColorId: number;
  createAt: number[];
  merchandiseId: number;
  providerId: string;
};

export type myCommercialList = {
  data: myCommercialItem[];
};

export const commercialApi = createApi({
  reducerPath: "commercialApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL! + "/commercial" }),
  tagTypes: ["commercials"],
  endpoints: (build) => ({
    //광고 등록
    addCommercial: build.mutation<ResponseType, addCommercialRequestType>({
      query: (body: addCommercialRequestType) => ({
        url: "/ads",
        method: "POST",
        body,
      }),
      invalidatesTags: [{ type: "commercials", id: "LIST" }],
    }),

    //메인페이지 광고 노출
    getExpostCommercialList: build.query<commercialList,exposeCommercialRequestType>({
      query: (params: exposeCommercialRequestType) => ({
        url: "/ads",
        method: "GET",
        params,
      }),
      providesTags: () => [{ type: "commercials", id: "LIST" }],
    }),

    //광고 하나의 통계
    getCommercialAnalyses: build.query<analysesList, number>({
      query: (commercial_id: number) => ({
        url: `/analyses/${commercial_id}`,
        method: "GET",
      }),
      //provideTag로 관리가 안됨,여기서 캐시를 조작할 수가 없음
    }),

    //내 광고 리스트
    getMyCommercialList: build.query<myCommercialList, void>({
      query: () => ({
        url: "/ads/registed",
        method: "GET",
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.data.map(
                ({ id }) => ({ type: "commercials", id } as const)
              ),
              { type: "commercials", id: "LIST" },
            ]
          : [{ type: "commercials", id: "LIST" }],
    }),

    //제품 삭제
    removeCommercial: build.mutation<ResponseType, number>({
      query: (commercial_id: number) => ({
        url: "/ads",
        method: "DELETE",
        params: {
          commercial_id,
        },
      }),
      invalidatesTags: [{ type: "commercials", id: "L" }],
    }),
  }),
});

export const {
  useGetCommercialAnalysesQuery,
  useGetExpostCommercialListQuery,
  useGetMyCommercialListQuery,
  useRemoveCommercialMutation,
  useAddCommercialMutation,
} = commercialApi;
