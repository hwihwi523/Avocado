import styled from "@emotion/styled";
import { Stack, Button } from "@mui/material";
import Grid from "@mui/material/Grid";
import { BlockText, InlineText } from "../atoms";
import router from "next/router";
import { Member } from "@/src/features/auth/authSlice";
import { useEffect } from "react";

const ProviderProfile: React.FC<{ member: Member }> = (props) => {
  const { gender, mbti_id, name, personal_color_id, picture_url, grade } =
    props.member;

  function redirectionToSellerPage() {
    router.push("/seller");
  }

  return (
    <>
      <Background>
        <Stack spacing={3}>
          <BlockText style={{ textAlign: "center" }} type="L">
            <InlineText type="B" size="1.1rem">
              {name}
            </InlineText>
            님 반갑습니다.
          </BlockText>

          <Button
            onClick={redirectionToSellerPage}
            style={{
              backgroundColor: "black",
              color: "white",
              padding: "10px",
              width: "100%",
            }}
          >
            판매자 페이지로 이동
          </Button>
        </Stack>
      </Background>
    </>
  );
};

export default ProviderProfile;

type StyleProps = {
  left: string;
  color: string;
};

const Background = styled.div`
  max-width: 1200px;
  border: 1px solid #dddddd;
  border-radius: 10px;
  padding: 30px 90px 30px 90px;
  box-sizing: border-box;
`;
