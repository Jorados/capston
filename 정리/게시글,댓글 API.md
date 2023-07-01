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
request: Pageable 객체 ( size,page변수 / requestParam형태 ) -> 안보내면 디폴트 사이즈로 조회됨  
response : "글이 생성 되었습니다."   
![image](https://github.com/Jorados/capston/assets/100845256/d52c7d64-bfe4-4cab-8ed7-61e38d6aa7e4)       

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
 
