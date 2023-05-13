import { Inter } from "next/font/google";
import styled from "@emotion/styled";
import { Grid, Button } from "@mui/material";
import Head from "next/head";

import {
  ProductCardsRow,
  Category,
  Commercials,
  UserProfile,
} from "../components/oranisms";
import { useRouter } from "next/router";
import { BlockText, InlineText } from "../components/atoms";
import { AppState, useAppSelector, wrapper } from "../features/store";
import { authenticateTokenInPages } from "../utils/authenticateTokenInPages";
import { productApi } from "../features/product/productApi";
import { setProductListBySearch } from "../features/product/productSlice";
import {useState, useEffect} from 'react'
import { mbti_list, personal_color_list } from "../components/atoms/data";
import PopupCommercial from "../components/oranisms/PopupCommercial";


const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const router = useRouter();
  const [popup, setPopup] = useState<boolean>(false)

  useEffect(()=>{
    let expiration = localStorage.getItem("commercial_expiration_time")
    console.log("expiration >>> ",expiration)
    if(expiration){
      const currentTime = new Date().getTime();
      if(currentTime < Number(expiration)){
        setPopup(false);
      }else{
        setPopup(true);
      }
    }else{
      setPopup(true);
    }
  },[])

  const member = useAppSelector((state: AppState) => state.auth.member);

  const name = "김싸피";
  return (
    <BackgroundDiv>
      <Head>
        <title>Avocado</title>
        <meta
          name="description"
          content="mbti와 personal color를 기반으로 하는 개인화 추천 쇼핑몰"
        />
        <meta
          name="keywords"
          content={`mbit, 퍼스널컬러, 상의, 하의, 원피스, 신발, 가방, 악세서리`}
        />
        <meta property="og:title" content="Avocado" />
        <meta
          property="og:description"
          content="mbti와 personal color를 기반으로 하는 개인화 추천 쇼핑몰"
        />
      </Head>

      <Grid container gap={5}>
        {/* 사용자 프로필 */}
        <Grid item xs={12}>
          {member ? (
            <UserProfile member={member} />
          ) : (
            <Button
              fullWidth
              style={{
                backgroundColor: "white",
                color: "black",
                padding: "80px 20px",
                border: "1px solid black",
                marginTop: "20px",
              }}
              onClick={() => {
                router.push("/login");
              }}
            >
              3초 간편 로그인 하기
            </Button>
          )}
        </Grid>

        {/* 메뉴 카테고리 */}
        <Grid item xs={12}>
          <Category />
        </Grid>

        {/* 개인화 추천 제품 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {name}
            </InlineText>
            님을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow isLogin={!!member} />
        </Grid>

        {/* 브랜드 광고 */}
        <Grid item xs={12}>
          <BlockText>맞춤형 광고</BlockText>
          <Commercials />
        </Grid>

        {/* 펄스널 컬러별 추천 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {member?.personal_color_id
                ? personal_color_list[member?.personal_color_id]
                : "???"}{" "}
            </InlineText>
            사용자들을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow isLogin={!!member} />
        </Grid>

        {/* mbti별 추천 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {member?.mbti_id ? mbti_list[member?.mbti_id] : "???"}{" "}
            </InlineText>
            사용자들을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow isLogin={!!member} />
        </Grid>
      </Grid>


<PopupCommercial open={popup} setOpen={setPopup}/>

              

    </BackgroundDiv>
  );
}

const BackgroundDiv = styled.div`
  padding: 10px;
  box-sizing: border-box;
  margin-bottom: 50px;
`;

const StyledSpan = styled.span`
  font-weight: bold;
  font-size: 20px;
`;

// 서버에서 Redux Store를 초기화하고, wrapper.useWrappedStore()를 사용해
// 클라이언트에서도 동일한 store를 사용하도록 설정
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );

    // 필요한 내용 작성

    //유저정보 가져오기
    // console.log(">>>>>>>>>>>",store.getState().auth.member);

    //함수 불러오기
    // store.dispatch(
    //   productApi.endpoints.getProductDetail.initiate(lastSegment)
    // );

    //store에 집어 넣기
    // store.dispatch(setProductListBySearch());

    //
    return {
      props: {},
    };
  }
);
