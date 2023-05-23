import { useGetCartQuery } from "@/src/features/product/productApi";
import styled from "@emotion/styled";
import { Button, Stack } from "@mui/material";
import Head from "next/head";
import router from "next/router";
import { useSnackbar } from "notistack";
import { useEffect } from "react";
import { BlockText, InlineText } from "../../components/atoms";
import { CartItem } from "../../components/molecues";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
const CartList = () => {
  const { data: cartlistData, isLoading, refetch } = useGetCartQuery();

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
    refetch();
  }, []);

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function totalPrice() {
    let result = 0;
    if (cartlistData) {
      for (let i = 0; i < cartlistData.length; i++) {
        result += cartlistData[i].discounted_price * cartlistData[i].quantity;
      }
    }
    return result;
  }

  //구매하기 함수
  function purchaseHandler() {
    if (member && cartlistData) {
      const cartList = cartlistData.map((item) => ({
        brand_name: item.brand_name,
        merchandise_id: item.merchandise_id,
        merchandise_category: item.merchandise_category,
        images: item.image_url,
        merchandise_name: item.merchandise_name,
        price: item.price,
        discounted_price: item.discounted_price,
        size: item.size,
        quantity: item.quantity,
      }));

      router.push({
        pathname: "/billing",
        query: {
          member: JSON.stringify(member),
          products: JSON.stringify(cartList),
        },
      });
    }
  }

  return (
    <Background>
      <Head>
        <title>장바구니</title>
      </Head>
      <BlockText type="B" size="1.2rem" style={{ margin: "10px" }}>
        장바구니
      </BlockText>
      <Stack spacing={2}>
        {cartlistData &&
          cartlistData.map((item, i) => <CartItem data={item} key={i} />)}
        <Total>
          <BlockText size="1.2rem">총 금액 :</BlockText>
          <BlockText size="1.6rem" color="red">
            {" "}
            {formatCurrency(totalPrice())}
          </BlockText>
        </Total>
        <Button
          style={{ padding: "20px", backgroundColor: "black", color: "white" }}
          fullWidth
          onClick={purchaseHandler}
        >
          <InlineText type="L" size="1.1rem" color="white">
            결제하기
          </InlineText>
        </Button>
      </Stack>
    </Background>
  );
};

export default CartList;

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

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  background-color: #dddddd;
  padding-bottom: 50px;
  height: 100%;
`;

const Total = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 70px;
  background-color: white;
  border-radius: 10px;
  margin-top: 20px;
  padding: 0px 20px;
`;
