import { useRouter } from "next/router";
import querystring from "querystring";

export function useQueryParams() {
  const router = useRouter();
  const params = querystring.stringify(router.query);
  return new URLSearchParams(params);
}
