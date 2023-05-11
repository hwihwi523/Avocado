import Cookies from "universal-cookie";

export function createCookiesInstance(): Cookies {
  return new Cookies();
}
