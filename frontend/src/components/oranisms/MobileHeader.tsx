import styled from "@emotion/styled";
import { Stack, IconButton, Button } from "@mui/material";
import Link from "next/link";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import Image from "next/image";
import { AppState, useAppSelector } from "@/src/features/store";
import { useSnackbar } from "notistack";
import router from "next/router";

const MobileHeader = (props: any) => {
  const member = useAppSelector((state: AppState) => state.auth.member);

  const { enqueueSnackbar } = useSnackbar();

  function cartBtnClickHandler() {
    if (!member) {
      enqueueSnackbar(`로그인이 필요한 서비스입니다.`, {
        variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
        anchorOrigin: {
          horizontal: "center", //(left, center, right)
          vertical: "top", //top, bottom
        },
      });
      router.push("/login");
      return;
    }
    router.push("/user/cartList");
  }

  return (
    <Background>
      <Stack
        direction={"row"}
        justifyContent="space-between"
        alignItems={"center"}
      >
        {/* 로고 이미지 */}
        <ImageBox>
          <Link href="/">
            <Image
              alt="로고 이미지"
              src="/assets/images/logo.png"
              fill
              style={{ objectFit: "cover" }}
            />
          </Link>
        </ImageBox>

        {/* 장바구니  */}
        <IconButton onClick={cartBtnClickHandler}>
          <ShoppingCartOutlinedIcon style={{ fontSize: "1.8rem" }} />
        </IconButton>
      </Stack>
    </Background>
  );
};

export default MobileHeader;

const Background = styled.div`
  width: 100%;
  padding: 10px 5px 10px 15px;
`;

const ImageBox = styled.div`
  position: relative;
  width: 150px;
  height: 30px;
`;
