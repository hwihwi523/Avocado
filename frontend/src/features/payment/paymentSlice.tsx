import { createSlice } from "@reduxjs/toolkit";
import { HYDRATE } from "next-redux-wrapper";

export interface ProductForPayment {
  merchandise_id: number;
  quantity: number;
  price: number;
  size: string;
}

interface PaymentState {}

const initialState: PaymentState = {};

export const paymentSlice = createSlice({
  name: "payment",
  initialState,
  reducers: {},
  extraReducers: {
    [HYDRATE]: (state, action) => {
      // console.log("HYDRATE-PAYMENT", state, action.payload);
      return {
        ...state,
        ...action.payload.product,
      };
    },
  },
});

export const {} = paymentSlice.actions;
