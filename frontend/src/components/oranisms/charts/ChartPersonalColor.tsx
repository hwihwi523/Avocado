import styled from "@emotion/styled";
import { Bar } from "react-chartjs-2";
import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

//그래프 옵션
export const options = {
  responsive: true,
  plugins: {
    //라벨 옵션
    legend: {
      display: false,
      position: "top" as const,
    },
    //타이틀 옵션
    title: {
      display: true,
      text: "Personal Color",
    },
  },
};

interface ChartPersonalColorProps {
  personalColorData: number[];
}

const ChartPersonalColor: React.FC<ChartPersonalColorProps> = ({
  personalColorData,
}) => {
  //더미 데이터
  const personal_color_purchase = [
    {
      type: "봄 라이트",
      purchase: personalColorData[0],
    },
    {
      type: "봄 브라이트",
      purchase: personalColorData[1],
    },
    {
      type: "여름 라이트",
      purchase: personalColorData[2],
    },
    {
      type: "여름 브라이트",
      purchase: personalColorData[3],
    },
    {
      type: "여름 뮤트",
      purchase: personalColorData[4],
    },
    {
      type: "가을 뮤트",
      purchase: personalColorData[5],
    },
    {
      type: "가을 스트롱",
      purchase: personalColorData[6],
    },
    {
      type: "가을 딥",
      purchase: personalColorData[7],
    },
    {
      type: "겨울 브라이트",
      purchase: personalColorData[8],
    },
    {
      type: "겨울 딥",
      purchase: personalColorData[9],
    },
  ];

  //그래프에 넣을 데이터 형태에 맞게 넣어주는 함수
  function dataFormat() {
    //데이터 모양
    let data = {
      labels: ["※ 클릭 시 컬러명을 볼 수 있습니다."],
      datasets: [
        {
          label: "",
          data: [0],
          backgroundColor: "",
        },
      ],
    };

    //데이터 삽입
    data.datasets = personal_color_purchase.map((item: any, i: number) => ({
      label: item.type,
      data: [item.purchase],
      backgroundColor: `rgba(${personal_bar_color[i]},0.5)`,
    }));

    return data;
  }

  return (
    <Background>
      <Bar options={options} data={dataFormat()} />
    </Background>
  );
};

export default ChartPersonalColor;

const Background = styled.div`
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #dddddd;
  border-radius: 10px;
`;

const personal_bar_color = [
  "255,105,1",
  "254,66,20",
  "152,43,186",
  "94,47,233",
  "76,32,178",
  "1,98,255",
  "0,141,182",
  "6,110,145",
  "101,158,52",
  "119,186,65",
  "153,211,95",
  "218,235,54",
  "255,250,64",
  "253,197,5",
  "254,171,3",
];
