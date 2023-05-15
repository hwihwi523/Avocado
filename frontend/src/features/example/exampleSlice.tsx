import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ExampleState, ExampleUser } from "@/src/types/exampleTypes";
import { HYDRATE } from "next-redux-wrapper";

const initialState: ExampleState = {
  exampleUser: null,
  exampleToken: "",
  exampleProducts: [],
};

export const exampleSlice = createSlice({
  name: "example",
  initialState,
  reducers: {
    setExampleUser: (state, action: PayloadAction<ExampleUser>) => {
      state.exampleUser = action.payload;
    },
    setExampleToken: (state, action: PayloadAction<string>) => {
      state.exampleToken = action.payload;
    },
    clearExampleUser: (state) => {
      state.exampleUser = null;
      state.exampleToken = "";
    },
  },
  // Special reducer for hydrating the state
  // You also used the HYDRATE function in next-redux-wrapper to ensure that the state on
  // the server side matches the client side of your app.
  // 서버와 클라이언트 단의 store가 일치하도록 하기 위해 필요.
  extraReducers: {
    [HYDRATE]: (state, action) => {
      // console.log("HYDRATE-EXAMPLE", state, action.payload);
      return {
        ...state,
        ...action.payload.example,
      };
    },
  },
});

export const { setExampleUser, setExampleToken, clearExampleUser } =
  exampleSlice.actions;
