import styled from "@emotion/styled";
import { Grid, TextField, Rating, Stack } from "@mui/material";
import { useRef, useState } from "react";
import { Button } from "@mui/material";
import {
  productApi,
  useGetProductReviewsQuery,
  useRegistProductReviewMutation,
} from "@/src/features/product/productApi";
import { useRouter } from "next/router";

const ReviewInput = (props: any) => {
  const [rating, setRating] = useState(0);
  const inputRef = useRef<HTMLInputElement>();
  const router = useRouter();

  // url 마지막에서 product Id 가져오기
  const lastSegment = router.asPath.split("/").pop();
  const productId = parseInt(lastSegment!, 10);

  // review 등록
  const [registReview, result] = useRegistProductReviewMutation();

  // reviews 데이터 refetch
  const { refetch } = useGetProductReviewsQuery(lastSegment!);

  async function submitHandler(e: any) {
    e.preventDefault();
    if (inputRef.current) {
      // 여기 useMutation 작성해야함
      registReview({
        productId,
        score: rating,
        content: inputRef.current.value,
      })
        .then((res) => {
          console.log("REVIEW REGIST: ", rating, inputRef.current!.value, res);
          // 초기화
          setRating(0);
          inputRef.current!.value = "";
          refetch();
        })
        .catch((e) => {
          console.log("REVIEW REGIST ERROR: ", e);
        });
    } else {
      // 내용이 입력되지 않은 경우 처리
    }
  }
  return (
    <Background>
      <form onSubmit={submitHandler}>
        <Grid container alignItems={"center"}>
          <Grid item xs={12} style={{ marginBottom: "2%" }}>
            <Stack
              spacing={1}
              direction={"row"}
              justifyContent={"space-between"}
            >
              <TextField
                fullWidth
                id="standard-basic"
                label="리뷰작성"
                variant="outlined"
                inputRef={inputRef}
              />
              <Rating
                size="small"
                name="simple-controlled"
                value={rating}
                onChange={(event, newValue) => {
                  setRating(newValue ? newValue : 0);
                }}
                style={{
                  border: "1px solid #dddddd",
                  boxSizing: "border-box",
                  padding: "18px 0px 18px 10px",
                  width: "50%",
                  textAlign: "center",
                  borderRadius: "5px",
                }}
              />
            </Stack>
          </Grid>

          <Grid item xs={12}>
            <Button
              fullWidth
              style={{
                height: "50px",
                backgroundColor: "black",
                color: "white",
              }}
              onClick={submitHandler}
            >
              등록하기
            </Button>
          </Grid>
        </Grid>
      </form>
    </Background>
  );
};

//여기는 SeoulNamsan 적용 안되서 기본 sens-self로 함
const Background = styled.div`
  margin-bottom: 10px;
`;

export default ReviewInput;
