import { ProductReview } from "@/src/features/product/productSlice";
import styled from "@emotion/styled";
import { BlockText } from "../atoms";
import { Review, ReviewInput } from "../molecues";

type ProductReviewProps = {
  reviews: ProductReview[];
};

const ProductReviewOrg: React.FC<ProductReviewProps> = ({ reviews }) => {
  return (
    <>
      <ReviewInput />
      {reviews ? (
        reviews.map((item: ProductReview) => (
          <Review review={item} key={item.id} />
        ))
      ) : (
        <BlockText color="grey" type="L">
          리뷰가 없습니다.
        </BlockText>
      )}
    </>
  );
};

export default ProductReviewOrg;

const Background = styled.div``;
