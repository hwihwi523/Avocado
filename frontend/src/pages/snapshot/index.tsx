import styled from "@emotion/styled";
import { Stack } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import { SnapshotItem } from "../../components/molecues";
import router from "next/router";
import Link from "next/link";
const Snapshot = () => {
  //더미 데이터
  const data = [
    {
      like: true,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_640.jpg",
      name: "김싸피",
      products: ["제품1", "제품2", "제품3"],
      avatar: "summer_woman",
      mbti: "ISTP",
      personal_color: "spring bright",
      content:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.",
    },
    {
      like: true,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_640.jpg",
      name: "김싸피",
      products: ["제품1", "제품2", "제품3"],
      avatar: "summer_woman",
      mbti: "ISTP",
      personal_color: "spring bright",
      content:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.",
    },
    {
      like: true,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_640.jpg",
      name: "김싸피",
      products: ["제품1", "제품2", "제품3"],
      avatar: "summer_woman",
      mbti: "ISTP",
      personal_color: "spring bright",
      content:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.",
    },
    {
      like: true,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_640.jpg",
      name: "김싸피",
      products: ["제품1", "제품2", "제품3"],
      avatar: "summer_woman",
      mbti: "ISTP",
      personal_color: "spring bright",
      content:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.",
    },
    {
      like: true,
      img_url:
        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_640.jpg",
      name: "김싸피",
      products: ["제품1", "제품2", "제품3"],
      avatar: "summer_woman",
      mbti: "ISTP",
      personal_color: "spring bright",
      content:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.",
    },
  ];

  return (
    <Background>
      <Stack direction={"column"} spacing={10}>
        {data.map((item, i) => (
          <SnapshotItem {...item} key={i} />
        ))}
      </Stack>

      <RegistButton
        onClick={() => {
          router.push("/snapshot/regist"); //snapshot regist로 이동해야함
        }}
      >
        <AddIcon />
      </RegistButton>
    </Background>
  );
};

export default Snapshot;

const Background = styled.div`
  padding: 10px;
  box-sizing: border-box;
`;

const RegistButton = styled.button`
  position: fixed;
  border-radius: 50px;
  width: 50px;
  height: 50px;
  right: 10px;
  bottom: 10%;
  background-color: black;
  color: white;
  font-size: 2rem;
  box-shadow: 3px 3px 10px grey;
`;
