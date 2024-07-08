package com.example.miseya.data

//data class District(val name: String, val subdistricts: List<String>? = null)
//
//data class City(val name: String, val districts: List<District>)
//
//val cities = listOf(
//    City(
//        "서울", listOf(
//            District("중구"),
//            District("종로구"),
//            District("용산구"),
//            District("광진구"),
//            District("성동구"),
//            District("중랑구"),
//            District("동대문구"),
//            District("성북구"),
//            District("도봉구"),
//            District("은평구"),
//            District("서대문구"),
//            District("마포구"),
//            District("강서구"),
//            District("구로구"),
//            District("영등포구"),
//            District("동작구"),
//            District("관악구"),
//            District("강남구"),
//            District("서초구"),
//            District("송파구"),
//            District("강동구"),
//            District("금천구"),
//            District("강북구"),
//            District("양천구"),
//            District("노원구")
//        )
//    ),
//
//    City(
//        "부산", listOf(
//            District("중구", listOf("광복동")),
//            District("동구", listOf("초량동")),
//            District("남구", listOf("대연동")),
//            District("사하구", listOf("장림동")),
//            District("연제구", listOf("연산동")),
//            District("북구", listOf("덕천동")),
//            District("사상구", listOf("학장동")),
//            District("해운대구", listOf("우동")),
//            District("금정구", listOf("부곡동")),
//            District("기장군", listOf("기장읍")),
//            District("서구", listOf("대신동")),
//            District("영도구", listOf("청학동")),
//            District("부산진구", listOf("전포동")),
//            District("동래구", listOf("온천동")),
//            District("강서구", listOf("대저동")),
//            District("수영구", listOf("광안동"))
//        )
//    ),
//
//    City(
//        "대구", listOf(
//            District("중구", listOf("남산1동")),
//            District("동구", listOf("신암동")),
//            District("서구", listOf("내당동")),
//            District("남구", listOf("대명동")),
//            District("북구", listOf("복현동")),
//            District("수성구", listOf("범어동")),
//            District("달서구", listOf("상인동")),
//            District("달성군", listOf("화원읍"))
//        )
//    ),
//
//    City(
//        "인천", listOf(
//            District("중구", listOf("중산동")),
//            District("동구", listOf("만석동")),
//            District("미추홀구", listOf("주안동")),
//            District("연수구", listOf("송도동")),
//            District("남동구", listOf("구월동")),
//            District("부평구", listOf("부평동")),
//            District("계양구", listOf("작전동")),
//            District("서구", listOf("가좌동")),
//            District("강화군", listOf("강화읍")),
//            District("옹진군", listOf("북도면"))
//        )
//    ),
//
//    City(
//        "광주", listOf(
//            District("동구", listOf("산수동")),
//            District("서구", listOf("화정동")),
//            District("남구", listOf("주월동")),
//            District("북구", listOf("문흥동")),
//            District("광산구", listOf("송정동"))
//        )
//    ),
//
//    City(
//        "대전", listOf(
//            District("동구", listOf("용운동")),
//            District("중구", listOf("은행동")),
//            District("서구", listOf("둔산동")),
//            District("유성구", listOf("봉명동")),
//            District("대덕구", listOf("송촌동"))
//        )
//    ),
//
//    City(
//        "울산", listOf(
//            District("중구", listOf("학성동")),
//            District("남구", listOf("삼산동")),
//            District("동구", listOf("방어동")),
//            District("북구", listOf("명촌동")),
//            District("울주군", listOf("언양읍"))
//        )
//    ),
//
//    City(
//        "강원", listOf(
//            District("춘천시", listOf("중앙동")),
//            District("원주시", listOf("중앙동(강원)")),
//            District("강릉시", listOf("옥천동")),
//            District("동해시", listOf("천곡동")),
//            District("속초시", listOf("노학동")),
//            District("삼척시", listOf("남양동")),
//            District("홍천군", listOf("홍천읍")),
//            District("횡성군", listOf("횡성읍")),
//            District("영월군", listOf("영월읍")),
//            District("평창군", listOf("평창읍")),
//            District("정선군", listOf("정선읍")),
//            District("철원군", listOf("갈말읍")),
//            District("화천군", listOf("화천읍")),
//            District("양구군", listOf("양구읍")),
//            District("인제군", listOf("인제읍")),
//            District("고성군", listOf("간성읍")),
//            District("양양군", listOf("양양읍"))
//        )
//    ),
//
//    City(
//        "충북", listOf(
//            District("청주시", listOf("흥덕구")),
//            District("충주시", listOf("중앙탑면")),
//            District("제천시", listOf("중앙동")),
//            District("보은군", listOf("보은읍")),
//            District("옥천군", listOf("옥천읍")),
//            District("영동군", listOf("영동읍")),
//            District("진천군", listOf("진천읍")),
//            District("괴산군", listOf("괴산읍")),
//            District("음성군", listOf("음성읍")),
//            District("단양군", listOf("단양읍")),
//            District("증평군", listOf("증평읍"))
//        )
//    ),
//
//    City(
//        "충남", listOf(
//            District("천안시", listOf("동남구")),
//            District("공주시", listOf("중동")),
//            District("보령시", listOf("대천동")),
//            District("아산시", listOf("온양동")),
//            District("서산시", listOf("동문동")),
//            District("논산시", listOf("내동")),
//            District("계룡시", listOf("두마면")),
//            District("당진시", listOf("합덕읍")),
//            District("금산군", listOf("금산읍")),
//            District("부여군", listOf("부여읍")),
//            District("서천군", listOf("서천읍")),
//            District("청양군", listOf("청양읍")),
//            District("홍성군", listOf("홍성읍")),
//            District("예산군", listOf("예산읍")),
//            District("태안군", listOf("태안읍"))
//        )
//    ),
//
//    City(
//        "전북", listOf(
//            District("전주시", listOf("완산구")),
//            District("군산시", listOf("중앙동")),
//            District("익산시", listOf("영등동")),
//            District("정읍시", listOf("수성동")),
//            District("남원시", listOf("금동")),
//            District("김제시", listOf("요촌동")),
//            District("완주군", listOf("삼례읍")),
//            District("진안군", listOf("진안읍")),
//            District("무주군", listOf("무주읍")),
//            District("장수군", listOf("장수읍")),
//            District("임실군", listOf("임실읍")),
//            District("순창군", listOf("순창읍")),
//            District("고창군", listOf("고창읍")),
//            District("부안군", listOf("부안읍"))
//        )
//    ),
//
//    City(
//        "경북", listOf(
//            District("포항시", listOf("남구")),
//            District("경주시", listOf("동천동")),
//            District("김천시", listOf("신음동")),
//            District("안동시", listOf("중구동")),
//            District("구미시", listOf("송정동")),
//            District("영주시", listOf("하망동")),
//            District("영천시", listOf("야사동")),
//            District("상주시", listOf("중동면")),
//            District("문경시", listOf("모전동")),
//            District("경산시", listOf("중방동")),
//            District("군위군", listOf("군위읍")),
//            District("의성군", listOf("의성읍")),
//            District("청송군", listOf("청송읍")),
//            District("영양군", listOf("영양읍")),
//            District("영덕군", listOf("영덕읍")),
//            District("청도군", listOf("청도읍")),
//            District("고령군", listOf("고령읍")),
//            District("성주군", listOf("성주읍")),
//            District("칠곡군", listOf("왜관읍")),
//            District("예천군", listOf("예천읍")),
//            District("봉화군", listOf("봉화읍")),
//            District("울진군", listOf("울진읍")),
//            District("울릉군", listOf("울릉읍"))
//        )
//    ),
//
//    City(
//        "경남", listOf(
//            District("창원시", listOf("의창구")),
//            District("진주시", listOf("상봉동")),
//            District("통영시", listOf("명정동")),
//            District("사천시", listOf("사천읍")),
//            District("김해시", listOf("내외동")),
//            District("밀양시", listOf("내일동")),
//            District("거제시", listOf("고현동")),
//            District("양산시", listOf("중앙동")),
//            District("의령군", listOf("의령읍")),
//            District("함안군", listOf("함안읍")),
//            District("창녕군", listOf("창녕읍")),
//            District("고성군", listOf("고성읍")),
//            District("남해군", listOf("남해읍")),
//            District("하동군", listOf("하동읍")),
//            District("산청군", listOf("산청읍")),
//            District("함양군", listOf("함양읍")),
//            District("거창군", listOf("거창읍")),
//            District("합천군", listOf("합천읍"))
//        )
//    ),
//    City(
//        "제주", listOf(
//            District("제주시", listOf("건입동")),
//            District("서귀포시", listOf("대정읍"))
//        )
//    ),
//    City(
//        "세종", listOf(
//            District("세종시", listOf("고운동"))
//        )
//    )
//)
