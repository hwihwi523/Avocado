import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { BlockText } from "../../components/atoms";
import { OrderItem } from "../../components/molecues";
import Head from "next/head";
import { AppState, useAppSelector, wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import { productApi } from "@/src/features/product/productApi";
import { setProductListForOrderlist } from "@/src/features/product/productSlice";
import { useEffect } from "react";
import router from "next/router";
import { useSnackbar } from "notistack";

const OrderList = () => {
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

  const orderlist = useAppSelector(
    (state: AppState) => state.product.productListForOrderlist
  );

  return (
    <Background>
      <Head>
        <title>구매 목록</title>
      </Head>
      <BlockText type="B" size="1.2rem" style={{ margin: "10px" }}>
        구매 목록
      </BlockText>
      <Stack spacing={2}>
        {orderlist.map((item, i) => (
          <OrderItem data={item} key={i} />
        ))}
      </Stack>
    </Background>
  );
};

export default OrderList;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  background-color: #dddddd;
  height: 100vh;
`;

export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    // type consumer 인 경우에만 조회
    const member = store.getState().auth.member;
    if (member && member.type == "consumer") {
      // 토큰을 헤더에 담아 유저의 구매 내역 조회
      // 토큰
      let cookie = context.req?.headers.cookie;
      let accessToken = cookie
        ?.split(";")
        .find((c) => c.trim().startsWith("ACCESS_TOKEN="))
        ?.split("=")[1];
      // 조회
      const orderlistResponse = await store.dispatch(
        productApi.endpoints.getOrderListWithServer.initiate(
          accessToken ? accessToken : ""
        )
      );
      const orderlist = orderlistResponse.data?.data.content;
      if (orderlist) {
        store.dispatch(setProductListForOrderlist(orderlist));
      }
    }

    return {
      props: {},
    };
  }
);
