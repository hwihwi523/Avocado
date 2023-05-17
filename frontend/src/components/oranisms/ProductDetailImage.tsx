import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Carousel from "react-material-ui-carousel";
import Image from "next/image";
import Link from "next/link";
import Chip from "@mui/material/Chip";
import { BlockText, InlineText } from "../atoms";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import { ProductDetail } from "@/src/features/product/productSlice";
import router from "next/router";
type ProductDetailImageProps = {
  product: ProductDetail | null;
};

const ProductDetailImage: React.FC<ProductDetailImageProps> = ({ product }) => {
  const images = product?.images;
  const product_name = product?.merchandise_name;
  const brand = product?.brand_name;
  const tags = [
    product?.mbti,
    product?.personal_color,
    product?.merchandise_category,
  ];
  const discount = product?.discounted_price; //할인된 가격
  const price = product?.price; //원가
  const remaining = product?.inventory; //재고

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  // 브랜드 스토어 아이디
  const brandId = product ? product.provider_id : "";

  function redirectionToStorePage() {
    router.push({
      pathname: `/store/${product?.brand_name.replace(" ", "_")}`,
      query: {
        provider_id: product?.provider_id,
      },
    });
  }

  return (
    <>
      <Stack
        direction="row-reverse"
        justifyContent="space-between"
        onClick={redirectionToStorePage}
      >
        <BlockText type="L" size="1.2rem" style={{ margin: "30px 0 10px 0" }}>
          {brand} STORE <ArrowRightIcon />
        </BlockText>
      </Stack>
      <Carousel animation="slide" autoPlay={true}>
        {images?.map((url: string, i) => (
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
          <BlockText
            color="grey"
            size="1.2rem"
            style={{ margin: "30px 0 0 0" }}
          >
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
            {tags
              .filter((item): item is string => Boolean(item))
              .map((item: string) => (
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
            {price && formatCurrency(price)}
          </BlockText>
        </Grid>

        {/* 할인가 + 할인률 */}
        <Grid item xs={12}>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText type="L" size="2rem">
              {price && discount && formatCurrency(discount)}
            </InlineText>
            <InlineText type="L" size="2rem" color="red">
              {price &&
                discount &&
                Math.ceil(((price - discount) / price) * 100)}
              %
            </InlineText>
          </Stack>
          <BlockText
            size="1rem"
            type="L"
            color="grey"
            style={{ marginTop: "20px" }}
          >
            {remaining && <> 남은 수량 : {remaining}</>}
          </BlockText>
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
