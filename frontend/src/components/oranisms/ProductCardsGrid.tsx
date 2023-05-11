
import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import {ProductCard} from "../molecues";

type ProductInfo = {
  id: number;
  img_url: string;
  price: number;
  discount: number;
  isBookmark?: boolean;
  tags: string[];
  brand: string;
};

const ProductCardsGrid:React.FC<{data : ProductInfo[]}> = (props) => {
  // 더미 데이터

  
  return (
    <Grid container>
      {props.data &&
        props.data?.map((item: ProductInfo, i) => (
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
