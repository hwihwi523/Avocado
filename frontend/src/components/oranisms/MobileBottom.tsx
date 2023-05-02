//컴포넌트를 만들 때 테스트 하는 곳

import { Stack } from "@mui/material";
import HomeIconOutlinedIcon from "@mui/icons-material/HomeOutlined";
import SearchIcon from "@mui/icons-material/Search";
import ImageIcon from "@mui/icons-material/Image";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";
import BookmarkBorderOutlinedIcon from "@mui/icons-material/BookmarkBorderOutlined";
import IconButton from "@mui/material/IconButton";
import styled from "@emotion/styled";
import Link from "next/link";

const MobileBottom = (props: any) => {
  return (
    <BackgroundDiv>
      {/* 홈 */}
      <Stack direction={"row"} justifyContent="space-around">
        <Link href="">
          <IconButton aria-label="home">
            <HomeIconOutlinedIcon
              fontSize="large"
              style={{ fill: "#FFFFFF" }}
            />
          </IconButton>
        </Link>

        {/* 찾기 */}
        <Link href="">
          <IconButton aria-label="search">
            <SearchIcon fontSize="large" style={{ fill: "#FFFFFF" }} />
          </IconButton>
        </Link>

        {/* 스넵샷 */}
        <Link href="">
          <IconButton aria-label="snapshot">
            <ImageIcon fontSize="large" style={{ fill: "#FFFFFF" }} />
          </IconButton>
        </Link>

        {/* 찜 목록 */}
        <Link href="">
          <IconButton aria-label="bookmark">
            <BookmarkBorderOutlinedIcon
              fontSize="large"
              style={{ fill: "#FFFFFF" }}
            />
          </IconButton>
        </Link>

        {/* 마이페이지 */}
        <Link href="">
          <IconButton aria-label="mypage">
            <AccountCircleOutlinedIcon
              fontSize="large"
              style={{ fill: "#FFFFFF" }}
            />
          </IconButton>
        </Link>
      </Stack>
    </BackgroundDiv>
  );
};

export default MobileBottom;

const BackgroundDiv = styled.div`
  background-color: black;
  width:100%;
  color: white;
  
  position:fixed;
  bottom:0px;
  z-index:100;
`;
