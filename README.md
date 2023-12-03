# Dust notification app / Miseya - 미세먼지 알림 서비스

## 서비스 소개
사용자가 선택한 지역의 미세먼지 농도를 표시해주는 서비스입니다.

<br><br>

## 프로젝트 상세 

### 개발기간
23.09.15 ~ 23.09.22

<br><br>

### 시연 영상
https://github.com/byu-rin/Miseya/assets/130144220/3e08039a-df93-4c24-a137-cc4136958699

<br><br>

   ## 주요 기능
### MainActivity
* 지역을 선택하면 해당 지역의 미세먼지 농도를 보여준다.

### NetWorkInterface
* Retrofit 을 통해 서버로부터 대기 정보를 비동기적으로 가져오며 요청을 실행하는동안 UI 차단 없이 백그라운드에서 데이터를 받아온다.

### NetWorkClient
* Retrofit 을 사용하여 HTTP 클라이언트 설정, 공공데이터포털의 대기 정보에 접근

### DustDTO
* 공공데이터포털에서 받아온 대기 오염 정보를 나타내기 위한 데이터 클래스들을 정의

<br><br>

   ## 주요 기술
* Retrofit : '@GET()'은 HTTP GET 요청을 수행하는 엔드포인트 정의
* Coroutine : 'suspend' 키워드를 이용하여 코루틴에서 호출.
* Spinner : 대기질 정보를 보기 위해 지역(도,시,군)을 선택하는 데 사용
* Retrofit : 대기 질 데이터를 가져오기 위한 네트워크 호출
* Coroutine : ('lifecycleScope.launch') 활용하여 UI가 반응적으로 유지
* API : 공공데이터포털 API 를 활용
* Gson : 서버로부터 받은 JSON 데이터를 Kotlin 으로 변환함. ('@SerializedName') 을 통해 다른 이름의 속성을 가진 객체 매핑
