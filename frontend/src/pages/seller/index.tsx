import styled from "@emotion/styled";
// import { Stack, Chip, IconButton } from "@mui/material";
// import Image from "next/image";
import { BlockText, InlineText } from "../../components/atoms";
// import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
// import FavoriteIcon from "@mui/icons-material/Favorite";
// import { Button } from "@mui/material";
import { useState } from "react";
// import Grid from "@mui/material/Grid";
// import AddIcon from "@mui/icons-material/Add";
// import { CartItem, SnapshotItem } from "../components/molecues";
// import router from "next/router";
// import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
// import TextField from "@mui/material/TextField";
// import { useSnackbar } from "notistack";
// import {
//   ProductCardsRow,
//   UserProfile,
//   UserStateSummary,
// } from "../components/oranisms";
// import { ChartPersonalColor } from "../components/oranisms/charts";
// import ClearIcon from "@mui/icons-material/Clear";
// import StarIcon from "@mui/icons-material/Star";
// import { InputAdornment, TextField } from "@mui/material";
// import { AddressInput } from "../components/molecues";
// import MapIcon from "@mui/icons-material/Map";
// import Script from "next/script";

import {
  MyProductsList,
  MyCommercial,
  StoreState,
} from "../../components/oranisms/seller";
import * as React from "react";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";

const Seller = () => {
  const [page, setPage] = useState("통계");

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setPage(newValue);
  };

  return (
    <Background>
      <TabContext value={page}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList onChange={handleChange} aria-label="lab API tabs example"  centered>
            <Tab label="통계" value="통계" />
            <Tab label="상품 목록" value="상품 목록" />
            <Tab label="광고 전략" value="광고 전략" />
          </TabList>
        </Box>

        {/* 통계 */}
        <TabPanel style={{padding:"10px 0px 90px 0px" , margin:0}} value="통계">
          <StoreState />
        </TabPanel>

        {/* 상품 목록 */}
        <TabPanel style={{padding:"10px 0px 90px 0px" , margin:0}} value="상품 목록">
          <MyProductsList />
        </TabPanel>

        {/* 광고 전략 */}
        <TabPanel style={{padding:"10px 0px 90px 0px" , margin:0}} value="광고 전략">
          <MyCommercial />
        </TabPanel>
      </TabContext>
    </Background>
  );
};

export default Seller;

const Background = styled.div`
  padding: 10px;
`;
