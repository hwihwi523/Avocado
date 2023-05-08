import { ExamplePost } from "@/src/types/exampleTypes";
import { useGetPostsQuery } from "@/src/queries/examplePostApi";
import { useSelector } from "react-redux";
import { AppState } from "@/src/features/store";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function ExamplePostList() {
  const isLoggedIn = useSelector((state: AppState) => state.auth.isLoggedIn);
  const router = useRouter();

  useEffect(() => {
    // 로그인되어 있지 않은 경우 로그인 페이지로 이동
    if (!isLoggedIn) {
      router.replace("/login");
    }
  }, [isLoggedIn, router]);

  return (
    <ul>
      <li>이야아</li>
    </ul>
  );
}
