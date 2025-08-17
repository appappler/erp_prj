# ERP_Project : 사원관리 프로그램

<br/>

## 📑 목차  
- [📝 프로젝트 소개](#project-intro)
- [🛠 기술 스택](#tech-stack)
- [💾 ERD](#erd)
- [🔗 ClassDiagram](#ClassDiagram)
- [✨ 주요 기능 소개](#main-features)
- [🎬 시연 화면](#ui)
- [💁‍♂️ 팀원 소개](#team-members)


<br/>

<h2 id="project-intro">📝 프로젝트 소개</h2>
기존에는 사원 정보를 수기로 작성하거나 엑셀 파일로 개별 관리하여<br>
데이터 누락이나 중복 발생, 실시간 조회의 어려움, 업무 비효율이 많았습니다. <br>
이를 해결하기 위해 Java와 Oracle을 이용한 데스크톱 사원관리 프로그램을 기획하게 되었습니다.

프로젝트 목적:
실무와 유사한 환경에서 Java + DB 연동 프로젝트를 직접 기획하고 완성함으로써 <br>
프로그램 개발 전 과정을 경험하고 팀 협업 능력을 기르기 위함

진행 일정 (총 약 3주 소요): <br>
1주차 : 기획 및 요구사항 정의, ERD 설계, UI 설계 <br>
2주차 : DB 구축, 기능별 클래스 구현, 화면 연동 <br>
3주차 : 기능 통합, 테스트 및 예외 처리, 보고서 작성 <br>

구체적인 실행 내역: <br>
기능별 담당 역할 분담 (예: UI 담당 / DB 담당 등) <br>
주요 기능 단위로 테스트 진행 후 통합 <br>
역할별 로그인 처리 및 권한 분리 <br>
화면 간 이동 및 데이터 연동 테스트 <br>
마무리 단계에서 기능 점검 및 수정 보완 <br>

- **개발 기간**: 2025.02.14 ~ 2025.02.27 (3주)
- **팀 구성**: 8명 

<br/>

<h2 id="tech-stack">🛠 기술 스택</h2>

### Backend
- **Language**: Java (JDK 11)
- **Database**: Oracle Database

### Development Environment
- **IDE**: Eclipse IDE
- **OS**: Windows 11
- **Version Control**: Git

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white"> <img src="https://img.shields.io/badge/eclipse-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

<br/>

<h2 id="erd">💾 ERD</h2>

<img width="800" height="600" alt="4조_ERD" src="https://github.com/user-attachments/assets/a5965185-85be-4bd9-ac6f-481ba5d4da9b" />

<br/>

<h2 id="ClassDiagram">🔗 ClassDiagram</h2>

### 👥 사용자 클래스 다이어그램
<img width="800" height="600" alt="4조_사원_클다" src="https://github.com/user-attachments/assets/c13b28bb-0793-4062-8a66-09c57fbbc27a" />


### 👨‍💼 관리자 클래스 다이어그램
<img width="800" height="600" alt="4조_관리자_클다" src="https://github.com/user-attachments/assets/734fc4c7-d878-4ad7-89e8-8fdea1f24dec" />


<br/>

<h2 id="main-features">✨ 주요 기능 소개</h2>

### 👥 사용자 기능
출퇴근 등록 : 출근/퇴근 버튼으로 본인 근태 입력 <br>
내 정보 확인 및 수정 : 개인정보 확인 및 일부 수정 가능 <br>
급여 조회 : 본인의 급여 항목 확인 <br>
문서 공유 : 부서 간 문서 공유 <br>

### 👨‍💼 관리자 기능
근태 대시보드 : 전체 사원의 당일 출퇴근 현황 확인 <br>
사원 등록 : 신규 사원 정보 등록 <br>
사원 목록 : 전체 목록 조회 + 상세 정보 확인 및 수정 가능 <br>
급여 관리 : 사원의 급여 관리 <br>
부서 관리 : 부서 추가 / 수정 / 삭제 <br>
직급 관리 : 직급 목록  <br>
근태 관리 : 사원의 근태(출근, 퇴근, 휴가) 기록 확인 및 조정 <br>
문서 관리 : 부서 간 공유된 문서 조회 <br>

<h2 id="ui">🎬 시연 화면</h2>

### 👥 사용자 화면
<img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/a678c562-5efa-488f-a96c-e950f81b0779" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/5ae4b8f3-7dbb-4533-a776-8e0b51a8d61e" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/c8f25e9c-34c8-449c-8d84-ce8a1bfb7f3d" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/daf3dbbe-5740-42cc-8560-aaf7d1ec7759" />

### 👨‍💼 관리자 화면
<img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/a46daac8-4081-4b0f-bcca-e570c0069a03" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/ec54b5f4-9291-4e18-be45-0e26a615f7fa" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/0bf987c1-d5e6-4353-b635-e63e056021a2" /><img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/2e9471b9-6746-4ccb-ba68-93c838472a6c" />

<br/>

<h2 id="team-members">💁‍♂️ 팀원 소개</h2>

- **김민진** [팀장]  
  - DBA / ERD 구조 설계
  - 개발문서 관리
  - 발표자료 및 시연

- **김세형** [부팀장]  
  - 사원 등록
  - 사원 조회

- **정성재**  
  - 급여 조회
  - 연봉 관리

- **심규민**  
  - 문서 공유
  - 문서 관리

- **박선은**  
  - 부서 조회
  - 부서 관리  

- **최승재**  
  - 근태 관리 
  - 대시보드

- **이장훈**  
  - 로그인
  - 연동   

- **정제균**  
  - 직급 조회 
  - 연봉 조회

<br/>

---

**© 2025 ERP Project Team. 본 프로젝트는 교육 목적으로 개발되었습니다.**

> 🙋 README 작성: 김민진
