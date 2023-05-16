import styled from "@emotion/styled";
import Grid from "@mui/material/Grid";
import {
  ChartMbti,
  ChartPersonalColor,
} from "@/src/components/oranisms/charts";
import {
  ProductDetailImage,
  ProductCardsRow,
  ProductDescription,
  ProductReviewOrg,
} from "@/src/components/oranisms";
import { IconButton, Stack } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import * as React from "react";
import { BlockText } from "@/src/components/atoms";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Slide from "@mui/material/Slide";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { TransitionProps } from "@mui/material/transitions";
import { useState, useEffect } from "react";
import { Button } from "@mui/material";
import Head from "next/head";
import { useRouter } from "next/router";
import { AppState, useAppSelector, wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import {
  productApi,
  useAddCartMutation,
  useGetIsWishlistQuery,
  useGetProductReviewsQuery,
} from "@/src/features/product/productApi";
import {
  ProductReview,
  setSelectedProductDetail,
} from "@/src/features/product/productSlice";
import dynamic from "next/dynamic";
import { statisticApi } from "@/src/features/statistic/statisticApi";
import { setSelectedProductStatisticData } from "@/src/features/statistic/statisticSlice";
import ProductBottom from "@/src/components/oranisms/ProductBottom";

const ProductDetailPage = () => {
  // const ProductBottom = dynamic(
  //   () => import("@/src/components/oranisms/ProductBottom"),
  //   { ssr: false }
  // );

  const router = useRouter();
  const product = useAppSelector(
    (state: AppState) => state.product.selectedProductDetail
  );
  const member = useAppSelector((state: AppState) => state.auth.member);
  // 통계 자료
  const statisticData = useAppSelector(
    (state: AppState) => state.statistic.selectedProductStatisticData
  );
  const ageGenderData = statisticData.age_gender_score;
  const mbtiData = statisticData.mbti_score;
  const personalColorData = statisticData.personal_color_score;

  // url 마지막에서 product Id 가져오기
  const productId = router.asPath.split("/").pop();
  // reviews는 클라이언트 단에서 호출 => 등록 및 삭제 시 데이터 refetch를 위해
  const { data: getReviewsData, error: getReviewsError } =
    useGetProductReviewsQuery(productId!);
  const reviews = getReviewsData as ProductReview[] | [];
  // wishlist button 상태관리를 위해
  const { data: getIsWishlistData, error } = useGetIsWishlistQuery({
    merchandise_name: product ? product.merchandise_name : "",
  });
  const isWishlist = getIsWishlistData ? getIsWishlistData.data : false;
  // Cart
  const [addCart, result] = useAddCartMutation();

  const [size, setSize] = useState("M");
  const [count, setCount] = useState(1);
  const [open, setOpen] = React.useState(false);

  //모달 오픈
  const handleClickOpen = () => {
    setOpen(true);
  };

  //모달 닫기
  const handleClose = () => {
    setOpen(false);
  };

  //사이즈 변경
  const handleChange = (event: SelectChangeEvent) => {
    setSize(event.target.value as string);
  };

  //수량 증가
  function addCountHandler() {
    setCount(count + 1);
  }

  //수량 감소
  function minusCountHandler() {
    if (count > 1) {
      setCount(count - 1);
    }
  }

  //구매하기 함수
  function purchaseHandler() {
    if (member) {
      router.push({
        pathname: "/billing",
        query: {
          member: JSON.stringify(member),
          products: JSON.stringify([
            {
              id: product?.id,
              brand_namd: product?.brand_name,
              merchandise_id: product?.merchandise_id,
              merchandise_category: product?.merchandise_category,
              images: product?.images,
              merchandise_name: product?.merchandise_name,
              price: product?.price,
              discounted_price: product?.discounted_price,
              size,
              count,
            },
          ]),
        },
      });
    }
  }

  // 장바구니에 담기
  function addToCart() {
    if (!member) {
      return;
    }
    addCart({ merchandise_id: product!.merchandise_id, size, quantity: count })
      .unwrap()
      .then((res) => router.push("/user/cartList"))
      .catch((e) => console.log("ADD CART ERROR: ", e));
  }

  return (
    <div>
      <Head>
        <title>제품이름</title>
        <meta name="description" content="스토어 설명" />
        <meta name="keywords" content={`제품카테고리, 제품 종류 , 등등`} />
        <meta property="og:title" content="스토어 이름" />
        <meta property="og:description" content="스토어 설명" />
      </Head>
      <Background>
        <Grid container gap={2}>
          {/* 제품 이미지 */}
          <Grid item xs={12}>
            <ProductDetailImage product={product} />
            <DividerBar />
          </Grid>

          {/* mbti chart */}
          <Grid item xs={12}>
            <ChartMbti mbtiData={mbtiData} />
          </Grid>

          {/* personal color chart */}
          <Grid item xs={12}>
            <ChartPersonalColor personalColorData={personalColorData} />
          </Grid>

          {/* 상품 설명 */}
          <Grid item xs={12}>
            <ProductDescription description={product?.description} />
            <DividerBar />
          </Grid>

          {/* 연관 상품 */}
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              연관 상품
            </BlockText>
            <ProductCardsRow isLogin={true} />
            <DividerBar />
          </Grid>

          {/* 리뷰작성 */}
          <Grid item xs={12}>
            <BlockText type="B" size="1.3rem" style={{ marginBottom: "10px" }}>
              리뷰 작성하기
            </BlockText>
            <ProductReviewOrg reviews={reviews} />
          </Grid>
        </Grid>
      </Background>

      {/*  구매 모달 */}
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
        style={{ marginTop: "60%" }}
      >
        <Grid
          container
          justifyContent={"space-between"}
          alignItems={"center"}
          style={{ width: "300px", padding: "20px" }}
          gap={2}
        >
          <Grid item xs={5}>
            <FormControl fullWidth size="medium">
              <InputLabel id="demo-simple-select-label">size</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={size}
                label="size"
                onChange={handleChange}
              >
                <MenuItem value={"S"}>S</MenuItem>
                <MenuItem value={"M"}>M</MenuItem>
                <MenuItem value={"L"}>L</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={5} style={{ boxSizing: "border-box" }}>
            <Stack direction={"row"} alignItems={"center"}>
              <IconButton onClick={addCountHandler}>
                <AddIcon />
              </IconButton>
              <BlockText style={{ margin: "0px 10px" }}>{count}</BlockText>
              <IconButton onClick={minusCountHandler}>
                <RemoveIcon />
              </IconButton>
            </Stack>
          </Grid>
          <Grid item xs={12}>
            <Stack spacing={1}>
              <Button
                fullWidth
                style={{
                  backgroundColor: "black",
                  color: "white",
                  padding: "30px",
                }}
                onClick={purchaseHandler}
              >
                구매하기
              </Button>
              <Button
                onClick={addToCart}
                fullWidth
                style={{
                  backgroundColor: "white",
                  color: "black",
                  border: "1px solid black",
                  padding: "30px",
                }}
              >
                장바구니
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Dialog>

      <ProductBottom openModal={handleClickOpen} isWishlist={isWishlist} />
    </div>
  );
};

export default ProductDetailPage;

// 모달창 애니메이션 옵션
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return (
    <div>
      <Slide direction="up" ref={ref} {...props} />;
    </div>
  );
});

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  margin-bottom: 100px;
`;

const DividerBar = styled.div`
  border: 1px solid #dddddd;
  height: 1px;
  margin: 20px 0px;
