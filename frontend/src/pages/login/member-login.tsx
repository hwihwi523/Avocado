import { useLoginMutation } from "@/src/features/auth/authApi";
import { AppState, wrapper } from "@/src/features/store";
import Head from "next/head";
import { useRouter } from "next/router";
import { useDispatch, useSelector } from "react-redux";
import { removeTokenAll } from "@/src/utils/tokenManager";
import { clearAuth } from "@/src/features/auth/authSlice";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { useEffect } from "react";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function MemberLogin() {
  const dispatch = useDispatch();
  const member = useSelector((state: AppState) => state.auth.member);
  const router = useRouter();
  // // 로그인 상태인 경우 메인 페이지로 이동
  // useEffect(() => {
  //   if (member) {
  //     router.replace("/");
  //   }
  // }, [member, router]);

  const [login, { isLoading }] = useLoginMutation();

  const handleLogin = async () => {
    // 로그인 로직
    console.log("로그인 버튼이 클릭되었습니다.");
    try {
      // 요청으로 받아온 url로 유저 redirect -> 카카오 로그인
      const res = await login({ provider: "kakao" }).unwrap();
      console.log("멤버 로그인 요청 상태:", res);
      const url = res.url;
      router.push(url);
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    dispatch(clearAuth());
    // 로그인 페이지로 이동
    router.push("/login");
  };

  return (
    <div>
      <Head>
        <title>일반 사용자 로그인</title>
      </Head>
      {member && <div>{member.email}</div>}
      <div>여기는 사용자 로그인 페이지</div>
      <br />
      <button onClick={handleLogin}>로그인</button>
      <br />
      <button onClick={handleLogout}>로그아웃</button>
      <br />
      <button onClick={() => router.push("/example")}>이동 테스트</button>
    </div>
  );
}

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
