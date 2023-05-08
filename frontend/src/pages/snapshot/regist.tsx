import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../../components/atoms";
import { Button } from "@mui/material";
import { useState, ChangeEvent } from "react";
import { useSnackbar } from "notistack";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import TextField from "@mui/material/TextField";


const SnapshotRegist = () => {
    const { enqueueSnackbar } = useSnackbar();
    const [previewImage, setPreviewImage] = useState<string | null>(null);
    const [content, setContent] = useState<string>("");
  
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
  
    //콘텐츠 핸들러
    const handleContent = (event: ChangeEvent<HTMLInputElement>) => {
      const inputText = event.target.value;
      if (inputText.length <= 100) {
        setContent(inputText);
      } else {
        event.target.value = content;
      }
    };
  
    return (
      <Background>
        <BlockText type="B" size="1.5rem">
          게시글 작성
        </BlockText>
  
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
  
          {/* 내용등록 */}
          <div>
            <TextField
              fullWidth
              id="outlined-multiline-flexible"
              label="Multiline"
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
    padding: 10px;
    background-color: #dddddd;
    padding: 20% 10px 0 10px;
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
  