# Antigravity 과제 수행 내역
## Global
### Exception Handler
1. Global하게 처리하는 ExceptionHandler와 AntigravityException을 추가했습니다.
2. ErrorCode 인터페이스를 두고, ProductErrorCode enum으로 구현하여 손쉽게 에러를 추가할 수 있도록 작성했습니다.

### Package
1. 전역으로 쓰이는 Exception Handler 등과 domain으로 구분할 수 있도록 패키지 구조를 변경했습니다.

## DB
### JPA & QueryDSL
1. jpa로 변경하여 비즈니스 로직을 쉽고 가독성 좋게 작성하였습니다.
2. Promotion을 가져오는 조건들이 많아 Jpa(Jpa Specification)과 JPQL 대신 queryDSL을 활용하여 가독성을 좋게 구현하였습니다.

## Service
### ProductDiscountService
1. 확장성을 위해 ProductDiscountService를 Interface로 변경하였습니다.
2. applyPromotionsToProduct에 productId, product - promotion 간 매핑, promotion을 차례로 검증했습니다.
   - product - promotion 간 검증 시에는 request parameter로 들어온 coupon 중 해당 product와 매핑되는 promotion Id만 전달받도록 구현했습니다.
   - promotion은 위에서 전달받은 promotion Id 중, 현재 날짜를 기준으로 사용 가능한 promotion 객체만 받습니다.
3. 빠른 계산을 위해 이미 coupon을 선택하지 않았거나, 가격이 10000원 아래인 경우 적용 없이 바로 가격을 return하도록 구현했습니다.

### DiscountCalculator
1. TDD에서의 용이성과 계산이라는 책임만 가지기 위하여 클래스를 분리했습니다.
2. 전달되는 promotion의 개수가 한정적일 것이라고 예상하여 우선 할인이 들어가는 모든 금액을 더했습니다.
   - 할인 계산 중, 10000원 아래인 경우에 먼저 return 하려고 하였으나, 반복 작업이 생겨 promotion을 모두 계산 하도록 구현했습니다.
3. interface에 MIN_VALUE를 따로 두어 정책적으로 최솟값이 바뀌는 경우 빠르게 대응하도록 구현했습니다.

## Controller
### ProductDiscountController
1. request parameter를 ModelAttribute로 바인딩하여 깔끔하게 받을 수 있도록 구현했습니다.
2. 보낼 수 있는 형식은 아래와 같습니다.
```
http://localhost:8089/products/amount?productId=1&couponIds=1,2
```