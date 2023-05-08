import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC } from "react";
import { SessionProvider } from "next-auth/react";
import { Provider } from "react-redux";
import { AppProps } from "next/app";
import type { Session } from "next-auth";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { SnackbarProvider } from "notistack";
// 토큰 관리, 자동 로그인, '오늘 더 이상 이 창을 보지 않음', 장바구니(우리 프로젝트 X) 등을 위해 사용
import { Cookies, CookiesProvider } from "react-cookie";

export const appCookies = new Cookies(); // 앱에서 사용할 쿠키 생성

// Use of the <SessionProvider> is mandatory to allow components that call
// `useSession()` anywhere in your application to access the `session` object.
// 현재의 형태에 적용하기 위해 공식 레퍼런스들과 다르게 작성한 부분들이 있음.
const MyApp: FC<AppProps<{ session: Session }>> = ({
  Component,
  pageProps,
}) => {
  const { store, props } = wrapper.useWrappedStore({
    ...pageProps,
    session: pageProps.session,
  });
  return (
    <SessionProvider session={pageProps.session}>
      <Provider store={store}>
        <CookiesProvider>
          <SnackbarProvider maxSnack={3}>
            <MobileHeader />
            {/* 여기서 pageProps를 컴포넌트에 내려주지 않으면 SSR 불가 */}
            <Component {...pageProps} />
            <MobileBottom />
          </SnackbarProvider>
        </CookiesProvider>
      </Provider>
    </SessionProvider>
  );
};

export default MyApp;
