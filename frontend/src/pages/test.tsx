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
import Button from "@mui/material/Button";
import SendIcon from "@mui/icons-material/Send";
import { useRef } from "react";
import { ReviewInput, Review } from "../components/molecues";

import { Category, ProductCardsGrid } from "../components/oranisms";

const Store = () => {
  const img_url = "store_main_image";
  return (
    <Background>
      {/* 스토어 이미지 */}
      <Stack spacing={3} direction={"column"}>
        <Imagebox>
          <Image
            src={`/assets/exampleImage/${img_url}.png`}
            alt="제품 이미지"
            fill
            style={{ objectFit: "cover" }}
          />
        </Imagebox>

        {/* 카테고리 */}
        <Category />

        {/* 제품들 뭐 하기로 했는데 까먹음  */}
        
        <ProductCardsGrid />
      </Stack>
    </Background>
  );
};
export default Store;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;

const Imagebox = styled.div`
  position: relative;
  width: 100%;
  height: 30vh;
  margin-bottom: 10px;
`;
