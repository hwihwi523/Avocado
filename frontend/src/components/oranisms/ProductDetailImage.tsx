import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../molecues";
import Image from "next/image";
import Chip from "@mui/material/Chip";
import { BlockText, InlineText } from "../atoms";

const ProductDetailImage = () => {
  const images = [
    "https://cdn.pixabay.com/photo/2023/03/16/15/00/woman-7856919_960_720.jpg",
    "https://cdn.pixabay.com/photo/2023/04/28/12/18/dogs-7956516_960_720.jpg",
    "https://cdn.pixabay.com/photo/2022/10/06/13/17/monks-7502654_960_720.jpg",
  ];
  const product_name = "오버사이즈 울 트랜치 코트";
  const brand = "Valentino";
  const tags = ["ESTJ", "가을뮤트", "상의"];
  const discount = 10000;
  const price = 43000;
  const remaining = 997;


   //숫자 변환 함수 3000  => 3,000원
   function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  return (
    <>
      <Carousel animation="slide" autoPlay={true}>
        {images.map((url: string, i) => (
          <Imagebox key={i}>
            <Image
              src={url}
              alt="제품 이미지"
              fill
              style={{ objectFit: "cover" }}
            />
          </Imagebox>
        ))}
      </Carousel>
      <Grid container mb={5}>
        {/* 브랜드 */}
        <Grid item xs={12}>
          <BlockText color="grey" size="1.2rem" style={{ margin: "30px 0 0 0"}}>
          {brand}
          </BlockText>
        </Grid>

        {/* 제품명 */}
        <Grid item xs={12}>
          
          <BlockText type="B" size="1.8rem">
          {product_name}
          </BlockText>
        </Grid>

        {/*태그*/}
        <Grid item xs={12}>
          <Stack direction="row" spacing={3} gap={1}>
            {tags.map((item: string) => (
              <Chip
                size="small"
                label={item}
                key={item}
                variant="outlined"
                style={{ fontSize: "12px", margin: "5px 0 40px 0" }}
              />
            ))}
          </Stack>
        </Grid>

        {/* 원래 가격 */}
        <Grid item xs={12}>
          <BlockText
          type="L"
          size="1.5rem"
            style={{ textDecoration: "line-through", color: "grey" }}
          >
            {formatCurrency(price)}
          </BlockText>
        </Grid>

        {/* 원가 + 할인률 */}
        <Grid item xs={12}>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText type="L" size="2rem">{formatCurrency(price - discount)}</InlineText>
            <InlineText type="L"  size="2rem" color="red">
              {Math.ceil((discount / price) * 100)}%
            </InlineText>
          </Stack>
          <BlockText size="1rem" type="L" color="grey" style={{marginTop:"20px"}}>남은 수량 : {remaining}</BlockText>
        </Grid>
      </Grid>
    </>
  );
};

export default ProductDetailImage;

const Imagebox = styled.div`
  position: relative;
  width: 100%;
  height: 500px;
`;
