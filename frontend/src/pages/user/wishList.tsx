import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { BlockText } from "../../components/atoms";
import { WishItem } from "../../components/molecues";
import Head from "next/head";
import { useGetWishlistQuery } from "@/src/features/product/productApi";
const WishList = () => {
  // wishlist 호출
  const { data: wishlist } = useGetWishlistQuery();

  return (
    <Background>
      <Head>
        <title>찜 목록</title>
      </Head>
      <BlockText type="B" size="1.2rem" style={{ margin: "10px" }}>
        찜 목록
      </BlockText>
      <Stack spacing={2}>
        {wishlist &&
          wishlist.map((item, i) => <WishItem item={item} key={i} />)}
      </Stack>
    </Background>
  );
};

export default WishList;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  background-color: #dddddd;
  height: 100vh;
`;

// // 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// // 클라이언트에서도 동일한 store를 사용하도록 설정
// export const getServerSideProps = wrapper.getServerSideProps(
//   (store) => async (context) => {
//     // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
//     await authenticateTokenInPages(
//       { req: context.req, res: context.res },
//       store
//     );
//     // 서버에서 토큰을 헤더에 넣어주기 위한 작업
//     let cookie = context.req?.headers.cookie;
//     let accessToken = cookie
//       ?.split(";")
//       .find((c) => c.trim().startsWith("ACCESS_TOKEN="))
//       ?.split("=")[1];
//     // wishlist 가져오기
//     const wishlistResponse = await store.dispatch(
//       productApi.endpoints.getWishlist.initiate({ token: accessToken })
//     );
//     const wishlist = wishlistResponse.data;
//     {
//       wishlist && store.dispatch(setProductListForWishlist(wishlist));
//     }

//     return {
//       props: {},
//     };
//   }
// );
