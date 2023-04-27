//컴포넌트를 만들 때 테스트 하는 곳

import Image from "next/image";
import { Stack } from "@mui/material";

const Bottom = (props: any) => {
  return (
    <div>
      <h1>바텀 만들기~~</h1>
      <Stack direction="row" spacing={2}></Stack>
    </div>
  );
};

export default Bottom;
