import { AppState } from "../store";

export const selectMember = (state: AppState) => state.auth.member;
export const selectAccessToken = (state: AppState) => state.auth.accessToken;
export const selectRefreshToken = (state: AppState) => state.auth.refreshToken;
