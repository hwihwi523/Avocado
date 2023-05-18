import styled from "@emotion/styled";
import { Stack } from "@mui/material";
import { ChartCommercialState } from "../charts";
import MouseOutlinedIcon from "@mui/icons-material/MouseOutlined";
import { InlineText } from "../../atoms";
import VisibilityOutlinedIcon from "@mui/icons-material/VisibilityOutlined";
import PaidOutlinedIcon from "@mui/icons-material/PaidOutlined";
import SellOutlinedIcon from "@mui/icons-material/SellOutlined";
import { useGetCommercialAnalysesQuery } from "@/src/features/commercial/commercialApi";
import { useState, useEffect } from "react";

const CommercialState: React.FC<{ commercialId: number }> = (props) => {
  const { commercialId } = props;

  const { data: analyses, refetch } =
    useGetCommercialAnalysesQuery(commercialId);

  const [date, setDate] = useState<string[]>([]);
  const [exposureCnt, setExposureCnt] = useState<number[]>([]);
  const [clickCnt, setClickCnt] = useState<number[]>([]);
  const [purchaseAmount, setPurchaseAmount] = useState<number[]>([]);
  const [quantity, setQuantity] = useState<number[]>([]);

  // console.log("analyses >>>", analyses);

  useEffect(() => {
    if (analyses) {
      refetch();
      setDate(analyses.map((item) => item.date));
      setExposureCnt(analyses.map((item) => item.exposure_cnt));
      setClickCnt(analyses.map((item) => item.click_cnt));
      setPurchaseAmount(analyses.map((item) => item.purchase_amount));
      setQuantity(analyses.map((item) => item.quantity));
    }
  }, [commercialId]);

  //숫자 변환 함수 3000 = > 3,000
  function formatCurrency(num: number) {
    return num.toLocaleString("en-US");
  }

  //총합 판단하는 함수
  function totalAmount(data: number[]) {
    let result = 0;
    for (let i = 0; i < data.length; i++) {
      result += data[i];
    }

    return formatCurrency(result);
  }

  return (
    <Background>
      <Stack spacing={2}>
        <Stack direction={"column"} spacing={1}>
          <Stack spacing={1} direction={"row"}>
            <IconBox>
              <MouseOutlinedIcon color="error" />
              <InlineText size="0.9rem" color="grey">
                총 클릭 수
              </InlineText>
              <InlineText>{totalAmount(clickCnt)} 번</InlineText>
            </IconBox>
            <IconBox>
              <VisibilityOutlinedIcon color="success" />
              <InlineText size="0.9rem" color="grey">
                총 노출 수
              </InlineText>
              <InlineText>{totalAmount(exposureCnt)} 번</InlineText>
            </IconBox>
          </Stack>
          <Stack spacing={1} direction={"row"}>
            <IconBox>
              <PaidOutlinedIcon color="primary" />
              <InlineText size="0.9rem" color="grey">
                총 판매 액수
              </InlineText>
              <InlineText>{totalAmount(purchaseAmount)} 원</InlineText>
            </IconBox>
            <IconBox>
              <SellOutlinedIcon color="warning" />
              <InlineText size="0.9rem" color="grey">
                총 판매 수
              </InlineText>
              <InlineText>{totalAmount(quantity)} 개</InlineText>
            </IconBox>
          </Stack>
        </Stack>
        <ChartCommercialState
          date={date}
          value={exposureCnt}
          title={"광고 노출"}
          color="green"
        />
        <ChartCommercialState
          date={date}
          value={clickCnt}
          title={"클릭 수"}
          color="red"
        />
        <ChartCommercialState
          date={date}
          value={quantity}
          title={"판매 량"}
          color="orange"
        />
        <ChartCommercialState
          date={date}
          value={purchaseAmount}
          title={"판매 총액"}
          color="blue"
        />
      </Stack>
    </Background>
  );
};

export default CommercialState;

const Background = styled.div`
  padding: 30px 10px 0px 10px;
  box-sizing: border-box;
`;

const IconBox = styled.div`
  border: 1px solid #dddddd;
  border-radius: 10px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 10px;
`;
