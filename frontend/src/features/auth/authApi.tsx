import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/dist/query";
import { Member } from "./authSlice";

interface SellerLoginRequest {
  email: string;
  password: string;
}

export const authAPi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({ baseUrl: "" }),
  endpoints: (builder) => ({
    sellerLogin: builder.mutation<Member, SellerLoginRequest>({
      query: (body) => ({
        url: "",
        method: "POST",
        body,
      }),
    }),
  }),
});
