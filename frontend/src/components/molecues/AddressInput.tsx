import { Button } from "@mui/material";
import DaumPostcode from "react-daum-postcode";

const AddressInput: React.FC<{
  setVisible: (data: boolean) => void;
  dataRef: (data: string) => void;
}> = (props) => {
  const handleComplete = (data: any) => {
    let fullAddress = data.address;
    let extraAddress = "";
    props.dataRef(fullAddress);
    props.setVisible(false);
  };

  return (
    <>
      <div>
        <DaumPostcode onComplete={handleComplete} />
        <Button
          sx={{ mt: 3 }}
          fullWidth
          onClick={() => {
            props.setVisible(false);
          }}
          variant="outlined"
          color="error"
        >
          close
        </Button>
      </div>
    </>
  );
};

export default AddressInput;
