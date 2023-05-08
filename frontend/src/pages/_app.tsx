import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC } from "react";
import { Provider } from "react-redux";
import { AppProps } from "next/app";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";
import { SnackbarProvider } from "notistack";

const MyApp: FC<AppProps> = ({ Component, ...rest }) => {
  const { store, props } = wrapper.useWrappedStore(rest);
  return (
    <SnackbarProvider maxSnack={3}>
      <Provider store={store}>
        <MobileHeader />
        <Component {...props.pageProps} />
        <MobileBottom />
      </Provider>
    </SnackbarProvider>
  );
};

export default MyApp;
