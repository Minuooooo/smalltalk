## 서버는 배포되지 않았습니다.
<br>

## 개요
- 프로필 설정 없이 익명으로 자유롭게 소통할 수 있는 채팅 API 서버입니다.
- 다양한 기능이 아닌 최소한으로 구현하여 완성도에 초점을 맞춥니다.
- 구현에서 끝내지 않고 테스트 속도 및 성능 측면에서 비교하여 최적의 방식을 선택합니다.
- 코드 안전성 향상을 위해 단위 및 통합 테스트를 작성합니다.
<br>

## 기술 스택
Core
- Kotlin, Spring Boot
<br>

Database
- Redis, InfluxDB
<br>

Infrastructure
- Docker, Grafana K6
<br>

## 테스트
- Kotest를 활용하여 단위 및 통합 테스트 작성
- 테스트 컨테이너 구축
<br>

## 성능 테스트
![Stress_Test](https://github.com/user-attachments/assets/402e7e20-a7c0-42d1-bb59-e0ca41863eeb)
<br></br>
K6가 서버에 대용량 트래픽을 요청하여 부하 테스트를 수행합니다.
<br>
Telegraf는 Redis 컨테이너에서 시계열 데이터를 수집하고, 이를 InfluxDB에 저장합니다.
<br>
저장된 데이터는 Grafana를 통해 시각화하여 실시간으로 모니터링할 수 있습니다.
<br></br>

