import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { useState } from "react";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import * as React from "react";
import { useSnackbar } from "notistack";
import { Button } from "@mui/material";
import { BlockText } from "../../atoms";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import { personal_color_list } from "../../atoms/data";

//memberInfo의 타입
type RequestType = {
  gender: String;
  age_group: number; // 10, 20, 30, 40, 50, 60, 70. null 불가
  height: number | null; // 0 이상. null 가능
  weight: number | null; // 0 이상. null 가능
  mbti_id: number | null; // 0 이상 15이하
  personal_color_id: number | null; // 0이상 9이하
};

const PersonalColorForm: React.FC<{
  pageHandler: () => void;
  setMemberInfo: any;
}> = (props) => {
  const { pageHandler, setMemberInfo } = props;
  const [personalColor, setPersonalColor] = useState("10");

  const { enqueueSnackbar } = useSnackbar();
  const handleChange = (event: SelectChangeEvent) => {
    setPersonalColor(event.target.value);
  };

  function submitHandler() {
    if (personalColor === "10") {
      enqueueSnackbar(`퍼스널 컬러를 선택하지 않았습니다. `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return;
    }

    setMemberInfo((preState: RequestType) => ({
      ...preState,
      personal_color_id:
        Number(personalColor) !== -1 ? Number(personalColor) : null,
    }));

    pageHandler();
  }

  return (
    <>
      <Stack spacing={3}>
        {/* 퍼스널 컬러 선택 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">퍼스널 컬러</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={personalColor}
            label="mbti"
            onChange={handleChange}
          >
            <MenuItem value={"10"}>선택안함</MenuItem>
            {personal_color_list.map((item, i) => (
              <MenuItem value={i.toString()} key={i}>
                {item}
              </MenuItem>
            ))}
          </Select>
          <BlockText color="#1875d2" style={{ marginTop: "10px" }}>
            <a href="https://mycolor.kr/" target="_blank">
              퍼스널 컬러 확인하러 가기{" "}
            </a>
            <ArrowForwardIcon />
          </BlockText>
        </FormControl>

        {/* 셋팅 버튼 */}
        <Button
          fullWidth
          style={{
            color: "white",
            backgroundColor: "black",
            padding: "20px",
          }}
          onClick={submitHandler}
        >
          확인
        </Button>
      </Stack>
    </>
  );
};

export default PersonalColorForm;

const Background = styled.div``;

const UploadButtonImg = styled.div`
  width: 100%;
  margin: 0 auto;
  height: 200px;
  background: #dddddd;
  border-radius: 10px;
  text-align: center;
  line-height: 200px;
`;

const UploadInput = styled.input`
  display: none;
`;
