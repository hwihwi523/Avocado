import styled from "@emotion/styled";
import Head from "next/head";
import { Button, Stack, Box } from "@mui/material";
import { useRouter } from "next/router";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import { useGetProductListQuery } from "@/src/features/product/productApi";
import Image from "next/image";
import { useLoginMutation } from "@/src/features/auth/authApi";
import CircularProgress from "@mui/material/CircularProgress";
export default function Login() {
  const router = useRouter();
  //이건 왜 있는거지
  // const { data, error, isLoading } = useGetProductListQuery({});

  const handleSellerLoginClick = () => {
    router.push("/login/seller-login"); // 셀러 로그인 페이지 URL
  };
  // const handleMemberLoginClick = () => {
  //   router.push("/login/member-login"); // 셀러 로그인 페이지 URL
  // };

  const [login, { isLoading }] = useLoginMutation();

  //
  const handleLogin = async () => {
    // 로그인 로직
    console.log("로그인 버튼이 클릭되었습니다.");
    try {
      // 요청으로 받아온 url로 유저 redirect -> 카카오 로그인
      const res = await login({ provider: "kakao" }).unwrap();
      console.log("멤버 로그인 요청 상태:", res);
      const url = res.url;
      router.push(url);
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  return (
    <Background>
      <Stack spacing={1}>
        <Head>
          <title>로그인</title>
        </Head>
        <ImageBox>
          <Image
            src="/assets/images/avocado.png"
            width={200}
            height={200}
            style={{
              objectFit: "cover",
              margin: "0 auto",
              marginBottom: "10%",
            }}
            alt="로고"
          />
        </ImageBox>
        <Button onClick={handleLogin}>
          <Image
            src="/assets/images/kakao_login_bar.png"
            width={350}
            height={250}
            alt="카카오 로그인 바"
          />
        </Button>
        <Button onClick={handleSellerLoginClick}>
          <Image
            src="/assets/images/seller_login_bar.png"
            width={350}
            height={250}
            alt="판매자 로그인 바"
          />
        </Button>
      </Stack>
    </Background>
  );
}

const ImageBox = styled.div`
  position: relative;
  width: auto;
  height: 20%;
`;

const Background = styled.div`
  padding: 40% 10px;
  box-sizing: border-box;
`;

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    // await authenticateTokenInPages(
    //   { res: context.res, req: context.req },
    //   store
    // );

    return {
      props: {},
    };
  }
);
