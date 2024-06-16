# 构建阶段
FROM node:18 AS builder

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json 到工作目录
COPY package*.json ./

# 安装项目依赖
RUN npm install --legacy-peer-deps

# 复制整个项目到工作目录
COPY . .

# 构建项目
RUN npm run build

# 生产阶段
FROM nginx:alpine

# 复制构建的文件到 Nginx 的 HTML 目录
COPY --from=builder /app/dist /usr/share/nginx/html

# 复制自定义的 Nginx 配置文件
COPY nginx.conf /etc/nginx/nginx.conf

# 暴露应用程序运行端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]


  