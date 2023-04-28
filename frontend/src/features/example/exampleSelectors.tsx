import { RootState } from "../store";

export const selectExampleUser = (state: RootState) =>
  state.example.exampleUser;
export const selectExampleToken = (state: RootState) =>
  state.example.exampleUser;
