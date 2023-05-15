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
import { IconButton, Stack } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import * as React from "react";
import { BlockText } from "@/src/components/atoms";
import ProductBottom from "@/src/components/oranisms/ProductBottom";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Slide from "@mui/material/Slide";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { TransitionProps } from "@mui/material/transitions";
import { useState } from "react";
import { Button } from "@mui/material";
import Head from "next/head";
import router from "next/router";

const ProductDetail = () => {
  const [size, setSize] = useState("M");
  const [count, setCount] = useState(1);
  const [open, setOpen] = React.useState(false);

  //모달 오픈
  const handleClickOpen = () => {
    setOpen(true);
  };

  //모달 닫기
  const handleClose = () => {
    setOpen(false);
  };

  //사이즈 변경
  const handleChange = (event: SelectChangeEvent) => {
    setSize(event.target.value as string);
  };

  //수량 증가
  function addCountHandler() {
    setCount(count + 1);
  }

  //수량 감소
  function minusCountHandler() {
    if (count > 1) {
      setCount(count - 1);
    }
  }

  //구매하기 함수
  function purchaseHandler() {
    console.log({
      id: 123123,
      size,
      count,
    });

    router.push("/billing");
  }

  // 장바구니에 담기
  function addToCart() {
    console.log({
      id: 123123,
      size,
      count,
    });
  }

  return (
    <>
      <Head>
        <title>제품이름</title>
        <meta name="description" content="스토어 설명" />
        <meta name="keywords" content={`제품카테고리, 제품 종류 , 등등`} />
        <meta property="og:title" content="스토어 이름" />
        <meta property="og:description" content="스토어 설명" />
      </Head>
      <Background>
        <Grid container gap={2}>
          {/* 제품 이미지 */}
          <Grid item xs={12}>
            <ProductDetailImage />
            <DividerBar />
          </Grid>

          {/* mbti chart */}
          <Grid item xs={12}>
            <ChartMbti />
          </Grid>

          {/* personal color chart */}
          <Grid item xs={12}>
            <ChartPersonalColor />
          </Grid>

          {/* 상품 설명 */}
          <Grid item xs={12}>
            <ProductDescription />
            <DividerBar />
          </Grid>

          {/* 연관 상품 */}
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              연관 상품
            </BlockText>
            <ProductCardsRow isLogin={true} />
            <DividerBar />
          </Grid>

          {/* 리뷰작성 */}
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              리뷰 작성하기
            </BlockText>
            <ProductReview />
          </Grid>
        </Grid>
      </Background>

      {/*  구매 모달 */}
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <Grid
          container
          justifyContent={"space-between"}
          alignItems={"center"}
          style={{ width: "300px", padding: "20px" }}
          gap={2}
        >
          <Grid item xs={5}>
            <FormControl fullWidth size="medium">
              <InputLabel id="demo-simple-select-label">size</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={size}
                label="size"
                onChange={handleChange}
              >
                <MenuItem value={"S"}>S</MenuItem>
                <MenuItem value={"M"}>M</MenuItem>
                <MenuItem value={"L"}>L</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={5} style={{ boxSizing: "border-box" }}>
            <Stack direction={"row"} alignItems={"center"}>
              <IconButton onClick={addCountHandler}>
                <AddIcon />
              </IconButton>
              <BlockText style={{ margin: "0px 10px" }}>{count}</BlockText>
              <IconButton onClick={minusCountHandler}>
                <RemoveIcon />
              </IconButton>
            </Stack>
          </Grid>
          <Grid item xs={12}>
            <Stack spacing={1}>
              <Button
                fullWidth
                style={{
                  backgroundColor: "black",
                  color: "white",
                  padding: "30px",
                }}
                onClick={purchaseHandler}
              >
                구매하기
              </Button>
              <Button
                onClick={addToCart}
                fullWidth
                style={{
                  backgroundColor: "white",
                  color: "black",
                  border: "1px solid black",
                  padding: "30px",
                }}
              >
                장바구니
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Dialog>

      <ProductBottom openModal={handleClickOpen} />
    </>
  );
};

export default ProductDetail;

// 모달창 애니메이션 옵션
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

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
