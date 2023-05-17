import styled from "@emotion/styled";
import { Button, Stack } from "@mui/material";
import { InlineText, BlockText } from "../../components/atoms";
import { useEffect } from "react";
import {
  ProductCardsRow,
  UserProfile,
  UserStateSummary,
} from "../../components/oranisms";
import { useRouter } from "next/router";
import {
  ChartCategoryPurchase,
  ChartPersonalColor,
} from "../../components/oranisms/charts";
import Link from "next/link";
import Head from "next/head";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
import { removeTokenAll } from "@/src/utils/tokenManager";
import { useDispatch } from "react-redux";
import { clearAuth } from "@/src/features/auth/authSlice";
import {
  useGetOrderListQuery,
  useGetSnapshotCntAndLikeCntQuery,
} from "@/src/features/snapshot/snapshotApi";
import {
  useGetCartQuery,
  useGetRecentlyViewProductsListQuery,
  useGetWishlistQuery,
} from "@/src/features/product/productApi";
import CircularProgress from "@mui/material/CircularProgress";
import { statisticApi } from "@/src/features/statistic/statisticApi";
import { setConsumerStatisticData } from "@/src/features/statistic/statisticSlice";

const Mypage = () => {
  const member = useAppSelector((state: AppState) => state.auth.member);
  const dispatch = useDispatch();
  const router = useRouter();

  const consumer_statistic = useAppSelector(
    (state: AppState) => state.statistic.consumerStatisticData
  );
  console.log("consumer_statistic >>> ", consumer_statistic);

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US") + "원";
  }

  //좋아요 갯수, 스넵샷 게시물 개수
  const { data: userSummary } = useGetSnapshotCntAndLikeCntQuery();

  //장바구니 목록
  const { data: cartList, isLoading: cartList_loading } = useGetCartQuery();

  //구매목록
  const { data: orderList, isLoading: orderList_loading } =
    useGetOrderListQuery();

  //찜목록
  const { data: wishList, isLoading: wishList_laoding } = useGetWishlistQuery();

  //최근 본 상품 목록
  const { data: recentlyView, isLoading: recentlyView_laoding } =
    useGetRecentlyViewProductsListQuery();

  //로그인 안했다면 로그인 페이지로 보내버리기
  useEffect(() => {
    if (!member) {
      router.push("/login");
    }
  }, []);

  //로그아웃 핸들러
  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    dispatch(clearAuth());
    //로그인 페이지로 이동
    router.replace("/");
  };

  return (
    <Background>
      <Head>
        <title>마이페이지</title>
      </Head>
      <Stack direction={"column"} spacing={2}>
        {member && <UserProfile member={member} />}
        <Button
          onClick={handleLogout}
          fullWidth
          variant="outlined"
          color="error"
          style={{ padding: "10px" }}
        >
          로그아웃
        </Button>
        <UserStateSummary
          data={{
            cart_cnt: cartList ? cartList.length : 0,
            wishlist_cnt: wishList ? wishList.length : 0,
            snapshot_cnt: userSummary ? userSummary.styleshot_cnt : 0,
            like_cnt: userSummary ? userSummary.like_cnt : 0,
          }}
        />
        <ChartCategoryPurchase data={consumer_statistic.categories} />

        <div
          style={{
            border: "1px solid #dddddd",
            borderRadius: "10px",
            padding: "20px",
          }}
        >
          <Stack direction="row" justifyContent={"space-between"}>
            <BlockText>총 구매액 : </BlockText>
            <BlockText>
              {formatCurrency(consumer_statistic.total_money)}
            </BlockText>
          </Stack>
        </div>

        <Box>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText>최근 본 상품 </InlineText>
          </Stack>
          {recentlyView && recentlyView.data.length !== 0 ? (
            <ProductCardsRow data={recentlyView.data as any[]} />
          ) : (
            <EmptyBox>
              {recentlyView_laoding ? (
                <CircularProgress />
              ) : (
                "최근 본 상품이 없습니다."
              )}
            </EmptyBox>
          )}
        </Box>

        <Box>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText>장바구니 </InlineText>
            <Link href="/user/cartList">
              <InlineText>더보기 +</InlineText>
            </Link>
          </Stack>

          {cartList && cartList.length !== 0 ? (
            <ProductCardsRow data={cartList as any[]} />
          ) : (
            <EmptyBox>
              {cartList_loading ? (
                <CircularProgress />
              ) : (
                "장바구니에 감긴 제품이 없습니다."
              )}
            </EmptyBox>
          )}
        </Box>

        <Box>
          <Stack direction={"row"} justifyContent={"space-between"}>
            <InlineText>구매목록 </InlineText>
            <Link href="/user/orderList">
              <InlineText>더보기 +</InlineText>
            </Link>
          </Stack>
          {orderList && orderList.data.content.length !== 0 ? (
            <ProductCardsRow data={orderList.data.content as any[]} />
          ) : (
            <EmptyBox>
              {orderList_loading ? (
                <CircularProgress />
              ) : (
                "구매한 제품이 없습니다. "
              )}
            </EmptyBox>
          )}
        </Box>
      </Stack>
    </Background>
  );
};

export default Mypage;

const Background = styled.div`
  padding: 10px 10px 70px 10px;
`;

const Box = styled.div`
  padding-top: 100px;
`;

const EmptyBox = styled.div`
  margin-top: 10px;
  border-radius: 10px;
  border: 1px solid #dddddd;
  height: 200px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    let cookie = context.req?.headers.cookie;
    let accessToken = cookie
      ?.split(";")
      .find((c) => c.trim().startsWith("ACCESS_TOKEN="))
      ?.split("=")[1];

    if (accessToken) {
      //사용자 통계정보 가져오기
      const consumer_statistic = await store.dispatch(
        statisticApi.endpoints.getStatisticDataForConsumer.initiate(accessToken)
      );

      if (consumer_statistic.data) {
        store.dispatch(setConsumerStatisticData(consumer_statistic.data.data));
      }
    }

    return {
      props: {},
    };
  }
);
