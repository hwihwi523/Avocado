import { useLoginMutation } from "@/src/features/auth/authApi";
import { AppState, wrapper } from "@/src/features/store";
import Head from "next/head";
import { useRouter } from "next/router";
import { useDispatch, useSelector } from "react-redux";
import { removeToken, removeTokenAll } from "@/src/utils/tokenManager";
import { clearAuth } from "@/src/features/auth/authSlice";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { useEffect } from "react";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import { appCookies } from "../_app";

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
    fetch("/api/logout", { method: "POST" })
      .then(() => {
        // 로그아웃 성공 시 처리할 로직 작성
        console.log("WEB_LOGOUT_SUCCESS");
        // router.push("/login");
      })
      .catch((error) => {
        console.error("WEB_LOGOUT_FAIL: ", error);
      });
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

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { res: context.res, req: context.req },
      store
    );

    return {
      props: {},
    };
  }
);
