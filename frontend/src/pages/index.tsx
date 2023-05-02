import { Inter } from "next/font/google";
import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";

import {
  ProductCardsRow,
  Category,
  Commercials,
  MainUserProfile,
} from "../components/oranisms";
import { BlockText } from "../components/atoms";

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
          <MainUserProfile />
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
          <BlockText>
            지금 Hot한 브랜드 모음
          </BlockText>
          <Commercials />
        </Grid>

        {/* 펄스널 컬러별 추천 */}
        <Grid item xs={12}>
          <BlockText>
            퍼스널 컬러 “???” 사용자들을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow />
        </Grid>

        {/* mbti별 추천 */}
        <Grid item xs={12}>
          <BlockText>
            MBTI “???” 사용자들을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow />
        </Grid>
      </Grid>
    </BackgroundDiv>
  );
}

const BackgroundDiv = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;

const StyledSpan = styled.span`
  font-weight: bold;
  font-size: 20px;
`;
