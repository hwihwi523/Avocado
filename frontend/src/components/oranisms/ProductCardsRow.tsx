import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../molecues";
import Button from "@mui/material/Button";
import router from "next/router";

type ProductInfo = {
  id: number;
  img_url: string;
  price: number;
  discount: number;
  isBookmark: boolean;
  tags: string[];
  brand: string;
};

const ProductCardsRow: React.FC<{}> = (props) => {
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
    <RowScrollable>
      {data &&
        data?.map((item: ProductInfo, i) => (
          <ProductCard data={item} key={i} />
        ))}
    </RowScrollable>
  );
};

export default ProductCardsRow;

const RowScrollable = styled.div`
  position: relative;
  display: flex;
  width: 100%;
  overflow-x: scroll;
  white-space: nowrap;

  ::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
`;

const LoginBox = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #dddddd;
  background-color: rgba(255, 255, 255, 0.8);
  line-height: 40%;
  padding: 25% 10%;
  box-sizing: content-box;
  left: 0px;
  top: 0px;
`;
