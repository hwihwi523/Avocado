import styled from "@emotion/styled";

import {
  CategoryScale,
  Chart as ChartJS,
  Filler,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  Title,
  Tooltip,
} from "chart.js";
import React from "react";
import { Line } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Filler,
  Legend
);

type Item = {
  title: string;
  value: number[];
  date: string[];
  color: string;
};

const ChartCommercialState: React.FC<Item> = (props) => {
  const { value, date, title, color } = props;

  //그래프 옵션
  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
        position: "top" as const,
      },
      title: {
        display: true,
        text: title,
      },
    },
  };

  //데이터 옵션
  const data = {
    labels: date,
    datasets: [
      {
        fill: true,
        label: `일별 ${title}`,
        data: value,
        borderColor: color,
        backgroundColor: "rgba(255,249,196,0.5)",
      },
    ],
  };

  return (
    <Background>
      <Line options={options} data={data} />
    </Background>
  );
};

export default ChartCommercialState;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #dddddd;
  border-radius: 10px;
`;
