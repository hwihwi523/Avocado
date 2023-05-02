import styled from "@emotion/styled";
import { BlockText } from "../atoms";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);


const ChartMbti = () => {
//실제로 받아와야 하는 데이터
 const mbti_data = [
  {
    type: "ISTJ",
    purchase: 123,
  },
  {
    type: "ISFJ",
    purchase: 13,
  },
  {
    type: "INFJ",
    purchase: 23,
  },
  {
    type: "INTJ",
    purchase: 23,
  },
  {
    type: "ISTP",
    purchase: 23,
  },
  {
    type: "ISFP",
    purchase: 103,
  },
  {
    type: "INFP",
    purchase: 53,
  },
  {
    type: "INTP",
    purchase: 13,
  },
  {
    type: "ESTP",
    purchase: 17,
  },
  {
    type: "ESFP",
    purchase: 44,
  },
  {
    type: "ENFP",
    purchase: 55,
  },
  {
    type: "ENTP",
    purchase: 81,
  },
  {
    type: "ESTJ",
    purchase: 1,
  },
  {
    type: "ESFJ",
    purchase: 3,
  },
  {
    type: "ENFJ",
    purchase: 1,
  },
  {
    type: "ENTJ",
    purchase: 2,
  },
];


  return (
    <Background>
      <BlockText type="B" style={{padding:"10px 0"}}>Preference by mbti </BlockText>
      <Pie data={dataFormat(mbti_data)} options={options} />
    </Background>
  );
};



export default ChartMbti;

//구매량 순서대로 정렬하는 함수
function sortByPurchaseAscending(data:any) {
  return data.sort((a:any, b:any) => a.purchase - b.purchase);
}


//가져온 데이터를 그래프에 넣을 수 있는 형태로 변환
export const dataFormat = (mbti_datas:any) => {
  //정렬하기
  const mbti_data = sortByPurchaseAscending(mbti_datas)

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

  data.labels = mbti_data.map((item:any) => item.type);
  data.datasets[0].data = mbti_data.map((item:any) => item.purchase);
  data.datasets[0].backgroundColor = mbti_color.map(
    (color) => `rgba(${color},0.6)`
  );


  return data;
};

const options={
  plugins:{
    legend:{
      display:true,
    }
  }
}



const Background = styled.div`
  border: 1px solid #dddddd;
  width: 100%;  
  border-radius:10px;
  text-align:center;
  padding:10px;
  box-sizing:border-box;
`;


const mbti_color =[
 
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
]