### api 스팩 (테스트 방법 사진 추가하기)
  - localhost:8080/join   
    - http 메서드 : POST 
    - request : username , password
    - reponse : msg -> 회원가입 성공
    ![image](https://user-images.githubusercontent.com/100845256/223089730-2b4ae9b9-5059-49e6-97e3-768b8143268c.png)     



  - locahost:8080/login
    - http 메서드 : POST 
    - request : username , password
    - respnse : msg -> 로그인 성공
    - response.header : Authorization -> Bearer 인증타입의 JWT 토큰 반환
    ![image](https://user-images.githubusercontent.com/100845256/223089889-c0910a29-3216-4b96-a8ac-99d14e61b2fe.png)   
    ![image](https://user-images.githubusercontent.com/100845256/223089954-0bc91fe3-52f3-4922-b09b-9d88c6b990ec.png)   


    
  - locahost:8080/logout
    - fornt에서 해야할 것 같다.   


### 테스트 해봐야하는 api 스팩
  - locahost:8080/api/user/1  --> 일반 회원 권한 -> join을 통해서 가입하면 들어가짐.   
    - http 메서드 : GET 
    - header에 'Authorization' 라는 이름으로 Bearer 타입 토큰을 서버로 request 해야한다.
    - request : Authorization 의 Bearer 토큰
    - response : 일반 회원 페이지 -> 서버에서 이 토큰을 확인하고 권한인가를 해줬다는 뜻.
    ![image](https://user-images.githubusercontent.com/100845256/223090696-57249d99-310f-4f5d-b83d-a1eb7d0e4092.png)   
 
  - localhost:8080/api/admin/1 --> 관리자 회원 권한 -> join을 통해서 들어가도 안들어가짐.   
    - http 메서드 : GET 
    - request : Authorization 의 Bearer 토큰
    - resposne : "권한이 없습니다"
    ![image](https://user-images.githubusercontent.com/100845256/223091052-ba29bc84-3e53-419d-ba55-3567bb862c6d.png)   
