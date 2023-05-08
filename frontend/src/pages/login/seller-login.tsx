import { useSellerLoginMutation } from "@/src/features/auth/authApi";
import { Button } from "@mui/material";
import Head from "next/head";
import { useState } from "react";
import { useDispatch } from "react-redux";

export default function SellerLogin() {
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
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  return (
    <>
      <Head>
        <title>판매자 로그인</title>
      </Head>
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
          <input type="email" name="email" placeholder="test@test.com" />
        </label>
        <label>
          비밀번호 :
          <input type="password" name="password" />
        </label>
        <button type="submit">로그인</button>
      </form>
    </>
  );
}
