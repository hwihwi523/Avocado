import Head from "next/head";
import Button from "@mui/material/Button";
import { useRouter } from "next/router";
import authenticateMemberInPages from "@/src/utils/authenticateMemberInPages";
import { wrapper } from "@/src/features/store";

export default function Login() {
  const router = useRouter();

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

export const getServerSideProps = wrapper.getServerSideProps(
  (store) =>
    async ({ req }) => {
      const cookie = req?.headers.cookie;
      const refreshToken = cookie
        ?.split(";")
        .find((c) => c.trim().startsWith("REFRESH_TOKEN="))
        ?.split("=")[1];
      if (refreshToken) {
        console.log("SERVER_REFRESH_TOKEN:", refreshToken);
        authenticateMemberInPages(store, refreshToken);
      } else {
        console.log("SERVER_REFRESH_TOKEN: No REFRESH_TOKEN");
      }

      return {
        props: {},
      };
    }
);
