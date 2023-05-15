import styled from "@emotion/styled";
import { useState } from "react";

import {
  MyProductsList,
  MyCommercial,
  StoreState,
} from "../../components/oranisms/seller";
import * as React from "react";
import { Box, Tab } from "@mui/material";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import Head from "next/head";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
import { useEffect } from "react";
import router from "next/router";


const Seller = () => {
  //member 정보
  const member = useAppSelector((state: AppState) => state.auth.member);
  const [page, setPage] = useState("통계");
  useEffect(() => {
    if (!member) {
      router.replace("/");
    }
  }, []);

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setPage(newValue);
  };

  console.log(member);

  return (
    <Background>
      <Head>
        <title>판매자 전용 페이지</title>
      </Head>
      <TabContext value={page}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList
            onChange={handleChange}
            aria-label="lab API tabs example"
            variant="fullWidth"
            centered
          >
            <Tab label="통계" value="통계" />
            <Tab label="상품 목록" value="상품 목록" />
            <Tab label="광고 전략" value="광고 전략" />
          </TabList>
        </Box>

        {/* 통계 */}
        <TabPanel
          style={{ padding: "10px 0px 90px 0px", margin: 0 }}
          value="통계"
        >
          <StoreState />
        </TabPanel>

        {/* 상품 목록 */}
        <TabPanel
          style={{ padding: "10px 0px 90px 0px", margin: 0 }}
          value="상품 목록"
        >
          <MyProductsList provider_id={member ? member.id : "" } />
        </TabPanel>

        {/* 광고 전략 */}
        <TabPanel
          style={{ padding: "10px 0px 90px 0px", margin: 0 }}
          value="광고 전략"
        >
          <MyCommercial />
        </TabPanel>
      </TabContext>
    </Background>
  );
};

export default Seller;

export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (context) => {
    // 쿠키의 토큰을 통해 로그인 확인, 토큰 리프레시, 실패 시 로그아웃 처리 등
    await authenticateTokenInPages(
      { req: context.req, res: context.res },
      store
    );
    return {
      props: {},
    };
  }
);

const Background = styled.div`
  padding: 10px;
`;

