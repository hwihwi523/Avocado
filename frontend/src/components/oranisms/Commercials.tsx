import styled from "@emotion/styled";
import { useRouter } from "next/router";
import Carousel from "react-material-ui-carousel";
import Image from "next/image";
const Commercials = (props: any) => {
  const router = useRouter();

  let items = [
    {
      id: 1,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/25/18/14/mountain-7950729__340.jpg",
    },
    {
      id: 2,
      img_url:
        "https://cdn.pixabay.com/photo/2023/03/30/18/27/animal-7888465__340.jpg",
    },
    {
      id: 3,
      img_url:
        "https://cdn.pixabay.com/photo/2023/03/28/19/55/lake-7884049__340.jpg",
    },
    {
      id: 4,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/16/08/10/flower-7929400__340.jpg",
    },
  ];

  return (
    <Commercial>
      <Carousel animation="slide" indicators={false} interval={2000}>
        {items.map((item, i) => (
          <ImageBox
            key={i}
            onClick={() => {
              router.push("product/" + item.id);
            }}
          >
            <Image
              src={item.img_url}
              alt="광고 이미지"
              fill
              style={{ objectFit: "cover" }}
            />
          </ImageBox>
        ))}
      </Carousel>
    </Commercial>
  );
};

export default Commercials;

const Commercial = styled.div`
  width: 100%;
`;

const ImageBox = styled.div`
  width: 100vw;
  height: 30vh;
`;
