## [ User ]   

### 1. 회원 정보 수정
매핑타입 : @PatchMapping   
매핑주소 : /user/update   
request: Requestboy에 nickname(String),email(String) 입력하면 수정가능      
response : "회원정보가 수정되었습니다."         
![image](https://github.com/Jorados/capston/assets/100845256/fd0cb0b2-e048-41ce-ac68-f1c4b397c8d6)    
![image](https://github.com/Jorados/capston/assets/100845256/d1ea6bc4-fe4a-4858-aa7f-11fff461ccef)     

### 2. 회원 포인트 충전    
매핑타입 : @PatchMapping     
매핑주소 : /user/point      
request: RequestParam 으로 name="chargePoint" 로 value(int타입) 보내면됨.     
response : "포인트 충전이 완료되었습니다."        
![image](https://github.com/Jorados/capston/assets/100845256/3f870429-b2b9-48a7-b242-b32412e095b3)      



## [ Post ]   

### 1. 글 생성   
매핑타입 : @PostMapping   
매핑주소 : /post/create   
request: Requestboy에 글 생성정보 , 로그인상태   
response : "글이 생성 되었습니다."        
![image](https://github.com/Jorados/capston/assets/100845256/c8c90cd9-9142-46d0-a7bb-8f84fe4e4142)   

### 2. 모든 글 조회   
매핑타입 : @GetMapping   
매핑주소 : /post/readAll   
request: Pageable 객체 ( size,page변수 / requestParam형태 ) -> 안보내면 디폴트 사이즈로 조회됨 , keyword(String) -> "lastest" , "oldest"
response : 글 조회    
![image](https://github.com/Jorados/capston/assets/100845256/831adc8e-5816-4acd-8547-3228ea5298cf)    



### 3. 특정 글 조회  
매핑타입: @GetMapping    
매핑주소: /post/read/{postId}  
request: Pathvariable 방식으로 Long타입 postId    
response: 글 정보   
![image](https://github.com/Jorados/capston/assets/100845256/06a11e7e-71a9-4552-9a0d-ed18c958c61f)      

### 4. 글 수정   
매핑타입: @PatchMapping   
매핑주소: /post/update/{postId}   
request: Pathvariable 방식으로 Long타입 postId, RequestBody에 글 수정정보(title,content), 로그인상태    
response: "글 수정이 완료 되었습니다"   
![image](https://github.com/Jorados/capston/assets/100845256/1430040d-df4a-436d-b85a-a3449f463aeb)        
![image](https://github.com/Jorados/capston/assets/100845256/d84b23cd-071b-4a60-b023-2cbdc52eb4b6)       

### 5. 글 삭제    
매핑타입: @DeleteMapping   
매핑주소: /post/delete/{postId}   
request: Pathvariable 방식으로 Long타입 postId, 로그인 상태   
response: "글 삭제가 완료 되었습니다"    
![image](https://github.com/Jorados/capston/assets/100845256/0509eb34-73b1-4ba7-bb22-7816b18d4f94)     

### 6. 내가 쓴 댓글의 글 검색
매핑타입: @GetMapping    
매핑주소: /post/postByMyComments    
request: 로그인 상태       
response: 글 ( 중복 x )     
![image](https://github.com/Jorados/capston/assets/100845256/047ffb77-ace0-41b6-ac2b-34ce5ae93e68)        


### 7. 게시글 검색   
매핑타입: @GetMapping    
매핑주소: /post/search    
request: RequestParam형태로 searchType(title,content/String타입), RequestParam형태로 sortType(lastest,oldest/String타입) , RequestParam형태로 keyword(String타입) , Pageable객체       
response: 검색결과       
![image](https://github.com/Jorados/capston/assets/100845256/a7373f73-7235-42cf-98a5-6d3fbb3496eb)    
   

## [ Comment ]   

### 1. 특정 글에 대한 댓글 생성   
매핑타입: @PostMapping    
매핑주소: /comment/create/{postId}    
request: RequestBody에 글 생성정보 (content 변수 1개), 로그인상태 , Pathvariable 방식으로 Long타입 postId    
response: "댓글이 생성 되었습니다"    
![image](https://github.com/Jorados/capston/assets/100845256/ef0963f5-1691-41ba-8d0d-966e1baf1bc0)     

###  2. 특정 글에 대한 댓글 조회   
매핑타입: @GetMapping    
매핑주소: /comment/readAll/{postId}   
request: Pathvariable 방식으로 Long타입 postId, Pageable 객체(size,page변수 /requestParam형태로 ) -> 안보내면 디폴트 사이즈로 조회됨  
response: 댓글   
![image](https://github.com/Jorados/capston/assets/100845256/5b905400-668d-4234-b64a-0c96c40d82b3)   

### 3. 댓글 수정    
매핑타입: @PatchMapping   
매핑주소: /comment/update/{commentId}   
request: Pathvariable 방식으로 Long타입 postId, RequestBody에 수정정보(content), 로그인상태     
response: "댓글이 수정 되었습니다."   
![image](https://github.com/Jorados/capston/assets/100845256/504a5aa2-42b7-4cc6-8dd5-f513eaf76d2d)      
![image](https://github.com/Jorados/capston/assets/100845256/022eca00-dd77-40ab-bafc-669843a90ae4)      

### 4. 댓글 삭제   
매핑타입: @DeleteMapping   
매핑주소: /comment/delete/{commentId}    
request: Pathvariable 방식으로 Long타입 postId, 로그인상태    
response: "댓글이 삭제 되었습니다."    
![image](https://github.com/Jorados/capston/assets/100845256/0ab05327-4bc6-4eb5-8d6f-8aa3d2e25165)      


 
