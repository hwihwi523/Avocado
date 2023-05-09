import { ExamplePost } from "@/src/types/exampleTypes";
import { useGetPostsQuery } from "@/src/queries/examplePostApi";
import { useSelector } from "react-redux";
import { AppState, wrapper } from "@/src/features/store";
import { useRouter } from "next/router";
import { useEffect } from "react";
import { Member, clearAuth, setMember } from "@/src/features/auth/authSlice";
import jwt from "jsonwebtoken";
import { DecodedToken } from "@/src/features/auth/authApi";
import { appCookies } from "../_app";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";

export default function ExamplePostList() {
  const member = useSelector((state: AppState) => state.auth.member);
  const router = useRouter();

  useEffect(() => {
    // 로그인되어 있지 않은 경우(member가 null인 경우) 로그인 페이지로 이동하는 예제
    if (!member) {
      router.replace("/login");
    }
  }, [member, router]);

  return (
    <ul>
      <li>이야아</li>
    </ul>
  );
}

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) =>
    async ({ req }) => {
      const cookie = req?.headers.cookie;
      const refreshToken = cookie
        ?.split(";")
        .find((c) => c.trim().startsWith("REFRESH_TOKEN="))
        ?.split("=")[1];
      if (refreshToken) {
        console.log("SERVER_REFRESH_TOKEN:", refreshToken);
        authenticateMemberInPages(store, refreshToken);
      } else {
        console.log("SERVER_REFRESH_TOKEN: No REFRESH_TOKEN");
      }

      return {
        props: {},
      };
    }
);
