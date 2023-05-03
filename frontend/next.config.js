/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: [
      "cdn.pixabay.com",
      "img.freepik.com",
      "www.dior.com",
      "d3t32hsnjxo7q6.cloudfront.net",
    ],
  },
};

module.exports = nextConfig;
