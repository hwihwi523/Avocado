import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Link from "next/link";
import Image from "next/image";
//할일 링크 설정하기

const iconStyle = {
  textAlign: "center",
  fontWeight: "bold",
};

const UserProfile = (props: any) => {
  const icons = ["메뉴", "상의", "하의", "원피스", "신발", "백", "액세서리"];

  return (
    <IconBox>
      <Grid container justifyContent={"space-around"}>
        {icons.map((item: string) => (
          <Grid item sx={iconStyle} key={item}>
            <Link href="">
              <Image
                src={`/assets/icons/${item}.png`}
                alt={item}
                width={40}
                height={40}
              />
            </Link>
            <MenuText>{item}</MenuText>
          </Grid>
        ))}
      </Grid>
    </IconBox>
  );
};

export default UserProfile;

const IconBox = styled.div`
  margin: 10px auto;
`;

const MenuText = styled.p`
  font-family: SeoulNamsanL;
  font-size: 12px;
  margin-top: 10px;
  color: gray;
`;
