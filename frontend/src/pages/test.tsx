import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from '@mui/material/Typography';
import Carousel from "react-material-ui-carousel";
import {ProductCard} from "../components/molecues";


type ProductInfo={
  id:number,
  img_url:string,
  price:number,
  discount:number,
  isBookmark:boolean,
  tags:string[],
  brand:string
}

const ProductCardsRow = (props: any) => {
   const data =[
    {
      id : 123123,
      img_url : "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price : 32000,
      discount : 10000,
      brand : "MUJI",
      isBookmark : true,
      tags :["ESFP", "SPRING", "상의"],
    },{
      id : 123123,
      img_url : "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price : 32000,
      discount : 10000,
      brand : "MUJI",
      isBookmark : true,
      tags :["ESFP", "SPRING", "상의"],
    },{
      id : 123123,
      img_url : "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price : 32000,
      discount : 10000,
      brand : "MUJI",
      isBookmark : true,
      tags :["ESFP", "SPRING", "상의"],
    },{
      id : 123123,
      img_url : "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price : 32000,
      discount : 10000,
      brand : "MUJI",
      isBookmark : true,
      tags :["ESFP", "SPRING", "상의"],
    },{
      id : 123123,
      img_url : "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b",
      price : 32000,
      discount : 10000,
      brand : "MUJI",
      isBookmark : true,
      tags :["ESFP", "SPRING", "상의"],
    }
   ]

 

  return (
    <RowScrollable>
      {
        data ? (
          data.map((item:ProductInfo, i)=>(
            <ProductCard data={item} key={i} />
          ))
        ) : (
          <h3>로그인을 안해서 추천을 할 수가 없습니다. </h3>
        )
      }
      

    </RowScrollable>
    
  );
};

export default ProductCardsRow;

const RowScrollable = styled.div`
display:flex;
width: 100%;
overflow-x: scroll;
white-space: nowrap;
`

