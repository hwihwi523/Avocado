import styled from "@emotion/styled";
import { Stack, Box } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import { SnapshotItem } from "../../components/molecues";
import router from "next/router";
import Head from "next/head";
import { useState, useEffect } from "react";
import { useGetSnapshotListQuery } from "@/src/features/snapshot/snapshotApi";
import { BlockText } from "@/src/components/atoms";
import { SnapshotItem as snapshotItemType } from "@/src/features/snapshot/snapshotApi";
import { AppState, useAppSelector, wrapper } from "../../features/store";
import { authenticateTokenInPages } from "../../utils/authenticateTokenInPages";
import { useSnackbar } from "notistack";
import { useInView } from "react-intersection-observer";
import LinearProgress from "@mui/material/LinearProgress";

const Snapshot = () => {
  // 화면 제일 하단으로 갔는지 감시하는 라이브러리 => 무한스크롤 쌉가능
  const [ref, inView] = useInView();
  //화면에 에러표시
  const { enqueueSnackbar } = useSnackbar();

  const [lastId, setLastId] = useState<null | number>(null);
  const [isLastPage, setIsLastPage] = useState(false); // 마지막 페이지
  const [size, setSize] = useState(12);

  //데이터 불러오기
  const { data, isLoading, error } = useGetSnapshotListQuery(
    lastId === null ? { size } : { size, lastId }
  );
  //유저정보 가져오기 => 글쓰기를 할 수 있냐 없냐 판별하기 위해서
  const member = useAppSelector((state: AppState) => state.auth.member);

  //무한스크롤을 위해 게시글을 담아둘 객체
  const [snapshotList, setSnapshotList] = useState<snapshotItemType[]>([]);

  function redirectToRegistrationPage() {
    if (!member) {
      enqueueSnackbar(`로그인이 필요한 서비스 입니다.`, {
        variant: "error",
        anchorOrigin: {
          horizontal: "center",
          vertical: "bottom",
        },
      });
      return;
    }
    router.push("/snapshot/regist");
  }

  useEffect(() => {
    //data 안에 변하지 않는 변수가 있을거임 그거 찾으셈
    if (data && data.styleshot_list.length > 0) {
      setLastId(data.last_id);
      setIsLastPage(data.last_page);
      setSnapshotList((prevValue) => {
        // 이미 추가된 데이터는 중복으로 추가하지 않도록 필터링
        const filteredData = data.styleshot_list.filter(
          (item) => !prevValue.some((prevItem) => prevItem.id === item.id)
        );
        return [...prevValue, ...filteredData];
      });

    }
  }, [inView]);

  // 잘 넘어오는지 출력해 보기

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
        <InfinityScroll ref={ref}>
          {/* 로딩바 */}
          {isLoading && (
            <Box sx={{ width: "100%" }}>
              <LinearProgress />
            </Box>
          )}
          {/* 마지막 페이지 */}
          {isLastPage && <BlockText>마지막 페이지 입니다. </BlockText>}
        </InfinityScroll>
      </Stack>

      <RegistButton onClick={redirectToRegistrationPage}>
        글 쓰기 <AddIcon />
      </RegistButton>
    </Background>
  );
};

export default Snapshot;
const InfinityScroll = styled.div`
  width: 100%;
  height: 100px;
`;

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
