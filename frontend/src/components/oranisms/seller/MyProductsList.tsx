import styled from "@emotion/styled";
import {
  ProductItem,
  useGetProductListByCategoryQuery,
} from "@/src/features/seller/sellerApi";
import { useState, useEffect } from "react";
import Category from "../Category";
import ProductCardsGrid from "../ProductCardsGrid";
import { useInView } from "react-intersection-observer";
import LinearProgress from "@mui/material/LinearProgress";
import { BlockText, InlineText } from "../../atoms";
import { Box, Grid } from "@mui/material";
import { ProductCard } from "../../molecues";

const MyProductsList: React.FC<{ provider_id: string }> = (props) => {
  const [ref, inView] = useInView(); //무한스크롤 감지 라이브러리
  const { provider_id } = props; //판매자 이이디
  const [category, setCategory] = useState<number>(6); //카테고리 , 처음에는 전체를 조회하기위해 null 표시
  const [last, setLast] = useState<number | null>(null); //마지막 제품 아이디
  const [size, setSize] = useState<number>(12); //한번에 보여주는 제품 개수
  const [isLastPage, setIsLastPage] = useState(false); // 마지막 페이지 인지 여부 확인
  const [registOpen, setRegistOpen] = useState(false); // 등록페이지 popup

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

  useEffect(() => {
    setLast(null);
    setIsLastPage(false);
    setProductList([]);
  }, [category]);

  function registDialogHandler() {}

  return (
    <>
      <Category setCategory={setCategory} />

      {/* <ProductCardsGrid/> */}
      <Grid container>
        {!last &&
          data?.data.content.map((item, i) => (
            <Grid item lg={3} md={3} sm={4} xs={6} key={i}>
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
            <Grid item lg={3} md={3} sm={4} xs={6} key={i}>
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

      {isLastPage ? (
        <BlockText
          style={{ textAlign: "center", padding: "40px 0" }}
          color="grey"
        >
          마지막 페이지 입니다.{" "}
        </BlockText>
      ) : isLoading ? (
        <Box sx={{ width: "100%" }}>
          <LinearProgress />
        </Box>
      ) : (
        <InfinityScroll ref={ref} />
      )}
    </>
  );
};

export default MyProductsList;

const InfinityScroll = styled.div`
  width: 100%;
  height: 100px;
`;
