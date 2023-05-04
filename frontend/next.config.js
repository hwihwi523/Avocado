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
    ],
  },
};

module.exports = nextConfig;
