import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import { useState } from "react";
import { IconButton } from "@mui/material";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";
import { BlockText, InlineText } from "../atoms";
import Button from "@mui/material/Button";

const ProductBottom: React.FC<{ openModal: () => void }> = (props) => {
  // const { isBookmark }= props;

  const { openModal } = props;

  const [isBookmark, setIsBookmark] = useState(false);

  return (
    <Background>
      <Grid
        container
        justifyContent={"space-between"}
        alignItems={"center"}
        style={{ height: "100%" }}
      >
        <Grid item xs={2}>
          <IconButton
            aria-label="fingerprint"
            style={{ color: "black" }}
            size="large"
            onClick={() => {
              setIsBookmark(!isBookmark);
            }}
          >
            {isBookmark ? (
              <BookmarkOutlinedIcon fontSize="large"/>
            ) : (
              <BookmarkBorderOutlinedIcon fontSize="large"/>
            )}
          </IconButton>
        </Grid>
        <Grid item xs={10}>
          {/* 검은색바탕에 흰색글씨 */}
          <Button
            style={{ color: "white", width: "100%", backgroundColor: "black",padding:"10px" }}
            onClick={openModal}
          >
            구매하기
          </Button>
        </Grid>
      </Grid>
    </Background>
  );
};

export default ProductBottom;

const Background = styled.div`
  border: 1px solid #dddddd;
  background-color: white;
  z-index: 1000;
  position: fixed;
  bottom: 0px;
  height: 70px;
  width: 100%;
  padding: 0 10px;
`;
