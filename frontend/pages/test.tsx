import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Divider from '@mui/material/Divider';



//글씨 적용해야함 

const UserProfile = (props: any) => {
  const { avatar, mbti, persenalColor, grade } = props;

  const name = "김범식";

  // 각 펄스널 컬레에 대한 인덱싱이 들어있어야 한다. 컬러코드하고 봄여름가을경을 그에 대한 값도..

  //인덱싱으로 들어올 것을 염두하고 작성하는 중
  //그게 아니라면 좀더 계층적으로 했을 거임
  const personalColor = [
    {
      name: "spring warm",
      type: "light",
      colors: ["FBE5D7", "FFF2CD", "DFF7C3", "CDF9FC", "CBCCFE"],
    },
    {
      name: "spring warm",
      type: "bright",
      colors: ["E7352B", "F47C57", "FFDC50", "1097E9", "6AA344 "],
    },
    {
      name: "summer cool",
      type: "light",
      colors: ["FFE0FF", "CACEFB", "D8C0FE", "99C4F9", "DEEAF6"],
    },
    {
      name: "summer cool",
      type: "bright",
      colors: ["E54899", "377AFF", "A667D2", "92A8FD", "48D4C7"],
    },
    {
      name: "summer cool",
      type: "mute",
      colors: ["48D4C7", "C390B1", "94AFCD", "A2D1BF", "828282"],
    },
    {
      name: "autumn warm",
      type: "mute",
      colors: ["DEC769", "E3BB97", "9BB182", "73A8AE", "C36438"],
    },
    {
      name: "autumn warm",
      type: "strong",
      colors: ["870001", "652621", "744706", "454A12", "00506B"],
    },
    {
      name: "autumn warm",
      type: "deep",
      colors: ["C55B11", "BF9100", "4D7630", "A80000", "00506B"],
    },
    {
      name: "winter cool",
      type: "bright",
      colors: ["E623A5", "0E17D6", "1A9F72", "0D0D0D", "F2F2F2"],
    },
    {
      name: "winter cool",
      type: "deep",
      colors: ["AC0056", "9F103C", "37005D", "08362C", "1E0D2F"],
    },
  ];

  return (
    <>
      <Background>
        <Grid container alignItems={"center"}>
          <Grid item xs={7} style={{marginBottom:"30px"}}>
            <AvatarDiv>
              <ColorBox left="70px" color="FFF2CD"/>
              <ColorBox left="50px" color="cbccfe"/>
              <ColorBox left="30px" color="DFF7C3"/>
              <img
                src="asset/avatar/spring-man.png"
                width="100px"
                alt="my avatar"
                style={{position:"absolute"}}
              />
            </AvatarDiv>
          </Grid>
          <Grid item xs={5}>
            <Typography variant="caption">
              <StyledSpan>{name}</StyledSpan>님 반갑습니다.
            </Typography>
          </Grid>
          <Grid item xs={12}>
          <Stack
        direction="row"
        divider={<Divider orientation="vertical" flexItem />}
        spacing={6}
        alignItems={"top"}
        justifyContent={"center"}
        style={{}}
      >
        <TextDiv>
            <StyledTitle>MBTI</StyledTitle>
            <br/>
            <Typography variant="subtitle2">INFJ</Typography>
        </TextDiv>
        <TextDiv>
            <StyledTitle>Color</StyledTitle>
            <br/>
            <Typography variant="subtitle2">Spring<br/>bright</Typography>
        </TextDiv><TextDiv>
            <StyledTitle>Grade</StyledTitle>
            <br/>
            <Typography variant="subtitle2">1.LV</Typography>
        </TextDiv>
      </Stack>

          </Grid>
        </Grid>
      </Background>
    </>
  );
};

export default UserProfile;

type StyleProps={
    left:string,
    color:string
}




const Background = styled.div`
  border: 1px solid #dddddd;
  border-radius: 10px;
  padding: 40px 10px;
  box-sizing: border-box;
`;

const AvatarDiv = styled.div`
margin-left:30px;
    position:relative;
    height:120px;
  padding: 10px;
  box-sizing: border-box;
`;

const StyledSpan = styled.span`
  font-weight: bold;
  font-size: 16px;
`;

const ColorBox =styled.span<StyleProps>`
    position:absolute;
    width:100px;
    height:100px;
    border-radius :100px;
    background-color:#${props => props.color};
    left:${props => props.left}
    
`

const TextDiv = styled.div`
    text-align:center;
    padding: 5px;
    box-sizing: border-box;
`

const StyledTitle = styled.p`
    color:gray;
    margin-bottom:0px;
`