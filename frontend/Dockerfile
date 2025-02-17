# Node.js 22 LTS 이미지 사용
FROM node:22

# 작업 디렉토리 설정
WORKDIR /app

# 패키지 설치를 위해 package.json과 package-lock.json 복사
COPY package*.json ./

# 패키지 설치
RUN npm install

# 전체 소스 코드 복사
COPY . .

# 개발 서버 실행 (디버깅 모드 활성화)
CMD ["npm", "run", "dev"]
