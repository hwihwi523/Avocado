import { configureStore, ThunkAction } from "@reduxjs/toolkit";
import { Action } from "redux";
import { createWrapper } from "next-redux-wrapper";
import { combineReducers } from "@reduxjs/toolkit";
import { exampleSlice } from "./example/exampleSlice";
import { examplePostsApi } from "../queries/examplePostApi";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import thunkMiddleware from "redux-thunk";

// Root reducer 설정
const rootReducer = combineReducers({
  example: exampleSlice.reducer,
  [examplePostsApi.reducerPath]: examplePostsApi.reducer,
});

// https://github.com/kirill-konshin/next-redux-wrapper#redux-toolkit
// next.js + next-redux-wrapper + RTK
const makeStore = () => {
  return configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware().concat(
        thunkMiddleware,
        examplePostsApi.middleware
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
