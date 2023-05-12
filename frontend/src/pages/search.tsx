import styled from "@emotion/styled";
import { TextField, InputAdornment } from "@mui/material";
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

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemButton from "@mui/material/ListItemButton";

const Search = () => {
  const inputRef = useRef<HTMLInputElement>(null);
  const [recommand, setRecommand] = useState("");
  const [searchWord, setSearchWord] = useState("");
  const [recommandListOpen, setRecommandListOpen] = useState(true);

  const { data: recommands, isLoading: recommandsLoading } =
    useGetRecommandsQuery(recommand);
  const { data: productList, isLoading: productListLoading } =
    useGetProductListQuery(searchWord);

  // 제출 함수
  const submitHandler = (event: any) => {
    setRecommandListOpen(false);
    event.preventDefault();
    if(inputRef.current){
      setSearchWord(inputRef.current.value)
    }else{
      setSearchWord("")
    }
  };

  //여기서 쓰로틀링 api 사용할 거임
  const searchRecommend = useCallback(
    debounce((inputText: string) => {
      // 검색어 추출
      setRecommand(inputText);
      setRecommandListOpen(true);

      console.log({
        recommands,
        recommandListOpen,
      });
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
        <title>{searchWord} 검색</title>
      </Head>

      {/* 검색창 */}
      <form onSubmit={submitHandler}>
        <TextField
          onFocus={() => setRecommandListOpen(true)}
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
      {recommandListOpen && recommands && (
        <List
          style={{
            position: "absolute",
            left: 0,
            width: "100%",
            padding: "10px",
            top: "87px",
            zIndex: 100,
          }}
        >
          {recommands.map((item, i) => (
            <ListItem
              key={i}
              style={{ border: "1px solid #dddddd", backgroundColor: "white" }}
              component="div"
              disablePadding
            >
              <ListItemButton
                style={{ height: "70px" }}
                onClick={() => {
                  setSearchWord(item.name);
                  setRecommandListOpen(false);
                }}
              >
                <ListItemText primary={item.name} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      )}

      {/* 검색 결과 */}
      {productList && searchWord && (
        <>
          <BlockText color="grey" size="0.9rem" style={{ margin: "20px 0" }}>
            검색어: {searchWord}
          </BlockText>
          <ProductCardsGrid data={...productList} />
        </>
      )}

      {/* 검색어 입력 안했을 경우 */}
      {!searchWord && (
        <BlockText
          color="grey"
          style={{ textAlign: "center", marginTop: "20%" }}
        >
          검색어를 입력해 주세요
        </BlockText>
      )}
    </Background>
  );
};

export default Search;

const Background = styled.div`
  padding: 40px 10px 50px 10px;
  position: relative;
  width: 100vw;
  box-sizing: border-box;
`;
