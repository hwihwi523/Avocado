import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Image from "next/image";
import { Category, ProductCardsGrid } from "@/src/components/oranisms";

const Store = () => {
  const img_url = "store_main_image";
  return (
    <Background>
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
