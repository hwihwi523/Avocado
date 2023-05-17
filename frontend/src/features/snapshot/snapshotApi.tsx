// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks
import { createApi } from "@reduxjs/toolkit/query/react";
import { customFetchBaseQuery } from "@/src/utils/customFetchBaseQuery";
const API_URL = process.env.NEXT_PUBLIC_API_URL;

export type Wear = {
  merchandise_id: number;
  name: string;
  imgurl: string;
};

//전체 스넵샷 불러올때
export type SnapshotRequestType = {
  lastId: number | null;
  size: number;
};

export type SnapshotItem = {
  my_styleshot: boolean; // 내 게시글인지 여부
  iliked: boolean; //내가 좋아요 눌렀는지 여부
  content: string;
  id: number;
  created_at: string;
  picture_url: string;
  total_likes: number;
  wears: Wear[];
  user_info: {
    gender: string;
    name: string;
    mbti_id: number;
    personal_color_id: number;
  };
};

export type SnapshotList = {
  last_id: number;
  last_page: boolean;
  styleshot_list: SnapshotItem[];
};

export type ResponseType = {
  status: number;
  msg: string;
};

export type RequestType = {
  content: string;
  picture: string;
  wears: number[];
};

//구매내역에서 가져올 값들
export type ProductItem = {
  brand_name: string;
  merchandise_id: number;
  merchandise_icategory: string;
  merchandise_name: string;
  price: number;
  discounted_price: number;
  mbti: string | null;
  personal_color: string | null;
  age_group: string;
  purchase_id: string;
  purchase_data: string;
  size: string;
};

//구매내역 조회시 반환될 값
export type OrderListResponse = {
  message: string;
  data: {
    content: ProductItem[];
    is_last_page: boolean;
    last_id: null;
    last_date: string; //무한 스크롤 사용시 해당정보 넘겨야함
  };
};

export const snapshotApi = createApi({
  reducerPath: "snapshotApi",
  baseQuery: customFetchBaseQuery({ baseUrl: API_URL! }),
  tagTypes: ["snapshot", "merchandise"],
  endpoints: (build) => ({
    //내 스넵샷 조회
    getMySnapshotList: build.query<SnapshotList, void>({
      query: () => ({
        url: "/community/my-styleshots",
        method: "GET",
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.styleshot_list.map(
                ({ id }) => ({ type: "snapshot", id } as const)
              ),
              { type: "snapshot", id: "LIST" },
            ]
          : [{ type: "snapshot", id: "LIST" }],
    }),

    //전체 스넵샷 조회
    getSnapshotList: build.query<SnapshotList, any>({
      query: (params) => ({
        url: "/community/styleshots",
        method: "GET",
        params,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.styleshot_list.map(
                ({ id }) => ({ type: "snapshot", id } as const)
              ),
              { type: "snapshot", id: "LIST" },
            ]
          : [{ type: "snapshot", id: "LIST" }],
    }),

    //스넵샷 삭제
    removeSnapshot: build.mutation<ResponseType, number>({
      query: (styleshot_id: number) => ({
        url: `/community/styleshots/${styleshot_id}`,
        method: "DELETE",
      }),
      invalidatesTags: [{ type: "snapshot", id: "LIST" }],
    }),

    //스넵샷 등록
    addSnapshot: build.mutation<ResponseType, any>({
      query: (body: any) => ({
        url: "/community/styleshots",
        method: "POST",
        body,
      }),
      invalidatesTags: [{ type: "snapshot", id: "LIST" }],
    }),

    //스넵샷 좋아요
    addSnapshotLike: build.mutation<ResponseType, number>({
      query: (styleshot_id: number) => ({
        url: `/community/styleshots/${styleshot_id}/like`,
        method: "POST",
      }),
      invalidatesTags: [{ type: "snapshot", id: "LIST" }],
    }),

    //스넵샷 좋아요 취소
    removeSnapshotLike: build.mutation<ResponseType, number>({
      query: (styleshot_id: number) => ({
        url: `/community/styleshots/${styleshot_id}/unlike`,
        method: "POST",
      }),
      invalidatesTags: [{ type: "snapshot", id: "LIST" }],
    }),

    //구매내역 => 스넵샷에 올릴 상품을 고르기 위해서는 구매내역이 필요하기 때문에 snapshotApi에 잠시 설정해둠
    getOrderList: build.query<OrderListResponse, void>({
      query: () => ({
        url: "/merchandise/merchandises/histories",
        method: "GET",
        params: {
          last_date: "2023-05-23T15:00:00", //전부다 불러올거기 때문에 임의로 설정해둠
          size: 100,
        },
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.data.content.map(
                ({ merchandise_id }) =>
                  ({ type: "merchandise", id: merchandise_id } as const)
              ),
              { type: "merchandise", id: "LIST" },
            ]
          : [{ type: "merchandise", id: "LIST" }],
    }),

    getSnapshotCntAndLikeCnt: build.query<
      { styleshot_cnt: number; like_cnt: number },
      void
    >({
      query: () => ({
        url: "community/my-statistics",
        method: "GET",
      }),
    }),
  }),
});

export const {
  useAddSnapshotMutation,
  useAddSnapshotLikeMutation,
  useGetMySnapshotListQuery,
  useGetSnapshotListQuery,
  useRemoveSnapshotLikeMutation,
  useRemoveSnapshotMutation,
  useGetOrderListQuery,
  useGetSnapshotCntAndLikeCntQuery,
} = snapshotApi;
