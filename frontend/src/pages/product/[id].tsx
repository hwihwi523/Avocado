import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import { ChartMbti,ChartPersonalColor } from "@/src/components/oranisms/charts";
import { ProductDetailImage, ProductCardsRow, ProductDescription } from "@/src/components/oranisms";
import ProductReview from "../test";

const ProductDetail = () => {
  return (
    <Background>
      <Grid container gap={2}>
        <Grid item xs={12}>
          <ProductDetailImage />
          <DividerBar />
        </Grid>
        <Grid item xs={12}>
          <ChartMbti />
          
        </Grid>
        <Grid item xs={12}>
          <ChartPersonalColor />
          
        </Grid>
        <Grid item xs={12}>
          <ProductDescription/>
          <DividerBar />
        </Grid>
        <Grid item xs={12}>
          <ProductCardsRow />
          <DividerBar />
        </Grid>
        <Grid item xs={12}>
          <ProductReview/>
        </Grid>
      </Grid>
    </Background>
  );
};

export default ProductDetail;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  margin-bottom: 100px;
`;

const DividerBar = styled.div`
  border: 1px solid #dddddd;
  height: 1px;
  margin: 20px 0px;
`;
