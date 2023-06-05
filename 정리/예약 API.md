## 1. 체육관 API - 조회 , 검색   

### 1. 체육관 조회    
 - "localhost:8080/center/all"   
 - Http 메서드 : GET   
 - request.header : x   
 - request.body : x   
 - response :  Page< Center >

![image](https://github.com/Jorados/capston/assets/100845256/2586f216-1ec9-438b-aeca-7d1585d9a8b5)      

### 2. 체육관 검색
 - "localhost:8080/center/search"      
 - Http 메서드 : GET     
 - request.header : x     
 - request.body :    
 - response :  Page< Center >   
 - param 형태로 page,size / searchValue    
![image](https://github.com/Jorados/capston/assets/100845256/11646d52-635e-42e1-b2ca-d97dff4569e3)       


## 2. 예약 API - 센터 예약 /  센터 예약정보 취소      

### 1.센터 예약 --> 추후 LocalDate변수 추가하여 모든 날 예약이 가능하도록 수정예정 , 현재는 자동 당일예약 처리      
 - "localhost:8080/center/{centerId}/reservation"   
 - Http 메서드 : POST   
 - request URI (PathVariable 방식) : centerId   
 - request.header : 인증토큰   
 - request.body : 예약정보 ( reservingTimes , headCount ) 

![image](https://github.com/Jorados/capston/assets/100845256/fd0ab95d-149b-4cf3-9b56-e5c0cc2c2ba3)    

### 2. 센터 예약정보 취소   
 - "localhost:8080/center/{centerId}/reservation/{reservationId}"  
 - Http 메서드 : DELETE   
 - request URI (PathVariable 방식) : centerId , reservationId   
 - request.header : 인증토큰   
 - request.body : x     

![image](https://github.com/Jorados/capston/assets/100845256/e33c4068-4de2-4462-8f14-caa34aec6571)   

 
## 3. 예약 API - 나의 예약목록 / 내 예약 내역 상세보기 / 체육관 예약 페이지 정보 요청

### 1. 나의 예약 목록      
 - "localhost:8080/center/reservations"   
 - Http 메서드 : GET      
 - request URI (PathVariable 방식) : x  
 - request.header : 인증토큰     
 - request.body : x     
![image](https://github.com/Jorados/capston/assets/100845256/5fd16a22-ded7-4967-9c05-e7a493082164)   

### 2. 내 예약 내역 상세보기   
 - "localhost:8080/center/{centerId}/reservation/{reservationId}"    
 - Http 메서드 : GET         
 - request URI (PathVariable 방식) : centerId , reservationId         
 - request.header : 인증토큰       
 - request.body : x         

![image](https://github.com/Jorados/capston/assets/100845256/1f2c00f2-c8cd-4b90-88ce-38a6c001f86f)     

### 3. 체육관 예약 페이지 정보 요청 
 - "localhost:8080/center/{centerId}/reservation"
 - Http 메서드 : GET      
 - request URI (PathVariable 방식) : centerId  
 - request.header : 인증토큰     
 - request.body : 파라미터 형식으로 날짜(date) --> 보내면 해당 날짜 예약정보와 해당 센터정보 response      

![image](https://github.com/Jorados/capston/assets/100845256/99433152-5908-4e71-8478-ba1376ab6cd9)       



