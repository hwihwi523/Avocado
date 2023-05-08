import Head from "next/head";
import Button from "@mui/material/Button";
import { useRouter } from "next/router";

export default function Login() {
  const router = useRouter();

  const handleSellerLoginClick = () => {
    router.push("/login/seller-login"); // 셀러 로그인 페이지 URL
  };

  return (
    <>
      <Head>
        <title>로그인</title>
      </Head>
      <div>여기는 로그인 페이지</div>
      <div>
        <Button variant="outlined">Member-Log-In</Button>
        <Button variant="outlined" onClick={handleSellerLoginClick}>
          Seller-Log-In
        </Button>
      </div>
    </>
  );
}
