import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { PersistConfig, persistReducer, persistStore } from "redux-persist";
// 액션 생성자가 Object 대신 Function을 반환할 수 있도록
// => 이때 함수는 dispatch와 getState를 인자로 받아 비동기 작업을 처리한 후 다시 액션을 반환
import thunkMiddleware from "redux-thunk";
import storage from "redux-persist/lib/storage"; // defaults to localStorage for web
import autoMergeLevel2 from "redux-persist/es/stateReconciler/autoMergeLevel2";
import { setupListeners } from "@reduxjs/toolkit/query";
import { exampleSlice } from "./example/exampleSlice";
import { examplePostsApi } from "../queries/examplePostApi";

// Root reducer 설정
const rootReducer = combineReducers({
  example: exampleSlice.reducer,
  [examplePostsApi.reducerPath]: examplePostsApi.reducer,
});

// Persist 설정
const persistConfig = {
  key: "avocado",
  storage,
  // 아래 옵션은 기본적으로 상태 트리의 모든 레벨을 재귀적으로 확인하고 하위 레벨에서 변경된 항목을 모두 병합
  // 객체는 얕은 복사(shallow copy), 배열은 전체 교체
  //   stateReconciler: autoMergeLevel2,
  whitelist: [""], // persist로 저장할 state 넣어두기
};

// Persist reducer
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Store
const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(thunkMiddleware, examplePostsApi.middleware),
});

// RTK Query Listnener
setupListeners(store.dispatch);

// Persistor
const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export { store, persistor };
