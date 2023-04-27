import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Image from "next/image";
import IconButton from "@mui/material/IconButton";
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';


const MobileHeader = (props: any) => {
  return (
    <>
      <HeaderBackground>
        <Stack direction={"row"} justifyContent="space-between">
          <Image src="/asset/images/logo.png" height={100} width={100} alt="logo" />
          <IconButton>
            <ShoppingCartOutlinedIcon fontSize="large" />
          </IconButton>
        </Stack>
      </HeaderBackground>
    </>
  );
};

export default MobileHeader;

const HeaderBackground = styled.div`
  width: 100%;
  padding: 5px;
`;
