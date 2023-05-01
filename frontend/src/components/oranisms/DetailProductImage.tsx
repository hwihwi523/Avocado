import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../molecues";
import Image from "next/image";
import Chip from "@mui/material/Chip";

const DetailProductImage = () => {
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
      <Grid container>
        {/* 브랜드 */}
        <Grid item xs={12}>
          <Typography variant="body2" style={{ margin: "30px 0 0 0", color: "grey" }}>
            {brand}
          </Typography>
        </Grid>

        {/* 제품명 */}
        <Grid item xs={12}>
          <Typography
            variant="h5"
            style={{ fontWeight: "bold" }}
          >
            {product_name}
          </Typography>
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
          <Typography
            variant="h5"
            style={{ textDecoration: "line-through", color: "grey" }}
          >
            {price}원
          </Typography>
        </Grid>

        {/* 원가 + 할인률 */}
        <Grid item xs={12}>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <Typography variant="h4">{price - discount}원</Typography>
            <Typography variant="h4" color="error">
              {Math.ceil((discount / price) * 100)}%
            </Typography>
          </Stack>
        </Grid>
      </Grid>
    </>
  );
};

export default DetailProductImage;

const Imagebox = styled.div`
  position: relative;
  width: 100%;
  height: 500px;
`;
