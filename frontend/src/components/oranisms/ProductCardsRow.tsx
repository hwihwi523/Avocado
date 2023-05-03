import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../molecues";
import Button from '@mui/material/Button';

type ProductInfo = {
  id: number;
  img_url: string;
  price: number;
  discount: number;
  isBookmark: boolean;
  tags: string[];
  brand: string;
};

const ProductCardsRow = (props: any) => {
    const isLogin = true;


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
    <RowScrollable scroll={isLogin}>

        {data && (data?.map((item: ProductInfo, i) => <ProductCard data={item} key={i} />))}
      
        
      {!isLogin && 
          <LoginBox>
          <Button size="large" style={{backgroundColor:"black", color:"white"}} variant="contained">로그인이 필요한 서비스 입니다.</Button>
          </LoginBox>
        }
      
    </RowScrollable>
  );
};

export default ProductCardsRow;

 type Scroll ={
    scroll : boolean,
}

const RowScrollable = styled.div<Scroll>`
  position: relative;
  display: flex;
  width: 100%;
  overflow-x: scroll;
  white-space: nowrap;

  ::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }

  overflow-x:${props => props.scroll ? "" : "hidden"};
  overflow-y:${props => props.scroll ? "" : "hidden"};
`;

const LoginBox = styled.div`
  position:absolute;
  width: 100%;
  height:100%;
  background-color: #dddddd;
  background-color: rgba( 255, 255, 255, 0.8 );
  line-height: 40%;
  padding:25% 10%;
  box-sizing:content-box;
  left: 0px;
  top: 0px;
`;
