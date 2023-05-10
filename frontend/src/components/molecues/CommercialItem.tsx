import styled from "@emotion/styled";
import { Stack, Chip, Button, Dialog, Slide } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../atoms";
import Link from "next/link";
import { useState } from "react";
import { TotalCommercialGraph } from "../oranisms/seller";
import * as React from 'react';
import { TransitionProps } from '@mui/material/transitions';

//풀스크린 모달창을 위한 옵션
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>,
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

type Item = {
  id: number;
  age: number;
  gender: string;
  imgurl: string;
  commercial_type_id: number;
  mbti_id: number;
  personal_color_id: number;
  create_at: number;
  merchandise_id: number;
  merchandise_name: string;
  provider_id: number;
};

const CommercialItem: React.FC<{ data: Item }> = (props) => {
  const {
    id,
    age,
    gender,
    imgurl,
    commercial_type_id,
    mbti_id,
    personal_color_id,
    create_at,
    merchandise_id,
    merchandise_name,
    provider_id,
  } = props.data;

  //모달 옵션
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  //더미 데이터

  return (
    <Background>
      <Stack spacing={0.5}>
        <ImageBox>
          <Image
            src={imgurl}
            alt="제품 이미지"
            fill
            style={{ objectFit: "cover" }}
          />
        </ImageBox>
        <Link href={`/product/${merchandise_id}`}>
          <BlockText style={{ margin: "10px 0" }} size="1.3rem">
            {merchandise_name}
          </BlockText>
        </Link>
        <Stack direction="row" justifyContent={"space-between"}>
          <InlineText color="grey" size="0.9rem">
            생성 날짜 :
          </InlineText>
          <InlineText color="grey" size="0.9rem">
            {create_at}
          </InlineText>
        </Stack>

        <Stack direction="row" justifyContent={"space-between"}>
          <InlineText size="0.9rem" color="grey">
            광고 타입 :
          </InlineText>
          <InlineText size="0.9rem" color="grey">
            {commercial_type_id}(팝업)
          </InlineText>
        </Stack>
        <Stack direction="row" spacing={1} style={{ marginTop: "30px" }}>
          <Chip label={"INFJ"} variant="outlined" />
          <Chip label={"personal_color_id"} variant="outlined" />
          <Chip label={"20대"} variant="outlined" />
        </Stack>
        <Stack direction={"row"} spacing={1}>

        <Button
          style={{ marginTop:"10px", backgroundColor: "black", color: "white", padding: "10px" }}
          fullWidth
          onClick={handleOpen}
          >
          통계 보기
        </Button>
        <Button
          style={{ marginTop:"10px", backgroundColor: "red", color: "white", padding: "10px" }}
          fullWidth
          onClick={handleOpen}
          >
          삭제 하기
        </Button>
          </Stack>
      </Stack>

      <Dialog
      fullScreen
        open={open}
        onClose={handleClose}
        scroll="body"
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        TransitionComponent={Transition}
        style={{paddingBottom:"50px"}}
      >
          <TotalCommercialGraph />
          <Button  style={{ width:"100%", color:"black", margin:"10px 0px 10px 0px", padding:"20px"}} onClick={handleClose}>닫기</Button>
      </Dialog>
    </Background>
  );
};

export default CommercialItem;
 
const Background = styled.div`
  padding: 10px;
  border-radius: 10px;
`;
const ImageBox = styled.div`
  position: relative;
  width: 100%;
  height: 20vh;
`;

