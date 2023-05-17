import styled from "@emotion/styled";
import { Doughnut } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

interface ChartAgeGroup {
  age_group: number;
  count: number;
}

const ChartAgeGroup: React.FC<{ data: ChartAgeGroup[] }> = (props) => {
  //실제로 받아와야 하는 데이터
  const age_group_data = props.data;
  return (
    <Background>
      <Doughnut data={dataFormat(age_group_data)} options={options} />
    </Background>
  );
};

export default ChartAgeGroup;

//크기 순서대로 정렬하는 함수
function sortByPurchaseAscending(data: ChartAgeGroup[]) {
  return data.sort((a: any, b: any) => a.count - b.count);
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

  data.labels = mbti_data.map((item: any) => item.age_group + " 대");
  data.datasets[0].data = mbti_data.map((item: any) => item.count);
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
      text: "Age",
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
