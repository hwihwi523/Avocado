import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import {
  ChartMbti,
  ChartPersonalColor,
} from "@/src/components/oranisms/charts";
import {
  ProductDetailImage,
  ProductCardsRow,
  ProductDescription,
  ProductReview,
} from "@/src/components/oranisms";

import { BlockText } from "@/src/components/atoms";
import ProductBottom from "@/src/components/oranisms/ProductBottom";

const ProductDetail = () => {
  return (
    <>
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
            <ProductDescription />
            <DividerBar />
          </Grid>
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              연관 상품
            </BlockText>
            <ProductCardsRow />
            <DividerBar />
          </Grid>
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              리뷰 작성하기
            </BlockText>
            <ProductReview />
          </Grid>
        </Grid>
      </Background>
      <ProductBottom />
    </>
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
