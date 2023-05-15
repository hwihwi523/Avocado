import { useRouter } from "next/router";
import querystring from "querystring";

// 브라우저 -> url을 받아와 쿼리 params 가져오기
export function useQueryParams() {
  const router = useRouter();
  const params = querystring.stringify(router.query);
  return new URLSearchParams(params);
}
