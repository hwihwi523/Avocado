import styled from "@emotion/styled";
import { Button, Dialog, DialogActions } from "@mui/material";
import Image from "next/image";
import Carousel from "react-material-ui-carousel";
import router from "next/router";
const PopupCommercial: React.FC<{
  open: boolean;
  setOpen: (open: boolean) => void;
}> = (props) => {
  //더미데이터
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

  const { open, setOpen } = props;
  const handleClose = () => {
    setOpen(false);
  };

  //12시간동안 보지 않기
  const handleOneDayClose = () => {
    const expirationTime = 12 * 60 * 60 * 1000; //12시간동안 popup광고 보지 않기
    const currentTime = new Date().getTime();
    const expiration = currentTime + expirationTime; // 만료시간
    localStorage.setItem(
      "commercial_expiration_time",
      JSON.stringify(expiration)
    ); //만료시간 세팅
    setOpen(false);
  };

  return (
    <Commercial>
      <Dialog
        PaperProps={{ sx: { width: "300px", height: "400px" } }}
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <Carousel animation="slide" indicators={false} interval={3000}>
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
        <DialogActions>
          <Button onClick={handleClose} color="success" autoFocus>
            닫기
          </Button>
          <Button onClick={handleOneDayClose} color="primary">
            하루동안 보지 않기
          </Button>
        </DialogActions>
      </Dialog>
    </Commercial>
  );
};

export default PopupCommercial;

const Commercial = styled.div`
  width: 100%;
`;

const ImageBox = styled.div`
  width: 300px;
  height: 400px;
`;
