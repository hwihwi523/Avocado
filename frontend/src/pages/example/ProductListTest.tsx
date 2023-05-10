// SSR 방식의 예제
import Head from "next/head";
import Axios from "axios";
import { TestProduct } from "@/src/queries/testProductListApi";
import Image from "next/image";
import { Product } from "@/src/features/product/productSlice";
import { authApi } from "@/src/features/auth/authApi";
import { wrapper } from "@/src/features/store";
import { useStore } from "react-redux";
import { productApi } from "@/src/features/product/productApi";
import jwt from "jsonwebtoken";

interface Props {
  products: Product[];
}

export default function Home({ products }: Props) {
  // console.log(products);

  // console.log("State on render", useStore().getState());

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
              <div>
                <Image
                  alt="상품 이미지"
                  src={product.image_url}
                  width={50}
                  height={50}
                />
              </div>
              <a>{product.merchandise_name}</a> ${product.price}
            </li>
          ))}
      </ul>
    </>
  );
}

export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async () => {
    // const productApiUrl = process.env.PRODUCT_API_URL;
    // const memberApiUrl = process.env.MEMBER_API_URL;
    // if (!productApiUrl) {
    //   throw new Error("API_URL 환경 변수가 설정되어 있지 않습니다.");
    // }
    // const res = await Axios.get(productApiUrl + "/merchandises?size=10");
    // const content = res.data.data.content;
    // console.log(content);

    // // store와 연동 예시 -> 판매자 로그인
    // const loginResponse = await store.dispatch(
    //   authApi.endpoints.sellerLogin.initiate({
    //     email: "avocado1@gmail.com",
    //     password: "avocado506",
    //   })
    // );
    // console.log("SELLER LOGIN RESPONSE:", loginResponse);
    // console.log(
    //   "SELLER LOGIN REFRESH_TOKEN:",
    //   loginResponse.data.refresh_token
    // );
    // // 토큰 파싱
    // const token = loginResponse.data.refresh_token;
    // const secret = process.env.JWT_SECRET;
    // try {
    //   const decoded = jwt.verify(token, secret);
    //   console.log("JWT_TOKEN_PARSING:", decoded);
    // } catch (err) {
    //   console.log("JWT_TOKEN_PARSING_ERR:", err);
    // }

    // store와 연동 예시 -> productList
    const productListResponse = await store.dispatch(
      productApi.endpoints.getProductList.initiate({
        store: "nike",
        category: 1,
        size: 3,
      })
    );

    let content: Product[] = [];
    if (productListResponse.data) {
      console.log(
        "PRODUCT LIST RESPONSE:",
        productListResponse.data.data.content
      );
      content = productListResponse.data.data.content;
    } else {
      throw new Error("Product List Response Error");
    }
    // 모든 API 작업이 끝날 때까지 대기 -> depreciated
    // await Promise.all(authApi.util.getRunningOperationPromises());

    console.log("SERVER STATE", store.getState().authApi);

    if (!content) {
      return {
        props: {
          products: [],
        },
      };
    }

    return {
      props: {
        products: content,
      },
    };
  }
);

// // CSR 방식의 예제
// import {
//   TestProduct,
//   useGetTestProductsQuery,
// } from "@/src/queries/testProductListApi";
// import { useState, useEffect } from "react";
// import { wrapper } from "@/src/features/store";
// import { useStore } from "react-redux";

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
