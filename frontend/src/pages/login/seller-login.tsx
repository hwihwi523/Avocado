import {
  DecodedToken,
  SellerLoginResponse,
  useSellerLoginMutation,
} from "@/src/features/auth/authApi";
import { AppState } from "@/src/features/store";
import Head from "next/head";
import { useRouter } from "next/router";
import { useState } from "react";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import jwt from "jsonwebtoken";
import { removeTokenAll, setToken } from "@/src/utils/tokenManager";
import { clearAuth } from "@/src/features/auth/authSlice";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function SellerLogin() {
  const member = useSelector(
    (state: AppState) => state.auth.member,
    (prev, curr) => prev !== curr // 이전 state와 현재 state를 비교하여 변경되었는지 확인
  );
  const router = useRouter();
  // // 로그인 상태인 경우 메인 페이지로 이동하는 예제
  // useEffect(() => {
  //   if (member) {
  //     router.replace("/");
  //   }
  // }, [member, router]);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [login, { isLoading }] = useSellerLoginMutation();

  const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault(); // 폼 제출 방지
    // 로그인 로직
    console.log("로그인 버튼이 클릭되었습니다.");
    try {
      const res = await login({ email, password }).unwrap();
      console.log("로그인 성공:", res);
      // 로그인 성공 시 쿠키 세팅
      const newAccessToken = res.access_token;
      const newRefreshToken = res.refresh_token;
      let accessExp = 0;
      let refreshExp = 0;
      try {
        const secret = SECRET;
        const decodedAccess = jwt.verify(newAccessToken, secret);
        const decodedRefresh = jwt.verify(newRefreshToken, secret);
        const decodedAccessToken = decodedAccess as DecodedToken;
        const decodedRefreshToken = decodedRefresh as DecodedToken;
        accessExp = decodedAccessToken.exp;
        refreshExp = decodedRefreshToken.exp;
        console.log("JWT_TOKEN_PARSING_EXP:", [accessExp, refreshExp]);
      } catch (err) {
        console.log("JWT_TOKEN_PARSING_ERR:", err);
      }
      setToken("ACCESS_TOKEN", res.access_token, accessExp);
      setToken("REFRESH_TOKEN", res.refresh_token, refreshExp);
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    clearAuth();
  };

  return (
    <>
      <Head>
        <title>판매자 로그인</title>
      </Head>
      {member && <div>{member.email}</div>}
      <div>여기는 판매자 로그인 페이지</div>
      <br />
      <form
        onSubmit={handleLogin}
        style={{
          display: "flex",
          flexDirection: "column",
          width: "50%",
        }}
      >
        <label>
          이메일 :
          <input
            type="email"
            name="email"
            placeholder="test@test.com"
            onChange={(e) => setEmail(e.target.value)}
          />
        </label>
        <label>
          비밀번호 :
          <input
            type="password"
            name="password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <button type="submit">로그인</button>
      </form>
      <button type="submit" onClick={handleLogout}>
        로그아웃
      </button>
    </>
  );
}
