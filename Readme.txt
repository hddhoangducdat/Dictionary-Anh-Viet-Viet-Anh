Họ và tên : Hoàng Đức Đạt 
MSSV: 1712325
Gmail: hddhoangducdat@gmail.com

Bài tập: Generic & Collection
Mức độ hoàn thành: Xong 

Chạy được trên OpenJDK11 

Cấu tạo thư mục:
    - Thư mục Release: Chứa Dictionary.jar là file có thể chạy trực tiếp project 
    - Thư mục source : Chứa source code 
        -> File Manifest.txt : Để tạo file .jar 
        -> Các file .java :
            + Dictionary.java : Là main class của chương trình 
            + Còn lại là các class phụ 
        -> Thư mục xml : Chứa các file xml
            + Ngoài 2 file xml từ điển là Anh_Viet.xml , Viet_Anh.xml 
            + Còn có thêm các file lưu dữ liệu như danh sách từ vựng yêu thích,
                lịch sử tìm kiếm (mỗi danh sách sẽ có 2 bản dành cho từ điển 
                Việt - Anh và Anh - Việt) 
    - File Readme.txt : chứa hướng dẫn sử dụng project 

Cách chạy project :
    - Cách 1 : Mở terminal hoặc cmd di chuyển tới thư mục source gõ lần lượt 
                    javac Dictionary.class 
                    java Dictionary
         Chương trình hiện lên 4 cửa số (tạo ra bằng java swing) sau đó có thể 
         test 

    - Cách 2: Vào folder Release bấm chạy trực tiếp Dictionary.jar 
        (Nếu gặp lỗi thử kiểm tra việc cấp quyền cho file Dictionary.jar chưa)

Cách test project :
    - Chương trình gồm có 4 cửa sổ (4 cửa sổ có thể chụm lại 1 chỗ nên phải 
    tách ra cho dễ nhìn)
    - Khi tắt 1 trong 4 cửa sổ thì chương trình sẽ tắt
    - Tên cửa sổ có thể thay đổi nếu người dùng chọn đổi từ điển (Anh_Viet , Viet_Anh)
    - Tùy vào nhu càu người dùng có thể thay đổi giữa 2 loại từ điển trên 

    1. Cửa sổ (Anh_Viet hoặc Viet_Anh):
        + Đơn giản ta ghi từ cần dịch ở ô trống bên cạch nút search 
        + Bấm nút search ta có thể thấy nghĩa ở phần khung lớn bên trên 
        + Sau khi search 1 chữ ta có bấm nút "Add current word at Favorite"
        để thêm vào cửa sổ yêu thích. Nếu chưa search chữ nào thì hiện lên 
        của sổ báo lỗi 

    2. Cửa sổ Add & Delete vocab 
        + Chương trình đã ví dụ sẵn 1 trường hợp đễ thêm và xóa từ vựng 
        + Ô trống ở trên là ô từ vựng , ô dưới là ô nghĩa 
        + Không được để ô nào trống khi bấm thêm 
        + Phải điển vào ô từ vựng để xóa (ô nghĩa có thể để trống) 
        + Từ nào có tồn tại trong từ điển thì không thể thêm chỉ có thể xóa 
        + Xóa thành công hoặc thất bại sẽ có 1 tin nhắn hiện ra trong phần 
        dịch của cửa số Anh_Viet/Viet_Anh 
        + Thêm thành công nghĩa của từ đó sẽ hiện lên trên cửa số Anh_Viet/Viet_Anh 
        
    3. Cửa số Your Favorite Vocab 
        + Mỗi khi người dùng thêm từ yêu thích sau khi bấm nút ở cửa sổ 
        Anh_Viet/Viet_Anh . Từ mới sẽ hiện ra trong khung ở giữa của cửa sổ 
        này 
        + Các từ vựng trên cửa sổ này là các list người dùng có thể nhấp chuột 
        2 lần váo từ đó để nghĩa của từ hiện lên trên cửa sổ Anh_Viet/Viet_Anh 
        + Có thể sắp xếp danh sách bằng nút A-Z hoặc Z-A 
    
    4. Search's History 
        + Khung ở giữa là 1 ô trống khi mới bắt đầu chạy là 1 ô trống sau 
        khi bấm nút Search's History thì khung sẽ hiện lên 1 dãy danh sách 
        các từ vựng và số lần tìm kiếm chúng trong khoảng thời gian người dùng 
        nhập 
        + Chương trình gợi ý trước cách ghi cho người bằng 2 ngày bên trái và phải 
        của khung danh sách (ngày/tháng/năm) . Ngày bên trái là ngày bắt đầu (Date 1)
        Ngày bên phải là ngày kết thúc (Date 2)
        *** Chỉ có trường hợp tìm kiếm trực tiếp mới tính và lưu lại trong danh 
        sách lịch sử tìm kiếm , Còn việc bấm vào từ vựng trong danh sách yêu thích 
        và danh sách lịch sử tìm kiếm sẽ không được xét 
 

 ** Lưu ý : Do file .jar không thể viết ra file ở trong và ngoài file , em chỉ có 
 thể đọc được file cố định mà không thể thay đổi 
            Khi sử dụng file .jar thì có thể làm các thao tác trên nhưng nó chỉ lưu 
tạm trên collection chứ không lưu vào file . Chỉ khi chạy bằng source code thì lưu 
vào file .xml mới có thể thực hiện 

    
