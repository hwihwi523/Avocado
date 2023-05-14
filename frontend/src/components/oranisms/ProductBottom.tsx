import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import { useState } from "react";
import { IconButton } from "@mui/material";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";

import Button from "@mui/material/Button";
import {
  useAddWishlistMutation,
  useGetProductDetailQuery,
  useRemoveWishlistMutation,
} from "@/src/features/product/productApi";
import { AppState, useAppSelector } from "@/src/features/store";
import { useRouter } from "next/router";
import { useSelector } from "react-redux";

const ProductBottom: React.FC<{ openModal: () => void }> = (props) => {
  const router = useRouter();
  const [addWishlist, addWishlistResult] = useAddWishlistMutation();
  const [removeWishlist, removeWishlistResult] = useRemoveWishlistMutation();
  const member = useAppSelector((state: AppState) => state.auth.member);
  const product = useSelector(
    (state: AppState) => state.product.selectedProductDetail
  );
  // url 마지막에서 product Id 가져오기
  const lastSegment = router.asPath.split("/").pop();
  // 상품 상세 정보 -> 찜 상태 가져오기
  const { data } = useGetProductDetailQuery(lastSegment!);

  const { openModal } = props;

  const [isWishlist, setIsWishlist] = useState(product?.is_wishlist);

  function WishlistBtnClickHandler() {
    console.log("WISHLIST: ", data);
    if (member) {
      if (isWishlist) {
        // 삭제

        removeWishlist()
          .then((res) => {
            setIsWishlist(false);
            console.log(res);
          })
          .catch((e) => console.log("REMOVE WISHLIST ERROR: ", e));
      } else {
        // 추가
        addWishlist()
          .then((res) => {
            setIsWishlist(true);
            console.log(res);
          })
          .catch((e) => console.log("ADD WISHLIST ERROR: ", e));
      }
    }
  }

  return (
    <Background>
      <Grid
        container
        justifyContent={"space-between"}
        alignItems={"center"}
        style={{ height: "100%" }}
      >
        <Grid item xs={2}>
          <IconButton
            aria-label="fingerprint"
            style={{ color: "black" }}
            size="large"
            onClick={WishlistBtnClickHandler}
          >
            {isWishlist ? (
              <BookmarkOutlinedIcon fontSize="large" />
            ) : (
              <BookmarkBorderOutlinedIcon fontSize="large" />
            )}
          </IconButton>
        </Grid>
        <Grid item xs={10}>
          {/* 검은색바탕에 흰색글씨 */}
          <Button
            style={{
              color: "white",
              width: "100%",
              backgroundColor: "black",
              padding: "10px",
            }}
            onClick={openModal}
          >
            구매하기
          </Button>
        </Grid>
      </Grid>
    </Background>
  );
};

export default ProductBottom;

const Background = styled.div`
  border: 1px solid #dddddd;
  background-color: white;
  z-index: 1000;
  position: fixed;
  bottom: 0px;
  height: 70px;
  width: 100%;
  padding: 0 10px;
`;
