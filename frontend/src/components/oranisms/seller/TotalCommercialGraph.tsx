import styled from "@emotion/styled";
import AddIcon from "@mui/icons-material/Add";
import { Button, Stack, Divider } from "@mui/material";
import { ChartCommercialState } from "../charts";
import MouseOutlinedIcon from "@mui/icons-material/MouseOutlined";
import { InlineText } from "../../atoms";
import VisibilityOutlinedIcon from "@mui/icons-material/VisibilityOutlined";
import PaidOutlinedIcon from "@mui/icons-material/PaidOutlined";
import SellOutlinedIcon from "@mui/icons-material/SellOutlined";
const CommercialState = () => {
  const data = [
    {
      date: "2023-05-12",
      exposure_cnt: 22,
      click_cont: 12,
      purchase_amount: 1000,
      quantity: 5,
    },
    {
      date: "2023-05-13",
      exposure_cnt: 23,
      click_cont: 13,
      purchase_amount: 23000,
      quantity: 8,
    },
    {
      date: "2023-05-14",
      exposure_cnt: 2,
      click_cont: 5,
      purchase_amount: 310000,
      quantity: 2,
    },
    {
      date: "2023-05-15",
      exposure_cnt: 42,
      click_cont: 7,
      purchase_amount: 10020,
      quantity: 1,
    },
    {
      date: "2023-05-16",
      exposure_cnt: 52,
      click_cont: 13,
      purchase_amount: 1300,
      quantity: 15,
    },
  ];

  let date = data.map((item) => item.date);
  let exposure_cnt = data.map((item) => item.exposure_cnt);
  let click_cnt = data.map((item) => item.click_cont);
  let purchase_amount = data.map((item) => item.purchase_amount);
  let quantity = data.map((item) => item.quantity);

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
              <MouseOutlinedIcon  />
              <InlineText size="0.9rem" >
                총 클릭 수
              </InlineText>
              <InlineText color="red">{totalAmount(click_cnt)} 번</InlineText>
            </IconBox>
            <IconBox>
              <VisibilityOutlinedIcon />
              <InlineText size="0.9rem" color="grey">
                총 노출 수
              </InlineText>
              <InlineText color="green">
                {totalAmount(exposure_cnt)} 번
              </InlineText>
            </IconBox>
          </Stack>
          <Stack spacing={1} direction={"row"}>
            <IconBox>
              <PaidOutlinedIcon />
              <InlineText size="0.9rem" color="grey">
                총 판매 액수
              </InlineText>
              <InlineText color="blue">
                {totalAmount(purchase_amount)} 원
              </InlineText>
            </IconBox>
            <IconBox>
              <SellOutlinedIcon />
              <InlineText size="0.9rem" color="grey">
                총 판매 수
              </InlineText>
              <InlineText color="orange">{totalAmount(quantity)} 개</InlineText>
            </IconBox>
          </Stack>
        </Stack>
        <ChartCommercialState
          date={date}
          value={exposure_cnt}
          title={"광고 노출"}
          color="green"
        />
        <ChartCommercialState
          date={date}
          value={click_cnt}
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
          value={purchase_amount}
          title={"판매 총액"}
          color="blue"
        />
      </Stack>
    </Background>
  );
};

export default CommercialState;

const Background = styled.div`
  padding: 10px;
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
