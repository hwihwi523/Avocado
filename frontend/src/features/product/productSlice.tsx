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

interface ProductState {
  selectedProduct: Product | null;
  productListBySearch: Product[];
  productListByMbti: Product[];
  productListByPersonalColor: Product[];
}

const initialState: ProductState = {
  selectedProduct: null,
  productListBySearch: [],
  productListByMbti: [],
  productListByPersonalColor: [],
};

export const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setSelectedProduct: (state, action: PayloadAction<Product>) => {
      state.selectedProduct = action.payload;
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
    clearSelectedProduct: (state) => {
      state.selectedProduct = null;
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
  setSelectedProduct,
  setProductListBySearch,
  setProductListByMbti,
  setProductListByPersonalColor,
  clearSelectedProduct,
  clearProductListAll,
} = productSlice.actions;
