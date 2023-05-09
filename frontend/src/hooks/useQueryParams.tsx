import { useRouter } from "next/router";

export function useQueryParams() {
  const router = useRouter();
  return new URLSearchParams(router.query);
}
