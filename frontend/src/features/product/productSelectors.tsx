import { AppState } from "../store";

export const selectProductListBySearch = (state: AppState) =>
  state.product.productListBySearch;
export const selectProductListrByMbti = (state: AppState) =>
  state.product.productListByMbti;
export const selectProductListByPersonalColor = (state: AppState) =>
  state.product.productListByPersonalColor;
