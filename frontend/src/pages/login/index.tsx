import Head from "next/head";
import Button from "@mui/material/Button";
import { useRouter } from "next/router";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { wrapper } from "@/src/features/store";
import { authenticateTokenInPages } from "@/src/utils/authenticateTokenInPages";
import { useGetProductListQuery } from "@/src/features/product/productApi";

export default function Login() {
  const router = useRouter();
  const { data, error, isLoading } = useGetProductListQuery({});

  const handleSellerLoginClick = () => {
    router.push("/login/seller-login"); // 셀러 로그인 페이지 URL
  };
  const handleMemberLoginClick = () => {
    router.push("/login/member-login"); // 셀러 로그인 페이지 URL
  };

  return (
    <>
      <Head>
        <title>로그인</title>
      </Head>
      <div>여기는 로그인 페이지</div>
      <div>
        <Button variant="outlined" onClick={handleMemberLoginClick}>
          Member-Log-In
        </Button>
        <Button variant="outlined" onClick={handleSellerLoginClick}>
          Seller-Log-In
        </Button>
      </div>
    </>
  );
}

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
