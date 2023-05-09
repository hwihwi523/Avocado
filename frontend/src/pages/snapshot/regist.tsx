import styled from "@emotion/styled";
import { Stack, Chip } from "@mui/material";
import Image from "next/image";
import {  InlineText } from "../../components/atoms";
import { Button } from "@mui/material";
import { useState, ChangeEvent } from "react";
import { useSnackbar } from "notistack";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import TextField from "@mui/material/TextField";
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

const SnapshotRegist = () => {
  const { enqueueSnackbar } = useSnackbar();
  const [value, setValue] = useState("");
  const [previewImage, setPreviewImage] = useState<string | null>(null);
  const [content, setContent] = useState<string>("");
  const [products, setProducts] = useState<Item[]>([]);

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

  //제출 함수
  function submitHandler() {
    if (!previewImage) {
      enqueueSnackbar(`이미지를 선택해 주세요 `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return;
    }

    if (content === "") {
      enqueueSnackbar(`내용을 작성해 주세요  `, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return;
    }

    console.log({
      content,
      previewImage,
    });
  }

  //구매 목록에서 선택한 것들
  const handleChange = (event: SelectChangeEvent) => {
    setValue(event.target.value as string);
    let item: any;
    for (let i = 0; i < order_list.length; i++) {
      if (order_list[i].product_name === event.target.value) {
        item = {
          product_name: order_list[i].product_name,
          product_id: order_list[i].product_id,
        };
        setProducts([...products, item] as Item[]);
      }
    }
    console.log(products);
  };

  //내용 핸들러
  const handleContent = (event: ChangeEvent<HTMLInputElement>) => {
    const inputText = event.target.value;
    if (inputText.length <= 100) {
      setContent(inputText);
    } else {
      event.target.value = content;
    }
  };

  // 선택한 구매목록 제거
  const handleDelete = (productId: number) => {
    setProducts((products: any) =>
      products.filter((item: any) => item.product_id !== productId)
    );
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

        {/* 구매내역선택 */}
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">구매내역</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="orderList"
            onChange={handleChange}
            value={value}
          >
            {order_list.map((item, i) => (
              <MenuItem value={item.product_name} key={i}>
                {item.product_name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        {/* 선택한 구매 내역 */}
        <Stack direction={"row"} flexWrap="wrap">
          {products &&
            products.map((item, i) => (
              <Chip
                label={item.product_name}
                key={i}
                variant="outlined"
                style={{ margin: "3px" }}
                onDelete={() => {
                  handleDelete(item.product_id);
                }}
              />
            ))}
        </Stack>

        {/* 내용등록 */}
        <div>
          <TextField
            fullWidth
            id="outlined-multiline-flexible"
            label="내용"
            multiline
            rows={4}
            onChange={handleContent}
          />
          <InlineText color="grey">글자수 : {content.length}/100</InlineText>
        </div>
        <Button
          style={{ backgroundColor: "black", color: "white", padding: "20px" }}
          fullWidth
          onClick={submitHandler}
        >
          작성
        </Button>
      </Stack>
    </Background>
  );
};

export default SnapshotRegist;

const Background = styled.div`
  background-color: #dddddd;
  padding: 50px 10px 50px 10px;
  box-sizing: border-box;
  width: 100%;
  height: 100vh;
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
