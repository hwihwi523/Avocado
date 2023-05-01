import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Link from "next/link";


//할일 링크 설정하기

const iconStyle={
  textAlign:"center",
  fontWeight:"bold",
}


const UserProfile = (props: any) => {

  const icons = ["메뉴","상의","하의","원피스","신발","백","액세서리"]
 

  return (
    <IconBox>
    <Grid container justifyContent={"space-around"} >
      {
        icons.map((item:string)=>(
          
          <Grid item sx={iconStyle} key={item}>
            <Link href="">
          <img src={`/asset/icons/${item}.png`} alt="원피스" width="40px"/>
          </Link>
          <Typography variant="subtitle2">{item}</Typography>
          </Grid>
        ))
      }
    </Grid>
    </IconBox>
  );
};

export default UserProfile;

const IconBox = styled.div`
  margin : 10px auto;
`
