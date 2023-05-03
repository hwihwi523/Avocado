// SSR 방식의 예제
import Head from "next/head";
import Link from "next/link";
import Axios from "axios";
import { TestProduct } from "@/src/queries/testProductListApi";
import Image from "next/image";

interface Props {
  products: TestProduct[];
}

export default function Home({ products }: Props) {
  return (
    <>
      <Head>
        <title>상품 목록</title>
      </Head>
      <h1>상품 목록</h1>
      <ul>
        {products &&
          products.map((product) => (
            <li key={product.id}>
              <Image
                alt="상품 이미지"
                src={product.image_link}
                width={300}
                height={300}
              />
              <a>{product.name}</a> ${product.price}
            </li>
          ))}
      </ul>
    </>
  );
}

export async function getServerSideProps() {
  const apiUrl = process.env.API_URL;
  if (!apiUrl) {
    throw new Error("API_URL 환경 변수가 설정되어 있지 않습니다.");
  }
  const res = await Axios.get(apiUrl);
  const products = res.data;
  return {
    props: {
      products,
    },
  };
}

// // CSR 방식의 예제
// import {
//   TestProduct,
//   useGetTestProductsQuery,
// } from "@/src/queries/testProductListApi";
// import { useState, useEffect } from "react";

// export default function ProductListTest() {
//   const { data, error, isLoading } = useGetTestProductsQuery();

//   const [list, setList] = useState<TestProduct[]>([]);
//   useEffect(() => {
//     if (data) {
//       setList(data);
//       console.log("Products:", data);
//     }
//   }, [data]);

//   return (
//     <div>
//       <div>test product list</div>
//       {list.map((product, index) => (
//         <div key={index}>
//           <p>{product.name}</p>
//         </div>
//       ))}
//     </div>
//   );
// }
