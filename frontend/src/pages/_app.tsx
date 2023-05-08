import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC } from "react";
import { SessionProvider } from "next-auth/react";
import { Provider } from "react-redux";
import { AppContext, AppProps } from "next/app";
import type { Session } from "next-auth";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { SnackbarProvider } from "notistack";
// 토큰 관리, 자동 로그인, '오늘 더 이상 이 창을 보지 않음', 장바구니(우리 프로젝트 X) 등을 위해 사용
import { Cookies, CookiesProvider } from "react-cookie";
import { NextPage } from "next";
import { DecodedToken } from "../features/auth/authApi";
import jwt from "jsonwebtoken";
import { Member, clearAuth, setMember } from "../features/auth/authSlice";

export const appCookies = new Cookies(); // 앱에서 사용할 쿠키 생성

// Use of the <SessionProvider> is mandatory to allow components that call
// `useSession()` anywhere in your application to access the `session` object.
// 현재의 형태에 적용하기 위해 공식 레퍼런스들과 다르게 작성한 부분들이 있음.
// https://nextjs.org/docs/pages/api-reference/functions/get-initial-props <= NextPage 사용 이유
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

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    const refreshToken = appCookies.get("REFRESH_TOKEN");
    // 토큰 파싱
    const decodedToken = jwt.decode(refreshToken) as DecodedToken;
    const isExpired =
      new Date(decodedToken.exp * 1000).getTime() <= new Date().getTime();
    // refresh token이 없거나 만료된 경우(고민 좀 더..)
    if (!refreshToken || isExpired) {
      // Redux Store에서 유저 정보 삭제
      store.dispatch(clearAuth());
      // 쿠키 삭제
      appCookies.remove("ACCESS_TOKEN");
      appCookies.remove("REFRESH_TOKEN");
    } else {
      // 아니면 member 정보 저장
      const member: Member = {
        type: decodedToken.type,
        id: decodedToken.id,
        email: decodedToken.email,
        name: decodedToken.name,
        picture_url: decodedToken.picture_url,
        gender: decodedToken.gender,
        age: decodedToken.age,
        height: decodedToken.height,
        weight: decodedToken.weight,
        mbti_id: decodedToken.mbti_id,
        personal_color_id: decodedToken.personal_color_id,
      };

      // Redux store에 유저 정보 dispatch
      store.dispatch(setMember(member));
    }

    return {
      props: {},
    };
  }
);
