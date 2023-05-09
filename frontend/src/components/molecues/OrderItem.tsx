import styled from "@emotion/styled";
import { Stack, IconButton } from "@mui/material";
import Image from "next/image";
import { BlockText } from "../atoms";
import Grid from "@mui/material/Grid";
import router from "next/router";
import ClearIcon from "@mui/icons-material/Clear";
import StarIcon from "@mui/icons-material/Star";

type Item = {
  img_url: string;
  brand_name: string;
  product_name: string;
  product_id: number;
  rating: number;
  size: string;
  count: number;
  price: number;
  discount: number;
  order_date:string;
};

const OrderItem: React.FC<{ data: Item }> = (props) => {
  const {
    img_url,
    brand_name,
    product_name,
    product_id,
    rating,
    size,
    count,
    price,
    discount,
    order_date,
  } = props.data;

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  function pageMove() {
    router.push("product/" + product_id);
  }

  return (
    <Background>
      <Grid container style={{ height: "100%" }} gap={1}>
        <Grid item xs={4}>
          <ImageBox>
            <Image
              onClick={pageMove}
              src={img_url}
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
                <BlockText size="0.8rem" type="L">
                  {brand_name}
                </BlockText>

                <BlockText size="0.8rem" type="L">
                    {order_date}
                </BlockText>
              </Stack>
              <BlockText>{product_name}</BlockText>
              <BlockText>
                <StarIcon /> {rating}
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
                <BlockText type="L" color="grey">
                  {size} {count}개
                </BlockText>
                <BlockText color="red" size="1.5rem" type="L">
                  {formatCurrency(price - discount)}
                </BlockText>
              </Stack>
            </Stack>
          </div>
        </Grid>
      </Grid>
    </Background>
  );
};

export default OrderItem;

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
