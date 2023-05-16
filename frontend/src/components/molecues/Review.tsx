import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Image from "next/image";
import Rating from "@mui/material/Rating";
import { BlockText, InlineText } from "../atoms";
import { IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { ProductReview } from "@/src/features/product/productSlice";
import {
  useGetProductReviewsQuery,
  useRemoveProductReviewMutation,
} from "@/src/features/product/productApi";
import { useRouter } from "next/router";
import { useAppSelector, AppState } from "@/src/features/store";

type ReviewProps = {
  review: ProductReview;
};

const Review: React.FC<ReviewProps> = ({ review }) => {
  const {
    reviewer,
    picture_url,
    mbti,
    personal_color,
    score,
    content,
    created_at,
    id,
  } = review;

  const member = useAppSelector((state: AppState) => state.auth.member);

  const router = useRouter();
  const [removeReview, result] = useRemoveProductReviewMutation();

  // url 마지막에서 product Id 가져오기
  const lastSegment = router.asPath.split("/").pop();
  const productId = parseInt(lastSegment!, 10);

  // reviews 데이터 refetch
  const { refetch } = useGetProductReviewsQuery(lastSegment!);

  function DeleteBtnClickHandler() {
    removeReview({ productId, reviewId: review.id })
      .then((res) => {
        console.log("REMOVE REVIEW RESULT: ", res);
        refetch();
      })
      .catch((e) => console.log("REMOVE REVIEW ERROR", e));
  }

  return (
    <Background>
      <Grid container p={2} gap={2}>
        <Grid item xs={12}>
          <Stack direction={"row"} spacing={2} alignItems={"center"}>
            <Image
              width={60}
              height={60}
              alt="사용자 프로필 이미지"
              src={picture_url ? picture_url : ""}
              style={{ maxHeight: "60px" }}
            />
            <Stack style={{ color: "gray", width: "100%" }}>
              <BlockText>
                <Stack
                  direction={"row"}
                  justifyContent={"space-between"}
                  alignItems={"center"}
                >
                  <div>
                    <InlineText>{reviewer} </InlineText>
                    <InlineText color="grey" type="L" size="12px">
                      {mbti} / {personal_color}
                    </InlineText>
                  </div>
                  {!!member && member.name === reviewer && (
                    <IconButton
                      aria-label="delete"
                      onClick={DeleteBtnClickHandler}
                    >
                      <CloseIcon color="error" />
                    </IconButton>
                  )}
                </Stack>
              </BlockText>
              <Rating
                readOnly
                name="half-rating"
                defaultValue={score}
                precision={1}
              />{" "}
            </Stack>
          </Stack>
        </Grid>
        <Grid item xs={12}>
          <BlockText>{content}</BlockText>
          <InlineText color="grey" type="L" size="12px">
            {created_at}
          </InlineText>
        </Grid>
      </Grid>
    </Background>
  );
};

export default Review;

const Background = styled.div`
  border: 1px solid #dddddd;
  width: 100%;
`;
