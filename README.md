# MISEYA

![Miseya](https://capsule-render.vercel.app/api?type=venom&color=0:134B70,100:508C9B&height=300&section=header&text=미세야.&fontSize=80&fontColor=D8E3E1&animation=fadeIn)

<br>

# Overview
**23.09.15 ~ 23.09.22** <br><br>

Miseya는 실시간 API 데이터를 활용해 미세먼지 정보를 시각적으로 보여주는 안드로이드 애플리케이션입니다.<br>
Jetpack Compose, Retrofit, OkHttp 등 다양한 기술과 라이브러리를 사용해 제작되었습니다.  <br>

<br>

![미세먼지 디자인 예시](https://github.com/user-attachments/assets/c53c2d8a-814f-44f4-81e7-7c4021e1fca2)

<br>

# 요구사항
- Android Studio 4.1 이상
- Java 1.8 이상
- Kotlin 1.5.0 이상

<br>

# 시작하기
## Clone the Repository
```sh
git clone https://github.com/byu-rin/miseya.git
cd miseya
```

<br>

### 1.  API 키 설정
> [!Tip]
> 1. 프로젝트 루트 디렉토리에 local.properties 파일을 만들어 주세요.
> 2. [공공데이터포털](https://www.data.go.kr/data/15073861/openapi.do)에서 API 사용 신청을 하면 발급받은 키를 이 파일에 추가합니다.
> <br>  🚀 과정이 번거롭다면, 아래 키를 활용하세요.

```
> api_key = OHeogT6EGM6my3ZyT0ATWQAW5BG7aqbnJny3WoYtxLthtOuc8uqK8irZieJUUPxAfLZJugVlo7MN0776O0dZqg==
```

<br>

### 2. 프로젝트 빌드하기
1. Android Studio로 프로젝트를 엽니다.
2. Gradle 파일을 동기화(Sync)합니다.
3. 에뮬레이터 또는 실제 기기에서 실행합니다.

<br>

# 사용된 주요 라이브러리
- Jetpack Compose
- OkHttp3
- Retrofit2
- PowerSpinner

<br>

# 사용법
이 프로젝트는 Android Jetpack Compose를 기반으로 작동합니다.
<br>

# 주요 기능
- 실시간 미세먼지 정보 표시: 슬라이더를 통해 지역의 공기질이 실시간으로 갱신됩니다.
- 모던한 UI: Jetpack Compose를 활용하여 부드럽고 반응형 UI를 구성했습니다.
- API 연동: Retrofit과 OkHttp를 이용해 외부 API로부터 데이터를 받아옵니다.

<br>

# 예시 코드
Retrofit을 이용해 미세먼지 데이터를 불러오는 간단한 예시입니다:

```kotlin
interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String
    ): Response<DustResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dustNetWork: NetWorkInterface = retrofit.create(NetWorkInterface::class.java)
```

<br>

# 스피너 커스터마이징
지역 선택용 스피너를 아래와 같이 커스터마이징할 수 있습니다.

```kotlin
@Composable
fun Spinner(
    items: List<String>,
    label: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) } // 드롭다운 메뉴 확장?
    var selectedOptionText by remember { mutableStateOf(label) } // 선택 항목 저장

    BoxWithConstraints( // 드롭다운 메뉴의 컨테이너
        modifier = modifier
            .fillMaxWidth()  // 필수: weight가 올바르게 동작하려면 필요
            .padding(10.dp)
            .clickable(onClick = { expanded = true })
            .background(Color.Transparent)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    )
}
```
<br>

# 드롭다운 메뉴 커스터마이징
```kotlin
@Composable
DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { boxWidth.toDp() })
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = item
                    expanded = false
                    onItemSelected(item)
                }) 
            }
        }
```

<br>

## 기여하기
🥰 이 프로젝트에 기여하고 싶다면, 저장소를 포크해서 브랜치를 만든 후 Pull Request를 보내주세요.
언제든지 환영합니다!


## 라이선스
이 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 LICENSE 파일을 참고하세요.

-----------

<br>

# Overview
**23.09.15 ~ 23.09.22** <br><br>
Miseya is an Android application designed to display air quality levels using real-time data from an API    .<br>This project utilizes various technologies and libraries, including Jetpack Compose, Retrofit, OkHttp, and more.<br>

<br>

![미세먼지 디자인 예시](https://github.com/user-attachments/assets/c53c2d8a-814f-44f4-81e7-7c4021e1fca2)

<br>

# Prerequisites
- Android Studio 4.1 or later
- Java 1.8 or later
- Kotlin 1.5.0 or later

<br>

# Getting Started

## Clone the Repository
```sh
git clone https://github.com/byu-rin/miseya.git
cd miseya
```
### 1. Set Up API Key

> [!TIP]
> 1. Create a 'local.properties' file in the root directory of the project.
> 2. You will receive a key on the [공공데이터포털](https://www.data.go.kr/data/15073861/openapi.do) website after applying for API utilization.
> 3. Add your API key to the 'local.properties' file.
> <br> 🚀 If it's annoying, use this API key.

```
api_key = OHeogT6EGM6my3ZyT0ATWQAW5BG7aqbnJny3WoYtxLthtOuc8uqK8irZieJUUPxAfLZJugVlo7MN0776O0dZqg==
```

<br>

### 2. Build the Project
1. Open the project in Android Studio.
2. Sync the project with Gradle files.
3. Run the project on an emulator or a physical device.

<br>

# Dependencies
This project depends on:
- [Jetpack Compose](https://github.com/android/compose-samples)
- [OkHttp3](https://github.com/square/okhttp)
- [Retrofit2](https://github.com/square/retrofit)
- [PowerSpinner](https://github.com/skydoves/PowerSpinner)

<br>

# Usage
This Project requires support for Android Jetpack Compose.
<br>
## Main Features
- Real-time Air Quality Display: Fetches and displays air quality levels using a slider that updates in real-time.
- Modern UI: Built with Jetpack Compose for a sleek and responsive user interface.
- API Integration: Uses Retrofit and OkHttp to fetch data from an external API.
<br>

# Example Code
Here's an example of how to fetch air quality data using Retrofit:

```kotlin
interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String
    ): Response<DustResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dustNetWork: NetWorkInterface = retrofit.create(NetWorkInterface::class.java)
```

<br>

# Customizing the Spinner
Spinner can be customized by modifying local and urban selection features:

```kotlin
@Composable
fun Spinner(
    items: List<String>,
    label: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) } // 드롭다운 메뉴 확장?
    var selectedOptionText by remember { mutableStateOf(label) } // 선택 항목 저장

    BoxWithConstraints( // 드롭다운 메뉴의 컨테이너
        modifier = modifier
            .fillMaxWidth()  // 필수: weight가 올바르게 동작하려면 필요
            .padding(10.dp)
            .clickable(onClick = { expanded = true })
            .background(Color.Transparent)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    )
}
```
<br>

# Customizing the DropdownMenu
```kotlin
@Composable
DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { boxWidth.toDp() })
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = item
                    expanded = false
                    onItemSelected(item)
                }) 
            }
        }
```
<br>

# Contributing
🥰 If you'd like to contribute to this project, please fork the repository and use a feature branch. <br>Pull requests are warmly welcome.

<br>

# License
This project is licensed under the MIT License. See the [LICENSE](https://github.com/byu-rin/Miseya/blob/compose/LICENSE.txt) file for details. 

