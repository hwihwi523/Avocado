import styled from "@emotion/styled";
import { Stack, IconButton, Button } from "@mui/material";
import Image from "next/image";
import { BlockText } from "../atoms";
import Grid from "@mui/material/Grid";
import router from "next/router";
import ClearIcon from "@mui/icons-material/Clear";
import StarIcon from "@mui/icons-material/Star";
import {
  ProductForWishlist,
  setProductListForWishlist,
} from "@/src/features/product/productSlice";
import { wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import {
  productApi,
  useGetWishlistQuery,
  useRemoveWishlistMutation,
} from "@/src/features/product/productApi";

const WishItem: React.FC<{ item: ProductForWishlist }> = (props) => {
  const {
    brand_name,
    merchandise_id,
    merchandise_category,
    image_url,
    merchandise_name,
    price,
    discounted_price,
    score,
    mbti,
    personal_color,
    age_group,
    wishlist_id,
  } = props.item;

  const [removeWishlistItem, result] = useRemoveWishlistMutation();
  const { refetch } = useGetWishlistQuery();

  // 숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function pageMove() {
    router.push("product/" + merchandise_id);
  }

  async function removeBtnClickHandler() {
    // 찜 목록에서 제거
    removeWishlistItem(merchandise_id)
      .unwrap()
      .then((res) => {
        console.log("REMOVE WISHLIST SUCCESS: ", res);
        refetch();
      })
      .catch((e) => console.log("REMOVE WISHLIST ERROR: ", e));
  }

  return (
    <Background>
      <Grid container style={{ height: "100%" }} gap={1}>
        <Grid item xs={4}>
          <ImageBox>
            <Image
              onClick={pageMove}
              src={image_url}
              alt="제품 이미지"
              fill
              style={{ objectFit: "cover" }}
            />
          </ImageBox>
        </Grid>
        <Grid item xs={7}>
          <div>
            <Stack style={{ width: "100%" }}>
              <Stack
                direction={"row"}
                alignItems={"center"}
                justifyContent={"space-between"}
              >
                <BlockText size="0.6rem" type="L">
                  {brand_name}
                </BlockText>

                <IconButton
                  aria-label="clearIcon"
                  style={{ margin: "0px", padding: "0px" }}
                  onClick={removeBtnClickHandler}
                >
                  <ClearIcon />
                </IconButton>
              </Stack>
              <BlockText>{merchandise_name}</BlockText>
              <BlockText>
                <StarIcon /> {score}
              </BlockText>
              <BlockText
                color="grey"
                style={{ textAlign: "right", textDecoration: "line-through" }}
              >
                {formatCurrency(price)}
              </BlockText>

              <Stack
                direction={"row"}
                alignItems={"center"}
                justifyContent={"space-between"}
              >
                <BlockText type="L" color="grey"></BlockText>
                <BlockText color="red" size="1.5rem" type="L">
                  {formatCurrency(discounted_price)}
                </BlockText>
              </Stack>
            </Stack>
          </div>
        </Grid>
      </Grid>
    </Background>
  );
};

export default WishItem;

const Background = styled.div`
  width: 100%;
  border-radius: 10px;
  padding: 10px;
  box-sizing: border-box;
  background-color: white;
`;

const ImageBox = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;
