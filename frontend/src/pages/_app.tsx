import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC } from "react";
import { Provider } from "react-redux";
import { AppContext, AppProps } from "next/app";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { SnackbarProvider } from "notistack";
// 토큰 관리, 자동 로그인, '오늘 더 이상 이 창을 보지 않음', 장바구니(우리 프로젝트 X) 등을 위해 사용
import { Cookies, CookiesProvider } from "react-cookie";
import { createCookiesInstance } from "../utils/createCookiesInstance";

export const appCookies = createCookiesInstance(); // 앱에서 사용할 쿠키 생성

const MyApp: FC<AppProps> = ({ Component, pageProps }) => {
  // console.log("rest: ", pageProps);
  const { store } = wrapper.useWrappedStore({
    ...pageProps,
  });
  return (
    <Provider store={store}>
      <CookiesProvider>
        <SnackbarProvider maxSnack={3} autoHideDuration={1000}>
          <MobileHeader />
          {/* 여기서 pageProps를 컴포넌트에 내려주지 않으면 SSR 불가 */}
          <Component {...pageProps} />
          <MobileBottom />
        </SnackbarProvider>
      </CookiesProvider>
    </Provider>
  );
};

export default MyApp;
