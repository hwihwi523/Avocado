import styled from "@emotion/styled";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

interface CharMbtiProps {
  mbtiData: number[];
}

const ChartMbti: React.FC<CharMbtiProps> = ({ mbtiData }) => {
  //실제로 받아와야 하는 데이터
  const mbti_data = [
    {
      type: "INFJ",
      purchase: mbtiData[0],
    },
    {
      type: "INFP",
      purchase: mbtiData[1],
    },
    {
      type: "INTJ",
      purchase: mbtiData[2],
    },
    {
      type: "INTP",
      purchase: mbtiData[3],
    },
    {
      type: "ISFJ",
      purchase: mbtiData[4],
    },
    {
      type: "ISFP",
      purchase: mbtiData[5],
    },
    {
      type: "ISTJ",
      purchase: mbtiData[6],
    },
    {
      type: "ISTP",
      purchase: mbtiData[7],
    },
    {
      type: "ENFJ",
      purchase: mbtiData[8],
    },
    {
      type: "ENFP",
      purchase: mbtiData[9],
    },
    {
      type: "ENTJ",
      purchase: mbtiData[10],
    },
    {
      type: "ENTP",
      purchase: mbtiData[11],
    },
    {
      type: "ESFJ",
      purchase: mbtiData[12],
    },
    {
      type: "ESFP",
      purchase: mbtiData[13],
    },
    {
      type: "ESTJ",
      purchase: mbtiData[14],
    },
    {
      type: "ESTP",
      purchase: mbtiData[15],
    },
  ];

  return (
    <Background>
      <Pie data={dataFormat(mbti_data)} options={options} />
    </Background>
  );
};

export default ChartMbti;

//크기 순서대로 정렬하는 함수
function sortByPurchaseAscending(data: any) {
  return data.sort((a: any, b: any) => a.purchase - b.purchase);
}

//가져온 데이터를 그래프에 넣을 수 있는 형태로 변환
export const dataFormat = (mbti_datas: any) => {
  //정렬하기
  const mbti_data = sortByPurchaseAscending(mbti_datas);

  const data = {
    labels: [""],
    datasets: [
      {
        label: " # 총 구매수 ",
        data: [0],
        backgroundColor: [""],
        borderWidth: 1,
      },
    ],
  };

  data.labels = mbti_data.map((item: any) => item.type);
  data.datasets[0].data = mbti_data.map((item: any) => item.purchase);
  data.datasets[0].backgroundColor = mbti_color.map(
    (color) => `rgba(${color},0.6)`
  );

  return data;
};

const options = {
  plugins: {
    legend: {
      display: true,
    },
    title: {
      display: true,
      text: "MBTI",
    },
  },
};

const Background = styled.div`
  border: 1px solid #dddddd;
  width: 100%;
  border-radius: 10px;
  text-align: center;
  padding: 10px;
  box-sizing: border-box;
`;

const mbti_color = [
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
