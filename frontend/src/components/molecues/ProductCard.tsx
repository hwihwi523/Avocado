import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";

import Stack from "@mui/material/Stack";
import Chip from "@mui/material/Chip";
import Tooltip from "@mui/material/Tooltip";

import IconButton from "@mui/material/IconButton";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";
import { useState } from "react";
import { useRouter } from "next/router";
import Image from "next/image";

import { BlockText, InlineText } from "../atoms";

const ProductCard = (props: any) => {
  const router = useRouter();

  const { id, img_url, price, discount, brand, isBookmark, tags } = props.data;

  const [bookmark, setBookmark] = useState(isBookmark);

  function pageMove() {
    router.push(`product/${id}`);
  }

  return (
    <Background>
      {/*북마크를 아래에 위치한 박스*/}
      <Card onClick={pageMove}>
        {/* 이미지 */}
        <Imagebox>
          <Image
            src={img_url}
            alt="제품 이미지"
            fill
            style={{ objectFit: "cover" }}
          />
        </Imagebox>

        <Grid container>
          {/*태그*/}
          <Grid item xs={12}>
            <Stack direction="row" spacing={1}>
              {tags.map((item: string) => (
                <Chip
                  size="small"
                  label={item}
                  key={item}
                  variant="outlined"
                  style={{ fontSize: "8px" }}
                />
              ))}
            </Stack>
          </Grid>

          {/* 브랜드 */}
          <Grid item xs={12}>
            <BlockText color="grey" style={{ margin: "5px 0 30px 0" }}>
              {brand}
            </BlockText>
          </Grid>

          {/* 원래 가격 */}
          <Grid item xs={12}>
            <InlineText
              type="L"
              color="grey"
              style={{ textDecorationLine: "line-through" }}
            >
              {price}원
            </InlineText>
          </Grid>

          {/* 원가 && 할인률 */}
          <Grid item xs={12}>
            <Stack direction={"row"} justifyContent={"space-between"}>
              <InlineText size="1.2rem" type="L">
                {price - discount}원
              </InlineText>
              <InlineText size="1.2rem" type="L" color="red">
                {Math.ceil((discount / price) * 100)}%
              </InlineText>
            </Stack>
          </Grid>
        </Grid>
      </Card>

      {/* 북마크 버튼 */}
      <IconButton
        style={{
          position: "absolute",
          right: "20px",
          bottom: "45%",
          backgroundColor: "black",
          color: "white",
          opacity: "0.5",
        }}
        onClick={() => {
          setBookmark(!bookmark);
        }}
      >
        <Tooltip title="찜하기" placement="top">
          {bookmark ? (
            <BookmarkOutlinedIcon fontSize="small" />
          ) : (
            <BookmarkBorderOutlinedIcon fontSize="small" />
          )}
        </Tooltip>
      </IconButton>
    </Background>
  );
};

export default ProductCard;

const Background = styled.div`
  position: relative;
`;

const Card = styled.div`
width:240px;
  padding: 10px;
  box-sizing: border-box;
  transition: 0.2s;
  box-shadow: 0 0 0 rgba(0, 0, 0, 0);
  &:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  }
`;

const Imagebox = styled.div`
  position: relative;
  width: 100%;
  height: 200px;
  margin-bottom: 10px;
`;
