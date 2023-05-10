import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Image from "next/image";
import { Category, ProductCardsGrid } from "@/src/components/oranisms";
import Head from 'next/head'
const Store = () => {
  const img_url = "store_main_image";
  return (
    <Background>
            <Head>
        <title>스토어 이름</title>
        <meta name="description" content=""/>스토어 설
        <meta
          name="keywords"
          content={`제품카테고리, 제품 종류 , 등등`}
        />
        <meta property="og:title" content="제품명" />
        <meta property="og:description" content="제품 설명" />
        <meta property="og:image" content="제품 이미지 url" />
        <meta property="og:url" content="제품 url" />
        <meta property="og:type" content="제품의 타입 등등" />
      </Head>
      {/* 스토어 이미지 */}
      <Stack spacing={3} direction={"column"}>
        <Imagebox>
          <Image
            src={`/assets/exampleImage/${img_url}.png`}
            alt="제품 이미지"
            fill
            style={{ objectFit: "cover" }}
          />
        </Imagebox>

        {/* 카테고리 */}
        <Category />

        {/* 제품들 뭐 하기로 했는데 까먹음  */}
        
        <ProductCardsGrid />
      </Stack>
    </Background>
  );
};
export default Store;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;

const Imagebox = styled.div`
  position: relative;
  width: 100%;
  height: 30vh;
  margin-bottom: 10px;
`;
