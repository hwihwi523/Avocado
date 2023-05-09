import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Image from "next/image";
import Rating from "@mui/material/Rating";
import { BlockText, InlineText } from "../atoms";
import { IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

//임시로 만든 날짜 반환 함수
function dateFormat() {
  let today: Date = new Date();
  let year: number = today.getFullYear();
  let month: number | string = today.getMonth() + 1;
  let date: number | string = today.getDate();

  if (month < 10) {
    month = "0" + month;
  }
  if (date < 10) {
    date = "0" + date;
  }

  return `${year}.${month}.${date}`;
}

const Review = (props: any) => {
  const { name, avatar, mbti, personal_color, rate, content } = props;

  return (
    <Background>
      <Grid container p={2} gap={2}>
        <Grid item xs={12}>
          <Stack direction={"row"} spacing={2}>
            <Image
              width={50}
              height={50}
              alt="아바타 이미지"
              src={`/assets/avatar/${avatar}.png`}
            />
            <Stack style={{ color: "gray", width: "100%" }}>
              <BlockText>
                <Stack direction={"row"} justifyContent={"space-between"} alignItems={"center"}>
                  <div>
                    <InlineText>{name} </InlineText>
                    <InlineText color="grey" type="L" size="12px">
                      {mbti} / {personal_color}
                    </InlineText>
                  </div>
                  <div>
                    <InlineText color="grey" type="L" size="12px">
                      {dateFormat()}
                    </InlineText>
                    <IconButton aria-label="delete">
                      <CloseIcon />
                    </IconButton>
                  </div>
                </Stack>
              </BlockText>
              <Rating readOnly name="half-rating" defaultValue={rate} precision={1} />{" "}
            </Stack>
          </Stack>
        </Grid>
        <Grid item xs={12}>
          <BlockText>{content}</BlockText>
        </Grid>
      </Grid>
    </Background>
  );
};

export default Review;

const Background = styled.div`
  border: 1px solid #dddddd;
  width: 100%;
`;
