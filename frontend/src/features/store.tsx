import { combineReducers, configureStore, ThunkAction } from "@reduxjs/toolkit";
import { createWrapper } from "next-redux-wrapper";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import { Action } from "redux";
import thunkMiddleware from "redux-thunk";
import { examplePostsApi } from "../queries/examplePostApi";
import { searchApi } from "../queries/searchApi";
import { testProductListApi } from "../queries/testProductListApi";
import { authApi } from "./auth/authApi";
import { authSlice } from "./auth/authSlice";
import { commercialApi } from "./commercial/commercialApi";
import { commercialSlice } from "./commercial/commercialSlice";
import { exampleSlice } from "./example/exampleSlice";
import { memberInfoApi } from "./memberInfo/memberInfoApi";
import { paymentApi } from "./payment/paymentApi";
import { paymentSlice } from "./payment/paymentSlice";
import { productApi } from "./product/productApi";
import { productSlice } from "./product/productSlice";
import { sellerApi } from "./seller/sellerApi";
import { snapshotApi } from "./snapshot/snapshotApi";
import { statisticApi } from "./statistic/statisticApi";
import { statisticSlice } from "./statistic/statisticSlice";

// Root reducer 설정
const rootReducer = combineReducers({
  example: exampleSlice.reducer,
  auth: authSlice.reducer,
  product: productSlice.reducer,
  payment: paymentSlice.reducer,
  commercial: commercialSlice.reducer,
  statistic: statisticSlice.reducer,

  [examplePostsApi.reducerPath]: examplePostsApi.reducer,
  [testProductListApi.reducerPath]: testProductListApi.reducer,
  [authApi.reducerPath]: authApi.reducer,
  [productApi.reducerPath]: productApi.reducer,
  [paymentApi.reducerPath]: paymentApi.reducer,
  [searchApi.reducerPath]: searchApi.reducer, //검색 api
  [memberInfoApi.reducerPath]: memberInfoApi.reducer, //유저정보 수정 api
  [snapshotApi.reducerPath]: snapshotApi.reducer, //스넵샷 api
  [commercialApi.reducerPath]: commercialApi.reducer, // 광고 api
  [sellerApi.reducerPath]: sellerApi.reducer, //판매자 페이지 api
  [statisticApi.reducerPath]: statisticApi.reducer,
});

// https://github.com/kirill-konshin/next-redux-wrapper#redux-toolkit
// next.js + next-redux-wrapper + RTK
const makeStore = () => {
  return configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware().concat(
        thunkMiddleware,
        examplePostsApi.middleware,
        testProductListApi.middleware,
        authApi.middleware,
        productApi.middleware,
        paymentApi.middleware,
        searchApi.middleware, //검색 api
        memberInfoApi.middleware, // 유저정보 수정 api
        snapshotApi.middleware, // 스넵샷 Api
        commercialApi.middleware, //광고 api
        sellerApi.middleware, //판매자 api
        statisticApi.middleware
      ),
    devTools: true,
  });
};

export type AppStore = ReturnType<typeof makeStore>;
export type AppState = ReturnType<AppStore["getState"]>;
export type AppDispatch = AppStore["dispatch"];
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  AppState,
  unknown,
  Action
>;

// Use throughout your app instead of plain `useDispatch` and `useSelector`
export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<AppState> = useSelector;

// wrapper
export const wrapper = createWrapper<AppStore>(makeStore);
