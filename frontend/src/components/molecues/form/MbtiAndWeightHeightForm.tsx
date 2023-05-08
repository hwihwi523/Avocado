import styled from "@emotion/styled";
import * as React from "react";
import FilledInput from "@mui/material/FilledInput";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import InputAdornment from "@mui/material/InputAdornment";
import FormControl from "@mui/material/FormControl";
import { useState, useRef } from "react";
import MenuItem from "@mui/material/MenuItem";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { Stack } from "@mui/material";
import Button from "@mui/material/Button";
import { ChangeEvent } from "react";
import { BlockText } from "../../atoms";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import {enqueueSnackbar} from 'notistack'
const MbtiAndWeightHeightForm: React.FC<{ pageHandler: () => void }> = (
  props
) => {
  const { pageHandler } = props;
  const [mbti, setMbti] = useState("");
  const [height, setHeight] = useState(0);
  const [weight, setWeight] = useState(0);

  //mbti 선택 핸들러
  const handleChange = (event: SelectChangeEvent) => {
    setMbti(event.target.value as string);
  };

  //키 선택 핸들러
  const heightChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    // value의 값이 숫자가 아닐경우 빈문자열로 replace 해버림.
    const onlyNumber = value.replace(/[^0-9]/g, "");
    setHeight(Number(event.target.value));
  };

  //몸무게 선택 핸들러
  const weightChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    // value의 값이 숫자가 아닐경우 빈문자열로 replace 해버림.
    const onlyNumber = value.replace(/[^0-9]/g, "");
    setWeight(Number(event.target.value));
  };

  const submitHandler = () => {
    if(mbti === ""){
      enqueueSnackbar(`mbti를 입력하지 않았습니다. `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return
    }

    


    console.log({
      mbti,
      height: height,
      weight: weight,
    });

    pageHandler(); //다음페이지로 이동하는 함수
  };

  return (
    <Background>
      <Stack spacing={2}>
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">mbti</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={mbti}
            label="mbti"
            onChange={handleChange}
          >
            <MenuItem value={"INTJ"}>INTJ</MenuItem>
            <MenuItem value={"INFJ"}>INFJ</MenuItem>
            <MenuItem value={"ISTJ"}>ISTJ</MenuItem>
            <MenuItem value={"ISTP"}>ISTP</MenuItem>
            <MenuItem value={"INTP"}>INTP</MenuItem>
            <MenuItem value={"INFP"}>INFP</MenuItem>
            <MenuItem value={"ISFJ"}>ISFJ</MenuItem>
            <MenuItem value={"ISFP"}>ISFP</MenuItem>
            <MenuItem value={"ENTJ"}>ENTJ</MenuItem>
            <MenuItem value={"ENFJ"}>ENFJ</MenuItem>
            <MenuItem value={"ESTJ"}>ESTJ</MenuItem>
            <MenuItem value={"ESTP"}>ESTP</MenuItem>
            <MenuItem value={"ESFJ"}>ESFJ</MenuItem>
            <MenuItem value={"ESFP"}>ESFP</MenuItem>
          </Select>
          <BlockText color="#1875d2" style={{ padding: "10px" }}>
            <a href="https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC" target="_blank">
              나의 mbti 알아보러 가기
            </a>
            <ArrowForwardIcon fontSize="small" />
          </BlockText>
        </FormControl>

        <FormControl variant="outlined" fullWidth>
          <OutlinedInput
            onChange={weightChange}
            type="number"
            id="outlined-adornment-weight"
            endAdornment={<InputAdornment position="end">kg</InputAdornment>}
            aria-describedby="outlined-weight-helper-text"
            inputProps={{
              "aria-label": "weight",
            }}
          />
        </FormControl>
        <FormControl variant="outlined" fullWidth>
          <OutlinedInput
            onChange={heightChange}
            type="number"
            id="outlined-adornment-height"
            endAdornment={<InputAdornment position="end">cm</InputAdornment>}
            aria-describedby="outlined-weight-helper-text"
            inputProps={{
              "aria-label": "height",
            }}
          />
        </FormControl>

        <Button
          style={{ backgroundColor: "black", color: "white", padding: "20px" }}
          onClick={submitHandler}
          fullWidth
        >
          확인
        </Button>
      </Stack>
    </Background>
  );
};

export default MbtiAndWeightHeightForm;

const Background = styled.div``;
