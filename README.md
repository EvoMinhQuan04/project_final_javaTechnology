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
9. Nhà môi giới
![Nhà môi giới](https://github.com/user-attachments/assets/547e1588-7128-45a6-92c8-785ae7d64fcb)
10. Tin tức
![Tin tức](https://github.com/user-attachments/assets/e42638b5-dc60-42ee-a9d2-8b25fdea5719)
11. Chi tiết tin tức
![Chi tiết tin tức](https://github.com/user-attachments/assets/14b8cfa6-91fa-4ed4-8932-83ffc60a29fe)
12. Đăng tin
![Đăng tin](https://github.com/user-attachments/assets/0abf3a78-107c-436e-bc7c-dddca0ba8428)
13. Quản trị viên
![Quản trị viên](https://github.com/user-attachments/assets/9f4db1c9-1462-443c-9296-7ac9358f6acb)
14. Quản lý người đăng tin
![Quản lý người đăng tin](https://github.com/user-attachments/assets/591a83ce-b280-4c29-b943-f44df5027172)
15. Quản lý gói tin
![Quản lý gói tin](https://github.com/user-attachments/assets/2cb50e2b-d7b7-4b51-90d9-fc0b6db39f50)
16. Quản lý bài đăng
![Quản lý bài đăng](https://github.com/user-attachments/assets/88521f4c-be99-4e65-b8d3-daa165be93a6)
17. Lịch sử giao dịch
![Lịch sử giao dịch](https://github.com/user-attachments/assets/6a40f8fc-761b-4167-9477-fbcc88195148)
18. Đổi mật khẩu
![Giao diện đổi mật khẩu admin](https://github.com/user-attachments/assets/a8f2d2e2-cc06-46cb-a8b1-19b83f515d41)
19. Thay đổi mật khẩu admin
![Thay đổi mật khẩu admin](https://github.com/user-attachments/assets/64b3c577-ec6c-4008-9939-35001a336a7a)
20. Thống kê
![Thống kê doanh thu](https://github.com/user-attachments/assets/0693e7e1-af05-4164-b55b-40d6999d0dca)
21. Thóng kê gói tin
![Thống kê gói tin](https://github.com/user-attachments/assets/3f5b245c-cf7d-4f00-9fb2-6bdb1a0c7e6b)
22. Người đăng tin
![Người đăng tin](https://github.com/user-attachments/assets/3a741da3-b325-4a80-a920-5388b328290e)
23. Quản lý gói tin
![Quản lý gói tin - Người đăng tin](https://github.com/user-attachments/assets/7f5c8ba6-8ddd-45b9-a2f6-5a4f129d2af6)
24. Lịch sử giao dịch
![Lịch sử giao dịch - người đăng tin](https://github.com/user-attachments/assets/602462e7-719a-42fb-bbe4-7447aa9571e5)
25. Đăng bài (bán/thuê) - người đăng tin
![Người đăng tin đăng tin](https://github.com/user-attachments/assets/58893576-608a-4e62-ae40-bef68783fab6)
26. Đăng tin tức - người đăng tin
![Đăng tin tức](https://github.com/user-attachments/assets/f00e17dc-02c7-4e4b-a400-c1ce9dc10ff3)
27. Sửa thông tin bài đăng (bán/thuê)
![Sửa thông tin bài đăng](https://github.com/user-attachments/assets/83d811d3-e152-4861-9af5-39c82ff65703)
28. Xóa bài đăng
![Xóa bài đăng](https://github.com/user-attachments/assets/cbebd62e-b921-4a43-935c-39863098fa52)
29. Sửa thông tin tin tức
![Sửa thông tin tin tức](https://github.com/user-attachments/assets/36e15ee6-5ef8-4ba4-bd40-c5791ab67dbb)
30. Xóa tin tức
![Xóa tin tức](https://github.com/user-attachments/assets/191aab14-39ec-4b39-8049-7987dfddd4fe)
31. Xóa bài đăng - admin
![Xóa bài đăng admin](https://github.com/user-attachments/assets/23b9140c-7ed9-4331-af8a-809f74da7842)
32. Xóa tin tức - admin
![Xóa tin tức admin](https://github.com/user-attachments/assets/7059de0c-771e-4196-9385-7d7370f0d9f8)
33. Giao diện đăng tin mới
![Người đăng tin đăng tin](https://github.com/user-attachments/assets/7c3b878d-67a4-4964-938f-3eb28c93a9e7)
34. Danh sách đăng tin - người đăng tin
![Danh sách tin đăng - người đăng tin](https://github.com/user-attachments/assets/6e305878-b969-429e-b801-3a868bd92ed3)
35. Quản lý tài khoản - người đăng tin
![Quản lý tài khoản - người đăng tin](https://github.com/user-attachments/assets/c68b443b-3824-404e-89af-fa6e1b07b8ad)
36. Cập nhật thông tin tài khoản - người đăng tin
![Cập nhật thông tin tài khoản](https://github.com/user-attachments/assets/e776743f-5544-4f77-8eae-aa14db2bedb4)
37. Giao diện đổi mật khẩu người đăng tin
![Giao diện đổi mật khẩu người đăng tin](https://github.com/user-attachments/assets/d63c15c8-d4b7-4801-9e8c-a190dd14978d)
38. Thay đổi mật khẩu người đăng tin
![Thay đổi mật khẩu người đăng tin](https://github.com/user-attachments/assets/eaec6b77-5530-48e0-9b17-496adf550e66)
39. Mua gói tin - người đăng tin
![Mua gói tin - người đăng tin](https://github.com/user-attachments/assets/7c245cc5-f1e5-4fb3-a648-793afe78ae52)
40. Thanh toán - người đăng tin
![Thanh toán - người đăng tin](https://github.com/user-attachments/assets/c2140f0f-0191-4801-8027-e0ef323ff458)
41. Thanh toán VNPay
![Thanh toán VNPay](https://github.com/user-attachments/assets/f2efa225-2cac-4694-9005-0604fb1b1c00)
42. Nạp tiền
![Nạp tiền](https://github.com/user-attachments/assets/a4b2404f-14c5-4d97-9185-74388dba03a1)
