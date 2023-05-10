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
import { wrapper } from "../features/store";
import { authenticateTokenInPages } from "../utils/authenticateTokenInPages";

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
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    // 필요한 내용 작성

    return {
      props: {},
    };
  }
);
