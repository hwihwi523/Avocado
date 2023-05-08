import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { BlockText } from "../../components/atoms";
import { CartItem } from "../../components/molecues";

const CartList = () => {
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
      <Stack spacing={2}>
        {data.map((item, i) => (
          <CartItem data={item} key={i} />
        ))}
      </Stack>
      <Total>
        <BlockText size="1.2rem">총 금액 :</BlockText>
        <BlockText size="1.8rem" color="red">
          {" "}
          {formatCurrency(totalPrice())}
        </BlockText>
      </Total>
    </Background>
  );
};

export default CartList;

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
