# QR_Reader
## QR코드 스캔을 통해 해당 URL 접속 및 자신의 포인트를 조회할 수 있는 앱
### 기능
- QR코드를 스캔하는 기능
    - integrator.initiateScan()
- 다시 스캔할 수 있는 기능
    - reScan
- 포인트를 조회할 수 있는 기능
    - pointCheck
- 적립된 point를 보여주는 text는 키보드 숨기기 및 텍스트 정렬
    - setGravity(Gravity.CENTER)
- 스캔 완료 후 url수정 방지를 위한 키보드 숨기기
    - setShowSoftInputOnFocus(false)
- QR 코드 인식 시에 삐- 소리가 나게하기
    - integrator.setBeepEnabled(true)
### 부족한 점
- 포인트 적립 기준
- 다른 앱과의 연동
