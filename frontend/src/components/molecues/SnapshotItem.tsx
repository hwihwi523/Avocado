import styled from "@emotion/styled";
import { Stack, Chip, IconButton } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../atoms";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";

import { useState } from "react";

type Item = {
  img_url: string;
  like: boolean;
  name: string;
  products: string[];
  avatar: string;
  mbti: string;
  personal_color: string;
  content: string;
  
};

const SnapshotItem:React.FC<Item> = ({
    like,
    name,
    products,
    avatar,
    mbti,
    personal_color,
    content,
    img_url,
  }) => {

  const [isLike, setIsLike] = useState(like);


  function likeHandler() {
    setIsLike(!isLike);
  }

  return (
    <Stack spacing={1}>
      {/* 스넵샷 이미지 */}
      <Imagebox>
        <Image
          src={img_url}
          alt="제품 이미지"
          fill
          style={{ objectFit: "cover" }}
        />
      </Imagebox>

      {/* 제품 링크 */}
      <Stack direction={"row"} spacing={1} margin={"10px 0"}>
        {products.map((item, i) => (
          <Chip key={i} label={item} variant="outlined" />
        ))}
      </Stack>

      {/* 유저 정보 */}
      <Stack direction={"row"} spacing={2} alignItems={"center"}>
        <Image
          width={50}
          height={50}
          alt="아바타 이미지"
          src={`/assets/avatar/${avatar}.png`}
        />
        <Stack style={{ color: "gray", width: "100%" }}>
          <BlockText>
            <Stack
              direction={"row"}
              justifyContent={"space-between"}
              alignItems={"center"}
            >
              <Stack>
                <InlineText>{name} </InlineText>
                <InlineText color="grey" type="L" size="0.8rem">
                  {mbti} / {personal_color}
                </InlineText>
              </Stack>
              <div>
                <IconButton aria-label="delete" onClick={likeHandler}>
                  {isLike ? (
                    <FavoriteBorderIcon fontSize="large" />
                  ) : (
                    <FavoriteIcon fontSize="large" />
                  )}
                </IconButton>
              </div>
            </Stack>
          </BlockText>
        </Stack>
      </Stack>

      {/* 글 내용 100자 제한임  */}
      <BlockText style={{ padding: "10px", textAlign: "justify" }}>
        {content}
      </BlockText>
    </Stack>
  );
};

export default SnapshotItem;

const Imagebox = styled.div`
  position: relative; //이게 제일 중요함
  width: 100%;
  height: 400px;
  margin-bottom: 10px;
`;
