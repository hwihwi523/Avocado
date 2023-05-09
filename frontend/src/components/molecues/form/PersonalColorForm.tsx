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
import router from 'next/router'

const PersonalColorForm: React.FC<{ pageHandler: () => void }> = (props) => {

  const{pageHandler} = props;
  const [personalColor, setPersonalColor] = useState("");

  const { enqueueSnackbar } = useSnackbar();
  const handleChange = (event: SelectChangeEvent) => {
    setPersonalColor(event.target.value as string);
  };

  function submitHandler() {
    if (personalColor === "") {
      enqueueSnackbar(`퍼스널 컬러를 선택하지 않았습니다. `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return;
    }

    console.log({
      personalColor,
    });
    pageHandler()
    
  }

  return (
    <>
      <Stack spacing={3}>
        {/* <div>
          <label htmlFor="imageInput">
            <UploadButtonImg>
              <CameraAltIcon />
            </UploadButtonImg>
          </label>
          <UploadInput
            id="imageInput"
            type="file"
            accept="image/*"
            // accept=".jpg,.jpeg,.png"
            multiple
          />
            <BlockText type="L" color="grey" size="0.9rem">* 얼굴이 정면으로 잘 드러나는 사진을 등록해 주세요</BlockText>
            <BlockText type="L" color="grey" size="0.9rem">* 이 사진은 퍼스널 컬러 테스트에만 사용됩니다.</BlockText>
        </div> */}

        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">퍼스널 컬러</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={personalColor}
            label="mbti"
            onChange={handleChange}
          >
            <MenuItem value={"spring_warm_light"}>spring warm light</MenuItem>
            <MenuItem value={"spring_warm_bright"}>spring warm bright</MenuItem>
            <MenuItem value={"summer_cool_light"}>summer cool light</MenuItem>
            <MenuItem value={"summer_cool_bright"}>summer cool bright</MenuItem>
            <MenuItem value={"summer_cool-mute"}>summer cool mute</MenuItem>
            <MenuItem value={"autumn_warm_mute"}>autumn warm mute</MenuItem>
            <MenuItem value={"autumn_warm_strong"}>autumn warm strong</MenuItem>
            <MenuItem value={"autumn_warmdeep"}>autumn warm deep</MenuItem>
            <MenuItem value={"winter_cool_bright"}>winter cool brignt</MenuItem>
            <MenuItem value={"winter_cool_deep"}>winter cool deep</MenuItem>
          </Select>
          <BlockText color="#1875d2" style={{marginTop:"10px"}}> 
            <a href="https://mycolor.kr/" target="_blank">
              퍼스널 컬러 확인하러 가기{" "}
            </a>
            <ArrowForwardIcon />
          </BlockText>
        </FormControl>

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
