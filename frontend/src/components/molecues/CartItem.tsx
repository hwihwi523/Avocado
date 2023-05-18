import styled from "@emotion/styled";
import { Stack, IconButton } from "@mui/material";
import Image from "next/image";
import { BlockText } from "../atoms";
import Grid from "@mui/material/Grid";
import router from "next/router";
import ClearIcon from "@mui/icons-material/Clear";
import StarIcon from "@mui/icons-material/Star";
import { ProductForCart } from "@/src/features/product/productSlice";
import { AppState, useAppSelector } from "@/src/features/store";
import {
  useGetCartQuery,
  useRemoveCartMutation,
} from "@/src/features/product/productApi";

const CartItem: React.FC<{ data: ProductForCart }> = (props) => {
  const {
    brand_name,
    cart_id,
    quantity,
    discounted_price,
    image_url,
    merchandise_category,
    merchandise_id,
    merchandise_name,
    price,
    score,
    size,
    age_group,
    mbti,
    personal_color,
  } = props.data;

  const member = useAppSelector((state: AppState) => state.auth.member);

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function pageMove() {
    router.push("product/" + merchandise_id);
  }

  // 장바구니에서 제거
  const [removeFromCart, result] = useRemoveCartMutation();
  const { refetch } = useGetCartQuery();
  function removeBtnHandler() {
    if (!member) {
      return;
    }
    removeFromCart(cart_id)
      .unwrap()
      .then((res) => {
        console.log(res);
        refetch();
      })
      .catch((e) => console.log("REMOVE CART ERROR: ", e));
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
                  onClick={removeBtnHandler}
                >
                  <ClearIcon />
                </IconButton>
              </Stack>
              <BlockText>{merchandise_name}</BlockText>
              <BlockText style={{ fontSize: "0.9rem" }}>
                <StarIcon style={{ fontSize: "1.1rem" }} /> {score}
              </BlockText>
              <BlockText
                color="grey"
                style={{
                  textAlign: "right",
                  textDecoration: "line-through",
                  fontSize: "0.9rem",
                }}
              >
                {formatCurrency(price)}
              </BlockText>

              <Stack
                direction={"row"}
                alignItems={"center"}
                justifyContent={"space-between"}
              >
                <BlockText type="L" color="grey">
                  {size} {quantity}개
                </BlockText>
                <BlockText color="red" size="1.1rem" type="L">
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

export default CartItem;

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
