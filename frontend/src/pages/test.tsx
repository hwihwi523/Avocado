import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Chip from "@mui/material/Chip";
import Image from "next/image";
import Rating from "@mui/material/Rating";

import { useState } from "react";

import { Pie, Doughnut } from "react-chartjs-2";
import { Bubble } from "react-chartjs-2";
import { IconButton } from "@mui/material";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";
import { BlockText, InlineText } from "../components/atoms";
import { ProductDetailImage, ProductCardsRow } from "../components/oranisms";
import { ChartPersonalColor } from "../components/oranisms/charts";

import FormControl from "@mui/material";
import TextField from "@mui/material/TextField";

const ProductDescription = (props: any) => {
  const [rating, setRating] = useState(2);

  return (
    <Background>
      <form>
        <Stack direction="row" gap={2} alignItems={"center"}>
          <Rating
            size="small"
            name="simple-controlled"
            value={rating}
            onChange={(event, newValue) => {
              setRating(newValue ? newValue : 0);
            }}
          />
          <TextField id="standard-basic" label="리뷰작성" variant="standard" />
        </Stack>
      </form>
    </Background>
  );
};

//여기는 SeoulNamsan 적용 안되서 기본 sens-self로 함
const Background = styled.div`
  font-family: sens-self;
  padding: 10px;
`;

export default ProductDescription;
