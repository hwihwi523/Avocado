import styled from "@emotion/styled";
import { Stack } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import { SnapshotItem } from "../../components/molecues";
import router from "next/router";
import Head from "next/head";
import { useState, useEffect } from "react";
import { useGetSnapshotListQuery } from "@/src/features/snapshot/snapshotApi";
import { BlockText } from "@/src/components/atoms";
import { SnapshotItem as snapshotItemType } from "@/src/features/snapshot/snapshotApi";




const Snapshot = () => {
  const { data, isLoading, error } = useGetSnapshotListQuery();
  const [snapshotList, setSnapshotList] = useState<snapshotItemType[]>([]);

  
  useEffect(()=>{
    if (data) {
      setSnapshotList((preValue) => [...preValue, ...data.styleshot_list]);
    }
    
  },[data])

  console.log("snapshotList >> ", snapshotList);

  return (
    <Background>
      <Head>
        <title>Avocado : snapshot</title>
        <meta name="description" content="snapshot 페이지" />
        <meta
          name="keywords"
          content={`mbit, 퍼스널컬러, 상의, 하의, 원피스, 신발, 가방, 악세서리`}
        />
        <meta property="og:title" content="snapshot" />
        <meta property="og:description" content="snapshot 페이지" />
      </Head>
      <Stack direction={"column"} spacing={10}>
        {snapshotList ? (
          snapshotList.map((item, i) => <SnapshotItem data={item} key={i} />)
        ) : (
          <BlockText
            color="grey"
            size="1.2rem"
            style={{ textAlign: "center", marginTop: "20%" }}
          >
            {" "}
            등록된 게시물이 없습니다.{" "}
          </BlockText>
        )}
      </Stack>

      <RegistButton
        onClick={() => {
          router.push("/snapshot/regist"); //snapshot regist로 이동해야함
        }}
      >
        글 쓰기 <AddIcon />
      </RegistButton>
    </Background>
  );
};

export default Snapshot;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;

const RegistButton = styled.button`
  position: fixed;
  border-radius: 50px;
  width: 110px;
  height: 50px;
  right: 10px;
  bottom: 10%;
  background-color: black;
  color: white;
  box-shadow: 3px 3px 10px grey;
`;
