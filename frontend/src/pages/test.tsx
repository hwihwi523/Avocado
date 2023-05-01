import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../components/molecues";

const DetailProductImage = () => {
  const images = [
    "https://cdn.pixabay.com/photo/2023/03/16/15/00/woman-7856919_960_720.jpg",
    "https://cdn.pixabay.com/photo/2023/04/28/12/18/dogs-7956516_960_720.jpg",
    "https://cdn.pixabay.com/photo/2022/10/06/13/17/monks-7502654_960_720.jpg",
  ];

  return (
    <>
      <Imagebox>
        <img
          src={images[0]}
          alt="제품 이미지"
          style={{
            objectFit: "cover",
            objectPosition: "center",
            height: "100%",
          }}
        />
      </Imagebox>
    </>
  );
};

export default DetailProductImage;

const Imagebox = styled.div`
  width: 100%;
  height: 180px;npm 
  overflow: hidden;
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
`;
