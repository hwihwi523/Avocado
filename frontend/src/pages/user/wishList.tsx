import { useGetWishlistQuery } from "@/src/features/product/productApi";
import { AppState, useAppSelector, wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Head from "next/head";
import { useRouter } from "next/router";
import { useSnackbar } from "notistack";
import { useEffect } from "react";
import { BlockText } from "../../components/atoms";
import { WishItem } from "../../components/molecues";

const WishList = () => {
  const router = useRouter();

  //로그인 정보 없으면 로그인 화면으로 보내기
  const member = useAppSelector((state: AppState) => state.auth.member);
  const { enqueueSnackbar } = useSnackbar();

  useEffect(() => {
    if (!member) {
      enqueueSnackbar(`로그인이 필요한 서비스입니다.`, {
        variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
        anchorOrigin: {
          horizontal: "center", //(left, center, right)
          vertical: "top", //top, bottom
        },
      });
      router.replace("/login");
    }
  }, []);

  // wishlist 호출
  const { data: wishlist } = useGetWishlistQuery();

  return (
    <Background>
      <Head>
        <title>찜 목록</title>
      </Head>
      <BlockText type="B" size="1.2rem" style={{ margin: "10px" }}>
        찜 목록
      </BlockText>
      <Stack spacing={2}>
        {wishlist &&
          wishlist.map((item, i) => <WishItem item={item} key={i} />)}
      </Stack>
    </Background>
  );
};

export default WishList;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  background-color: #dddddd;
  padding-bottom: 50px;
  height: 100%;
`;

// 멤버 상태 유지
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    return {
      props: {},
    };
  }
);
