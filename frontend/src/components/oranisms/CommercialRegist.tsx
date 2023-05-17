import styled from "@emotion/styled";
import { Stack, Divider } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../../components/atoms";
import { mbti_list, personal_color_list } from "@/src/components/atoms/data";
import { Button } from "@mui/material";
import { useState, ChangeEvent } from "react";
import { useSnackbar } from "notistack";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";

import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { useAddCommercialMutation } from "@/src/features/commercial/commercialApi";

//구매 목록 더미 데이터

const CommercialRegist: React.FC<{
  merchandise_id: number;
  merchandise_name: string;
  modalClose: () => void;
}> = (props) => {
  const { enqueueSnackbar } = useSnackbar();
  const { merchandise_id, merchandise_name, modalClose } = props;
  const [previewImage, setPreviewImage] = useState<string | null>(null);

  const [age, setAge] = useState(""); //나이대
  const [gender, setGender] = useState(""); //성별
  const [type, setType] = useState(""); //광고 타입
  const [personalColor, setPersonalColor] = useState("-1"); //퍼스널 컬러
  const [mbti, setMbti] = useState("-1"); //mbti
  const [image, setImage] = useState<any>(null);
  //이미지 핸들러
  const handleImageUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];

    if (file) {
      setImage(file);
      const reader = new FileReader();

      reader.onloadend = () => {
        setPreviewImage(reader.result as string);
      };

      reader.readAsDataURL(file);
    } else {
      setPreviewImage(null);
    }
  };

  const [addCommercial] = useAddCommercialMutation();

  //제출 함수 and 에러 표시
  function submitHandler() {
    if (!previewImage) {
      enqueueSnackbar(`이미지를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (!type) {
      enqueueSnackbar(`타입을 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (!age) {
      enqueueSnackbar(`나이대를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (!gender) {
      enqueueSnackbar(`성별을 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (mbti === "-1") {
      enqueueSnackbar(`mbti를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (personalColor === "-1") {
      enqueueSnackbar(`퍼스널 컬러를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    //보낼 데이터 셋팅
    const formData = new FormData();
    formData.set("merchandise_id", merchandise_id.toString());
    formData.set("merchandise_name", merchandise_name);
    formData.set("mbti_id", mbti);
    formData.set("personal_color_id", personalColor);
    formData.set("commercial_type_id", type);
    formData.set("file", image);
    formData.set("age", age);
    formData.set("gender", gender);

    addCommercial(formData)
      .unwrap()
      .then((res) => {
        modalClose(); //모달 닫기
        enqueueSnackbar(`광고를 등록했습니다. `, {
          variant: "success",
          anchorOrigin: {
            horizontal: "center",
            vertical: "top",
          },
        });
        return;

        //등록성공 메시지 보내기
      })
      .catch((err) => {
        //실패 메시지 날리기
        console.log("err >>>>>", err);
        enqueueSnackbar(`광고를 등록하지 못했습니다. `, {
          variant: "error",
          anchorOrigin: {
            horizontal: "center",
            vertical: "top",
          },
        });
        return;
      });
  }

  //selet에서 선택한거 셋팅해주는 함수들
  const ageHandleChange = (event: SelectChangeEvent) => {
    setAge(event.target.value as string);
  };

  const genderHandleChange = (event: SelectChangeEvent) => {
    setGender(event.target.value as string);
  };

  const typeHandleChange = (event: SelectChangeEvent) => {
    setType(event.target.value as string);
  };

  const personalColorHandleChange = (event: SelectChangeEvent) => {
    setPersonalColor(event.target.value as string);
  };

  const mbtiHandleChange = (event: SelectChangeEvent) => {
    setMbti(event.target.value as string);
  };

  return (
    <Background>
      {/* 이미지 등록 */}
      <Stack
        spacing={2}
        style={{
          backgroundColor: "white",
          padding: "20px",
          borderRadius: "10px",
        }}
      >
        <InlineText type="B" style={{ marginTop: "20px" }}>
          * 제품 정보
        </InlineText>
        <Divider></Divider>
        <UploadBox>
          <label htmlFor="upload_img">
            {previewImage ? (
              <Image
                src={previewImage as string}
                alt="제품 이미지"
                fill
                style={{ objectFit: "cover" }}
              />
            ) : (
              <UploadImg>
                <AddAPhotoIcon />
              </UploadImg>
            )}
          </label>
          <input
            type="file"
            accept="image/*"
            id="upload_img"
            onChange={handleImageUpload}
            style={{ visibility: "hidden" }}
          />
        </UploadBox>

        {/* 광고 타입 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">광고 타입</InputLabel>

          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="성별 "
            onChange={typeHandleChange}
            value={type}
          >
            <MenuItem value={"0"}>팝업</MenuItem>
            <MenuItem value={"1"}>메인페이지</MenuItem>
          </Select>
        </FormControl>

        <InlineText type="B" style={{ marginTop: "70px" }}>
          * 광고 타겟
        </InlineText>
        <Divider></Divider>

        {/* 나이선택 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">나이대</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="나이 대"
            onChange={ageHandleChange}
            value={age?.toString()}
          >
            <MenuItem value={10}>10대</MenuItem>
            <MenuItem value={20}>20대</MenuItem>
            <MenuItem value={30}>30대</MenuItem>
            <MenuItem value={40}>40대</MenuItem>
            <MenuItem value={50}>50대</MenuItem>
            <MenuItem value={60}>60대 이상</MenuItem>
          </Select>
        </FormControl>

        {/* 성별 선택 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">성별</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="성별 "
            onChange={genderHandleChange}
            value={gender}
          >
            <MenuItem value={"M"}>남자</MenuItem>
            <MenuItem value={"F"}>여자</MenuItem>
          </Select>
        </FormControl>

        {/* mbti */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">MBTI</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="mbti "
            onChange={mbtiHandleChange}
            value={mbti}
          >
            {mbti_list.map((item, i) => (
              <MenuItem value={i} key={i}>
                {item}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        {/* personal_color */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">퍼스널 컬러</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="퍼스널 컬러 "
            onChange={personalColorHandleChange}
            value={personalColor}
          >
            {personal_color_list.map((item, i) => (
              <MenuItem value={i} key={i}>
                {item}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <Button
          style={{
            backgroundColor: "black",
            color: "white",
            padding: "20px",
            marginTop: "70px",
          }}
          fullWidth
          onClick={submitHandler}
        >
          작성
        </Button>
      </Stack>
    </Background>
  );
};

export default CommercialRegist;

const Background = styled.div`
  padding: 10px;
  background-color: #dddddd;
  padding: 20% 10px 150px 10px;
  box-sizing: border-box;
  width: 100%;
`;

const UploadBox = styled.div`
  border: 1px solid #dddddd;
  position: relative;
  overflow: hidden;
  background-color: #dddddd;
  border-radius: 10px;
  width: 100%;
  height: 300px;
`;

const UploadImg = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #dddddd;
  border-radius: 10px;
`;
