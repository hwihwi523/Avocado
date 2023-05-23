import { Box, Button } from "@mui/material";
import Image from "next/image";
import router from "next/router";
import { BlockText } from "../components/atoms";

const Custom404: React.FC = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
        textAlign: "center",
      }}
    >
      <Image
        alt="404 image"
        src={"/assets/images/avocado_404.png"}
        width={175}
        height={70}
      ></Image>
      <BlockText
        style={{ fontFamily: "SeoulNamsanEB", marginBottom: -10 }}
        size="4rem"
      >
        404
      </BlockText>
      <BlockText style={{ fontFamily: "SeoulNamsanB" }} size="2rem">
        Not Found
      </BlockText>
      <BlockText size="0.7rem">
        죄송합니다. 페이지를 찾을 수 없습니다. <br />
        존재하지 않는 주소를 입력하셨거나, <br />
        요청하신 페이지의 주소가 <br />
        변경, 삭제되어 찾을 수 없습니다.
      </BlockText>
      <Button
        variant="outlined"
        style={{ marginTop: "2rem" }}
        color="error"
        onClick={() => router.replace("/")}
      >
        메인
      </Button>
    </Box>
  );
};

export default Custom404;
