INSERT INTO promotion
VALUES (1, 'COUPON', '30000원 할인쿠폰', 'WON', 30000, '2022-11-01', '2025-03-01');
INSERT INTO promotion
VALUES (2, 'CODE', '15% 할인코드', 'PERCENT', 15, '2022-11-01', '2025-03-01');
INSERT INTO promotion
VALUES (3, 'COUPON', '신규 가입 쿠폰', 'PERCENT', 15, '2022-11-01', '2023-03-01');

INSERT INTO product
VALUES (1, '피팅노드상품', 215000);
INSERT INTO product
VALUES (2, '피팅노드상품2', 100000);

INSERT INTO promotion_products
VALUES (1, 1, 1);
INSERT INTO promotion_products
VALUES (2, 2, 1);
INSERT INTO promotion_products
VALUES (3, 2, 2);
