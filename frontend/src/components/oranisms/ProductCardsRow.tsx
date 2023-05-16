import styled from "@emotion/styled";
import { useRouter } from "next/router";
import { Stack } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";
import { ProductCard } from "../molecues";
import Button from "@mui/material/Button";
import router from "next/router";
import { RecommendItem } from "@/src/features/statistic/statisticSlice"; //이 형태로 넣는게 제일 안전함

const ProductCardsRow: React.FC<{ data: RecommendItem[] }> = (props) => {
  const products_list = props.data;

  return (
    <RowScrollable>
      {products_list &&
        products_list.map((item: RecommendItem, i) => (
          <ProductCard
            data={{
              id: item.merchandise_id,
              img_url: item.image_url,
              price: item.price,
              discount: item.discounted_price,
              brand: item.brand_name,
              isBookmark: item.is_wishlist,
              tags: [item.merchandise_category],
            }}
            key={i}
          />
        ))}
    </RowScrollable>
  );
};

export default ProductCardsRow;

const RowScrollable = styled.div`
  position: relative;
  display: flex;
  width: 100%;
  overflow-x: scroll;
  white-space: nowrap;

  ::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
`;
