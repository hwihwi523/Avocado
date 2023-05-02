// SSG 방식의 예제
import { wrapper } from "@/src/features/store";
import {
  getRunningQueriesThunk,
  getTestProducts,
  testProductListApi,
} from "@/src/queries/testProductListApi";

export default function ProductListTest({ products }) {
  return (
    <div>
      <div>test product list</div>
      {products.map((product, index) => (
        <div key={index}>
          <p>{product.name}</p>
        </div>
      ))}
    </div>
  );
}

export const getStaticProps = wrapper.getStaticProps((store) => async () => {
  await store.dispatch(getTestProducts);
  const products = store.getState().testProductsApi.data;
  return {
    props: {
      products,
    },
  };
});

// // CSR 방식의 예제
// import { useGetTestProductsQuery } from "@/src/queries/testProductListApi";
// import { useState, useEffect } from "react";

// export default function ProductListTest() {
//   const { data, error, isLoading } = useGetTestProductsQuery();

//   const [list, setList] = useState([]);
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
