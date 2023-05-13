import styled from "@emotion/styled";
import {
  DecodedToken,
  SellerLoginResponse,
  useSellerLoginMutation,
} from "@/src/features/auth/authApi";
import {
  Stack,
  Button,
  FormControl,
  InputLabel,
  InputAdornment,
  Input,
  OutlinedInput,
  IconButton,
} from "@mui/material";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { AppState, wrapper } from "@/src/features/store";
import Head from "next/head";
import { useRouter } from "next/router";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import jwt from "jsonwebtoken";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import {
  removeToken,
  removeTokenAll,
  setToken,
} from "@/src/utils/tokenManager";
import { Member, clearAuth, setMember } from "@/src/features/auth/authSlice";
import { appCookies } from "../_app";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import { BlockText } from "@/src/components/atoms";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function SellerLogin() {
  const dispatch = useDispatch();
  const member = useSelector((state: AppState) => state.auth.member);
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
      let member: Member = {
        type: "", // 일반 사용자 or 판매자
        id: "",
        email: "",
        name: "",
      };
      try {
        const secret = SECRET;
        const decodedAccess = jwt.verify(newAccessToken, secret);
        const decodedRefresh = jwt.verify(newRefreshToken, secret);
        const decodedAccessToken = decodedAccess as DecodedToken;
        const decodedRefreshToken = decodedRefresh as DecodedToken;
        accessExp = decodedAccessToken.exp;
        refreshExp = decodedRefreshToken.exp;
        member = {
          type: decodedRefreshToken.type, // 일반 사용자 or 판매자
          id: decodedRefreshToken.id,
          email: decodedRefreshToken.email,
          name: decodedRefreshToken.name,
          picture_url: decodedRefreshToken.picture_url,
          gender: decodedRefreshToken.gender,
          age_group: decodedRefreshToken.age_group,
          height: decodedRefreshToken.height,
          weight: decodedRefreshToken.weight,
          mbti_id: decodedRefreshToken.mbti_id,
          personal_color_id: decodedRefreshToken.personal_color_id,
        };
        console.log("JWT_TOKEN_PARSING_EXP:", [accessExp, refreshExp]);
      } catch (err) {
        console.log("JWT_TOKEN_PARSING_ERR:", err);
      }
      // 쿠키에 토큰 저장
      setToken("ACCESS_TOKEN", res.access_token, accessExp);
      setToken("REFRESH_TOKEN", res.refresh_token, refreshExp);
      // store에 멤버 저장
      dispatch(setMember(member));
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    dispatch(clearAuth());
    //로그인 페이지로 이동
    router.push("/login");
  };

  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };

  return (
    <Background>
      <Stack
        spacing={2}
        style={{
          borderRadius: "10px",
          backgroundColor: "white",
          padding: "40px 10px",
          marginTop: "30%",
          
        }}
      >
        <Head>
          <title>판매자 로그인</title>
        </Head>
        <BlockText type="B" size="1.2rem">
          판매자 로그인
        </BlockText>
        <br />
        <form onSubmit={handleLogin}>
          <Stack>

            {/* 이메일 입력 */}
            <FormControl sx={{ m: 1 }} variant="outlined">
              <InputLabel htmlFor="outlined-adornment-email">email</InputLabel>
              <OutlinedInput
                onChange={(e) => setEmail(e.target.value)}
                id="outlined-adornment-email"
                type="email"
                endAdornment={
                  <InputAdornment position="end">
                    <AccountCircleIcon />
                  </InputAdornment>
                }
                label="email"
              />
            </FormControl>

            {/* 비밀번호 입력 */}
            <FormControl sx={{ m: 1 }} variant="outlined">
              <InputLabel htmlFor="outlined-adornment-password">
                Password
              </InputLabel>
              <OutlinedInput
                onChange={(e) => setPassword(e.target.value)}
                id="outlined-adornment-password"
                type={showPassword ? "text" : "password"}
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      onMouseDown={handleMouseDownPassword}
                      edge="end"
                    >
                      {showPassword ? (
                        <VisibilityOffIcon />
                      ) : (
                        <VisibilityIcon />
                      )}
                    </IconButton>
                  </InputAdornment>
                }
                label="Password"
              />
            </FormControl>
          </Stack>
          <Button
            type="submit"
            style={{
              backgroundColor: "black",
              color: "white",
              padding: "20px",
              boxSizing: "border-box",
              marginTop:"40px"
            }}
            fullWidth
          >
            로그인
          </Button>
        </form>
        {/* <button onClick={handleLogout}>로그아웃</button> */}
        {/* <button onClick={() => router.push("/example")}>이동 테스트</button> */}
      </Stack>
    </Background>
  );
}

const Background = styled.div`
  padding: 10px;
  background-color: #dddddd;
  height: 100vh;
`;

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
