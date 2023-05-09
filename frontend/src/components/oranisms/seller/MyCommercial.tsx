import styled from "@emotion/styled";
import AddIcon from "@mui/icons-material/Add";
import { Button, Stack, Divider } from "@mui/material";
import { CommercialState } from "../../molecues";

//더미데이터
const data = [
  {
    id: 123,
    age: 30,
    gender: "M",
    imgurl:
      "https://cdn.pixabay.com/photo/2023/04/24/17/48/bird-7948712__480.jpg",
    commercial_type_id: 2,
    mbti_id: 4,
    personal_color_id: 3,
    merchandise_id: 3,
    merchandise_name: "남자 가을 블레이저 더블자켓 마이 ",
    provider_id: 2,
    create_at: 20230902,
  },
  {
    id: 123,
    age: 30,
    gender: "M",
    imgurl:
      "https://cdn.pixabay.com/photo/2023/04/24/17/48/bird-7948712__480.jpg",
    commercial_type_id: 2,
    mbti_id: 4,
    personal_color_id: 3,
    merchandise_id: 3,
    merchandise_name: "남자 가을 블레이저 더블자켓 마이 ",
    provider_id: 2,
    create_at: 20230902,
  },
  {
    id: 123,
    age: 30,
    gender: "M",
    imgurl:
      "https://cdn.pixabay.com/photo/2023/04/24/17/48/bird-7948712__480.jpg",
    commercial_type_id: 2,
    mbti_id: 4,
    personal_color_id: 3,
    merchandise_id: 3,
    merchandise_name: "남자 가을 블레이저 더블자켓 마이 ",
    provider_id: 2,
    create_at: 20230902,
  },
  {
    id: 123,
    age: 30,
    gender: "M",
    imgurl:
      "https://cdn.pixabay.com/photo/2023/04/24/17/48/bird-7948712__480.jpg",
    commercial_type_id: 2,
    mbti_id: 4,
    personal_color_id: 3,
    merchandise_id: 3,
    merchandise_name: "남자 가을 블레이저 더블자켓 마이 ",
    provider_id: 2,
    create_at: 20230902,
  },
  {
    id: 123,
    age: 30,
    gender: "M",
    imgurl:
      "https://cdn.pixabay.com/photo/2023/04/24/17/48/bird-7948712__480.jpg",
    commercial_type_id: 2,
    mbti_id: 4,
    personal_color_id: 3,
    merchandise_id: 3,
    merchandise_name: "남자 가을 블레이저 더블자켓 마이 ",
    provider_id: 2,
    create_at: 20230902,
  },
];

const MyCommercial = () => {
  return (
    <Stack direction="column" spacing={5}>
      {data.map((item, i) => (
        <>
          <CommercialState data={item} key={i} />
          <Divider />
        </>
      ))}

      <RegistButton>
        <AddIcon />
      </RegistButton>
    </Stack>
  );
};

export default MyCommercial;

const RegistButton = styled.button`
  position: fixed;
  border-radius: 50px;
  width: 50px;
  height: 50px;
  right: 30px;
  bottom: 10%;
  background-color: black;
  color: white;
  font-size: 2rem;
  box-shadow: 3px 3px 10px grey;
`;
