import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { BlockText } from "../../components/atoms";
import { OrderItem } from "../../components/molecues";

const OrderList = () => {
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
      order_date: "2023.09.14",
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
      order_date: "2023.09.14",
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
      order_date: "2023.09.14",
    },
  ];

  return (
    <Background>
      <BlockText type="B" size="1.2rem" style={{margin:"10px"}}>
        구매 목록
      </BlockText>
      <Stack spacing={2}>
        {data.map((item, i) => (
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
