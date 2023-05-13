import styled from "@emotion/styled";
import { Stack, Chip, IconButton } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../atoms";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";

import { useState } from "react";
import { SnapshotItem as snapshotItemType } from "@/src/features/snapshot/snapshotApi";


const SnapshotItem:React.FC<{data:snapshotItemType}> = (props) => {
  const item = props.data;


  const [isLike, setIsLike] = useState(false);


  function likeHandler() {
    setIsLike(!isLike);
  }

  return (
    <Stack spacing={1}>
      {/* 스넵샷 이미지 */}
      <Imagebox>
        <Image
          src={item.picture_url}
          alt="제품 이미지"
          fill
          style={{ objectFit: "cover" }}
        />
      </Imagebox>

      {/* 제품 링크 */}
      <Stack  spacing={1} margin={"10px 0"}>
        {item.wears.map((item, i) => (
          <Chip key={i} label={item.name} variant="outlined" />
        ))}
      </Stack>

      {/* 유저 정보 */}
      <Stack direction={"row"} spacing={2} alignItems={"center"}>
        <Image
          width={50}
          height={50}
          alt="아바타 이미지"
          src={`/assets/avatar/${"winter_man"}.png`}
        />
        <Stack style={{ color: "gray", width: "100%" }}>
          <div>
            <Stack
              direction={"row"}
              justifyContent={"space-between"}
              alignItems={"center"}
            >
              <Stack>
                <InlineText>{"김싸피"} </InlineText>
                <InlineText color="grey" type="L" size="0.8rem">
                  {"ISTP"} / {"가을뮤트"}
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
          </div>
        </Stack>
      </Stack>

      {/* 글 내용 300자 제한임  */}
      <BlockText style={{ padding: "10px", textAlign: "justify", wordWrap:"break-word" }}>
        {item.content}
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