`;

// SSR
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { res: context.res, req: context.req },
      store
    );

    // URL에서 마지막 경로 세그먼트 가져오기
    const resolvedUrl = context.resolvedUrl;
    const segments = resolvedUrl.split("/");
    const lastSegment = segments[segments.length - 1];

    // lastSegment를 활용하여 필요한 데이터를 가져와서 페이지 렌더링에 활용
    const productDetailResponse = await store.dispatch(
      productApi.endpoints.getProductDetail.initiate(lastSegment)
    );
    const statisticDataResponse = await store.dispatch(
      statisticApi.endpoints.getStatisticDataForProductDetail.initiate(
        parseInt(lastSegment, 10)
      )
    );

    // 응답을 변환하여 store에 저장
    const productDetail = productDetailResponse.data;
    if (productDetail) {
      store.dispatch(setSelectedProductDetail(productDetail));
    } else {
      console.log("SERVER_NO_PRODUCT_DETAIL: ", productDetailResponse);
    }
    const statisticData = statisticDataResponse.data?.data;
    if (statisticData) {
      store.dispatch(setSelectedProductStatisticData(statisticData));
    } else {
      console.log(
        "SERVER_NO_STATISTIC_DATA_FOR_DETAIL: ",
        statisticDataResponse
      );
    }

    return {
      props: {},
    };
  }
);
