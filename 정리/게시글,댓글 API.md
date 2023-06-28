### [ Post ]
글 생성   
매핑타입 : @PostMapping   
매핑주소 : /post/create   
request: Requestboy에 글 생성정보 , 로그인상태   
response : "글이 생성 되었습니다."   

모든 글 조회    
매핑타입 : @GetMapping   
매핑주소 : /post/readAll   
request: Pageable 객체 ( size,page변수 / requestParam형태로 )  
response : "글이 생성 되었습니다."   

특정 글 조회  
매핑타입: @GetMapping    
매핑주소: /post/read/{postId}  
request: Pathvariable 방식으로 Long타입 postId    
response: 글 정보   

글 수정   
매핑타입: @PutMapping   
매핑주소: /post/update/{postId}   
request: Pathvariable 방식으로 Long타입 postId, RequestBody에 글 수정정보, 로그인상태    
response: "글 수정이 완료 되었습니다"   

글 삭제    
매핑타입: @DeleteMapping   
매핑주소: /post/delete/{postId}   
request: Pathvariable 방식으로 Long타입 postId, 로그인 상태   
response: "글 삭제가 완료 되었습니다"    
    
### [ Comment ]
특정 글에 대한 댓글 생성   
매핑타입: @PostMapping    
매핑주소: /comment/create/{postId}    
request: RequestBody에 글 생성정보, 로그인상태 , Pathvariable 방식으로 Long타입 postId    
response: "댓글이 생성 되었습니다"    
   
특정 글에 대한 댓글 조회   
매핑타입: @GetMapping    
매핑주소: /comment/readAll/{postId}   
request: Pathvariable 방식으로 Long타입 postId, Pageable 객체(size,page변수 /requestParam형태로 )    
response: 댓글   

댓글 수정    
매핑타입: @PatchMapping   
매핑주소: /comment/update/{commentId}   
request: Pathvariable 방식으로 Long타입 postId, RequestBody에 수정정보, 로그인상태     
response: "댓글이 수정 되었습니다."   

댓글 삭제   
매핑타입: @DeleteMapping   
매핑주소: /comment/delete/{commentId}    
request: Pathvariable 방식으로 Long타입 postId, 로그인상태    
response: "댓글이 삭제 되었습니다."    
