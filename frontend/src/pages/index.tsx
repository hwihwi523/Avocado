import { Inter } from "next/font/google";
import styled from "@emotion/styled";
import { Grid, Button, Box } from "@mui/material";
import Head from "next/head";
import Image from "next/image";
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
import {
  setProductListBySearch,
  setProductListForGuest,
} from "../features/product/productSlice";
import { useState, useEffect, useRef } from "react";
import { mbti_list, personal_color_list } from "../components/atoms/data";
import PopupCommercial from "../components/oranisms/PopupCommercial";
import ProviderProfile from "../components/oranisms/ProviderProfile";
import { commercialApi } from "../features/commercial/commercialApi";
import {
  setCarouselCommercialList,
  setPopupCommercialList,
} from "../features/commercial/commercialSlice";
import { statisticApi } from "../features/statistic/statisticApi";

import { setRecommendProductsData } from "../features/statistic/statisticSlice";
import { RequiredBox } from "../components/molecues";
import CategoryForMain from "../components/oranisms/CategoryForMain";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const router = useRouter();
  const [popup, setPopup] = useState<boolean>(false);

  const member = useAppSelector((state: AppState) => state.auth.member);
  const { consumer_recommends, mbti_recommends, personal_color_recommends } =
    useAppSelector(
      (state: AppState) => state.statistic.recommendedProductsData
    ); //추천 제품 가져오기
  const guest_recommends = useAppSelector(
    (state: AppState) => state.product.productListForGuest
  );
  let general_recommends = [];
  if (consumer_recommends.length !== 0)
    general_recommends = consumer_recommends;
  else general_recommends = guest_recommends;

  const popup_list = useAppSelector(
    (state: AppState) => state.commercial.popupCommercialList
  );
  const carousel_list = useAppSelector(
    (state: AppState) => state.commercial.carouselCommercialList
  );

  useEffect(() => {
    //팝업 함수
    let expiration = localStorage.getItem("commercial_expiration_time");
    if (member?.type === "consumer") {
      if (expiration) {
        const currentTime = new Date().getTime();
        if (currentTime < Number(expiration)) {
          setPopup(false);
        } else {
          setPopup(true);
        }
      } else {
        setPopup(true);
      }
    }

    //성별 나이대 입력 안했으면 등록 페이지로 보내버리기
    if (!!member) {
      if (member.gender === "") {
        router.push("/register");
      }
    }
  }, [member]);

  const name = member ? member.name + " " : "??? ";
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

      <Grid container gap={3}>
        {/* 사용자 프로필 */}
        <Grid item xs={12}>
          {/* 로그인 안했을 때 */}
          {!member && (
            <Button
              fullWidth
              style={{
                backgroundColor: "white",
                color: "black",
              }}
              onClick={() => {
                router.push("/login");
              }}
            >
              {/* <Image
                src="/assets/images/kakao_login_bar.png"
                width={400}
                height={250}
                alt="카카오 로그인 바"
              /> */}
              <Box
                sx={{
                  display: "flex", // flex 컨테이너로 설정
                  flexDirection: "column",
                  width: "100%",
                  height: "180px",
                  backgroundColor: "black",
                  borderRadius: "5px",
                  color: "white",
                  fontSize: "2.9rem",
                  alignItems: "center",
                  justifyContent: "center",
                  textAlign: "center",
                  textTransform: "none",
                  lineHeight: "1.4",
                  fontFamily: "SeoulNamsanB",
                }}
              >
                Wellcome,
                <br />
                Start Avocado!
              </Box>
            </Button>
          )}

          {/* 판매자로 로그인 했을 경우 */}
          {member && member.type === "provider" && (
            <ProviderProfile member={member} />
          )}

          {/* 구매자로 로그인 했을 경우 */}
          {member && member.type === "consumer" && (
            <UserProfile member={member} />
          )}
        </Grid>

        {/* 메뉴 카테고리 */}
        <Grid item xs={12}>
          <CategoryForMain />
        </Grid>

        {/* 개인화 추천 제품 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {name}
            </InlineText>
            님을 위한 추천 아이템
          </BlockText>
          <ProductCardsRow data={general_recommends} />
        </Grid>

        {/* 브랜드 광고 */}
        <Grid item xs={12}>
          <BlockText>Today`s 스페셜 이슈</BlockText>
          <Commercials data={carousel_list} />
        </Grid>

        {/* mbti별 추천 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {!!member &&
              (member.mbti_id || member.mbti_id === 0) &&
              member.mbti_id !== -1
                ? '"' + mbti_list[member.mbti_id] + '" '
                : "??? MBTI "}
            </InlineText>
            사용자들을 위한 추천 아이템
          </BlockText>

          {!member && (
            <RequiredBox
              title={"로그인이 필요한 서비스 입니다."}
              to={"/login"}
            />
          )}

          {!!member &&
            (member.mbti_id === -1 ? (
              <RequiredBox
                title={"나의 Personal color 설정하러 가기"}
                to={"/user/regist"}
              />
            ) : (
              <ProductCardsRow data={mbti_recommends} />
            ))}
        </Grid>

        {/* 펄스널 컬러별 추천 */}
        <Grid item xs={12}>
          <BlockText color="grey" type="L">
            <InlineText size="1.2rem" color="black" type="B">
              {!!member &&
              (member.personal_color_id || member.personal_color_id === 0) &&
              member.personal_color_id !== -1
                ? '"' + personal_color_list[member.personal_color_id] + '" '
                : "??? 퍼스널 컬러 "}
            </InlineText>
            사용자들을 위한 추천 아이템
          </BlockText>

          {!member && (
            <RequiredBox
              title={"로그인이 필요한 서비스 입니다."}
              to={"/login"}
            />
          )}

          {!!member &&
            (member.personal_color_id === -1 ? (
              <RequiredBox
                title={"나의 Personal color 설정하러 가기"}
                to={"/user/regist"}
              />
            ) : (
              <ProductCardsRow data={personal_color_recommends} />
            ))}
        </Grid>
      </Grid>

      <PopupCommercial open={popup} setOpen={setPopup} data={popup_list} />
    </BackgroundDiv>
  );
}

const BackgroundDiv = styled.div`
  padding: 10px;
  box-sizing: border-box;
  margin-bottom: 50px;
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

    // ======== 광고  서버사이드 랜더링 =================

    //유저정보 가져오기
    const member = store.getState().auth.member;

    //member정보를 확인하고 적절한 광고Api 변수를 반환함
    function commercialRequestType() {
      if (!member) {
        return {}; //로그인 안했으면 아무거나 넘겨주겠지!
      }

      const age = member.age_group;
      const gender = member.gender;
      const mbti_id = member.mbti_id;
      const personal_color_id = member.personal_color_id;

      //사용자 설정에 따른 각종 리턴 타입
      if (mbti_id === -1) {
        if (personal_color_id === -1) {
          return { age, gender };
        } else {
          return { age, gender, personal_color_id };
        }
      } else {
        if (personal_color_id === -1) {
          return { age, gender, mbti_id };
        } else {
          return { age, gender, mbti_id, personal_color_id };
        }
      }
    }

    //비동기라 propmise형태로 가져오게 됨
    const commercial = await store.dispatch(
      commercialApi.endpoints.getExpostCommercialList.initiate(
        commercialRequestType()
      )
    );

    store.dispatch(
      setPopupCommercialList(commercial.data ? commercial.data.popup_list : [])
    );

    store.dispatch(
      setCarouselCommercialList(
        commercial.data ? commercial.data.carousel_list : []
      )
    );

    // ======== 추천상품 서버사이드 랜더링 =================

    // 서버에서 토큰을 헤더에 넣어주기 위한 작업
    let cookie = context.req?.headers.cookie;
    let accessToken = cookie
      ?.split(";")
      .find((c) => c.trim().startsWith("ACCESS_TOKEN="))
      ?.split("=")[1];

    if (accessToken) {
      const recommend_products = await store.dispatch(
        statisticApi.endpoints.getStatisticDataForPersonalRecommendation.initiate(
          accessToken
        )
      );

      if (recommend_products.data) {
        store.dispatch(setRecommendProductsData(recommend_products.data.data));
      }

      // store.dispatch()
    } else {
      // 로그인 상태가 아니라면 게스트용 데이터 가져오기
      const guest_recommend_products = await store.dispatch(
        productApi.endpoints.getGuestRecommendProductList.initiate()
      );
      if (guest_recommend_products.data) {
        store.dispatch(setProductListForGuest(guest_recommend_products.data));
      }
    }

    return {
      props: {},
    };
  }
);
