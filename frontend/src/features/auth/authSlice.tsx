import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { HYDRATE } from "next-redux-wrapper";

// refresh token 안에 포함된 내용
export interface Member {
  type: string; // 일반 사용자 or 판매자
  id: string;
  email: string;
  name: string;
  picture_url?: string;
  gender?: string;
  age?: number;
  height?: number;
  weight?: number;
  mbti_id?: number;
  personal_color_id?: number;
}

interface AuthState {
  member: Member | null;
  accessToken: string | "";
  refreshToken: string | "";
}

const initialState: AuthState = {
  member: null,
  accessToken: "",
  refreshToken: "",
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setMember: (state, action: PayloadAction<Member>) => {
      state.member = action.payload;
    },
    setAccessToken: (state, action: PayloadAction<string>) => {
      state.accessToken = action.payload;
    },
    setRefreshToken: (state, action: PayloadAction<string>) => {
      state.refreshToken = action.payload;
    },
    clearMember: (state) => {
      state.member = null;
      state.accessToken = "";
      state.refreshToken = "";
    },
  },
  extraReducers: {
    [HYDRATE]: (state, action) => {
      console.log("HYDRATE-AUTH", state, action.payload);
      return {
        ...state,
        ...action.payload.auth,
      };
    },
  },
});

export const { setMember, setAccessToken, setRefreshToken } = authSlice.actions;
