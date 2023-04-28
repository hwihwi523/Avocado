import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ExampleAuthState, ExampleUser } from "@/src/types/exampleTypes";
import { RootState } from "../store";

const initialState: ExampleAuthState = {
  exampleUser: null,
  exampleToken: "",
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
});

export const { setExampleUser, setExampleToken, clearExampleUser } =
  exampleSlice.actions;
