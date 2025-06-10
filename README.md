# 🧠 Study With Me - Backend

온라인 스터디프로젝트의 백엔드 서비스입니다.
사용자는 웹사이트에서 스터디를 진행할 수 있고, 스터디를 세팅하고 참여, 수많은 토론 과 자료 공유, 자동 출석 체크가 가능합니다.

---

## 🚀 주요 기술 스택

| 범위 | 기술                  |
| -- |---------------------|
| 언어 | Java 17             |
| 프레임워크 | Spring Boot         |
| 빌드 툴 | Maven               |
| DB | MySQL               |
| ORM | Spring Data JPA     |
| 인증 | Spring Security, JWT |
| 메일 | JavaMailSender      |
| 문서화 | Swagger (예정)        |
| 배포 | AWS EC2 / S3 (예정)   |

---

## 📂 프로젝트 구조

```
study-with-me/
 └─ src/
     └─ main/
         ├─ java/
         │   └─ org.seungjae/
         │       ├─ controller/
         │       ├─ service/
         │       ├─ repository/
         │       ├─ entity/
         │       └─ StudyWithMeApplication.java
         └─ resources/
             ├─ application.properties
             ├─ static/
             └─ templates/
```

---

## 🤖 주요 기능

* ✅ 스터디룰 생성 / 목록 조회
* ✅ 줌 연결 (Zoom 연동안 포함)
* ✅ 참여심청 / 승인 / 거절
* ✅ 공지, 자료 공유 게시판
* ✅ 자동 출석 체크 (Zoom 접속 기록 기반)
* ✅ 마이페이지에서 조회
* ✅ 리더 권한 이전
* ✅ 포스트 및 메일 인증

---


## 빌드 및 실행

```bash
./mvnw spring-boot:run
```

이것이 안되면 IDE에서 `StudyWithMeApplication.java` 실행

---

## 구현 예정 기능

* Swagger API 문서 자동화
* AWS S3 파일 업로드 연동
* Zoom API 연동 자동 스케줄러 출석 체크
* OAuth2 로그인 (Google, Kakao 등)

---

## 개발자

| 이름  | GitHub                                                 |
| --- |--------------------------------------------------------|
| 이승재 | [github.com/SeungjaeLee00](https://github.com/SeungjaeLee00) |

