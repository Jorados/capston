## 체육관 조회 API 및 예약 API 설계      

### 1. 체육관 조회    
 - "localhost:8080/center/all"   
 - Http 메서드 : GET   
 - request.header : x   
 - request.body : x   
 - response :  Page< Center >  --> 페이지 객체를 포함하는 리스트타입의 Center 정보   

![image](https://github.com/Jorados/capston/assets/100845256/2586f216-1ec9-438b-aeca-7d1585d9a8b5)      


## 2. 예약 API - 센터 예약 /  센터 예약정보 취소      

### 1.센터 예약   
 - "localhost:8080/center/{centerId}/reservation"   
 - Http 메서드 : POST   
 - request URI (PathVariable 방식) : centerId   
 - request.header : 인증토큰   
 - request.body : 예약정보 ( reservingTimes , headCount )   

![image](https://github.com/Jorados/capston/assets/100845256/ca4a123d-4174-44a2-ada3-1d03c77329c0)   

### 2. 센터 예약정보 취소   
 - "localhost:8080/center/{centerId}/reservation/{reservationId}"  
 - Http 메서드 : DELETE   
 - request URI (PathVariable 방식) : centerId , reservationId   
 - request.header : 인증토큰   
 - request.body : x     

![image](https://github.com/Jorados/capston/assets/100845256/e33c4068-4de2-4462-8f14-caa34aec6571)   

 
## 3. 예약 API - 나의 예약목록 / 나의 특정 예약 상세 / 나의 특정 예약 정보 / 체육관 예약 페이지 정보 요청

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
 - request.body : x 
![image](https://github.com/Jorados/capston/assets/100845256/e8458214-79b6-450b-bfc5-664bbd9e57e7)   



