import { signIn, useSession, signOut, getCsrfToken } from "next-auth/react";

export default function Home() {
  const { data: session } = useSession();

  async function myFunction() {
    const csrfToken = await getCsrfToken();
    console.log(csrfToken);
  }
  myFunction();

  if (session) {
    return (
      <>
        {session.user?.name}님 반갑습니다 <br />
        <button onClick={() => signOut()}>로그아웃</button>
      </>
    );
  }
  return (
    <>
      로그인되지 않았습니다 <br />
      <button onClick={() => signIn("kakao")}>로그인</button>
    </>
  );
}
