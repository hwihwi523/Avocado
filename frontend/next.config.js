/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: [
      "cdn.pixabay.com",
      "img.freepik.com",
      "www.dior.com", // 테스트 상품 목록
      "d3t32hsnjxo7q6.cloudfront.net", // 테스트 상품 목록
      "assets.myntassets.com",
      "blog.kakaocdn.net",
      "encrypted-tbn0.gstatic.com",
      "image.fmkorea.com",
      "img.wowtv.co.kr",
      "k.kakaocdn.net",
      "avocado-img.s3.ap-northeast-2.amazonaws.com",
    ],
  },
};

module.exports = nextConfig;
