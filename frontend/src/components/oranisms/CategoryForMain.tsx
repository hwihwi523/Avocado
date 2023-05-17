import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import Link from "next/link";
import { category_list } from "../atoms/data";
import Image from "next/image";
import router from "next/router";

//할일 링크 설정하기
const iconStyle = {
  textAlign: "center",
  fontWeight: "bold",
};

const CategoryForMain: React.FC<{}> = () => {
  const icons = ["전체", "상의", "하의", "원피스", "신발", "백", "액세서리"];

  return (
    <IconBox>
      <Grid container justifyContent={"space-around"}>
        {icons.map((item: string, i: number) => (
          <Grid
            item
            sx={iconStyle}
            key={item}
            onClick={() => {
              // 검색창으로 보내고 선택된 카테고리의 검색 결과 보여주기
              router.push({
                pathname: `/search`,
                query: { categoryName: `${item}` },
              });
            }}
          >
            <Image
              src={`/assets/icons/${item}.png`}
              alt={item}
              width={40}
              height={40}
            />
            <MenuText>{item}</MenuText>
          </Grid>
        ))}
      </Grid>
    </IconBox>
  );
};

export default CategoryForMain;

const IconBox = styled.div`
  margin: 10px auto;
`;

const MenuText = styled.p`
  font-family: SeoulNamsanL;
  font-size: 12px;
  margin-top: 10px;
  color: gray;
`;
