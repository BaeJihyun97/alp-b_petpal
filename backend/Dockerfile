# OpenJDK 21 사용
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

RUN echo "root ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers


# Gradle Wrapper 복사 및 실행 권한 추가
COPY gradlew gradlew.bat ./
COPY gradle gradle/
RUN chmod +x gradlew  # 실행 권한 부여

# Gradle 빌드를 위해 build.gradle과 settings.gradle 복사
COPY build.gradle settings.gradle ./

# 종속성 다운로드 (캐시 활용)
RUN ./gradlew build --no-daemon || return 0

# 전체 소스 코드 복사
COPY . .

# 애플리케이션 빌드
RUN ./gradlew build --no-daemon --exclude-task test

# JAR 파일 실행 (디버깅 옵션 포함)
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
# CMD ["java", "$JAVA_OPTS", "-jar", "build/libs/*.jar"]
# CMD ./gradlew bootRun --no-daemon --debug-jvm