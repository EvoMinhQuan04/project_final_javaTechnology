# Dự án Bất Động Sản xây dựng theo kiến trúc Microservice
## Người thực hiện:
- Cao Minh Quân - 52200136
- Đinh Phát Phát - 52200010
- Huỳnh Kiến Đông Duy - 52200244

## Cách cài đặt:
Clone dự án 
```bash
git clone https://github.com/EvoMinhQuan04/project_final_javaTechnology.git
```
Mở thư mục và cấu hình cơ sở dữ liệu MySQL
Đối với BdsService, tạo database với tên tương ứng trong cấu hình bên dưới - final_java
```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/final_java
    username: user_name của bạn
    password: pass_word của bạn

  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update

  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB

  mail:
    host: smtp.gmail.com
    port: 587
    username: caoquan415@gmail.com
    password: 'mật khẩu App password của gmail'  
    from: caoquan415@gmail.com    
    default-encoding: UTF-8     
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
      mail.smtp.from: caoquan415@gmail.com
      mail.debug: true
```
Đối với PayService, tạo tên database trong MySQL với tên tương ứng cấu hình bên dưới - depositdb
```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/depositdb
    username: user_name của bạn
    password: pass_word của bạn

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB


server:
  port: 8081
```
Di chuyển vào từng Service và chạy các lệnh sau:
```bash
mvn clean install !! mvn clean install -DskipTests (để bỏ qua unit Test)
mvn spring-boot: run
```
Đăng ký tài khoản và thông tin thẻ test VNP sau đó điền các thông tin cấu hình vào file VNPayConfig.java với các thuộc tính vnp_TmnCode, vnp_HashSecret tương ứng.
Thông tin thẻ mẫu 
```bash
Ngân hàng: NCB
Số thẻ: 	9704198526191432198
Tên chủ thẻ: NGUYEN VAN A
Ngày phát hành: 	07/15
Mã OTP: 123456
```
### KIỂM THỬ API VỚI POSTMAN
