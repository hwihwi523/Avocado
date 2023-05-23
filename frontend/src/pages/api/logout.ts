import { AppStore } from "@/src/features/store";
import { IncomingMessage, ServerResponse } from "http";

// /api/logout 요청을 처리하는 핸들러
export default function handler(
  req: IncomingMessage & {
    cookies: Partial<{
      [key: string]: string;
    }>;
  },
  res: ServerResponse<IncomingMessage>,
  store: AppStore
) {
  return new Promise<void>((resolve, reject) => {
    try {
      // 쿠키 삭제 -> 멤버 정보는 기존 SSR 과정에 포함된 로직에서 자동 삭제
      res.setHeader("Set-Cookie", [
        "ACCESS_TOKEN=;Max-Age=0;Path=/",
        "REFRESH_TOKEN=;Max-Age=0;Path=/",
      ]);
      res.statusCode = 200;
      res.statusMessage = "Logged out successfully";
      res.end();
    } catch (error) {
      res.statusCode = 500;
      res.statusMessage = "Failure : Logged out" + error;
      res.end();
    }
  });
}
