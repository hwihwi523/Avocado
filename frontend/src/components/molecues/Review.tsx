import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Image from "next/image";
import Rating from "@mui/material/Rating";

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

const Review = () => {
  const name = "김범식";
  const avatar = "autumn_man";
  const mbti = "ISTP";
  const personal_color = "가을뮤트";
  const rate = 4;
  const content = "음식이 친절하고 사장님이 맛있어요.";

  return (
    <Background>
      <Grid container p={2} gap={3}>
        <Grid item>
          <Stack direction={"row"} spacing={3}>
            <Image
              width={50}
              height={50}
              alt="아바타 이미지"
              src={`/asset/avatar/${avatar}.png`}
            />
            <Stack style={{ color: "gray" }}>
              <Typography variant="body1">
                <StyledSpan>{name} </StyledSpan>
                {mbti} / {personal_color}
              </Typography>
              <Typography alignItems={"center"}>
                <Rating name="half-rating" defaultValue={4} precision={1} />{" "}
                {dateFormat()}
              </Typography>
            </Stack>
          </Stack>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="body1">{content}</Typography>
        </Grid>
      </Grid>
    </Background>
  );
};

export default Review;
const StyledSpan = styled.span`
  font-weight: bold;
  margin-right:5px;
  font-size:20px;
  color:black;
`;

const Background = styled.div`
  border: 1px solid #dddddd;
  width: 100%;
`;
