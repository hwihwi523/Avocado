import styled from "@emotion/styled";
import { Stack, TextField, InputAdornment } from "@mui/material";
import { useRef, useState } from "react";
import SearchIcon from "@mui/icons-material/Search";
import { ProductCardsGrid } from "../components/oranisms";
import { BlockText, InlineText } from "../components/atoms";
import Head from "next/head"
const Search = () => {

    const inputRef = useRef<HTMLInputElement>(null);
    const [keyword, setKeyword]= useState("")
    
  
    const submitHandler = (event:any) => {
        event.preventDefault();
        if(inputRef.current){
            console.log(inputRef.current.value)
            setKeyword(inputRef.current.value)
        }
    };


    
  
  
    return (
      <Background>
        <Head>
          <title>{keyword} 검색</title>
        </Head>
        <form onSubmit={submitHandler} >
        <TextField
        inputRef={inputRef}
          fullWidth
          label="검색"
          InputProps={{
              endAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
        }}
        />
        </form>
        {/* 추천 검색어 */}
        <div></div>
  
        {keyword && (
          <BlockText style={{ margin: "10px" }} color="grey" size="0.9rem">
            검색어: {keyword}
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
