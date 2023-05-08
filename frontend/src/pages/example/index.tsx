import { ExamplePost } from "@/src/types/exampleTypes";
import { useGetPostsQuery } from "@/src/queries/examplePostApi";
import { useSelector } from "react-redux";
import { AppState } from "@/src/features/store";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function ExamplePostList() {
  const member = useSelector((state: AppState) => state.auth.member);
  const router = useRouter();

  useEffect(() => {
    // 로그인되어 있지 않은 경우(member가 null인 경우) 로그인 페이지로 이동하는 예제
    if (!member) {
      router.replace("/login");
    }
  }, [member, router]);

  return (
    <ul>
      <li>이야아</li>
    </ul>
  );
}
