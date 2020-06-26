프로젝트 설명 : 카카오페이 서버개발자 과제(카카오페이의 머니뿌리기, 받기, 조회 기능 api 부분 개발)
프로젝트 폴더 : JobOrder

개발언어 : JAVA
기본구조 : Spring boot, Postgresql DB, Mybatis

문제해결 전략

요구 사항 
● 뿌리기, 받기, 조회 기능을 수행하는 REST API 를 구현합니다. 
      ○ 요청한 사용자의 식별값은 숫자 형태이며 "X-USER-ID" 라는 HTTP Header로 전달됩니다. 
      ○ 요청한 사용자가 속한 대화방의 식별값은 문자 형태이며 "X-ROOM-ID" 라는 HTTP Header로 전달됩니다. 
      ○ 모든 사용자는 뿌리기에 충분한 잔액을 보유하고 있다고 가정하여 별도로 잔액에 관련된 체크는 하지 않습니다. 

문제해결 : RequestMapping 을 통해 3개의 api 작성. @RequestHeader 어노테이션을 사용하여 header 값 파싱

뿌리기 : @RequestMapping(path="/sprinklemoney/{money}/{persons}")
받기 : @RequestMapping(path="/takemoney/{token}")
조회 : @RequestMapping(path="/searchmyorderlist/{token}")
      
● 작성하신 어플리케이션이 다수의 서버에 다수의 인스턴스로 동작하더라도 기능에 문제가 없도록 설계되어야 합니다. 

문제해결 : 다수의 서버에서 동작할 것을 위해, RDBMS 사용(Postgresql) 윈도우로컬에 서버 설치하여 구현
          새로생성한 유저와 데이터베이스에 테이블 생성

          CREATE TABLE TB_ORDER(   
          TOKEN  VARCHAR(10),   
          GIVE_ID VARCHAR(50),   
          GIVE_ROOM VARCHAR(50),   
          TAKE_SEQ INT,   
          TAKE_ID VARCHAR(50),   
          TAKE_AMOUNT VARCHAR(20),   
          OPEN_DTM VARCHAR(14),   
          PRIMARY KEY(TOKEN, GIVE_ID, GIVE_ROOM, TAKE_SEQ));
          


● 각 기능 및 제약사항에 대한 단위테스트를 반드시 작성합니다. 

문제해결 : JobOrderApplicationTests.java 코드 작성 및 JUnit Test 수행 및 DB 데이터 확인

1. 뿌리기 API 
    ○ 뿌릴 금액, 뿌릴 인원을 요청값으로 받습니다. 
    ○ 뿌리기 요청건에 대한 고유 token을 발급하고 응답값으로 내려줍니다. 
    ○ 뿌릴 금액을 인원수에 맞게 분배하여 저장합니다. (분배 로직은 자유롭게 구현해 주세요.) 
    ○ token은 3자리 문자열로 구성되며 예측이 불가능해야 합니다.
    
문제해결 : token 값은 대문자, 소문자, 숫자 로 구성되도록 Random 함수 사용하여 3자리 문자열 획득 및 Response 로 전달(json)
          뿌리기 시에 DB에 금액 분할한 정보를 등록함, 받기유저 정보 없는 Row 생성

2. 받기 API 
● 다음 조건을 만족하는 받기 API를 만들어 주세요. 
    ○ 뿌리기 시 발급된 token을 요청값으로 받습니다. 
    ○ token에 해당하는 뿌리기 건 중 아직 누구에게도 할당되지 않은 분배건 하나를 API를 호출한 사용자에게 할당하고, 그 금액을 응답값으로 내려줍니다. 
    ○ 뿌리기 당 한 사용자는 한번만 받을 수 있습니다. 
    ○ 자신이 뿌리기한 건은 자신이 받을 수 없습니다. 
    ○ 뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다. 
    ○ 뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기 실패 응답이 내려가야 합니다. 

문제해결 : 대상조건을 SQL로 처리 하였으며, 
          이미 받은 뿌리기 건에 처리를 위해 NOT EXISTS 구문을 사용
          뿌리기가 실행된 시간을 저장하여, interval 함수를 사용하여 10분 유효 기능 처리
          받기유저가 할당되지 않은 모든 row 를 조회 하여, 0번쨰 row 에 받기 유저 정보를 등록    
    
3. 조회 API 
● 다음 조건을 만족하는 조회 API를 만들어 주세요. 
    ○ 뿌리기 시 발급된 token을 요청값으로 받습니다. 
    ○ token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줍니다. 현재 상태는 다음의 정보를 포함합니다. 
    ○ 뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트) 
    ○ 뿌린 사람 자신만 조회를 할 수 있습니다. 다른사람의 뿌리기건이나 유효하지 않은 token에 대해서는 조회 실패 응답이 내려가야 합니다. 
    ○ 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다. 
    
문제해결 : Response Class 를 구성하여, Json 형태로 데이터 리턴
결과 샘플
{
    "successyn": "Y", << 성공여부
    "errmessage": null, << controller err 메시지
    "servicers": {
        "returnmsg": null, << 성공여부 N 일시 메시지
        "token": null,
        "amount": null,
        "opendtm": "20200626220422", << 뿌린시각
        "totalamount": 70000, << 뿌린금액
        "takenamount": 46666, << 받기완료금액
        "takeinfos": [
            {
                "token": null,
                "giveId": null,
                "giveRoom": null,
                "takeSeq": 0,
                "takeId": "plaire", << 받기 사용자ID
                "takeAmount": "23333", << 받기 금액
                "openDtm": null
            },
            {
                "token": null,
                "giveId": null,
                "giveRoom": null,
                "takeSeq": 0,
                "takeId": "hhhi",
                "takeAmount": "23333",
                "openDtm": null
            }
        ]
    }
}
          
