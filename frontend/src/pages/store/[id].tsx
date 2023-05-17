import styled from "@emotion/styled";
import {
  ProductItem,
  useGetProductListByCategoryQuery,
} from "@/src/features/seller/sellerApi";
import { useState, useEffect } from "react";

import { Category } from "@/src/components/oranisms";
import { useInView } from "react-intersection-observer";
import CircularProgress from "@mui/material/CircularProgress";
import { BlockText, InlineText } from "@/src/components/atoms";
import {
  Box,
  Button,
  Grid,
  Dialog,
  AppBar,
  Toolbar,
  IconButton,
  Stack,
} from "@mui/material";
import { ProductCard } from "@/src/components/molecues";
import Slide from "@mui/material/Slide";
import * as React from "react";
import { TransitionProps } from "@mui/material/transitions";
import { CommercialRegist } from "@/src/components/oranisms";
import CloseIcon from "@mui/icons-material/Close";
import { useRouter } from "next/router";
import Head from "next/head";
const Store: React.FC<{ provider_id: string }> = (props) => {
  const router = useRouter();
  const { provider_id, id: brand_name } = router.query;
  const [ref, inView] = useInView(); //무한스크롤 감지 라이브러리
  // const provider_id = props.provider_id;
  const [category, setCategory] = useState<number>(6); //카테고리 , 처음에는 전체를 조회하기위해 null 표시
  const [last, setLast] = useState<number | null>(null); //마지막 제품 아이디
  const [size, setSize] = useState<number>(12); //한번에 보여주는 제품 개수
  const [isLastPage, setIsLastPage] = useState(false); // 마지막 페이지 인지 여부 확인

  //rtk로 넘어가는 파라미터
  function requeryValue() {
    if (category === 6) {
      if (!last) {
        //null
        return { size, provider_id };
      }
      if (!!last) {
        //not null
        return { size, last, provider_id };
      }
    }

    if (category !== 6) {
      if (!last) {
        return { category, size, provider_id };
      }
      if (!!last) {
        return { category, size, provider_id, last };
      }
    }
  }

  const [productList, setProductList] = useState<ProductItem[]>([]);
  const { data, isLoading, error } = useGetProductListByCategoryQuery(
    requeryValue()
  );

  //무한스크롤을 이용하기위해 이어 붙임
  useEffect(() => {
    //data 안에 변하지 않는 변수가 있을거임 그거 찾으셈
    if (data) {
      setLast(data.data.last_id);
      setIsLastPage(data.data.is_last_page);
      setProductList((prevValue) => {
        // 이미 추가된 데이터는 중복으로 추가하지 않도록 필터링
        const filteredData = data.data.content.filter(
          (item) =>
            !prevValue.some(
              (prevItem) => prevItem.merchandise_id === item.merchandise_id
            )
        );
        return [...prevValue, ...filteredData];
      });
    }
  }, [inView]);

  //카테고리 변경될때마다 초기화 됨
  useEffect(() => {
    setLast(null);
    setIsLastPage(false);
    setProductList([]);
  }, [category]);

  return (
    <>
      <Head>
        <title>{brand_name}</title>
        <meta name="description" content={brand_name as string} />
        스토어 설명
        <meta name="keywords" content={brand_name as string} />
      </Head>

      <HeaderBox>
        <BlockText color="white" size="2rem">
          {brand_name && brand_name.toString().replace("_", " ").toUpperCase()}
        </BlockText>
        <BlockText color="white" size="1rem">
          s t o r e
        </BlockText>
      </HeaderBox>

      <Category setCategory={setCategory} />

      <Grid container>
        {!last &&
          data?.data.content.map((item, i) => (
            <Grid item lg={3} md={3} sm={4} xs={6} key={i} p={0.5}>
              <ProductCard
                data={{
                  id: item.merchandise_id,
                  img_url: item.image_url,
                  price: item.price,
                  discount: item.discounted_price,
                  brand: item.brand_name,
                  isBookmark: null, //내 상품에는 북마크가 없기 때문
                  tags: [item.merchandise_category],
                }}
              />
            </Grid>
          ))}

        {productList &&
          productList.map((item, i) => (
            <Grid item lg={3} md={3} sm={4} xs={6} key={i} p={0.5}>
              <ProductCard
                data={{
                  id: item.merchandise_id,
                  img_url: item.image_url,
                  price: item.price,
                  discount: item.discounted_price,
                  brand: item.brand_name,
                  isBookmark: null, //내 상품에는 북마크가 없기 때문
                  tags: [item.merchandise_category],
                }}
              />
            </Grid>
          ))}
      </Grid>

      {/* 무한 스크롤을 위한 감시 div와 로딩바 */}
      {isLastPage ? (
        <BlockText
          style={{ textAlign: "center", padding: "40px 0" }}
          color="grey"
        >
          마지막 페이지 입니다.{" "}
        </BlockText>
      ) : isLoading ? (
        <LoadingBox>
          <CircularProgress />
        </LoadingBox>
      ) : (
        <InfinityScroll ref={ref} />
      )}
    </>
  );
};

export default Store;

const HeaderBox = styled.div`
  background: linear-gradient(to bottom, #000000, #555555);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 17vh;
  margin-bottom: 5vh;
`;

const LoadingBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 80vh;
`;

//모달창을 위한 옵션
const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const InfinityScroll = styled.div`
  width: 100%;
  height: 100px;
`;
