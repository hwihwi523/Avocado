import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC, useEffect, useState } from "react";
import { Provider } from "react-redux";
import { AppContext, AppProps } from "next/app";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { SnackbarProvider } from "notistack";
// 토큰 관리, 자동 로그인, '오늘 더 이상 이 창을 보지 않음', 장바구니(우리 프로젝트 X) 등을 위해 사용
import { Cookies, CookiesProvider } from "react-cookie";
import { createCookiesInstance } from "../utils/createCookiesInstance";
import { Box, LinearProgress, ThemeProvider, createTheme } from "@mui/material";
import { Router } from "next/router";

export const appCookies = createCookiesInstance(); // 앱에서 사용할 쿠키 생성

const MyApp: FC<AppProps> = ({ Component, pageProps }) => {
  // console.log("rest: ", pageProps);
  const { store } = wrapper.useWrappedStore({
    ...pageProps,
  });

  // 테마 생성
  const theme = createTheme({
    palette: {
      primary: {
        main: "#000",
        contrastText: "#FFF",
      },
    },
  });

  // 로딩 스피너
  const [isLoading, setIsLoading] = useState(false);
  useEffect(() => {
    // 라우터 이벤트 핸들러 등록
    Router.events.on("routeChangeStart", handleRouteChangeStart);
    Router.events.on("routeChangeComplete", handleRouteChangeComplete);
    Router.events.on("routeChangeError", handleRouteChangeComplete);
    // 컴포넌트 언마운트 시 이벤트 핸들러 제거
    return () => {
      Router.events.off("routeChangeStart", handleRouteChangeStart);
      Router.events.off("routeChangeComplete", handleRouteChangeComplete);
      Router.events.off("routeChangeError", handleRouteChangeComplete);
    };
  }, []);

  const handleRouteChangeStart = () => {
    setIsLoading(true);
  };

  const handleRouteChangeComplete = () => {
    setIsLoading(false);
  };

  return (
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <CookiesProvider>
          <SnackbarProvider maxSnack={3} autoHideDuration={1000}>
            <MobileHeader />
            {isLoading && (
              <Box sx={{ width: "100%" }}>
                <LinearProgress sx={{ color: "primary.main" }} />
              </Box>
            )}
            {/* 여기서 pageProps를 컴포넌트에 내려주지 않으면 SSR 불가 */}
            <Component {...pageProps} />
            <MobileBottom />
          </SnackbarProvider>
        </CookiesProvider>
      </Provider>
    </ThemeProvider>
  );
};

export default MyApp;
