import styled from "@emotion/styled";
import { Chip, Stack } from "@mui/material";
import Image from "next/image";
import Link from "next/link";
import * as React from "react";
import { BlockText, InlineText } from "../atoms";

import { myCommercialItem } from "@/src/features/commercial/commercialApi";
import { mbti_list, personal_color_list } from "../atoms/data";

const CommercialItem: React.FC<{
  data: myCommercialItem;
}> = (props) => {
  const {
    id,
    age,
    gender,
    imgurl,
    commercial_type_id,
    mbti_id,
    personal_color_id,
    created_at,
    merchandise_id,
    merchandise_name,
    provider_id,
  } = props.data;

  //삭제 버튼
  function deleteHandler() {}

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
            {created_at[0] +
              "년 " +
              created_at[1] +
              "월 " +
              created_at[2] +
              "일 " +
              created_at[3] +
              "시 " +
              created_at[4] +
              "분 "}
          </InlineText>
        </Stack>

        <Stack direction="row" justifyContent={"space-between"}>
          <InlineText size="0.9rem" color="grey">
            광고 타입 :
          </InlineText>
          <InlineText size="0.9rem" color="grey">
            {commercial_type_id === 1 ? "메인페이지" : "팝업"}
          </InlineText>
        </Stack>
        <Stack direction="row" spacing={1} style={{ marginTop: "30px" }}>
          <Chip label={mbti_list[mbti_id]} variant="outlined" />
          <Chip
            label={personal_color_list[personal_color_id]}
            variant="outlined"
          />
          <Chip label={age + "대"} variant="outlined" />
        </Stack>
      </Stack>
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
