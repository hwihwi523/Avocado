import { RecommendProductItem } from "@/src/features/statistic/statisticSlice";
import styled from "@emotion/styled";
import { ProductCard } from "../molecues";

const ProductCardsRow: React.FC<{ data: RecommendProductItem[] }> = (props) => {
  const products_list = props.data;

  return (
    <RowScrollable>
      {products_list &&
        products_list.map((item: RecommendProductItem, i) => (
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

  overflow-y: hidden;

  ::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
`;
