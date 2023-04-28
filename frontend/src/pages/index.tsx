import { Inter } from "next/font/google";
import Image from "next/image";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main>
      <Image
        src="/images/logo.png"
        width={400}
        height={100}
        alt="로고입니다. "
      />
      <h1>가즈아ㅏㅏ </h1>
    </main>
  );
}
