import styled from "@emotion/styled";
import { Stack, TextField, InputAdornment } from "@mui/material";
import { ChangeEvent, useState } from "react";
import SearchIcon from "@mui/icons-material/Search";
import { ProductCardsGrid } from "../components/oranisms";
import { BlockText, InlineText } from "../components/atoms";

const Search = () => {
    const [keyword, setKeyword] = useState("");
    const [showKeyword, setShowKeyword] = useState("");
  
    const handleContent = (event: ChangeEvent<HTMLInputElement>) => {
      const inputText = event.target.value;
      setKeyword(inputText);

    };
  
    const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
      if (event.key === "Enter") {
        setShowKeyword(keyword);
      }else{
        return;
      }
    };
  
    return (
      <Background>
        <TextField
          fullWidth
          label="검색"
          value={keyword}
          onChange={handleContent}
          onKeyPress={handleKeyPress}
          InputProps={{
            endAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
        />
  
        {showKeyword && (
          <BlockText style={{ margin: "10px" }} color="grey" size="0.9rem">
            검색어: {showKeyword}
          </BlockText>
        )}
<ProductCardsGrid />
      </Background>
    );
  };

export default Search;

const Background = styled.div`
  padding: 40px 10px 50px 10px;
`;
