import styled from "@emotion/styled";
import { Stack, Button } from "@mui/material";
import { BlockText, InlineText } from "../components/atoms";
import { useState } from "react";
import { InputAdornment, TextField } from "@mui/material";
import { AddressInput } from "../components/molecues";
import MapIcon from "@mui/icons-material/Map";
import { useSnackbar } from "notistack";
import Head from "next/head"
const BillingPage = () => {
  const [addressInputVisible, setAddressInputVisible] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  //주문자 정보
  const [address, setAddress] = useState("");
  const name = "김싸피";
  const email = "abc@abc.com";
  const order_date = "2023.09.13";

  //상품 더미데이터
  const products = [
    {
      id: 123,
      product_name: "청바지",
      size: "M",
      count: 4,
      price: 30000,
      discount: 10000,
    },
    {
      id: 123,
      product_name: "흰 티셔츠",
      size: "M",
      count: 4,
      price: 30000,
      discount: 10000,
    },
    {
      id: 123,
      product_name: "검은색 아우터",
      size: "M",
      count: 4,
      price: 30000,
      discount: 10000,
    },
    {
      id: 123,
      product_name: "양말",
      size: "M",
      count: 4,
      price: 30000,
      discount: 10000,
    },
  ];

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  //총 할인 금액
  function totalDiscount() {
    let result = 0;
    for (let i = 0; i < products.length; i++) {
      result += products[i].discount * products[i].count;
    }
    return formatCurrency(result);
  }

  //총 결제 긍맥
  function totalPrice() {
    let result = 0;
    for (let i = 0; i < products.length; i++) {
      result += (products[i].price - products[i].discount) * products[i].count;
    }
    return formatCurrency(result);
  }

  function paymantHandler(){
    if(!address){
        enqueueSnackbar(`주소를 입력해 주세요 `, {
            variant: "error",
            anchorOrigin: {
              horizontal: "center",
              vertical: "bottom",
            },
          });
          return;
    }
    console.log("결제 했다~")
  }



  return (
    <Background>
      <Head>
        <title>계산서</title>
      </Head>
      <Stack spacing={2} style={{ marginBottom:"50px"}}>
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
            ({ product_name, count, id, price, discount, size }, i) => (
              <Stack direction={"row"} justifyContent={"space-between"} key={i}>
                <BlockText>
                  {product_name}{" "}
                  <InlineText size="0.8rem" color="grey">
                    / {size} {count}개
                  </InlineText>
                </BlockText>
                <BlockText>{formatCurrency(price * count)}</BlockText>
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
