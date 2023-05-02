import { Inter } from "next/font/google";
import Head from "next/head";
import Image from "next/image";

// const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main>
      <Image
        src="/assets/images/logo.png"
        width={400}
        height={100}
        alt="로고 이미지 "
      />
      <h1> TEXT </h1>
      <div
        style={{
          fontFamily: "'SeoulNamsanEB', sans-serif",
          fontSize: "24px",
          color: "red",
        }}
      >
        {" "}
        Hello my name is 예삐예삐yo{" "}
      </div>
      <p
        style={{
          fontSize: "24px",
          color: "red",
        }}
      >
        Hello, world! 이거 적용되고 있는 거 맞냐 아니네
      </p>
      <p
        style={{
          fontFamily: "Helvetica, sans-serif",
          fontSize: "24px",
          color: "blue",
        }}
      >
        This is my Next.js app. 가을 아침은 내게 커다란 기쁨이야
      </p>
    </main>
  );
}
