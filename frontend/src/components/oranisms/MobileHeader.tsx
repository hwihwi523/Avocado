import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import IconButton from "@mui/material/IconButton";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import Link from "next/link";

const MobileHeader = (props: any) => {
  return (
      <HeaderBackground>
        <Stack
          direction={"row"}
          justifyContent="space-between"
          alignItems={"center"}
        >
          {/* 로고 이미지 */}
          <Link href="/">
            <img
              src="/assets/images/logo.png"
              height="100%"
              width="150px"
              alt=""
            />
          </Link>

          {/* 장바구니  */}
          <Link href="/user/cartList">
            <IconButton>
              <ShoppingCartOutlinedIcon style={{fontSize:"1.8rem"}} />
            </IconButton>
          </Link>
        </Stack>
      </HeaderBackground>
  );
};

export default MobileHeader;

const HeaderBackground = styled.div`
  width: 100%;
  padding: 10px 5px 0px 5px; 
`;
