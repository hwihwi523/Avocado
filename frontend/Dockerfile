FROM node:18-alpine

RUN mkdir -p /app
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package*.json ./

RUN npm install
copy . .
RUN npm run build

CMD ["npm", "run", "start"]
