import { AppProps } from "next/app";
import { SnackbarProvider } from "notistack";
import "public/fonts/style.css";
import { FC, useEffect, useState } from "react";
import { Provider } from "react-redux";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { wrapper } from "../features/store";
import "../styles/globals.css";
// 토큰 관리, 자동 로그인, '오늘 더 이상 이 창을 보지 않음', 장바구니(우리 프로젝트 X) 등을 위해 사용
import { Box, LinearProgress, ThemeProvider, createTheme } from "@mui/material";
import { Router } from "next/router";
import { CookiesProvider } from "react-cookie";
import { createCookiesInstance } from "../utils/createCookiesInstance";

export const appCookies = createCookiesInstance(); // 앱에서 사용할 쿠키 생성

const MyApp: FC<AppProps> = ({ Component, ...rest }) => {
  // console.log("rest: ", pageProps);
  const { store, props } = wrapper.useWrappedStore(rest);

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
  const [isAppLoading, setIsAppLoading] = useState(false);
  const handleRouteChangeStart = () => {
    setIsAppLoading(true);
  };
  const handleRouteChangeComplete = () => {
    setIsAppLoading(false);
  };
  useEffect(() => {
    Router.events.on("routeChangeStart", handleRouteChangeStart);
    Router.events.on("routeChangeComplete", handleRouteChangeComplete);
    Router.events.on("routeChangeError", handleRouteChangeComplete);

    return () => {
      Router.events.off("routeChangeStart", handleRouteChangeStart);
      Router.events.off("routeChangeComplete", handleRouteChangeComplete);
      Router.events.off("routeChangeError", handleRouteChangeComplete);
    };
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <CookiesProvider>
          <SnackbarProvider maxSnack={3} autoHideDuration={1000}>
            <MobileHeader />
            {isAppLoading && (
              <Box sx={{ width: "100%" }}>
                <LinearProgress sx={{ color: "primary.main" }} />
              </Box>
            )}
            {/* 여기서 pageProps를 컴포넌트에 내려주지 않으면 SSR 불가 */}
            <Component {...props.pageProps} />
            <MobileBottom />
          </SnackbarProvider>
        </CookiesProvider>
      </Provider>
    </ThemeProvider>
  );
};

export default MyApp;
