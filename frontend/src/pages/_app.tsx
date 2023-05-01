import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { MobileBottom, MobileHeader } from "../components/oranisms";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <MobileHeader />
      <Component {...pageProps} />
      <MobileBottom />
    </>
  );
}
