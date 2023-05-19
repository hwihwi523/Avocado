import styled from "@emotion/styled";
import { Stack, Button, Box } from "@mui/material";
import {
  ChartAgeGroup,
  ChartGender,
  ChartMbti,
  ChartPersonalColor,
} from "../charts";
import MouseOutlinedIcon from "@mui/icons-material/MouseOutlined";
import LocalAtmOutlinedIcon from "@mui/icons-material/LocalAtmOutlined";
import CheckroomOutlinedIcon from "@mui/icons-material/CheckroomOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import { useDispatch, useSelector } from "react-redux";
import { Member, clearAuth, setMember } from "@/src/features/auth/authSlice";
import router from "next/router";
import {
  removeToken,
  removeTokenAll,
  setToken,
} from "@/src/utils/tokenManager";
import { InlineText, BlockText } from "../../atoms";
import { AppState, useAppSelector } from "@/src/features/store";
import { useGetStatisticDataForProviderQuery } from "@/src/features/statistic/statisticApi";
import CircularProgress from "@mui/material/CircularProgress";
import { mbti_list, personal_color_list_eng } from "../../atoms/data";
import { StatisticDataForProvider } from "@/src/features/statistic/statisticSlice";

const StoreState = () => {
  const dispatch = useDispatch();

  const { data: provider, isLoading } = useGetStatisticDataForProviderQuery();
  const provider_statistic = provider?.data;

  //숫자 변환 함수 3000  => 3,000원
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US");
  }

  //로그아웃 버튼
  const handleLogout = () => {
    console.log("로그아웃 버튼이 클릭되었습니다.");
    removeTokenAll();
    dispatch(clearAuth());
    //로그인 페이지로 이동
    router.push("/login");
  };

  if (isLoading) {
    return (
      <LoadingBox>
        <CircularProgress />
      </LoadingBox>
    );
  }

  //mbti chart Data에 맞는 형태로 반환하는 함수
  function chartMbtiDataForm(provider_statistic: StatisticDataForProvider) {
    const result = [];
    for (let i = 0; i < mbti_list.length; i++) {
      let skip = false;

      for (let j = 0; j < provider_statistic?.mbtis.length; j++) {
        if (mbti_list[i] === provider_statistic.mbtis[j].kind) {
          result.push(provider_statistic.mbtis[j].count);
          skip = true;
          break;
        }
      }
      if (!skip) {
        result.push(0);
      }
    }
    return result;
  }

  //personal chat Data에 맞는 형태로 변환하는 함수
  function chartPersonalColorDataForm(
    provider_statistic: StatisticDataForProvider
  ) {
    const result = [];
    for (let i = 0; i < personal_color_list_eng.length; i++) {
      let skip = false;
      for (let j = 0; j < provider_statistic?.personal_colors.length; j++) {
        if (
          personal_color_list_eng[i] ===
          provider_statistic.personal_colors[j].kind
        ) {
          result.push(provider_statistic.personal_colors[j].count);
          skip = true;
          break;
        }
      }
      if (!skip) {
        result.push(0);
      }
    }
    return result;
  }

  return (
    <>
      {provider_statistic && (
        <Stack direction="column" spacing={1}>
          {/* 각종 수치 */}
          <Stack direction="row" justifyContent={"space-between"} spacing={1}>
            <IconBox>
              <MouseOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                조회 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.click_count)}
              </InlineText>
            </IconBox>
            <IconBox>
              <LocalShippingOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                판매 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.sell_count)}
              </InlineText>
            </IconBox>
          </Stack>
          <Stack direction="row" justifyContent={"space-between"} spacing={1}>
            <IconBox>
              <LocalAtmOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                총 판매 액
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.total_revenue) + "원"}
              </InlineText>
            </IconBox>
            <IconBox>
              <CheckroomOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                상품 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.merchandise_count)}
              </InlineText>
            </IconBox>
          </Stack>

          {/* 그래프 */}
          <Stack spacing={2}>
            <Box>
              <BlockText
                type="L"
                style={{ marginTop: "30px", marginBottom: "10px" }}
              >
                {" "}
                성별 구매 통계{" "}
              </BlockText>
              <ChartGender data={provider_statistic.genders} />
            </Box>

            <Box>
              <BlockText
                type="L"
                style={{ marginTop: "30px", marginBottom: "10px" }}
              >
                {" "}
                MBTI별 구매 통계{" "}
              </BlockText>
              <ChartMbti mbtiData={chartMbtiDataForm(provider_statistic)} />
            </Box>

            <Box>
              <BlockText
                type="L"
                style={{ marginTop: "30px", marginBottom: "10px" }}
              >
                {" "}
                Personal Color별 구매 통계{" "}
              </BlockText>
              <ChartPersonalColor
                personalColorData={chartPersonalColorDataForm(
                  provider_statistic
                )}
              />
            </Box>

            <Box>
              <BlockText
                type="L"
                style={{ marginTop: "30px", marginBottom: "10px" }}
              >
                {" "}
                나이대별 구매 통계{" "}
              </BlockText>
              <ChartAgeGroup data={provider_statistic.age_groups} />
            </Box>

            {/* 로그아웃 버튼 */}
            <Button
              fullWidth
              color="error"
              variant="outlined"
              style={{ padding: "20px", marginTop: "20px" }}
              onClick={handleLogout}
            >
              로그아웃
            </Button>
          </Stack>
        </Stack>
      )}
    </>
  );
};

export default StoreState;
const LoadingBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 80vh;
`;

const IconBox = styled.div`
  border: 1px solid #dddddd;
  border-radius: 10px;
  height: 80px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
