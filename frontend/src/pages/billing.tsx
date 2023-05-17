import styled from "@emotion/styled";
import { Stack, Button } from "@mui/material";
import { BlockText, InlineText } from "../components/atoms";
import { useState } from "react";
import { InputAdornment, TextField } from "@mui/material";
import { AddressInput } from "../components/molecues";
import MapIcon from "@mui/icons-material/Map";
import { useSnackbar } from "notistack";
import Head from "next/head";
import { useRouter } from "next/router";
import { ProductForBuy } from "../features/product/productSlice";
import { Member } from "../features/auth/authSlice";
import { ProductForPayment } from "../features/payment/paymentSlice";
import { useStartPaymentMutation } from "../features/payment/paymentApi";

const BillingPage = () => {
  const router = useRouter();
  const [addressInputVisible, setAddressInputVisible] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const [startPayment, result] = useStartPaymentMutation();

  // 주문자 정보
  let member: Member = {
    id: "",
    name: "",
    email: "",
    type: "",
  };
  if (typeof router.query.member === "string") {
    member = JSON.parse(router.query.member);
  }
  const [address, setAddress] = useState("");
  const name = member.name;
  const email = member.email;

  // 주문 일자 (현재 날짜)
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, "0");
  const day = String(currentDate.getDate()).padStart(2, "0");
  const order_date = `${year}.${month}.${day}`;

  // 상품 상세 or 장바구니에서 넘어온 상품들 파싱
  let products: ProductForBuy[] = [];
  if (typeof router.query.products === "string") {
    products = JSON.parse(router.query.products);
  }
  console.log(products);

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  //총 할인 금액
  function totalDiscount() {
    let result = 0;
    for (let i = 0; i < products.length; i++) {
      result +=
        (products[i].price - products[i].discounted_price) *
        products[i].quantity;
    }
    return formatCurrency(result);
  }

  //총 결제 금액
  function totalPrice() {
    let result = 0;
    for (let i = 0; i < products.length; i++) {
      result += products[i].discounted_price * products[i].quantity;
    }
    return formatCurrency(result);
  }

  async function paymantHandler() {
    if (!address) {
      enqueueSnackbar(`주소를 입력해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }
    // 결제
    // 요청을 보내기 위한 상품 객체
    let merchandises: ProductForPayment[] = [];
    let total_price: number = 0;
    products.forEach((product) => {
      const merchandise: ProductForPayment = {
        merchandise_id: product.merchandise_id,
        quantity: product.quantity,
        price: product.discounted_price,
        size: product.size,
      };
      merchandises.push(merchandise);
      total_price += product.discounted_price;
    });

    startPayment({
      total_price,
      success_url: "/user/mypage",
      fail_url: "/billing",
      merchandises,
    })
      .then((res) => {
        if ("data" in res) {
          const redirectUrl = res.data.data.next_redirect_pc_url;
          router.push(redirectUrl);
        } else {
          // Handle error response
          console.log(res.error);
        }
      })
      .catch((e) => console.log(e));

    console.log("결제~", { total_price, merchandises });
  }

  return (
    <Background>
      <Head>
        <title>계산서</title>
      </Head>
      <Stack spacing={2} style={{ marginBottom: "50px" }}>
        {/* 주문자정보 */}
        <Box>
          <BlockText size="1.5rem" style={{ marginBottom: "20px" }}>
            주문자 정보
          </BlockText>

          <Stack direction={"row"} justifyContent={"space-between"}>
            <BlockText color="grey">주문자 : </BlockText>
            <BlockText>{name}</BlockText>
          </Stack>

          <Stack direction={"row"} justifyContent={"space-between"}>
            <BlockText color="grey">이메일 : </BlockText>
            <BlockText>{email}</BlockText>
          </Stack>

          <Stack direction={"row"} justifyContent={"space-between"}>
            <BlockText color="grey">주문 날짜 : </BlockText>
            <BlockText>{order_date}</BlockText>
          </Stack>
        </Box>

        {/* 주소입력 */}
        <Box>
          <BlockText size="1.5rem" style={{ marginBottom: "20px" }}>
            주소 입력
          </BlockText>

          {addressInputVisible ? (
            <AddressInput
              setVisible={setAddressInputVisible}
              dataRef={setAddress}
            />
          ) : (
            <TextField
              placeholder="주소를 입력하세요"
              id="outlined-basic"
              fullWidth
              type="text"
              value={address}
              onClick={() => {
                setAddressInputVisible(!addressInputVisible);
              }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <MapIcon />
                  </InputAdornment>
                ),
              }}
            />
          )}
        </Box>

        {/* 결제정보 */}
        <Box>
          <BlockText size="1.5rem" style={{ marginBottom: "20px" }}>
            결제 정보
          </BlockText>
          {products.map(
            (
              { merchandise_name, quantity, price, discounted_price, size },
              i
            ) => (
              <Stack direction={"row"} justifyContent={"space-between"} key={i}>
                <Stack style={{ width: "75%" }}>
                  <BlockText
                    style={{
                      whiteSpace: "nowrap",
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      width: "80%",
                    }}
                  >
                    {merchandise_name}{" "}
                  </BlockText>
                  <InlineText size="0.8rem" color="grey">
                    / {size} {quantity}개
                  </InlineText>
                </Stack>
                <BlockText>{formatCurrency(price * quantity)}</BlockText>
              </Stack>
            )
          )}

          <Stack
            direction={"row"}
            justifyContent={"space-between"}
            style={{ margin: "20px 0" }}
          >
            <BlockText color="grey"> 총 할인금액 : </BlockText>
            <BlockText color="grey">- {totalDiscount()}</BlockText>
          </Stack>

          <hr />

          <Stack
            direction={"row"}
            justifyContent={"space-between"}
            style={{ margin: "20px 0" }}
          >
            <BlockText size="1.2rem"> 총 결제금액 : </BlockText>
            <BlockText size="1.2rem" color="red">
              {totalPrice()}
            </BlockText>
          </Stack>
        </Box>
        <Button
          fullWidth
          style={{
            backgroundColor: "black",
            color: "white",
            padding: "20px",
          }}
          onClick={paymantHandler}
        >
          결제하기
        </Button>
      </Stack>
    </Background>
  );
};

export default BillingPage;

const Background = styled.div`
  background-color: #dddddd;
  width: 100%;

  padding: 20% 10px 20% 10px;
  box-sizing: border-box;
`;

const Box = styled.div`
  background-color: white;
  border-radius: 10px;
  padding: 20px 30px;
`;
