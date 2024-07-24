# MISEYA

![Miseya](https://capsule-render.vercel.app/api?type=venom&color=0:134B70,100:508C9B&height=300&section=header&text=미세야.&fontSize=80&fontColor=D8E3E1&animation=fadeIn)

<br>

# Overview
**23.09.15 ~ 23.09.22** <br><br>
Miseya is an Android application designed to display air quality levels using real-time data from an API.<br>This project utilizes various technologies and libraries, including Jetpack Compose, Retrofit, OkHttp, and more.<br>

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

