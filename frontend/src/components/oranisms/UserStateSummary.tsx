import styled from "@emotion/styled";
import { Stack } from "@mui/material";



import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import InsertPhotoOutlinedIcon from '@mui/icons-material/InsertPhotoOutlined';
const UserStateSummary = () => {
  const cart = 11;
  const bookmark = 12;
  const snapshot = 33;
  const heart = 22;

  return (
    <>
      <Stack direction={"row"} spacing={1}>

        {/* 장바구니 */}
        <IconBox>
          <ShoppingCartOutlinedIcon />
          {cart}
        </IconBox>

        {/* 찜목록 */}
        <IconBox>
          <BookmarkBorderOutlinedIcon />
          {bookmark}
        </IconBox>

        {/* 스넵샷 */}
        <IconBox>
          <InsertPhotoOutlinedIcon />
          {snapshot}
        </IconBox>

        {/* 스넵샷 좋아요 */}
        <IconBox>
          <FavoriteBorderOutlinedIcon />
          {heart}
        </IconBox>
      </Stack>
    </>
  );
};

export default UserStateSummary;

const IconBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  border: 1px solid #dddddd;
  border-radius: 10px;
  width: 100%;
`;

