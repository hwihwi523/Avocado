import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";

//글씨 적용해야함

const MainUserProfile = (props: any) => {
  // const {  mbti, personalColor, grade } = props;

  //인덱싱으로 들어올 것을 염두하고 작성함 personalColor[1], personalColor[2], personalColor[3] ...
  const personalColors = [
    {
      name: "spring",
      type: "light",
      avatar_path: ["spring_man", "spring_woman"],
      colors: ["FBE5D7", "FFF2CD", "DFF7C3", "CDF9FC", "CBCCFE"],
    },
    {
      name: "spring",
      type: "bright",
      avatar_path: ["spring_man", "spring_woman"],
      colors: ["E7352B", "F47C57", "FFDC50", "1097E9", "6AA344 "],
    },
    {
      name: "summer",
      type: "light",
      avatar_path: ["summer_man", "summer_woman"],
      colors: ["FFE0FF", "CACEFB", "D8C0FE", "99C4F9", "DEEAF6"],
    },
    {
      name: "summer",
      type: "bright",
      avatar_path: ["summer_man", "summer_woman"],
      colors: ["E54899", "377AFF", "A667D2", "92A8FD", "48D4C7"],
    },
    {
      name: "summer",
      type: "mute",
      avatar_path: ["summer_man", "summer_woman"],
      colors: ["48D4C7", "C390B1", "94AFCD", "A2D1BF", "828282"],
    },
    {
      name: "autumn",
      type: "mute",
      avatar_path: ["autumn_man", "autumn_woman"],
      colors: ["DEC769", "E3BB97", "9BB182", "73A8AE", "C36438"],
    },
    {
      name: "autumn",
      type: "strong",
      avatar_path: ["autumn_man", "autumn_woman"],
      colors: ["870001", "652621", "744706", "454A12", "00506B"],
    },
    {
      name: "autumn",
      type: "deep",
      avatar_path: ["autumn_man", "autumn_woman"],
      colors: ["C55B11", "BF9100", "4D7630", "A80000", "00506B"],
    },
    {
      name: "winter",
      type: "bright",
      avatar_path: ["winter_man", "winter_woman"],
      colors: ["E623A5", "0E17D6", "1A9F72", "0D0D0D", "F2F2F2"],
    },
    {
      name: "winter",
      type: "deep",
      avatar_path: ["winter_man", "winter_woman"],
      colors: ["AC0056", "9F103C", "37005D", "08362C", "1E0D2F"],
    },
  ];

  //임시 데이터
  //이데이터 들은 props 로 넘어와야함
  //없으면 다른게 보여야함

  const name = "김싸피";
  const personalcolor = personalColors[1];
  const mbti = "ESTP";

  return (
    <>
      <Background>
        <Grid container alignItems={"center"}>
          {/* 상단 이미지 */}
          <Grid item xs={7} style={{ marginBottom: "10px" }}>
            <AvatarDiv>
              <ColorBox left="70px" color={personalcolor.colors[1]} />
              <ColorBox left="50px" color={personalcolor.colors[2]} />
              <ColorBox left="30px" color={personalcolor.colors[3]} />
              <img
                src={`asset/avatar/${personalcolor.avatar_path[0]}.png`}
                width="100px"
                alt="my avatar"
                style={{ position: "absolute" }}
              />
            </AvatarDiv>
          </Grid>
          <Grid item xs={5}>
            <Typography variant="caption">
              <StyledSpan>{name}</StyledSpan>님 반갑습니다.
            </Typography>
          </Grid>

          {/* 하단 메뉴 */}
          <Grid item xs={12}>
            <Stack
              direction="row"
              divider={<Divider orientation="vertical" flexItem />}
              spacing={4}
              alignItems={"top"}
              justifyContent={"center"}
            >
              <TextDiv>
                <StyledTitle>MBTI</StyledTitle>
                <br />
                <Typography variant="subtitle2" fontWeight={"bold"}>
                  {mbti}
                </Typography>
              </TextDiv>
              <TextDiv>
                <StyledTitle>Color</StyledTitle>
                <br />
                <Typography variant="subtitle2" fontWeight={"bold"}>
                  {personalcolor.name}
                  <br />
                  {personalcolor.type}
                </Typography>
              </TextDiv>
              <TextDiv>
                <StyledTitle>Grade</StyledTitle>
                <br />
                <Typography variant="subtitle2" fontWeight={"bold"}>
                  1.LV
                </Typography>
              </TextDiv>
            </Stack>
          </Grid>
        </Grid>
      </Background>
    </>
  );
};

export default MainUserProfile;

type StyleProps = {
  left: string;
  color: string;
};

const Background = styled.div`
  max-width: 390px;
  border: 1px solid #dddddd;
  border-radius: 10px;
  padding: 30px 10px;
  box-sizing: border-box;
`;

const AvatarDiv = styled.div`
  margin-left: 10px;
  position: relative;
  height: 120px;
  padding: 10px;
  box-sizing: border-box;
`;

const StyledSpan = styled.span`
  font-weight: bold;
  font-size: 16px;
`;

const ColorBox = styled.span<StyleProps>`
  position: absolute;
  width: 100px;
  height: 100px;
  border-radius: 100px;
  background-color: #${(props) => props.color};
  left: ${(props) => props.left};
`;

const TextDiv = styled.div`
  text-align: center;
  padding: 5px;
  box-sizing: border-box;
`;

const StyledTitle = styled.p`
  color: gray;
  margin-bottom: 0px;
`;
