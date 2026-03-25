@echo off
:: ============================================================
:: GlobalBooks – REST API Test Script (curl)
:: Q7: OrdersService endpoint tests
:: ============================================================

echo.
echo ============================================================
echo   Step 1: Get OAuth2 JWT Token from Keycloak
echo ============================================================
curl -s -X POST http://localhost:9000/realms/globalbooks/protocol/openid-connect/token ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "grant_type=password" ^
  -d "client_id=orders-client" ^
  -d "client_secret=YOUR_CLIENT_SECRET_HERE" ^
  -d "username=testuser" ^
  -d "password=password" ^
  | findstr "access_token"
echo.
echo --- Copy the access_token value above and set it below ---
set TOKEN=PASTE_TOKEN_HERE

echo.
echo ============================================================
echo   Step 2: POST /api/v1/orders – Create an Order (201)
echo ============================================================
curl -s -v -X POST http://localhost:8081/api/v1/orders ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer %TOKEN%" ^
  -d "{\"customerId\":\"C1001\",\"items\":[{\"bookId\":\"B001\",\"quantity\":2},{\"bookId\":\"B003\",\"quantity\":1}],\"shippingAddress\":{\"street\":\"1 Book Lane\",\"city\":\"London\",\"country\":\"UK\",\"postalCode\":\"EC1A 1BB\"}}"
echo.

echo.
echo ============================================================
echo   Step 3: GET /api/v1/orders/{id} – Retrieve the Order (200)
echo ============================================================
echo Replace ORD-XXXXXXXX-001 with actual orderId from Step 2
curl -s -X GET http://localhost:8081/api/v1/orders/ORD-XXXXXXXX-001 ^
  -H "Authorization: Bearer %TOKEN%"
echo.

echo.
echo ============================================================
echo   Step 4: GET /api/v1/orders – List All Orders (200)
echo ============================================================
curl -s -X GET http://localhost:8081/api/v1/orders ^
  -H "Authorization: Bearer %TOKEN%"
echo.

echo.
echo ============================================================
echo   Step 5: GET without token – Should return 401
echo ============================================================
curl -s -v -X GET http://localhost:8081/api/v1/orders
echo.
echo  ^ Expected: HTTP 401 Unauthorized

echo.
echo ============================================================
echo   Step 6: POST with invalid body – Should return 400
echo ============================================================
curl -s -v -X POST http://localhost:8081/api/v1/orders ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer %TOKEN%" ^
  -d "{\"customerId\":\"\",\"items\":[]}"
echo.
echo  ^ Expected: HTTP 400 Bad Request with validation errors

pause
