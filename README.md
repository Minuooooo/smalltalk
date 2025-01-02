## 현재 서버는 배포되지 않았습니다.
<br>

## 개요
- 별도의 프로필을 설정하지 않고 익명으로 소통할 수 있는 채팅 API 서버입니다.
- 다양한 기능이 아닌 최소한으로 구현하여 완성도에 초점을 맞춥니다.
- 구현에서 끝내지 않고 테스트 속도 및 성능 측면에서 비교하여 최적의 방식을 선택합니다.
- 코드 안전성 향상을 위해 단위 및 통합 테스트를 작성합니다.
<br>

## 기술 스택
Core
- Kotlin, Spring Boot

Database
- Redis, InfluxDB

Infrastructure
- Docker, Grafana K6

<br>

## 테스트
- Kotest를 활용하여 단위 및 통합 테스트 작성
- 테스트 컨테이너 구축
<br>

## 성능 테스트
![image](https://github.com/user-attachments/assets/6bd69180-3397-400b-8412-6e90db7f2c63)
<br></br>
K6를 통해 서버에 원하는 만큼 트래픽을 발생시켜 성능에 대한 여러 지표를 확인합니다.
<br>
Telegraf는 Redis 컨테이너에서 시계열 데이터를 수집하고 InfluxDB에 저장하는 역할입니다.
<br>
InfluxDB에서 저장된 데이터를 조회하여 Grafana로 시각화합니다.
<br></br>

