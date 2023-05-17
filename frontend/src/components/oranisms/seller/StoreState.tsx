import styled from "@emotion/styled";
import { Stack, Button } from "@mui/material";
import { ChartGender, ChartMbti, ChartPersonalColor } from "../charts";
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
const StoreState = () => {
  const dispatch = useDispatch();

  const { data: provider_statistic, isLoading } =
    useGetStatisticDataForProviderQuery();
  console.log("provider_statistic >>> ", provider_statistic?.data.genders);

  // 통계 자료
  const statisticData = useAppSelector(
    (state: AppState) => state.statistic.selectedProductStatisticData
  );
  const ageGenderData = statisticData.age_gender_score;
  const mbtiData = statisticData.mbti_score;
  const personalColorData = statisticData.personal_color_score;

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

  return (
    <>
      {provider_statistic && (
        <Stack direction="column" spacing={1}>
          <Stack direction="row" justifyContent={"space-between"} spacing={1}>
            <IconBox>
              <MouseOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                조회 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.data.click_count)}
              </InlineText>
            </IconBox>
            <IconBox>
              <LocalShippingOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                판매 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.data.sell_count)}
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
                {formatCurrency(provider_statistic.data.total_revenue) + "원"}
              </InlineText>
            </IconBox>
            <IconBox>
              <CheckroomOutlinedIcon />
              <InlineText type="L" size="0.8rem">
                상품 수
              </InlineText>
              <InlineText>
                {formatCurrency(provider_statistic.data.merchandise_count)}
              </InlineText>
            </IconBox>
          </Stack>
          <Stack spacing={2}>
            <ChartGender data={provider_statistic.data.genders} />
            <ChartMbti mbtiData={mbtiData} />
            <ChartPersonalColor personalColorData={personalColorData} />
            {/* 로그아웃 버튼 */}
            <Button
              fullWidth
              color="error"
              variant="outlined"
              style={{ padding: "20px" }}
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
