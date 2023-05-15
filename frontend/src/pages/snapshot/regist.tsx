import styled from "@emotion/styled";
import { Stack, Chip, Box } from "@mui/material";
import Image from "next/image";
import { BlockText } from "../../components/atoms";
import { Button } from "@mui/material";
import { useState, ChangeEvent } from "react";
import { useSnackbar } from "notistack";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import TextField from "@mui/material/TextField";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
import {
  useAddSnapshotMutation,
  useGetOrderListQuery,
} from "@/src/features/snapshot/snapshotApi";
import LinearProgress from "@mui/material/LinearProgress";
import router from "next/router";
//구매 목록 더미 데이터

type Item = {
  merchandise_id: number;
  merchandise_name: string;
};

const SnapshotRegist = () => {
  const { data: orderList, isLoading } = useGetOrderListQuery();

  const [
    addSnapshot,
    { isLoading: addSnapshotLoading, error: addSanpshotError },
  ] = useAddSnapshotMutation();

  const { enqueueSnackbar } = useSnackbar();
  const [value, setValue] = useState("");
  const [picture, setPicture] = useState<any>(null);
  const [previewImage, setPreviewImage] = useState<string | null>(null);
  const [content, setContent] = useState<string>(
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
  );
  const [products, setProducts] = useState<Item[]>([]);

  //이미지 핸들러 => 선택한 이미지 화면에 보이게 하기
  const handleImageUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    setPicture(file);
    if (file) {
      //파일 읽어오기
      const reader = new FileReader();

      reader.onloadend = () => {
        setPreviewImage(reader.result as string);
      };

      reader.readAsDataURL(file);
    } else {
      //파일 없으면 표시 안해줌
      setPreviewImage(null);
    }
  };

  //제출 함수
  function submitHandler() {
    //이미지 에러 처리
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

    //내용 에러 처리
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

    //보낼 데이터 셋팅
    const formData = new FormData();
    formData.set("picture", picture);
    formData.set("content", content);
    products.forEach((item) => {
      formData.append("wears", item.merchandise_id.toString());
    });

    // 스넵샷 등록하기
    addSnapshot(formData)
      .unwrap()
      .then((res) => {
        enqueueSnackbar(`게시물을 등록했습니다. `, {
          variant: "success",
          anchorOrigin: {
            horizontal: "center",
            vertical: "top",
          },
        });
        router.replace("/snapshot");

        return;
      })
      .catch((err) => {
        console.log(err);
        enqueueSnackbar(`등록 실패했습니다 `, {
          variant: "error",
          anchorOrigin: {
            horizontal: "center",
            vertical: "top",
          },
        });
        return;
      });
  }

  //구매 목록에서 선택한 것들
  const handleChange = (event: SelectChangeEvent) => {
    setValue(event.target.value);

    if (orderList) {
      for (let i = 0; i < orderList.data.content.length; i++) {
        //내가 구매목록에서 선택한것을 products에 넣어줌 => 이건 선택한 제품으로 등록됨
        if (orderList.data.content[i].merchandise_name === event.target.value) {
          let item = {
            merchandise_name: orderList.data.content[i].merchandise_name,
            merchandise_id: orderList.data.content[i].merchandise_id,
          };
          setProducts((preState) => [...preState, item]);
          return;
        }
      }
    }
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
  const handleDelete = (merchandise_name: string) => {
    setProducts((products: any) =>
      products.filter((item: any) => item.merchandise_name !== merchandise_name)
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
            {orderList && orderList.data.content && orderList.data.content ? (
              orderList.data.content.map((item, i) => (
                <MenuItem value={item.merchandise_name} key={i}>
                  {item.merchandise_name}
                </MenuItem>
              ))
            ) : (
              <MenuItem style={{ textAlign: "center" }}>
                <Box sx={{ width: "100%" }}>
                  <LinearProgress />
                </Box>
              </MenuItem>
            )}
          </Select>
        </FormControl>
        {/* 선택한 구매 내역 */}
        <Stack direction={"row"} flexWrap="wrap">
          {products &&
            products.map((item, i) => (
              <Chip
                label={item.merchandise_name}
                key={i}
                variant="outlined"
                style={{ margin: "3px" }}
                onDelete={() => {
                  handleDelete(item.merchandise_name);
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
            value={content}
            onChange={handleContent}
          />
          <BlockText color="grey">글자수 : {content.length}/100</BlockText>
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

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    // 필요한 내용 작성

    //유저정보 가져오기
    // console.log(">>>>>>>>>>>",store.getState().auth.member);

    //함수 불러오기
    // store.dispatch(
    //   productApi.endpoints.getProductDetail.initiate(lastSegment)
    // );

    //store에 집어 넣기
    // store.dispatch(setProductListBySearch());

    //
    return {
      props: {},
    };
  }
);

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
