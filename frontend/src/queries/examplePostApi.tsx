import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { ExamplePost } from "../types/exampleTypes";

export const examplePostsApi = createApi({
  reducerPath: "examplePostsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "/api" }),
  endpoints: (builder) => ({
    getPosts: builder.query<ExamplePost[], void>({
      query: () => "/posts",
    }),
    getPostById: builder.query<ExamplePost, number>({
      query: (postId) => `/posts/${postId}`,
    }),
  }),
});

export const { useGetPostsQuery, useGetPostByIdQuery } = examplePostsApi;
