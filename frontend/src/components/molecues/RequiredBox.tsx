import { BlockText } from "../atoms";
import { Button } from "@mui/material";
import Image from "next/image";
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
          position: "relative", // added for the parent of Image component
        }}
      >
        <Image
          src="/assets/images/empty.jpg"
          alt="Background"
          layout="fill" // fill the parent component
          objectFit="cover" // replicate backgroundSize="cover"
          objectPosition="center" // replicate backgroundPosition="center"
          quality={100} // adjust for your needs
        />
        <Button
          style={{
            backgroundColor: "black",
            color: "white",
            padding: "20px",
            width: "80%",
            boxShadow: "3px 3px 10px grey",
            position: "absolute", // added to position over the Image component
            zIndex: 1, // added to stack above the Image component
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
