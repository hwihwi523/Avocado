import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { InlineText } from "../../components/atoms";
import {
  ProductCardsRow,
  UserProfile,
  UserStateSummary,
} from "../../components/oranisms";
import { ChartPersonalColor } from "../../components/oranisms/charts";
import Link from 'next/link'

const Mypage = () => {
  return (
    <Background>
      <Stack direction={"column"} spacing={2}>
        <UserProfile />
        <UserStateSummary />
        <ChartPersonalColor />

        <Box>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText>최근 본 상품</InlineText>
            <InlineText>더보기 +</InlineText>
          </Stack>
          <ProductCardsRow />
        </Box>

        <Box>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText>구매 내역</InlineText>
            <Link href="/user/cartList">
            <InlineText>더보기 +</InlineText>
            </Link>
          </Stack>
          <ProductCardsRow />
        </Box>
      </Stack>
    </Background>
  );
};

export default Mypage;

const Background = styled.div`
  padding: 10px 10px 70px 10px;
`;

const Box = styled.div`
  padding-top: 100px;
`;
