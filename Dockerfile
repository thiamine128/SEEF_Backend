FROM --platform=$BUILDPLATFORM node:lts AS development
WORKDIR /code
COPY package.json /code/package.json
# 提前下载所有依赖项
RUN npm install

FROM development AS dev-envs
RUN apt-get update
RUN apt-get install -y --no-install-recommends git
COPY . /code
CMD [ "npm", "run", "serve" ]