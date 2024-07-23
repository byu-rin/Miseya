![Miseya](https://capsule-render.vercel.app/api?type=venom&color=0:134B70,100:508C9B&height=300&section=header&text=미세야.&fontSize=90&fontColor=D8E3E1)


# Miseya - 미세먼지 알림 서비스
> 선택 지역의 미세먼지 수치, 등급 알림 서비스<br/>
> 23.09.15 ~ 23.09.22

<br><br>

<img width="150" alt="미세먼지 좋음" src="https://github.com/user-attachments/assets/e0f4ec7d-2cc7-4786-8241-eff60c80a28b">
<img width="150" alt="미세먼지 나쁨" src="https://github.com/user-attachments/assets/c979d810-e814-4eff-aa55-ee3f0fefbaf8">
<img width="150" alt="미세먼지 보통" src="https://github.com/user-attachments/assets/3ef8f6e3-db7b-4248-b9ca-70ac4d1b7bf2">
<img width="150" alt="미세먼지 매우 나쁨" src="https://github.com/user-attachments/assets/a5a8c863-690a-4c9d-8396-86403b95d4a7">
<img width="150" alt="지역을 선택해 주세요" src="https://github.com/user-attachments/assets/8b003433-cefd-4cae-bf3e-fbf8ff809d3a">


<br><br>
## 사용된 기술 및 라이브러리

### Retrofit2 & OkHttp3
>미세먼지 정보를 API(공공데이터포털)로부터 가져올 때, Retrofit을 사용해 데이터를 조회, OkHttp3로 네트워크 성능을 관리

<br>

### Gson
>서버에서 받은 JSON 형식의 미세먼지 데이터를 안드로이드 앱 내에서 사용하기 위한 객체로 변환할 때 사용

<br>

### Coroutines
>Miseya 는 미세먼지 데이터를 외부 API로부터 비동기적으로 불러온다.<br>
>이 때, Retrofit과 코루틴을 결합하여 사용하면, 네트워크 요청을 간결하게 작성할 수 있다. <br>
>비동기 코드를 동기 코드처럼 읽고 쓸 수 있게 해주므로, 콜백 방식에서 발생하는 복잡성과 중첩 문제를 피할 수 있다.

<br>

### DataBinding & ViewBinding
>UI 컴포넌트와 데이터 소스를 바인딩하여 안전하게 데이터를 처리한다.<br>

<br>

### Hilt
>필요한 종속성을 자동 주입, 각 구성 요소의 초기화와 테스트 용이

<br><br>

## 개선 및 대안
>GSON 대신 serialization을 고려할 수 있다. 코틀린의 idiomatic 특성을 활용하여 성능최적화, 간결한 코드에 도움을 준다.<br/>
>민감한 데이터를 다루는 경우, HTTPS를 사용하여 네트워크 통신의 보안을 강화할 수 있다.
