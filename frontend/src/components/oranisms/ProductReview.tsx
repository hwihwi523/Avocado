import styled from "@emotion/styled";
import { BlockText, InlineText } from "../atoms";
import { ReviewInput,Review } from "../molecues";



const ProductReview = () =>{

  // 더미 댓글 데이터
  const data = [
    {
      name:"김싸피",
      avatar:"spring_man",
      mbti:"ISFP",
      personal_color:"봄 라이트",
      rate:"4",
      content:"사이즈가 작아요 크게 주문하세요"
    },
    {
      name:"김싸피",
      avatar:"spring_man",
      mbti:"ISFP",
      personal_color:"봄 라이트",
      rate:"4",
      content:"사이즈가 작아요 크게 주문하세요"
    },
    {
      name:"김싸피",
      avatar:"spring_man",
      mbti:"ISFP",
      personal_color:"봄 라이트",
      rate:"4",
      content:"사이즈가 작아요 크게 주문하세요"
    },
    {
      name:"김싸피",
      avatar:"spring_man",
      mbti:"ISFP",
      personal_color:"봄 라이트",
      rate:"4",
      content:"사이즈가 작아요 크게 주문하세요"
    },
    {
      name:"김싸피",
      avatar:"spring_man",
      mbti:"ISFP",
      personal_color:"봄 라이트",
      rate:"4",
      content:"사이즈가 작아요 크게 주문하세요"
    },
  ]
  


  return(
    <>
    <ReviewInput/>
    {
      data ? (
        data.map((item:any, i:number)=>(
          <Review {...item} key={i}/>
      ))
        ):(
          <BlockText color="grey" type="L">리뷰가 없습니다.</BlockText>
      )
    }

    </>
  )
}


export default ProductReview 

const Background = styled.div`

`