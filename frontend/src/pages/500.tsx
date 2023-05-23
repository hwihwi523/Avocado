import { Box, Button } from "@mui/material";
import Image from "next/image";
import router from "next/router";
import { BlockText } from "../components/atoms";

const Custom500: React.FC = () => {
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
        500
      </BlockText>
      <BlockText style={{ fontFamily: "SeoulNamsanB" }} size="2rem">
        Sever Error
      </BlockText>
      <BlockText size="0.7rem">
        죄송합니다. 기술적 문제로 서비스에 접속이 되지 않습니다. <br />
        일시적인 현상으로, 현재 담당 부서 확인 중입니다.
        <br />
        잠시 후 다시 이용해 보시면 정상 접속 될 수 있습니다.
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

export default Custom500;
