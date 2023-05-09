import styled from "@emotion/styled";
import { Stack,IconButton,Button } from "@mui/material";
import Link from 'next/link'
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import Image from "next/image";
const MobileHeader = (props: any) => {
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
        
      <Button style={{backgroundColor:"black", color:"white"}}>
        <Link href="/seller">
        판매자 페이지
        </Link>
      </Button>


        {/* 장바구니  */}
        <Link href="/user/cartList">
          <IconButton>
            <ShoppingCartOutlinedIcon style={{ fontSize: "1.8rem" }} />
          </IconButton>
        </Link>
      </Stack>
    </Background>
  );
};

export default MobileHeader;

const Background = styled.div`
  width: 100%;
  padding: 10px 5px 0px 5px;
`;

const ImageBox = styled.div`
  position: relative;
  width: 150px;
  height: 30px;
`;
