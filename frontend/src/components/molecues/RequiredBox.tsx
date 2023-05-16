import { BlockText } from "../atoms";
import { Button } from "@mui/material";
import router from "next/router";

const Required: React.FC<{ title: string; to: string }> = (props) => {
  function redirection() {
    router.push(props.to);
  }

  return (
    <>
      <BlockText
        style={{
          width: "100%",
          height: "320px",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          backgroundImage: `url("/assets/images/empty_img.jpg")`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <Button
          style={{
            backgroundColor: "black",
            color: "white",
            padding: "20px",
            width: "80%",
            boxShadow: "3px 3px 10px grey",
          }}
          onClick={redirection}
        >
          {props.title}
        </Button>
      </BlockText>
    </>
  );
};

export default Required;
