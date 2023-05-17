import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import { IconButton } from "@mui/material";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";

import Button from "@mui/material/Button";
import {
  useAddWishlistMutation,
  useGetIsWishlistQuery,
  useRemoveWishlistMutation,
} from "@/src/features/product/productApi";
import { AppState, useAppSelector } from "@/src/features/store";
import { useRouter } from "next/router";
import { useState } from "react";

const ProductBottom: React.FC<{
  openModal: () => void;
  isWishlist: boolean;
}> = (props) => {
  const router = useRouter();
  const [addWishlist, addWishlistResult] = useAddWishlistMutation();
  const [removeWishlist, removeWishlistResult] = useRemoveWishlistMutation();
  const member = useAppSelector((state: AppState) => state.auth.member);
  const product = useAppSelector(
    (state: AppState) => state.product.selectedProductDetail
  );
  // url 마지막에서 product Id 가져오기
  const lastSegment = router.asPath.split("/").pop();
  const productId = parseInt(lastSegment || "-1");
  // 상품 상세 정보 -> 찜 상태 가져오기
  const { refetch } = useGetIsWishlistQuery({
    merchandise_name: product ? product.merchandise_name : "",
  });
  // 상품이 wishlist에 포함되어 있는지 여부 가져오기
  const isWishlist = props.isWishlist;
  // console.log("WISHLIST DATA: ", isWishlist);

  const { openModal } = props;

  // 버튼 비활성화
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);
  // 클릭 이벤트 중복 체크
  const [isButtonClicked, setIsButtonClicked] = useState(false);

  async function WishlistBtnClickHandler() {
    if (isButtonClicked) {
      return; // 클릭 이벤트 중복 시, 함수 실행 중단
    }
    setIsButtonClicked(true);

    if (member) {
      if (isWishlist) {
        setIsButtonDisabled(true);
        // 삭제
        await removeWishlist(productId)
          .unwrap()
          .then((res) => {
            console.log(res);
          })
          .catch((e) => console.log("REMOVE WISHLIST ERROR: ", e))
          .finally(() => {
            setIsButtonDisabled(false); // 버튼 다시 활성화
          });
      } else {
        setIsButtonDisabled(true);
        // 추가
        await addWishlist(productId)
          .unwrap()
          .then((res) => {
            console.log(res);
          })
          .catch((e) => console.log("ADD WISHLIST ERROR: ", e))
          .finally(() => {
            setIsButtonDisabled(false); // 버튼 다시 활성화
          });
      }
    }
    refetch(); // [id] 페이지에서 getProductDetailQeeury를 refetch
    setIsButtonClicked(false); // 클릭 완료 후 버튼 클릭 상태 해제
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
            disabled={isButtonDisabled}
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
  bottom: 50px;
  height: 70px;
  width: 100%;
  padding: 0 10px;
`;
