import styled from "@emotion/styled";
import {
  Stack,
  TextField,
  InputAdornment,
  dividerClasses,
} from "@mui/material";
import { useRef, useState, ChangeEvent, useCallback } from "react";
import SearchIcon from "@mui/icons-material/Search";
import { ProductCardsGrid } from "../components/oranisms";
import { BlockText } from "../components/atoms";
import Head from "next/head";
import { debounce } from "@mui/material";

import {
  useGetRecommandsQuery,
  useGetProductListQuery,
} from "../queries/searchApi";

const Search = () => {
  const inputRef = useRef<HTMLInputElement>(null);
  const [keyword, setKeyword] = useState("");

  const { data: recommands, isLoading: recommandsLoading } =
    useGetRecommandsQuery(keyword);
  const { data: productList, isLoading: productListLoading } =
    useGetProductListQuery(keyword);

  // 제출 함수
  const submitHandler = (event: any) => {
    event.preventDefault();
    if (inputRef.current) {
      console.log(inputRef.current.value);
      setKeyword(inputRef.current.value);
    }
  };

  //여기서 쓰로틀링 api 사용할 거임
  const searchRecommend = useCallback(
    debounce((inputText: string) => {
      // 검색어 추출
      console.log(inputText);
    }, 500),
    []
  );

  //디바운스 옵션
  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    searchRecommend(value);
  };

  return (
    <Background>
      <Head>
        <title>{keyword} 검색</title>
      </Head>
      <form onSubmit={submitHandler}>
        <TextField
          inputRef={inputRef}
          onChange={handleInputChange}
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
      {recommands &&
        recommands.map((item, i) => <div key={i}>{item.name} </div>)}

      {keyword && (
        <BlockText style={{ margin: "10px" }} color="grey" size="0.9rem">
          검색어: {keyword}
        </BlockText>
      )}
      {productList &&
        productList.map((item, i) => <div key={i}>{item.name}</div>)}
    </Background>
  );
};

export default Search;

const Background = styled.div`
  padding: 40px 10px 50px 10px;
`;
