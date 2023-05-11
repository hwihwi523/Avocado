import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { HYDRATE } from "next-redux-wrapper";

export interface Product {
  id: number;
  brand_name: string;
  merchandise_id: number;
  merchandise_category: string;
  image_url: string;
  merchandise_name: string;
  price: number;
  discounted_price: number;
  score: number;
  mbti?: string | null;
  personal_color?: string | null;
  age_group?: string | null;
}

export interface ProductDetail {
  id: number;
  brand_name: string;
  merchandise_id: number;
  merchandise_category: string;
  images: string[];
  merchandise_name: string;
  price: number;
  discounted_price: number;
  score: number;
  description: string;
  mbti?: string | null;
  personal_color?: string | null;
  age_group?: string | null;
  is_purchased: boolean; // 사용자가 해당 상품을 구매했는지 여부 (로그인 안 했으면 false)
  is_reviewed: boolean; // 사용자가 해당 상품에 리뷰를 남겼는지 여부 (로그인 안 했으면 false)
}

interface ProductState {
  selectedProductDetail: ProductDetail | null;
  productListBySearch: Product[];
  productListByMbti: Product[];
  productListByPersonalColor: Product[];
}

const initialState: ProductState = {
  selectedProductDetail: null,
  productListBySearch: [],
  productListByMbti: [],
  productListByPersonalColor: [],
};

export const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setSelectedProductDetail: (state, action: PayloadAction<ProductDetail>) => {
      state.selectedProductDetail = action.payload;
    },
    setProductListBySearch: (state, action: PayloadAction<Product[]>) => {
      state.productListBySearch = action.payload;
    },
    setProductListByMbti: (state, action: PayloadAction<Product[]>) => {
      state.productListByMbti = action.payload;
    },
    setProductListByPersonalColor: (
      state,
      action: PayloadAction<Product[]>
    ) => {
      state.productListByPersonalColor = action.payload;
    },
    clearSelectedProductDetail: (state) => {
      state.selectedProductDetail = null;
    },
    clearProductListAll: (state) => {
      state.productListBySearch = [];
      state.productListByMbti = [];
      state.productListByPersonalColor = [];
    },
  },
  extraReducers: {
    [HYDRATE]: (state, action) => {
      console.log("HYDRATE-PRODUCT", state, action.payload);
      return {
        ...state,
        ...action.payload.product,
      };
    },
  },
});

export const {
  setSelectedProductDetail,
  setProductListBySearch,
  setProductListByMbti,
  setProductListByPersonalColor,
  clearSelectedProductDetail,
  clearProductListAll,
} = productSlice.actions;
