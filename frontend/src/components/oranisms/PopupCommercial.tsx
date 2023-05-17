import styled from "@emotion/styled";
import { Button, Dialog, DialogActions } from "@mui/material";
import Image from "next/image";
import Carousel from "react-material-ui-carousel";
import router from "next/router";

type CommercialItem = {
  imgurl: string;
  merchandise_id: number;
};

const PopupCommercial: React.FC<{
  open: boolean;
  setOpen: (open: boolean) => void;
  data: CommercialItem[];
}> = (props) => {
  //더미데이터

  const commercial_list = props.data;

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
                fill
                style={{ objectFit: "cover" }}
              />
            </ImageBox>
          ))}
        </Carousel>
        <DialogActions>
          <Button onClick={handleClose} color="success">
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
