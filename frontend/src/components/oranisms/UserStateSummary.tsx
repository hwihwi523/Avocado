import styled from "@emotion/styled";
import { Stack } from "@mui/material";

import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import InsertPhotoOutlinedIcon from "@mui/icons-material/InsertPhotoOutlined";

type Item = {
  cart_cnt: number;
  wishlist_cnt: number;
  snapshot_cnt: number;
  like_cnt: number;
};

const UserStateSummary: React.FC<{ data: Item }> = (props) => {
  const { cart_cnt, wishlist_cnt, snapshot_cnt, like_cnt } = props.data;
  return (
    <>
      <Stack direction={"row"} spacing={1}>
        {/* 장바구니 */}
        <IconBox>
          <ShoppingCartOutlinedIcon />
          {cart_cnt}
        </IconBox>

        {/* 찜목록 */}
        <IconBox>
          <BookmarkBorderOutlinedIcon />
          {wishlist_cnt}
        </IconBox>

        {/* 스넵샷 */}
        <IconBox>
          <InsertPhotoOutlinedIcon />
          {snapshot_cnt}
        </IconBox>

        {/* 스넵샷 좋아요 */}
        <IconBox>
          <FavoriteBorderOutlinedIcon />
          {like_cnt}
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
