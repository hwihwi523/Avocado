import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Link from "next/link";
import Stack from "@mui/material/Stack";
import Chip from "@mui/material/Chip";
import { Tangerine } from "next/font/google";

const ProductCard = (props: any) => {
  //const { img_url, price, discount, brand, isBookmark, tags }= props;
  const img_url =
    "https://img.freepik.com/free-photo/japanese-business-concept-with-business-person_23-2149268012.jpg?w=740&t=st=1682738359~exp=1682738959~hmac=4714981d0d5d09c27675131f07ee4ca11b7d6b57c39febce3e83bb918d3e129b";
  const price = 32000;
  const discount = 10000;
  const brand = "MUJI";
  const isBookmark = true;
  const tags = ["ESFP", "SPRING", "상의"];

  return (
    <Background>
      <Imagebox>
        <img
          src={img_url}
          alt="제품 이미지"
          style={{
            objectFit: "cover",
            objectPosition: "center",
            height: "100%",
          }}
        />
      </Imagebox>

      <Grid container>
        <Grid item xs={12}>
          <Stack direction="row" spacing={1}>
            {tags.map((item: string) => (
              <Chip label={item} key={item} variant="outlined" />
            ))}
          </Stack>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="body1" style={{ margin: "5px 0 30px 0" }}>
            {brand}
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Typography
            variant="h5"
            style={{ textDecoration: "line-through", color: "grey" }}
          >
            {price}
          </Typography>
        </Grid>

        <Grid item xs={12}>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <Typography variant="h4">{price - discount}</Typography>
            <Typography variant="h4" color="error">
              {Math.ceil((discount / price) * 100)}%
            </Typography>
          </Stack>
        </Grid>
      </Grid>
    </Background>
  );
};

export default ProductCard;
const Background = styled.div`
  width: 300px;
  margin: 10px;
`;

const Imagebox = styled.div`
  width: 100%;
  height: 300px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
`;
