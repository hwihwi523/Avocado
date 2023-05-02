
import "public/fonts/style.css";
import "../styles/globals.css";
import React, { FC } from "react";
import { Provider } from "react-redux";
import { AppProps } from "next/app";
import { wrapper } from "../features/store";
import { MobileBottom, MobileHeader } from "../components/oranisms";


const MyApp: FC<AppProps> = ({ Component, ...rest }) => {
  const { store, props } = wrapper.useWrappedStore(rest);
  return (
    <Provider store={store}>
      <MobileHeader/>
      <Component {...props.pageProps} />
      <MobileBottom/>
    </Provider>
  );
};

export default MyApp;
