# Team2-iFive

### 아이드랍(iDrop) - 믿을 수 있는 아이 픽업 모빌리티 서비스

<img width="495" alt="로고" src="https://github.com/softeerbootcamp-3rd/Team2-iFive/assets/39684697/b7561593-a4a6-4130-84ff-826161ee78c1">

## 목차
- [🐥 프로젝트 소개 🚗](#🐥-프로젝트-소개-🚗)  
- [😁 김상민 담당 모듈](#😁-김상민-담당-모듈)  
- [💪🏻 김상민 담당 역할](#💪🏻-김상민-담당-역할)  
- [📹 시연 영상 (유튜브) 🎬](#-📹-시연-영상-(유튜브)-🎬)  
- [🙌 팀 소개 🙌](#🙌-팀-소개-🙌])  
- [📃 프로젝트 문서 📃](#📃-프로젝트-문서-📃)  


## 🐥 프로젝트 소개 🚗
- 부모님들의 바쁜 일상을 고려하여 정기적인 아이 픽업 서비스를 제공합니다. 아이들의 안전과 부모님의 편의를 최우선에 두고, 아이들을 안전하게 픽업해드립니다.

- 안전이 보장된 기사분들과 매칭하여 부모님들께 안정감을 선사합니다. 우리 기사들은 엄격한 신뢰성 검증을 통과한 전문 운전자로, 아이들의 안전을 책임집니다.

- 더불어 실시간 위치 확인 및 승하차 알림 기능을 통해 부모님들이 언제든지 아이의 현재 위치를 확인하고 안심할 수 있도록 합니다.

## 😁 김상민 담당 모듈
### 1. 부모 사용자 화면 담당
- 구독 정보 입력: 픽업 요일, 픽업 시간, 출발지, 목적지(naver 지도 이용), 상세 주소 설정  
  ![도착지설정](https://github.com/Sang-minKIM/iDrop/assets/87116017/a06c356f-0a3d-441c-bdb0-0eb7bfea99e5) ![픽업신청](https://github.com/Sang-minKIM/iDrop/assets/87116017/3845c5a4-1075-4e2d-85cf-229ba59e84fd)  

- 픽업 기사 검색 및 상세 정보 조회: 입력한 구독 정보를 토대로 픽업 기사 검색  
  ![기사선택](https://github.com/Sang-minKIM/iDrop/assets/87116017/8f18c2ad-e278-4679-9911-e4aeace9aa5c)

- 구독 신청 현황 관리: 아이 픽업 기록 탐색(픽업 인증 사진, 기사 정보, 구독 기간 보기)  

### 2. 로그인 인증 및 사용자 권한 제어
- JWT를 이용한 로그인 구현
  - [로그인 개발 과정](https://velog.io/@sang-mini/React-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%96%B4%EB%96%BB%EA%B2%8C-%ED%95%98%EC%A7%80)  
- RoleProvider 컴포넌트로 권한 제어가 필요한 Router를 감싸서 부모, 운전기사 구별
  ```js
    function RoleProvider({ children }) {
      const role = useRouteLoaderData("auth");
      return (
        <>
            {typeof children === "function"
                ? children(role === "PARENT")
                : children}
        </>
      );
    }
  ```
  
### 3. 웹 push 알림
- FCM 및 서비스워커로 foreground & background push 알림 구현
  ![푸시알림](https://github.com/Sang-minKIM/iDrop/assets/87116017/552c4ff7-5f69-4570-916a-283eb85107c5)
- [푸시알림 구현 기록](https://velog.io/@sang-mini/%EC%9B%B9-%ED%91%B8%EC%8B%9C%EC%95%8C%EB%A6%BC-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0with.FCM)

### 4. websocket 초기 세팅 및 통신 테스트
- websocket을 활용하여 실시간 위치 좌표 전송 및 수신, 백엔드와 연결

### 5. 배포
- firebase hosting으로 배포: FCM 설정을 이미 한 상태라서 빠르게 배포 가능
    
### 6. 코드 품질 향상
- useModal, useCoords, useMarker 등의 커스텀 훅 구현으로 지도 사용 편리성을 증진
- 재사용 할 수 잇는 modal 컴포넌트 및 훅 구현
- 토큰 인증을 위한 util 함수를 만들어 권한 필요한 요청에 재사용

## 💪🏻 김상민 담당 역할
- 회의 진행 및 회의록 작성
- 팀원 상황 파악 및 공유: 팀원 개발 현황 정리, 개발 범위 확정
- Github Wiki 문서 관리
  - 커스텀 사이드바를 설정해서 카테고리화
  - 회고록 작성
  - 개발한 부분에 대한 고민을 문서화해서 공유
- 코드리뷰
- UI 디자인

## 📹 시연 영상 (유튜브) 🎬
### 픽업 구독 요청하기
[![Video Label](http://img.youtube.com/vi/D16UpcCU8S8/0.jpg)](https://youtu.be/D16UpcCU8S8)

### 픽업 - 실시간 위치추적
[![Video Label](http://img.youtube.com/vi/2nyEEmA7uak/0.jpg)](https://youtu.be/2nyEEmA7uak)

## 🙌 팀 소개 🙌

|                                개발(FE)                              |                                개발(FE)                                |                              개발(BE)                               |                             개발(BE)                                |                              개발(BE)                              |
| :-----------------------------------------------------------------: | :-------------------------------------------------------------------: | :----------------------------------------------------------------: | :---------------------------------------------------------------: | :----------------------------------------------------------------: |
| <img src="https://github.com/Sang-minKIM.png" width="100" height="100"> | <img src="https://github.com/yook-jongho.png" width="100" height="100"> | <img src="https://github.com/Gyeongsu1997.png" width="100" height="100"> | <img src="https://github.com/Win-9.png" width="100" height="100">     | <img src="https://github.com/eekrwl.png" width="100" height="100"> |
|               [@Sang-minKIM](https://github.com/Sang-minKIM)            |              [@yook-jongho](https://github.com/yook-jongho)     |                [@Gyeongsu1997](https://github.com/Gyeongsu1997)      |                [@Win-9](https://github.com/Win-9)                 |                [@eekrwl](https://github.com/eekrwl)                |              
|                               김상민                                  |                                육종호                                 |                               최경수                                  |                              강승구                                 |                               안채완                               |                               

### 🏆️ 팀 목표 🏆️

- 실제로 사용할 수 있는 서비스 만들기
- 개발자들끼리의 협업 경험하기
- 기술적인 발전하기

### 📜 그라운드 룰 📜

- 정기회의
  - 아침 회의(10:10)
  - 점심(13:30) 간단한 상황공유
  - 저녁 회고
- 하루에 한 번씩 다같이 리프레쉬 산책!!!
  - `오후 4시`
- 아무 의견이나 말하기 (자기 의견 자신감있게 주장하기)
- 카톡이나 슬랙 답장에 체크 표시 하기
- 소통을 위한 아카이빙 공간
  - 사적 카톡
  - 공적 슬랙

## 📃 프로젝트 문서 📃

### 개발 문서

| 문서 종류          | 링크                                                                               |
| ------------------ | ---------------------------------------------------------------------------------- |
| Github wiki           | [wiki](https://github.com/softeerbootcamp-3rd/Team2-iFive/wiki) |
| API 문서           | [API 명세서](https://www.notion.so/sangmini/API-37b47d5ead004049a4010e52aed3c73c?pvs=4) |
| ERD                | [ERD](https://www.erdcloud.com/d/xQ5iLZGSgB8bL73gm) |
| 코딩 컨벤션        | [Coding 컨벤션](https://github.com/softeerbootcamp-3rd/Team2-iFive/wiki/Coding-Convention)  |
| Git 컨벤션        | [Git 컨벤션](https://github.com/softeerbootcamp-3rd/Team2-iFive/wiki/Git-Convention)        |


<br/>

### 기획 문서

| 문서 종류     | 링크                                                                                                                                                                                                                           |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 기획안     | [기획안](https://github.com/softeerbootcamp-3rd/Team2-iFive/wiki/%EA%B8%B0%ED%9A%8D%EC%95%88)|
| 와이어 프레임 | [와이어 프레임](https://www.figma.com/file/E3AXcTrejyzuaIczrBVhQO/%EC%95%84%EC%9D%B4%EB%93%9C%EB%9E%8D?type=design&node-id=1554-255&mode=design) |

<br/><br/>
