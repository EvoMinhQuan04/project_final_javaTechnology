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
1. Đăng nhập
![Đăng nhập](https://github.com/user-attachments/assets/e523868a-a2ec-41b2-a7f2-f52026ae01f7)
2. Đăng xuất
![Đăng xuất](https://github.com/user-attachments/assets/18d344f5-d823-4bb7-8b27-ffd23bf37d37)
3. Trang chủ
![Trang chủ](https://github.com/user-attachments/assets/c82af0b7-27bc-4988-95c9-30fa525e03f1)
4. Đăng ký
![Đăng ký](https://github.com/user-attachments/assets/30762f48-356d-4a1a-a246-b74860127f76)
5. Nhà đất bán
![Nhà đất bán](https://github.com/user-attachments/assets/917e7b07-a079-4f70-9e72-bb1349d71060)
6. Chi tiết nhà đất bán
![Chi tiết nhà đất bán](https://github.com/user-attachments/assets/01abdf41-9a77-4d34-8b56-6ca5cfa238aa)
7. Nhà đất cho thuê
![Nhà đất cho thuê](https://github.com/user-attachments/assets/edff55be-967d-4d2f-8cc8-42ea5f01838e)
8. Chi tiết nhà đất cho thuê
![Chi tiết nhà đất cho thuê](https://github.com/user-attachments/assets/b3bf4f96-7072-4845-beb8-8f7d9a0399a9)

