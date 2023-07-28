## 2023/07/01 ~ 2023/07/25 Amazon Web Service (AWS) 배포

> 스프링 부트와 AWS로 혼자 구현하는 웹 서비스를 참고로 구현했습니다.

### 시스템 구조(흐름도)
![image](https://github.com/Jorados/capston/assets/100845256/2125dd08-7b54-497b-8b5f-aca882d6b053)   

> Windows를 사용중이라 , EC2서버에 접근할 때는 MobaXterm이라는 프로그램을 사용.   

### EC2, RDS 생성 ( 인스턴스 ec2 , RDS 생성 )
--- 
1. AWS EC2 인스턴스를 생성 해줍니다.
   - AMAZON LINUX로 서버를 구성해주고, 나머지 설정은 전부 프리티어로 합니다.    
   - 키페어 또한 새로 RSA 방식으로 하나 만들어줍니다.   
   - 이 프로젝트에서는 프론트를 고려해서 보안그룹을 구성할때 SSH,8080,80(nginx),3000(react) 이렇게 구성해줍니다.
    
2. RDS 생성 및 mysql 이용
   - 전부 프리티어로 진행합니다. mysql로 진행하고 , 최대 스토리지를 30GB로 설정 합니다.
   - 그리고 보안 그룹에 EC2의 보안 그룹을 추가해줍니다. 이렇게 해주면 ec2서버에서 RDS에 접근이 가능합니다.
    
3. Workbench를 이용해 DB에 접근하기
   - Workbeanch를 이용해 DB에 접근하고, 직접 DB를 구성해줍니다.
   - 그 후 ec2에 접속하여 mysql을 설치하고 명령어를 통해 DB에 접근이 가능한지 테스트 해봅니다.   

### 프로젝트 배포
--- 
1. EC2 서버에 배포하기
   - ec2서버에 프로젝트 git clone명령어를 이용해 다운 받아준다. 그리고 'cd 프로젝트'를 통해 프로젝트 디렉토리에 접근하여 ./gradlew test를 진행해줍니다.    
   - 실제 RDS에 접근하기 위한 real-db.properties를 생성하여 RDS정보를 입력해줍니다.   
   - 그리고 실제 프로젝트에서 real.properties를 생성하고 commit 해줍니다.   

2. 배포 스크립트 작성
   - 이제 배포스크립트 deploy.sh를 작성해줍니다. 실행되는 앱이있으면 종료시키고 git pull을 해주고 배포해주는 그런 스크립트!    
   - 권한도 부여 해줘여함. chmod +x ./gradlew 이런식으로.    
    
3. 배포 실행
   - ec2서버에서 해당 프로젝트 디렉토리로 접근 후 './deploy.sh'를 해줘서 배포를 진행합니다.
   - 8080포트로 접근하면 해당 프로젝트가 성공적으로 ec2서버에 배포된것을 확인 할 수 있습니다!      
     - ![KakaoTalk_20230710_153859756](https://github.com/Jorados/capston/assets/100845256/ea92334b-0cea-4e1e-93bb-d34443fef5dc)
   - 해당 IP주소는 탄력적 IP를 할당하지 않아서 노출 여부 상관없습니다 , 프로젝트 일부 api 호출 모습입니다.   
  

### Build & Deployment 배포 자동화
--- 
1. Travis CI를 이용해 빌드 자동화하기
   - travis ci를 회원가입 후 해당 프로젝트를 프리티어 버전으로 등록 후 연동을 해줍니다.
   - 그리고 해당 프로젝트에 .travis.yml 파일 생성 -> commit을 하게 되면 travis가 이를 감지하여 빌드를 수행한다.

2. Travis CI, AWS S3, CodeDeploy 연동
   - 이제 travis와 s3, CodeDeploy를 연동시켜 배포 자동화를 구현할 것입니다.
   - AWS S3란? Amazon Web Service에서 제공하는 일종의 파일 서버, 정적 파일이나 배포 파일 등을 관리하는 기능 지원, 이미지 업로드 구현 시 자주 사용
   - CodeDeploy란? AWS의 배포 서비스, 오토 스케일링 그룹 배포, 블루 그린 배포, 롤링 배포, EC2 단독 배포 등 많은 기능 지원
   - 전달 과정
      - ![image](https://github.com/Jorados/capston/assets/100845256/9c108740-1ecc-4be7-8b1a-7f46336bf907)
  
   - 왜 Travis CI에서 AWS CodeDeploy로 직접 전달하지 않는걸까? -> AWS CodeDeploy에는 저장 기능이 없기 때문입니다. Travis CI의 빌드 결과물을 저장했다가 CodeDeploy가 가져갈 수 있도록 보관하는 공간이 필요한데, 이 공간으로 AWS S3을 자주 사용한다고 합니다.      
   - CodeDeploy가 빌드, 배포 둘 다 할 수 있는데 왜 복잡하게 진행할까? -> 빌드 없이 배포만 필요할 때 대응하기 어렵습니다. 빌드와 배포가 붙어 있으면 예전에 빌드했던 결과물인 jar파일을 재사용할 수가 없다. 웬만하면 빌드와 배포를 분리하는 것이 좋습니다.   
   - S3와 Codedeploy를 생성 후 Travis CI와 AWS S3, CodeDeploy 연동해줍니다. (자세한 과정은 복잡하므로 생략)    
     
3. 배포 자동화 테스트    
   - 위의 과정까지 잘 진행했다면, 해당 프로젝트에서 커밋을 했을 때, travis가 이를 감지하여 빌드를 수행하여 생기는 jar파일을 만들어 S3에 전달하고 codedeploy에 배포를 요청하게 됩니다. 그리고 S3는 codedploy에 jar파일을 전달할 것이고, 이 파일을 이용해서 codedploy는 배포를 진행하게됩니다.        
   - 이러한 과정으로 자동화배포가 진행됩니다.   
      
### Nginx를 이용한 무중단 배포   
--- 
1. 문제가 하나 있다면, 배포 중에는 서비스를 이용할 수 없다는 것이다. 이 문제를 해결하기 위해서 NGINX를 이용합니다.    
   - NGINX 동작구조   
     - ![image](https://github.com/Jorados/capston/assets/100845256/f445048c-f481-494c-93ee-95b1cd8f2888)   
   - 프로젝트 + NGINX 동작구조  
     - 사용자는 서비스 주소로 접속 (http의 경우 80 포트, https의 경우 443 포트) ,Nginx는 사용자 요청을 받아 현재 연결된 Spring boot로 요청 전달, 두 번째 Spring boot는 연결되어 있지 않아 요청받지 못합니다.    
     - 신규 배포가 필요한 경우 -> 연결되지 않은, 두 번째 Spring boot에 배포를 합니다. (Nginx는 첫 번째 Spring boot와 연결된 상태라 서비스가 중단되지 않는다.) 배포 후에 정상적으로 두 번째 Spring boot가 구동 중인지 확인 , 2가 정상적이라면, nignx reload 명령어를 통해 Nginx 연결을 2와 연결합니다.    

2. 구현.
   - ec2에 접속하여 nginx설치 및 설정을 해줍니다. stop.sh , start.sh등등 총 5개의 스크립트파일을 프로젝트에 생성합니다.    
   - 그리고 배포를 담당하는 appsepc.yml를 수정해줍니다.      
   -  ps -ef | grep java 를 통해 8081 , 8082 번으로 프로젝트 2개가 띄워지는 지 확인하면 성공!  


