import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
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
import {
  useAddWishlistMutation,
  useRemoveWishlistMutation,
} from "@/src/features/product/productApi";
import { useSnackbar } from "notistack";

const ProductCard = (props: any) => {
  const router = useRouter();
  const { enqueueSnackbar } = useSnackbar();
  const { id, img_url, price, discount, brand, isBookmark, tags } = props.data;

  const [bookmark, setBookmark] = useState(isBookmark);

  const [removeWishList] = useRemoveWishlistMutation();
  const [addWishList] = useAddWishlistMutation();

  function pageMove() {
    router.push({
      pathname: `/product/${id}`,
    });
  }

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function addWishListHandler() {
    addWishList(id)
      .unwrap()
      .then((res) => {
        console.log(res);
        setBookmark(true);
      })
      .catch((err) => {
        enqueueSnackbar(`로그인이 필요한 서비스입니다. `, {
          variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
          anchorOrigin: {
            horizontal: "center", //(left, center, right)
            vertical: "top", //top, bottom
          },
        });
        console.log(err);
      });
  }

  function removeWishListHandler() {
    removeWishList(id)
      .unwrap()
      .then((res) => {
        console.log(res);
        setBookmark(false);
      })
      .catch((err) => {
        console.log(err);
      });
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
              {formatCurrency(price)}
            </InlineText>
          </Grid>

          {/* 원가 && 할인률 */}
          <Grid item xs={12}>
            <Stack direction={"row"} justifyContent={"space-between"}>
              <InlineText size="1.2rem" type="L">
                {formatCurrency(discount)}
              </InlineText>
              <InlineText size="1.2rem" type="L" color="red">
                {Math.ceil(((price - discount) / price) * 100)}%
              </InlineText>
            </Stack>
          </Grid>
        </Grid>
      </Card>

      {/* 북마크 버튼 */}
      {isBookmark !== null && (
        <IconButton
          style={{
            position: "absolute",
            right: "20px",
            bottom: "45%",
            backgroundColor: "black",
            color: "white",
            opacity: "0.5",
          }}
        >
          <Tooltip title="찜하기" placement="top">
            {bookmark ? (
              <BookmarkOutlinedIcon
                fontSize="small"
                onClick={removeWishListHandler}
              />
            ) : (
              <BookmarkBorderOutlinedIcon
                fontSize="small"
                onClick={addWishListHandler}
              />
            )}
          </Tooltip>
        </IconButton>
      )}
    </Background>
  );
};

export default ProductCard;

const Background = styled.div`
  position: relative;
  width: 100%;
  min-width: 170px;
`;

const Card = styled.div`
  width: 100%;
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
