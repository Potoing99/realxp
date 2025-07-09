# 🐉 RealXP - 현실 기반 RPG 성장 시스템

**RealXP**는 현실 속 자기개발 활동(예: 공부, 운동, 습관관리 등)을 게임 퀘스트처럼 등록하고 완료하면 캐릭터가 성장하는 **다마고치 스타일 RPG 게임**입니다.

---

## 🚀 주요 기능

| 기능 | 설명 |
|------|------|
| 🔐 로그인 / 회원가입 | 간단한 로그인/회원가입 시스템 |
| 📝 퀘스트 등록 | 퀘스트 이름과 카테고리를 입력하여 저장 |
| 📋 퀘스트 목록 | 등록된 퀘스트 리스트 확인 및 삭제 |
| 🐉 캐릭터 보기 | 현재 캐릭터의 레벨, 경험치 확인 |
| ⬆️ 경험치/레벨업 시스템 | 퀘스트 완료 시 경험치 획득, 일정 경험치 도달 시 자동 레벨업 |
| 🎨 캐릭터 성장 | 레벨에 따라 캐릭터 이미지가 변경됨 (5레벨 단위) |

---

## 📸 화면 미리보기

> ▶️ 전체 UI 화면은 `screenshots/` 폴더를 참고하세요.


---

## 📂 프로젝트 구조

```
RealXP/
├── src/
│   └── realxp/
│       ├── model/           # User, Quest, MyCharacter 도메인 클래스
│       ├── dao/             # UserDAO, QuestDAO - DB 연동 처리
│       ├── service/         # UserService, QuestService - 비즈니스 로직
│       ├── ui/              # LoginFrame, MainFrame 등 UI 구현
│       └── db/              # DBUtil - MySQL 연결 유틸
├── screenshots/             # 스크린샷 이미지 저장 폴더
├── README.md
└── mysql-connector-*.jar    # MySQL JDBC 드라이버
```

---

## 🛠️ 개발 환경

- Java 21
- Eclipse IDE 2024
- MySQL 8.x
- JDBC (mysql-connector-j-8.0.x.jar)
- Swing (Java GUI)

---

## 📌 참고 사항

- 퀘스트 ID는 자동 부여되며 UI에서는 사용자에게 노출되지 않도록 처리됨
- 경험치 시스템은 "현재 경험치 / 다음 레벨까지 필요한 경험치" 형식으로 표시
- 메인 캐릭터 이미지는 `realxp.ui.images/`에서 관리되며, 레벨 구간에 따라 자동 변경됨
