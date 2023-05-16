import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { HYDRATE } from "next-redux-wrapper";

export type ExposeCommercialItem = {
  imgurl: string;
  merchandise_id: number;
};

export type CommercialState = {
  popupCommercialList: ExposeCommercialItem[];
  carouselCommercialList: ExposeCommercialItem[];
};

const initialState: CommercialState = {
  popupCommercialList: [],
  carouselCommercialList: [],
};

export const commercialSlice = createSlice({
  name: "commercial",
  initialState,
  reducers: {
    //입력 함수
    setPopupCommercialList: (
      state,
      action: PayloadAction<ExposeCommercialItem[]>
    ) => {
      state.popupCommercialList = action.payload; //넣어도 원본 데이터를 훼손하는게 아니다
    },
    setCarouselCommercialList: (
      state,
      action: PayloadAction<ExposeCommercialItem[]>
    ) => {
      state.carouselCommercialList = action.payload;
    },

    //초기화 함수
    clearPopupCommercialList: (state) => {
      state.popupCommercialList = []; //넣어도 원본 데이터를 훼손하는게 아니다
    },
    clearCarouselCommercialList: (state) => {
      state.carouselCommercialList = [];
    },
    clearAllCommercialList: (state) => {
      state.carouselCommercialList = [];
    },
  },

  extraReducers:{
    [HYDRATE]:(state,action)=>{
        return {
            ...state,
            ...action.payload.commercial
        }
    }
  }

});

export const{
    setCarouselCommercialList,
    setPopupCommercialList,
    clearAllCommercialList,
    clearCarouselCommercialList,
    clearPopupCommercialList
} = commercialSlice.actions


