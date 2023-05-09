import { Inter } from "next/font/google";
import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";

import {
  ProductCardsRow,
  Category,
  Commercials,
  UserProfile,
} from "../components/oranisms";
import { BlockText } from "../components/atoms";
import { Member, clearAuth, setMember } from "../features/auth/authSlice";
import { wrapper } from "../features/store";
import { appCookies } from "./_app";
import { DecodedToken } from "../features/auth/authApi";
import jwt from "jsonwebtoken";
import authenticateMemberInPages from "../utils/authenticateMemberInPages";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const name = "김싸피";
  const mbti = "estp";
  const personalColor = "spring bright";

  return (
    <BackgroundDiv>
      <Grid container gap={5}>
        {/* 사용자 프로필 */}
        <Grid item xs={12}>
          <UserProfile />
        </Grid>

        {/* 메뉴 카테고리 */}
        <Grid item xs={12}>
          <Category />
        </Grid>

        {/* 개인화 추천 제품 */}
        <Grid item xs={12}>
          <BlockText>
            <StyledSpan>{name}</StyledSpan>
            님을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow />
        </Grid>

        {/* 브랜드 광고 */}
        <Grid item xs={12}>
          <BlockText>지금 Hot한 브랜드 모음</BlockText>
          <Commercials />
        </Grid>

        {/* 펄스널 컬러별 추천 */}
        <Grid item xs={12}>
          <BlockText>퍼스널 컬러 “???” 사용자들을 위한 추천 아이템</BlockText>
          <ProductCardsRow />
        </Grid>

        {/* mbti별 추천 */}
        <Grid item xs={12}>
          <BlockText>MBTI “???” 사용자들을 위한 추천 아이템</BlockText>
          <ProductCardsRow />
        </Grid>
      </Grid>
    </BackgroundDiv>
  );
}

const BackgroundDiv = styled.div`
  padding: 10px;
  box-sizing: border-box;
  margin-bottom: 50px;
`;

const StyledSpan = styled.span`
  font-weight: bold;
  font-size: 20px;
`;

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) =>
    async ({ req }) => {
      // 쿠키를 받아와 로그인 상태인지 확인
      const cookie = req?.headers.cookie;
      const refreshToken = cookie
        ?.split(";")
        .find((c) => c.trim().startsWith("REFRESH_TOKEN="))
        ?.split("=")[1];
      console.log("SERVER_REFRESH_TOKEN:", refreshToken);
      // 토큰이 있다면 member를 store에 저장
      authenticateMemberInPages(store, refreshToken);

      return {
        props: {},
      };
    }
);
