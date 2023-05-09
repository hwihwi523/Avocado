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

//구매 목록 더미 데이터

const order_list = [
  {
    product_id: 12,
    product_name: "상의",
  },
  {
    product_id: 13,
    product_name: "하의",
  },
  {
    product_id: 23,
    product_name: "아웃터",
  },
  {
    product_id: 3,
    product_name: "청바지",
  },
  {
    product_id: 1,
    product_name: "모자",
  },
];

type Item = {
  product_id: number;
  product_name: string;
};

const CommercialRegist = () => {
  const { enqueueSnackbar } = useSnackbar();
  const [previewImage, setPreviewImage] = useState<string | null>(null);

  const [age, setAge] = useState(""); //나이대
  const [gender, setGender] = useState(""); //성별
  const [product, setProduct] = useState(""); //상품목록 검색
  const [type, setType] = useState(""); //광고 타입
  const [personalColor, setPersonalColor] = useState(""); //퍼스널 컬러
  const [mbti, setMbti] = useState(""); //mbti 타입
  //이미지 핸들러
  const handleImageUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        setPreviewImage(reader.result as string);
      };

      reader.readAsDataURL(file);
    } else {
      setPreviewImage(null);
    }
  };

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

    if (!product) {
      enqueueSnackbar(`제품을 선택해 주세요 `, {
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

    if (!mbti) {
      enqueueSnackbar(`mbti를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    if (!personalColor) {
      enqueueSnackbar(`퍼스널 컬러를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "top",
        },
      });
      return;
    }

    console.log({
      previewImage,
      product,
      type,
      age,
      gender,
      mbti,
      personalColor,
    });
  }

  //selet에서 선택한거 셋팅해주는 함수들
  const ageHandleChange = (event: SelectChangeEvent) => {
    setAge(event.target.value as string);
  };

  const genderHandleChange = (event: SelectChangeEvent) => {
    setGender(event.target.value as string);
  };

  const productHandleChange = (event: SelectChangeEvent) => {
    setProduct(event.target.value as string);
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

        {/* 제품선택 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">내 상품</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="orderList"
            onChange={productHandleChange}
            value={product}
          >
            {order_list.map((item, i) => (
              <MenuItem value={item.product_id} key={i}>
                {item.product_name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

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
            <MenuItem value={1}>팝업</MenuItem>
            <MenuItem value={2}>메인페이지</MenuItem>
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
