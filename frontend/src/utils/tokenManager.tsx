// 로그인을 위한 토큰 매니저
import { appCookies } from "../pages/_app";

function setToken(
  key: "ACCESS_TOKEN" | "REFRESH_TOKEN",
  token: string,
  exp: number
) {
  // UNIXtimestamp가 ms 단위가 아니라 s 단위이므로 변환 필요
  const expires = new Date(exp * 1000);

  appCookies.set(key, token, {
    path: "/",
    expires: expires, // 이 부분을 설정해두면 만료 기한 이후 자동으로 삭제됨
  });
}

function removeToken(key: "ACCESS_TOKEN" | "REFRESH_TOKEN") {
  appCookies.remove(key, { path: "/" });
}

function removeTokenAll() {
  removeToken("ACCESS_TOKEN");
  removeToken("REFRESH_TOKEN");
}

function getAccessToken() {
  return appCookies.get("ACCESS_TOKEN");
}

function getRefreshToken() {
  return appCookies.get("REFRESH_TOKEN");
}

export {
  setToken,
  removeToken,
  removeTokenAll,
  getAccessToken,
  getRefreshToken,
};
