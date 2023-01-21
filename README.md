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

## 개발회고록
1. 요구사항을 읽고 spring security와 JWT를 사용하기로 했다.
2. 화이트리스트를 추가해서 요구사항에 있는 유저만 회원가입 되도록 했다.
3. 세금관련된 변수명에 대한 고민이 많았지만 있는 그대로 번역해서 사용했다.
4. 스크랩데이터가 많아서 필요한 것만 사용할까 고민했지만, 전부다 저장 하기로 하였다.
   - 스크랩데이터가 어떤 주기로 변하는지 정확히 몰라서 스크랩 요청할때마다 새로 저장하기로 했다.
   - 그래서 환급금 계산시 최근 스크랩된 데이터를 사용한다.
5. 암호알고리즘 사용에 대해 고민이 많았다.
   - 비밀번호는 복호화가 필요없어서 Bcrypt를 사용했다.
   - 주민등록번호는 복호화가 필요해서 Aes256을 사용했고 각 유저마다 IV를 새로 생성하여 보안을 강화했다.
   - 주민등록번호 중복체크도 필요해서 Sha256로 암호화 후 저장하여 사용했다.
6. Swagger를 사용하여 API테스트가 쉽도록 구현했다.

## 후기


## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA
    - OS: Mac
    - Git
- Server
    - Java11
    - Spring Boot 2.7.7
    - JPA
    - Gradle
    - H2

## 빌드. 실행
~~~
//빌드
./gradlew clean build

//디렉토리이동
cd build/libs

//실행
java -jar refund-0.0.1-SNAPSHOT.jar
~~~

## 테스트 방법
1. http://localhost:8080/swagger-ui/index.html# 페이지이동합니다
2. User API에서 회원가입 요청으로 회원가입합니다.
3. Authorization API에서 로그인 요청으로 로그인합니다
4. 로그인이 정상적으로 되었다면 access token이 발급됩니다
   - swagger 상단오른쪽에 authorize 버튼은 클릭하여 Bearer access token을 입력합니다.
5. Scrap API에서 스크랩 요청으로 스크랩 데이터를 저장합니다.
6. Refund API에서 환급금 조회 요청으로 환급금을 조회합니다.
