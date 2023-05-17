import styled from "@emotion/styled";
import { GendersData } from "@/src/features/statistic/statisticSlice";
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
import { Bar } from "react-chartjs-2";

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
  plugins: {
    title: {
      display: true,
      text: "Gender",
    },
  },
  responsive: true,
  scales: {
    x: {
      stacked: true,
    },
    y: {
      stacked: true,
    },
  },
};

const color_bar = ["rgb(53, 162, 235)", "rgb(255, 131, 157)"];

const ChartGender: React.FC<{ data: GendersData[] }> = (props) => {
  const gender_data = props.data;

  console.log("gender_data >>> ", gender_data);
  //그래프에 들어갈 데이터
  function dataFormat() {
    //데이터 모양
    let data = {
      labels: ["Gender"],
      datasets: gender_data.map((item: GendersData, i: number) => ({
        label: item.gender === "M" ? "남자" : "여자",
        data: [item.count],
        backgroundColor: color_bar[i],
      })),
    };

    return data;
  }

  return (
    <Background>
      <Bar options={options} data={dataFormat()} />
    </Background>
  );
};

export default ChartGender;

const Background = styled.div`
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #dddddd;
  border-radius: 10px;
`;
