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
  is_wishlist: boolean;
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
  inventory: number;
  score: number;
  description: string;
  mbti?: string | null;
  personal_color?: string | null;
  age_group?: string | null;
  is_purchased: boolean; // 사용자가 해당 상품을 구매했는지 여부 (로그인 안 했으면 false)
  is_reviewed: boolean; // 사용자가 해당 상품에 리뷰를 남겼는지 여부 (로그인 안 했으면 false)
  is_wishlist: boolean; // 사용자가 찜한 상품인지 여부
}

export interface ProductReview {
  id: number;
  reviewer: string;
  picture_url?: string | null; // 없으면 null
  mbti?: string | null; // 없으면 null
  personal_color?: string | null; // 없으면 null
  score: number;
  content: string;
  created_at: string;
}

export interface ProductForWishlist
  extends Omit<Product, "id" | "is_wishlist"> {
  wishlist_id: number;
}

export interface ProductForBuy {
  id: number;
  brand_name: string;
  merchandise_id: number;
  merchandise_category: string;
  images: string[];
  merchandise_name: string;
  price: number;
  discounted_price: number;
  size: string; // 구매할 사이즈
  count: number; // 구매할 수량
}

export interface ProductForCart extends Omit<Product, "id" | "is_wishlist"> {
  cart_id: number;
  size: string; // 구매할 사이즈
  count: number; // 구매할 수량
}

interface ProductState {
  selectedProductDetail: ProductDetail | null;
  productListBySearch: Product[];
  productListByMbti: Product[];
  productListByPersonalColor: Product[];
  productReiews: ProductReview[];
  productListForCart: ProductForCart[];
  productListForWishlist: ProductForWishlist[];
}

const initialState: ProductState = {
  selectedProductDetail: null,
  productListBySearch: [],
  productListByMbti: [],
  productListByPersonalColor: [],
  productReiews: [],
  productListForCart: [],
  productListForWishlist: [],
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
    setProductReviews: (state, action: PayloadAction<ProductReview[]>) => {
      state.productReiews = action.payload;
    },
    setProductListForCart: (state, action: PayloadAction<ProductForCart[]>) => {
      state.productListForCart = action.payload;
    },
    setProductListForWishlist: (
      state,
      action: PayloadAction<ProductForWishlist[]>
    ) => {
      state.productListForWishlist = action.payload;
    },
    clearSelectedProductDetail: (state) => {
      state.selectedProductDetail = null;
    },
    clearProductListAll: (state) => {
      state.productListBySearch = [];
      state.productListByMbti = [];
      state.productListByPersonalColor = [];
    },
    clearProductReviews: (state) => {
      state.productReiews = [];
    },
  },
  extraReducers: {
    [HYDRATE]: (state, action) => {
      // console.log("HYDRATE-PRODUCT", state, action.payload);
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
  setProductReviews,
  setProductListForCart,
  setProductListForWishlist,
  clearSelectedProductDetail,
  clearProductListAll,
  clearProductReviews,
} = productSlice.actions;
