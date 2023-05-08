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
import Dialog from '@mui/material/Dialog';
import * as React from 'react';
import { BlockText } from "@/src/components/atoms";
import ProductBottom from "@/src/components/oranisms/ProductBottom";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Slide from '@mui/material/Slide';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import { TransitionProps } from '@mui/material/transitions';
import {useState} from 'react'

const ProductDetail = () => {


  const[open, setOpen] = useState(false)
  const [size, setSize] = useState("M");
  const [count, setCount] = useState(1);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event: SelectChangeEvent) => {
    setSize(event.target.value as string);
  };


  function addCountHandler(){
    setCount(count+1)
  }

  function minusCountHandler(){
    if(count > 1){
      setCount(count-1);
    }
  }




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
        style={{ height: "100%" }}
        gap={1}
      >
        <Grid item xs={6}>
          <FormControl fullWidth size="small">
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

        <Grid item xs={5} style={{boxSizing:"border-box"}}>
          <Stack direction={"row"} alignItems={"center"}>
            <IconButton onClick={addCountHandler} ><AddIcon/></IconButton>
            <BlockText style={{ margin: "0px 20px" }}>{count}</BlockText>
            <IconButton onClick={minusCountHandler} ><RemoveIcon/></IconButton>
          </Stack>
        </Grid>
        </Grid>
      </Dialog>

      <ProductBottom />
    </>
  );
};

export default ProductDetail;


// 모달창 애니메이션 옵션
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>,
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
