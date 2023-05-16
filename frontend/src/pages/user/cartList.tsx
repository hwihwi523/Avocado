import styled from "@emotion/styled";
import { Stack, Button } from "@mui/material";
import { BlockText } from "../../components/atoms";
import { CartItem } from "../../components/molecues";
import Head from "next/head";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
import router from "next/router";
import { useEffect } from "react";
import { useGetCartQuery } from "@/src/features/product/productApi";

const CartList = () => {
  const member = useAppSelector((state: AppState) => state.auth.member);
  const { data: cartlistData, isLoading } = useGetCartQuery();
  console.log(cartlistData);

  //로그인 정보 없으면 로그인 화면으로 보내기
  useEffect(() => {
    if (!member) {
      router.push("/login");
    }
  }, []);

  //더미 데이터
  const data = [
    {
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/02/17/36/monstera-7895042__340.jpg",
      brand_name: "brand",
      product_name: "남자 블레이저 더블 자켓 마이",
      product_id: 123,
      rating: 4.5,
      size: "xl",
      count: 3,
      price: 30000,
      discount: 10000,
    },
    {
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/02/17/36/monstera-7895042__340.jpg",
      brand_name: "brand",
      product_name: "남자 블레이저 더블 자켓 마이",
      product_id: 123,
      rating: 4.5,
      size: "xl",
      count: 3,
      price: 30000,
      discount: 10000,
    },
    {
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/02/17/36/monstera-7895042__340.jpg",
      brand_name: "brand",
      product_name: "남자 블레이저 더블 자켓 마이",
      product_id: 123,
      rating: 4.5,
      size: "xl",
      count: 3,
      price: 30000,
      discount: 10000,
    },
  ];

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function totalPrice() {
    let result = 0;
    for (let i = 0; i < data.length; i++) {
      result += data[i].price - data[i].discount;
    }

    return result;
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
        {data.map((item, i) => (
          <CartItem data={item} key={i} />
        ))}
        <Total>
          <BlockText size="1.2rem">총 금액 :</BlockText>
          <BlockText size="1.8rem" color="red">
            {" "}
            {formatCurrency(totalPrice())}
          </BlockText>
        </Total>
        <Button
          style={{ padding: "10px", backgroundColor: "black", color: "white" }}
          fullWidth
        >
          결제하기
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
  height: 100vh;
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
