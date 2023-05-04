import styled from "@emotion/styled";




const ProductDescription = (props:any) =>{
    // const {content} = props;
  
    //더미데이터
   const content = `<p style="text-align: justify;"><strong>Composition</strong><br />Black T-shirt made of 65% cotton and 35% polyester, has a round ribbed neckline, short sleeves, grey panel detailing on the side seams, rubber print swoosh logo on the upper right chest and T90 transferred onto the upper left chest<br /><br /><strong>Fitting</strong><br />Regular<br /><br /><strong>Wash care</strong><br />Machine wash warm with like colours<br />Wash inside out<br />Do not bleach <br />Tumble dry on low<br />Warm iron<br />Do not dry clean<br /><br />Gear up for an aggressive game of football in this black T90 T-shirt from nike. The classic design is updated with grey panel detailing and bold T90 and nike logos, while the fabric keeps you fresh and comfortable during extended training sessions. Team it&nbsp; with track pants and performance shoes.</p>`
   
  
    return(
      <Background>
        {/* html 마크업 언어 파싱해주는 속성 */}
         <div dangerouslySetInnerHTML={{ __html: content }} />
      </Background>
    )
  }
  
  //여기는 SeoulNamsan 적용 안되서 기본 sens-self로 함
  const Background = styled.div`
    font-family:sens-self;
    padding:40px 10px;

  `
  
  export default ProductDescription;