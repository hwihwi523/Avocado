// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
import { createApi } from "@reduxjs/toolkit/query/react";
const API_URL = process.env.NEXT_PUBLIC_API_URL;

type ResponseType = {
  msg: string;
  status: number;
};

type RequestType = {
  gender: String;
  age_group: number; // 10, 20, 30, 40, 50, 60, 70. null 불가
  height: number | null; // 0 이상. null 가능
  weight: number | null; // 0 이상. null 가능
  mbti_id: number | null; // 0 이상 15이하
  personal_color_id: number | null; // 0이상 9이하
};

export const memberInfoApi = createApi({
  reducerPath: "memberApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL! + "/member" }),
  tagTypes: ["member"],
  endpoints: (build) => ({
    addMemberInfo: build.mutation<ResponseType, RequestType>({
      query(body) {
        return {
          url: "/consumer",
          method: "PUT",
          body,
        };
      },
    }),
  }),
});

export const { useAddMemberInfoMutation } = memberInfoApi;
