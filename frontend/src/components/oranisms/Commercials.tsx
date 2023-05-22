import styled from "@emotion/styled";
import { useRouter } from "next/router";
import Carousel from "react-material-ui-carousel";
import Image from "next/image";

type CommercialItem = {
  merchandise_id: number;
  imgurl: string;
};

const Commercials: React.FC<{ data: CommercialItem[] }> = (props) => {
  const commercial_list = props.data;

  const router = useRouter();

  return (
    <Commercial>
      <Carousel animation="slide" indicators={false} interval={2000}>
        {commercial_list.map((item, i) => (
          <ImageBox
            key={i}
            onClick={() => {
              router.push("product/" + item.merchandise_id);
            }}
          >
            <Image
              src={item.imgurl}
              alt="광고 이미지"
              width={392}
              height={275}
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
