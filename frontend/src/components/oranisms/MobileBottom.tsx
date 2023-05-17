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
import { useAppSelector, AppState } from "@/src/features/store";
import router from "next/router";
import { useSnackbar } from "notistack";

const MobileBottom = (props: any) => {
  const member = useAppSelector((state: AppState) => state.auth.member);
  const { enqueueSnackbar } = useSnackbar();

  function wishlistRouterHandler() {
    if (!!member) {
      if (member.type === "provider") {
        enqueueSnackbar(`일반 사용자 계정으로 로그인해 주세요.`, {
          variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
          anchorOrigin: {
            horizontal: "center", //(left, center, right)
            vertical: "top", //top, bottom
          },
        });
      } else {
        router.push("/user/wishList");
      }
    } else {
      enqueueSnackbar(`로그인이 필요한 서비스입니다.`, {
        variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
        anchorOrigin: {
          horizontal: "center", //(left, center, right)
          vertical: "top", //top, bottom
        },
      });
      router.push("/login");
    }
  }

  function myPageRouterHandler() {
    if (!!member) {
      if (member.type === "provider") {
        router.push("/seller");
      } else {
        router.push("/user/mypage");
      }
    } else {
      enqueueSnackbar(`로그인이 필요한 서비스입니다.`, {
        variant: "error", //info(파란색), error(빨간색), success(초록색), warning(노란색)
        anchorOrigin: {
          horizontal: "center", //(left, center, right)
          vertical: "top", //top, bottom
        },
      });
      router.push("/login");
    }
  }

  return (
    <BackgroundDiv>
      {/* 홈 */}
      <Stack direction={"row"} justifyContent="space-around">
        <Link href="/">
          <IconButton aria-label="home">
            <HomeIconOutlinedIcon
              fontSize="large"
              style={{ fill: "#FFFFFF" }}
            />
          </IconButton>
        </Link>

        {/* 찾기 */}
        <Link href="/search">
          <IconButton aria-label="search">
            <SearchIcon fontSize="large" style={{ fill: "#FFFFFF" }} />
          </IconButton>
        </Link>

        {/* 스넵샷 */}
        <Link href="/snapshot">
          <IconButton aria-label="snapshot">
            <ImageIcon fontSize="large" style={{ fill: "#FFFFFF" }} />
          </IconButton>
        </Link>

        {/* 찜 목록 */}
        <IconButton aria-label="bookmark" onClick={wishlistRouterHandler}>
          <BookmarkBorderOutlinedIcon
            fontSize="large"
            style={{ fill: "#FFFFFF" }}
          />
        </IconButton>

        {/* 마이페이지 */}
        <IconButton aria-label="mypage" onClick={myPageRouterHandler}>
          <AccountCircleOutlinedIcon
            fontSize="large"
            style={{ fill: "#FFFFFF" }}
          />
        </IconButton>
      </Stack>
    </BackgroundDiv>
  );
};

export default MobileBottom;

const BackgroundDiv = styled.div`
  background-color: black;
  width: 100%;
  color: white;

  position: fixed;
  bottom: 0px;
  z-index: 100;
`;
