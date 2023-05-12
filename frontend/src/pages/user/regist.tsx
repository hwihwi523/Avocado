import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { useState } from "react";
import Button from "@mui/material/Button";

import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import * as React from "react";
import IconButton from "@mui/material/IconButton";
import {
  GenderAndAgeForm,
  MbtiAndWeightHeightForm,
  PersonalColorFormfrom,
} from "../../components/molecues/form";
import router from "next/router";
import { BlockText } from "@/src/components/atoms";
import Head from "next/head";

import { useAddMemberInfoMutation } from "@/src/features/memberInfo/memberInfoApi";

//memberInfo의 타입
type RequestType = {
  gender: String;
  age_group: number; // 10, 20, 30, 40, 50, 60, 70. null 불가
  height: number | null; // 0 이상. null 가능
  weight: number | null; // 0 이상. null 가능
  mbti_id: number | null; // 0 이상 15이하
  personal_color_id: number | null; // 0이상 9이하
};

const UserInputForm = () => {
  const [page, setPage] = useState(0);
  const [addMemberInfo, { isLoading, error }] = useAddMemberInfoMutation();

  function moveNextForm() {
    setPage(page + 1);
  }

  function movePreviouseForm() {
    setPage(page - 1);
  }

  const [memberInfo, setMemberInfo] = useState<RequestType>({
    gender: "M", //기본값
    age_group: 10, //기본값
    height: null, // 0 이상. null 가능
    weight: null, // 0 이상. null 가능
    mbti_id: null, // 0 이상 15이하
    personal_color_id: null, // 0이상 9이하
  });

  function submitHandler() {
    addMemberInfo(memberInfo)
      .unwrap()
      .then((res) => {
        console.log("res : ", res);
        router.push("/")
      })
      .catch((error) => {
        console.log("error : ", error);
      });
    console.log(memberInfo);
  }

  return (
    <Stack spacing={2}>
      <Head>
        <title>개인정보 등록</title>
      </Head>

      <div style={{ marginTop: "20px", display: page == 0 ? "none" : "" }}>
        <IconButton
          color="default"
          aria-label="previous page"
          component="label"
          onClick={movePreviouseForm}
        >
          <ArrowBackIosNewIcon />
        </IconButton>
      </div>
      <Background>
        {/* 성별과 나이 입력 */}
        {page == 0 && (
          <>
            <GenderAndAgeForm
              setMemberInfo={setMemberInfo}
              pageHandler={moveNextForm}
            />
          </>
        )}

        {/* mbti 키 몸무게 입력 */}
        {page == 1 && (
          <>
            <MbtiAndWeightHeightForm
              setMemberInfo={setMemberInfo}
              pageHandler={moveNextForm}
            />
          </>
        )}

        {/* 퍼스널 컬러 선택 */}
        {page == 2 && (
          <>
            <PersonalColorFormfrom
              setMemberInfo={setMemberInfo}
              pageHandler={moveNextForm}
            />
          </>
        )}

        {/* 설정이 모두 완료되고 메인페이지로 이동 */}
        {page == 3 && (
          <>
            <BlockText
              size="1.5rem"
              style={{
                textAlign: "center",
                alignContent: "center",
                padding: "100px 0",
              }}
            >
              설정이 모두 완료되었습니다.
            </BlockText>
            <Button
              fullWidth
              style={{
                backgroundColor: "black",
                color: "white",
                padding: "20px",
              }}
              onClick={submitHandler}
            >
              메인 페이지로 이동
            </Button>
          </>
        )}

        {/* 선택 안하고 건너뛰는 버튼 */}
      </Background>
      {(page === 1 || page === 2) && (
        <Button
          onClick={moveNextForm}
          style={{
            backgroundColor: "white",
            color: "black",
            padding: "20px",
            position: "fixed",
            bottom: "10%",
            border: "1px solid black",
          }}
          fullWidth
        >
          건너뛰기
        </Button>
      )}
    </Stack>
  );
};

export default UserInputForm;

const Background = styled.div`
  padding: 10px;
  margin-top: 20%;
  box-sizing: border-box;
`;
