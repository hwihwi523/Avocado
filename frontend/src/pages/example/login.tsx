import { signIn, useSession, signOut, getCsrfToken } from "next-auth/react";

// next-auth를 이용한 로그인 방식 -> 이번 프로젝트에서는 사용 X
export default function Home() {
  const { data: session } = useSession();

  async function myFunction() {
    const csrfToken = await getCsrfToken();
    console.log(csrfToken);
  }
  myFunction();

  async function handleSignOut() {
    await signOut();
  }

  if (session) {
    return (
      <>
        {session.user?.name}님 반갑습니다 <br />
        <button onClick={handleSignOut}>로그아웃</button>
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
