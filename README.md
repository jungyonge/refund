## 삼점삼 과제테스트

### 필수 요구 사항
~~~
- 과제 구현 시 Java11, Spring Boot2.7.x, JPA, H2, Gradle을 빠짐없이 모두 활용합니다.
- 프로젝트 character set은 UTF-8로 설정합니다.
- 삼쩜삼 백엔드 엔지니어 채용 과제 2
- DB는 H2 Embeded DB를 사용합니다.
- 회원가입, 환급액 계산, 유저 정보 조회 API를 구현합니다.
- 모든 요청과 응답에 대하여 application/json 타입으로 구현합니다.
- 각 기능 및 제약 사항에 대한 단위 테스트를 작성해야 하며 gradle 기준으로 실행 돼야합니다.
- swagger를 이용하여 API확인 및 API실행이 가능하도록 구현합니다.
- 민감 정보(주민등록번호, 비밀번호 등)는 암호화 된 상태로 저장합니다.
- README파일을 이용하여 Swagger 주소 및 요구 사항 구현 여부, 구현 방법, 그리고 검증 결과에 대해 작성합니다.
~~~

--- 
## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA
    - OS: Mac
    - Git
- Server
    - Java11
    - Spring Boot 2.7.7
    - Spring Security
    - JWT
    - JPA
    - Gradle
    - H2
  
---
## 빌드, 실행
~~~
//압축해제
unzip szs-refund.zip

//폴더이동
cd /refund

//빌드
./gradlew clean build

//디렉토리이동
cd build/libs

//실행
java -jar refund-0.0.1-SNAPSHOT.jar

//swagger 접속
http://localhost:8080/swagger-ui/index.html

//h2-console 접속 
http://localhost:8080/h2-console/login.jsp
~~~

---
## 요구사항 구현 여부
- 회원가입 구현 완료
  - 주어진 홍길동, 김둘리, 마징가, 베지터, 손오공의 이름과 주민번호로만 가입가능
- 로그인 구현 완료
  - 로그인 완료시 JWT access token, refresh token 발급
- 회원정보 조회 구현 완료
  - access token에 담긴 정보로 조회
- 스크랩 요청 구현 완료
  - https://codetest.3o3.co.kr/v2/scrap 로 데이터 요청 후 DB저장
- 환급금 조회 구현 완료
  - 스크랩이 사전에 완료되어야 조회 가능
- Swageer로 API테스트 환경 구축 완료

---
## 구현방법
- Spring security, JWT를 사용하여 인증, 인가 구현했습니다.
- 회원가입, 로그인, 토큰재발급을 제외한 다른 요청에는 Header에 Jwt 필요합니다.
  - 화이트리스트를 사전에 DB에 등록하여 리스트안에 있을 시 회원가입 가능합니다.
- 암호화 알고리즘은 총 3개를 사용 했습니다.
  - 비밀번호는 복호화가 필요없다고 생각하여 Bcrypt 사용
  - 주민등록번호는 스크랩데이터 요청시 필요하여 복호화가 가능한 AES256 사용, 유저마다 IV를 랜덤생성하여 보안강화
  - 주민등록번호는 중복체크를 하기위해 SHA256암호화하여 저장후 중복체크
- 스크랩조회시 나오는 데이터는 모두 저장했습니다.
- 저장된 데이터를 바탕으로 환급금 조회를 합니다.
- Swagger를 사용하여 API 테스트 환경을 구축했습니다.

---
## 테스트 순서
1. http://localhost:8080/swagger-ui/index.html# 페이지이동합니다
2. User API에서 회원가입 요청으로 회원가입합니다.
3. Authorization API에서 로그인 요청으로 로그인합니다
4. 로그인이 정상적으로 되었다면 access token이 발급됩니다
   - swagger 상단오른쪽에 authorize 버튼은 클릭하여 Bearer access token을 입력합니다.
5. Scrap API에서 스크랩 요청으로 스크랩 데이터를 저장합니다.
6. Refund API에서 환급금 조회 요청으로 환급금을 조회합니다.

---
## 테스트결과
~~~
{
    "이름": "홍길동",
    "결정세액": "0",
    "퇴직연금세액공제": "900,000"
}

{
    "이름": "김둘리",
    "결정세액": "145,500",
    "퇴직연금세액공제": "200,000"
}

{
    "이름": "마징가",
    "결정세액": "556,000",
    "퇴직연금세액공제": "1,085,000"
}

{
    "이름": "베지터",
    "결정세액": "1,687,000",
    "퇴직연금세액공제": "380,000"
}

{
    "이름": "손오공",
    "결정세액": "675,800",
    "퇴직연금세액공제": "450,000"
}
~~~

---
## 후기
1. 확실히 레이어드 아키텍처를 지향하니 테스트코드 작성하기가 좀더 수월 한 것 같다.
2. LocalDateTime.now()로직을 변경하여 mocking하기 쉽게 해야겠다.
3. Swagger를 사용하니 테스트가 편한 점도 있지만, 설정등 할게 많은 듯 하다.