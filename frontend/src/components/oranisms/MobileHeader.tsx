import { appCookies } from "@/src/pages/_app";
import styled from "@emotion/styled";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import { IconButton, Stack } from "@mui/material";
import Image from "next/image";
import Link from "next/link";
import router from "next/router";
import { useSnackbar } from "notistack";

const MobileHeader = () => {
  // 모든 페이지에서 SSR을 하지 않으므로, 헤더에서 로그인 여부를 판단하기 위해 쿠키 이용
  const token = appCookies.get("ACCESS_TOKEN");

  const { enqueueSnackbar } = useSnackbar();

  function cartBtnClickHandler() {
    if (!token) {
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
              width={150}
              height={30}
              style={{ objectFit: "cover" }}
            />
          </Link>
        </ImageBox>

        {/* 장바구니  */}
        <IconButton onClick={cartBtnClickHandler} className="cart_btn">
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
