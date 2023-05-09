import {
  DecodedToken,
  SellerLoginResponse,
  useLoginMutation,
  useSellerLoginMutation,
} from "@/src/features/auth/authApi";
import { AppState, wrapper } from "@/src/features/store";
import Head from "next/head";
import { useRouter } from "next/router";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import jwt from "jsonwebtoken";
import { removeTokenAll, setToken } from "@/src/utils/tokenManager";
import { Member, clearAuth, setMember } from "@/src/features/auth/authSlice";
import { appCookies } from "../_app";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function MemberLogin() {
  const dispatch = useDispatch();
  const member = useSelector((state: AppState) => state.auth.member);
  const router = useRouter();
  // // 로그인 상태인 경우 메인 페이지로 이동하는 예제
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
      // 요청으로 받아온 url로 유저 redirect
      const res = await login({ provider: "kakao" }).unwrap();
      console.log("멤버 로그인 요청 상태:", res);
      const url = res.url;
      router.push(url);
      //   // 로그인 성공 시 쿠키 세팅 <-
      //   const newAccessToken = res.access_token;
      //   const newRefreshToken = res.refresh_token;
      //   let accessExp = 0;
      //   let refreshExp = 0;
      //   let member: Member = {
      //     type: "", // 일반 사용자 or 판매자
      //     id: "",
      //     email: "",
      //     name: "",
      //   };
      //   try {
      //     const secret = SECRET;
      //     const decodedAccess = jwt.verify(newAccessToken, secret);
      //     const decodedRefresh = jwt.verify(newRefreshToken, secret);
      //     const decodedAccessToken = decodedAccess as DecodedToken;
      //     const decodedRefreshToken = decodedRefresh as DecodedToken;
      //     accessExp = decodedAccessToken.exp;
      //     refreshExp = decodedRefreshToken.exp;
      //     member = {
      //       type: decodedRefreshToken.type, // 일반 사용자 or 판매자
      //       id: decodedRefreshToken.id,
      //       email: decodedRefreshToken.email,
      //       name: decodedRefreshToken.name,
      //       picture_url: decodedRefreshToken.picture_url,
      //       gender: decodedRefreshToken.gender,
      //       age_group: decodedRefreshToken.age_group,
      //       height: decodedRefreshToken.height,
      //       weight: decodedRefreshToken.weight,
      //       mbti_id: decodedRefreshToken.mbti_id,
      //       personal_color_id: decodedRefreshToken.personal_color_id,
      //     };
      //     console.log("JWT_TOKEN_PARSING_EXP:", [accessExp, refreshExp]);
      //   } catch (err) {
      //     console.log("JWT_TOKEN_PARSING_ERR:", err);
      //   }
      //   // 쿠키에 토큰 저장
      //   setToken("ACCESS_TOKEN", res.access_token, accessExp);
      //   setToken("REFRESH_TOKEN", res.refresh_token, refreshExp);
      //   // store에 멤버 저장
      //   dispatch(setMember(member));
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    clearAuth();
    // 메인 페이지로 이동
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
