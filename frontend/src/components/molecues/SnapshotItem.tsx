import styled from "@emotion/styled";
import { Stack, Chip, IconButton, Button, Box, Skeleton } from "@mui/material";
import Image from "next/image";
import { BlockText, InlineText } from "../atoms";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";
import router from "next/router";
import { useState } from "react";
import {
  SnapshotItem as snapshotItemType,
  useAddSnapshotLikeMutation,
  useRemoveSnapshotLikeMutation,
} from "@/src/features/snapshot/snapshotApi";
import { useRemoveSnapshotMutation } from "@/src/features/snapshot/snapshotApi";
import {
  mbti_list,
  personal_color_list,
  personal_color_list_eng,
} from "../atoms/data";
import { useSnackbar } from "notistack";

const SnapshotItem: React.FC<{ data: snapshotItemType; refetch: any }> = (
  props
) => {
  const item = props.data;
  const { enqueueSnackbar } = useSnackbar();
  const [removeSnapshot] = useRemoveSnapshotMutation();
  const [addSnapshotLike] = useAddSnapshotLikeMutation();
  const [removeSnapshotLike] = useRemoveSnapshotLikeMutation();

  const [isLike, setIsLike] = useState(item.iliked);

  //게시물 삭제
  function deleteHandler() {
    removeSnapshot(item.id)
      .unwrap()
      .then((res) => {
        enqueueSnackbar(`게시물이 삭제되었습니다. `, {
          variant: "info",
          anchorOrigin: {
            horizontal: "center",
            vertical: "top",
          },
        });
        props.refetch();
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  }

  //날자 변환 함수
  function dateFormat(date: string) {
    const dateTimeString = date;
    const dateObj = new Date(dateTimeString);
    const year = dateObj.getFullYear();
    const month = dateObj.getMonth() + 1;
    const day = dateObj.getDate();
    const hours = dateObj.getHours();
    const minutes = dateObj.getMinutes();

    return `${year}년 ${month}월 ${day}일  ${hours}시 ${minutes}분`;
  }

  function redirectToProductPage(id: number) {
    router.push(`/product/${id}`);
  }

  //좋아요 누름
  function addLikeHandler() {
    console.log("좋아요 동작");
    addSnapshotLike(item.id)
      .unwrap()
      .then((res) => {
        console.log(res);
        setIsLike(true);
      })
      .catch((err) => console.log(err));
  }

  //좋아요 취소
  function removeLikeHandler() {
    console.log("싫어요 동작");
    removeSnapshotLike(item.id)
      .unwrap()
      .then((res) => {
        console.log(res);
        setIsLike(false);
      })
      .catch((err) => console.log(err));
  }

  // 이미지 로딩 중 처리
  const [isImageLoading, setIsImageLoading] = useState(true);
  const handleImageLoad = () => {
    setIsImageLoading(false);
  };

  return (
    <Stack spacing={1}>
      <Stack
        direction="row"
        justifyContent={"space-between"}
        alignItems={"center"}
      >
        {/* 스넵샷 이미지 */}
        <BlockText color="grey" size="0.8rem" style={{ textAlign: "right" }}>
          {dateFormat(item.created_at)}
        </BlockText>

        {item.my_styleshot && (
          <Button
            color="error"
            onClick={deleteHandler}
            style={{
              display: "flex",
              alignItems: "flex-end",
              padding: 0,
              marginRight: "-11px",
            }}
          >
            삭제
          </Button>
        )}
      </Stack>
      <Imagebox style={{ marginTop: "1px" }}>
        {isImageLoading && (
          <Box
            sx={{
              width: "100%",
              height: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Skeleton variant="rounded" width={"100%"} height={"100%"} />
          </Box>
        )}{" "}
        {/* 로딩 스피너 */}
        <Image
          src={item.picture_url}
          alt="제품 이미지"
          fill
          style={{ objectFit: "cover" }}
          loading="lazy"
          onLoad={handleImageLoad}
        />
      </Imagebox>

      {/* 제품 링크 */}
      <Stack spacing={1} direction={"row"} flexWrap={"wrap"} margin={"10px 0"}>
        {item.wears.map((item, i) => (
          <Chip
            label={item.name}
            key={i}
            variant="outlined"
            onClick={() => {
              redirectToProductPage(item.merchandise_id);
            }}
          />
        ))}
      </Stack>

      {/* 유저 정보 */}
      <Stack direction={"row"} spacing={2} alignItems={"center"}>
        <Image
          width={50}
          height={50}
          alt="아바타 이미지"
          src={`/assets/avatar/${
            personal_color_list_eng[item.user_info.personal_color_id].split(
              "_"
            )[0]
          }_${item.user_info.gender === "M" ? "man" : "woman"}.png`}
        />
        <Stack style={{ color: "gray", width: "100%" }}>
          <div>
            <Stack
              direction={"row"}
              justifyContent={"space-between"}
              alignItems={"center"}
            >
              <Stack>
                <InlineText>{item.user_info.name} </InlineText>
                <InlineText color="grey" type="L" size="0.8rem">
                  {mbti_list[item.user_info.mbti_id]} /{" "}
                  {personal_color_list[item.user_info.personal_color_id]}
                </InlineText>
              </Stack>
              <div>
                <IconButton
                  aria-label="delete"
                  onClick={isLike ? removeLikeHandler : addLikeHandler}
                >
                  {isLike ? (
                    <FavoriteIcon color="error" fontSize="large" />
                  ) : (
                    <FavoriteBorderIcon color="error" fontSize="large" />
                  )}
                </IconButton>
              </div>
            </Stack>
          </div>
        </Stack>
      </Stack>

      {/* 글 내용 300자 제한임  */}
      <BlockText
        style={{
          padding: "10px",
          textAlign: "justify",
          wordWrap: "break-word",
        }}
      >
        {item.content}
      </BlockText>
    </Stack>
  );
};

export default SnapshotItem;

const Imagebox = styled.div`
  position: relative; //이게 제일 중요함
  width: 100%;
  height: 400px;
  margin-bottom: 10px;
`;
