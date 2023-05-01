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

const ProductCard = (props: any) => {
  const router = useRouter();

  const { id, img_url, price, discount, brand, isBookmark, tags }= props.data;
  

  const [bookmark, setBookmark] = useState(isBookmark);

  function pageMove() {
    router.push(`product/detail/${id}`);
  }

  return (
    <StyledDiv>
      {/*북마크를 아래에 위치한 박스*/}
      <Card onClick={pageMove}>
        {/* 이미지 */}
        <Imagebox>
          <img
            src={img_url}
            alt="제품 이미지"
            style={{
              objectFit: "cover",
              objectPosition: "center",
              height: "100%",
            }}
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
            <Typography
              variant="body2"
              style={{ margin: "5px 0 30px 0", color: "grey" }}
            >
              {brand}
            </Typography>
          </Grid>

          {/* 원래 가격 */}
          <Grid item xs={12}>
            <Typography
              variant="body2"
              style={{ textDecoration: "line-through", color: "grey" }}
            >
              {price}원
            </Typography>
          </Grid>

          {/* 원가 + 할인률 */}
          <Grid item xs={12}>
            <Stack direction={"row"} justifyContent={"space-between"}>
              <Typography variant="body1">{price - discount}원</Typography>
              <Typography variant="body1" color="error" style={{fontWeight:"bold"}}>
                {Math.ceil((discount / price) * 100)}%
              </Typography>
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
    </StyledDiv>
  );
};

export default ProductCard;

const StyledDiv = styled.div`
  position: relative;
  width: 180px;
`;

const Card = styled.div`
  padding: 10px;
  box-sizing: border-box;
  transition: 0.2s;
  box-shadow: 0 0 0 rgba(0, 0, 0, 0);
  &:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  }
`;

const Imagebox = styled.div`
  width: 100%;
  height: 180px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
`;
