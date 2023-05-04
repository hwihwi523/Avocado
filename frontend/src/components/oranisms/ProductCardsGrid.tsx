
import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import {ProductCard} from "../molecues";

type ProductInfo = {
  id: number;
  img_url: string;
  price: number;
  discount: number;
  isBookmark: boolean;
  tags: string[];
  brand: string;
};

const ProductCardsGrid = (props: any) => {
  // 더미 데이터
  const data: ProductInfo[] = [
    {
      id: 123123,
      img_url:
        "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price: 32000,
      discount: 10000,
      brand: "MUJI",
      isBookmark: true,
      tags: ["ESFP", "SPRING", "상의"],
    },
    {
      id: 123123,
      img_url:
        "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price: 32000,
      discount: 10000,
      brand: "MUJI",
      isBookmark: true,
      tags: ["ESFP", "SPRING", "상의"],
    },
    {
      id: 123123,
      img_url:
        "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price: 32000,
      discount: 10000,
      brand: "MUJI",
      isBookmark: true,
      tags: ["ESFP", "SPRING", "상의"],
    },
    {
      id: 123123,
      img_url:
        "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price: 32000,
      discount: 10000,
      brand: "MUJI",
      isBookmark: true,
      tags: ["ESFP", "SPRING", "상의"],
    },
    {
      id: 123123,
      img_url:
        "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price: 32000,
      discount: 10000,
      brand: "MUJI",
      isBookmark: true,
      tags: ["ESFP", "SPRING", "상의"],
    },
  ];
  return (
    <Grid container>
      {data &&
        data?.map((item: ProductInfo, i) => (
          <Grid item lg={3} md={3} sm={4} xs={6} key={i}>
            <ProductCard data={item} key={i} />
          </Grid>
        ))}
    </Grid>
  );
};

export default ProductCardsGrid;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;
