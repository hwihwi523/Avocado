import { AppState } from "../store";

export const selectExampleUser = (state: AppState) => state.example.exampleUser;
export const selectExampleToken = (state: AppState) =>
  state.example.exampleToken;
