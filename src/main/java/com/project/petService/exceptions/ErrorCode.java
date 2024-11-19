package com.project.petService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Lỗi khóa không hợp lệ", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên người dùng phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION(1006, "Thông tin tài khoản hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Tuổi của bạn phải ít nhất là {min}", HttpStatus.BAD_REQUEST),


    ROOM_NOT_EXITS(1008, "Phòng không tồn tại", HttpStatus.BAD_REQUEST),


    // Dùng chung all
    PHONE_FORMAT(1011, "Số điện thoại không đúng định dạng", HttpStatus.BAD_REQUEST),
    PHONE_VALID(1608, "Phone must be 10 characters", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1012, "Số điện thoại không được để trống", HttpStatus.BAD_REQUEST),
    PHONE_EXISTS(1012, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_FORMAT(1607, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(1013, "Email không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1014, "Email phải nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1012, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_BLANK(1009, "Địa chỉ không được để trống", HttpStatus.BAD_REQUEST),
    ADDRESS_VALID(1010, "Địa chỉ phải nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),
    PRICE_NOT_NULL(1011, "Giá không được để trống", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1012, "Giá phải lớn hơn hoặc bằng {value}", HttpStatus.BAD_REQUEST),
    QUANTITY_NOT_NULL(1011, "Số lượng không được để trống", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(1012, "Số lượng phải lớn hơn hoặc bằng {value}", HttpStatus.BAD_REQUEST),
    DATE_VALID(1013, "Ngày phải có định dạng yyyy-MM-dd", HttpStatus.BAD_REQUEST),
    DATE_VALID_PAST(1013, "Ngày sinh phải ở trong quá khứ", HttpStatus.BAD_REQUEST),
    TITLE_NOT_BLANK(1604, "Tiêu đề không được để trống", HttpStatus.BAD_REQUEST),
    TITLE_VALID(1605, "Tiêu đề phải nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),
    START_VALID(1606, "Số sao đánh giá phải nằm trong khoảng từ 0 đến 5", HttpStatus.BAD_REQUEST),
    NAME_NOT_BLANK(1607, "Tên không được để trống", HttpStatus.BAD_REQUEST),
    NAME_VALID(1608, "Tên phải nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_BLANK(1607, "Token không được để trống", HttpStatus.BAD_REQUEST),
    TOKEN_VALID(1608, "Token phải nằm trong khoảng từ {min} max {max} ký tự", HttpStatus.BAD_REQUEST),
    STATUS_NOT_BLANK(1109, "Trạng thái sản phẩm không được để trống", HttpStatus.BAD_REQUEST),
    STATUS_INVALID(1110, "Trạng thái sản phẩm phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),
    TOTAL_NOT_NULL(2001, "Tổng tiền không được để trống", HttpStatus.BAD_REQUEST),
    TOTAL_INVALID(2002, "Tổng tiền phải lớn hơn hoặc băng 0", HttpStatus.BAD_REQUEST),



    //=================== USER BEGIN =====================
    USER_EXISTS(2602, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(2602, "Người dung không tồn tại", HttpStatus.BAD_REQUEST),
    GENDER_NOT_BLANK(2501, "Giới tính không được để trống.", HttpStatus.BAD_REQUEST),
    GENDER_INVALID(2501, "Giới tính không đúng", HttpStatus.BAD_REQUEST),

    USER_NOT_NULL(2501, "Bạn chưa chọn user", HttpStatus.BAD_REQUEST),
    USER_INVALID(2502, "ID người dùng phải lớn hơn hoặc bằng {value}", HttpStatus.BAD_REQUEST),

    PASSWORD_NOT_BLANK(2301, "Bạn chưa nhập mật khẩu", HttpStatus.BAD_REQUEST),
    REPASSWORD_NOT_BLANK(2301, "Bạn chưa nhập lại mật khẩu", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2302, "Mật khẩu phải chứa từ 6 đến 30 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.", HttpStatus.BAD_REQUEST),
    PASSWORD_FORMAT(2302, "Mật khẩu phải chứa từ 6 đến 30 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.", HttpStatus.BAD_REQUEST),
    USERNAME_FORMAT(1607, "Tên đăng nhập chỉ được chứa chữ và số.", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_BLANK(1013, "Tên đăng nhập không được để trống", HttpStatus.BAD_REQUEST),
    USERNAME_VALID(1608, "Tên tài khoản phải nằm trong khoảng từ {min} max {max} ký tự", HttpStatus.BAD_REQUEST),
    //=================== USER END =======================

    //=================== AREA BEGIN =======================
    AREA_NOT_BLANK(1101, "Tên khu vực không được để trống", HttpStatus.BAD_REQUEST),
    AREA_INVALID(1102, "Tên khu vực phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),

    AREA_EXISTS(1151, "Khu vực đã tồn tại", HttpStatus.BAD_REQUEST),
    AREA_NOT_EXISTS(1151, "Khu vực bạn chọn không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== AREA END =======================

    //=================== AREA BUSINESS =======================
    BUSINESS_NAME_NOT_BLANK(1201, "Tên kinh doanh không được để trống!", HttpStatus.BAD_REQUEST),
    BUSINESS_NAME_INVALID(1202, "Tên kinh doanh phải nằm trong khoảng từ {min} đến {max} ký tự!", HttpStatus.BAD_REQUEST),
    BUSINESS_TYPE_NOT_EMPTY(1201, "Bạn chưa chọn phòng!", HttpStatus.BAD_REQUEST),
    BUSINESS_AREA_NOT_NULL(1201, "Bạn chưa chọn khu vực!", HttpStatus.BAD_REQUEST),
    BUSINESS_AREA_INVALID(1202, "ID khu vực phải >= {value}", HttpStatus.BAD_REQUEST),

    BUSINESS_NOT_BLANK(1201, "Bạn chưa chọn cửa hàng kinh doanh", HttpStatus.BAD_REQUEST),
    BUSINESS_INVALID(1202, "ID phải >= {value}", HttpStatus.BAD_REQUEST),

    BUSINESS_NAME_EXISTS(1203, "Tên kinh doanh đã tồn tại!", HttpStatus.BAD_REQUEST),
    BUSINESS_NAME_NOT_EXISTS(1203, "Cửa hàng kinh doanh không tồn tại!", HttpStatus.BAD_REQUEST),
    BUSINESS_TYPE(1204, "Không tồn tại loại hình kinh doanh với ID = ", HttpStatus.BAD_REQUEST),

    BUSINESS_EXISTS(1151, "Cửa hàng kinh doanh đã tồn tại", HttpStatus.BAD_REQUEST),
    BUSINESS_NOT_EXISTS(1151, "Cửa hàng kinh doanh không đã tồn tại", HttpStatus.BAD_REQUEST),
    //=================== BUSINESS END =======================


    //=================== CATEGORY BEGIN =======================
    CATEGORY_NOT_BLANK(1101, "Loại sản phẩm không được để trống", HttpStatus.BAD_REQUEST),
    CATEGORY_INVALID(1102, "Loại sản phẩm phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),
    CATEGORY_ID_INVALID(1202, "ID loại sản phẩm phải >= {value}", HttpStatus.BAD_REQUEST),

    CATEGORY_EXISTS(1151, "Loại sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTS(1151, "Loại sản phẩm bạn chọn không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== CATEGORY END =======================

    //=================== PRODUCT BEGIN =======================
    PRODUCT_NOT_BLANK(1101, "Sản phẩm không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_INVALID(1102, "Sản phẩm phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_INVALID(1102, "ID sản phẩm phải >= {value}", HttpStatus.BAD_REQUEST),
    DESCRIPTION_INVALID(1104, "Mô tả sản phẩm phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),
    BRAND_NOT_BLANK(1107, "Thương hiệu không được để trống", HttpStatus.BAD_REQUEST),
    BRAND_INVALID(1108, "Thương hiệu phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_ENOUGH(1109, "Sản phẩm bạn chọn tạm hết hàng hoặc không đủ số lượng", HttpStatus.BAD_REQUEST),

    PRODUCT_EXISTS(1151, "Tên sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTS(1151, "Sản phẩm bạn chọn không tồn tại", HttpStatus.BAD_REQUEST),
        //=================== PRODUCT END =======================

    //=================== CART BEGIN =======================
    CART_NOT_BLANK(1101, "Giỏ hàng không được để trống", HttpStatus.BAD_REQUEST),
    CART_INVALID(1102, "Giỏ hàng phải nằm trong khoảng từ {min} đến {max} ký tự.", HttpStatus.BAD_REQUEST),

    CART_EXISTS(1151, "Sản phẩm đã tồn tại trong giỏ hàng", HttpStatus.BAD_REQUEST),
    CART_NOT_EXISTS(1151, "Sản phẩm không tồn tại trong giỏ hàng", HttpStatus.BAD_REQUEST),
    //=================== CART END =======================


    //=================== INVENTORY BEGIN =======================
    INVENTORY_EXISTS(1151, "Sản phẩm đã tồn tại ", HttpStatus.BAD_REQUEST),
    INVENTORY_NOT_EXISTS(1151, "Sản phẩm không tồn ", HttpStatus.BAD_REQUEST),
    //=================== INVENTORY END =======================

    //=================== PERMISSION BEGIN =====================
    PERMISSION_NOT_BLANK(2501, "Quyền là bắt buộc", HttpStatus.BAD_REQUEST),
    PERMISSION_INVALID(2502, "ID quyền phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    PERMISSION_EXISTS(2602, "Tên quyền đã tồn tại", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTS(2602, "Quyền không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== PERMISSION END =======================

    //=================== ROLE BEGIN =====================
    ROLE_NOT_BLANK(2501, "Vai trò là bắt buộc", HttpStatus.BAD_REQUEST),
    ROLE_INVALID(2502, "ID vai trò phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    ROLE_EXISTS(2602, "Tên vai trò đã tồn tại", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTS(2602, "Vai trò không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ROLE END =======================

    //=================== ORDER_DETAIL BEGIN =====================
    ORDER_DETAIL_NOT_BLANK(2501, "Chi tiết đặt hàng là bắt buộc", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_INVALID(2502, "ID đặt hàng phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    ORDER_DETAIL_NOT_EXISTS(2602, "Chi tiết đặt hàng không tồn tại", HttpStatus.BAD_REQUEST),

    ORDER_NOT_EXISTS(2602, "Đơn hàng không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ORDER_DETAIL END =======================

    //=================== INVOICE BEGIN =====================
    INVOICE_NOT_BLANK(2501, "Chi tiết đặt hàng là bắt buộc", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID(2502, "ID đặt hàng phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    INVOICE_NOT_EXISTS(2602, "Hóa đơn không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ORDER_DETAIL END =======================

    //=================== ATTRIBUTE BEGIN =====================
    ATTRIBUTE_NOT_BLANK(2501, "Thuộc tính sản phẩm là bắt buộc", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_INVALID(2502, "ID thuộc tính phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    ATTRIBUTE_EXISTS(2602, "Tên thuộc tính đã tồn tại", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_NOT_EXISTS(2602, "Thuộc tính không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ATTRIBUTE END =======================

    //=================== ATTRIBUTE_TYPE BEGIN =====================
    ATTRIBUTE_TYPE_NOT_BLANK(2501, "Loại thuộc tính sản phẩm là bắt buộc", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_TYPE_INVALID(2502, "ID loại thuộc tính phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    ATTRIBUTE_TYPE_EXISTS(2602, "Tên loại thuộc tính đã tồn tại", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_TYPE_NOT_EXISTS(2602, "Loại thuộc tính không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ATTRIBUTE_TYPE END =======================

    //=================== SIZE BEGIN =====================
    SIZE_NOT_BLANK(2501, "Kích cỡ sản phẩm là bắt buộc", HttpStatus.BAD_REQUEST),
    SIZE_INVALID(2502, "ID kích cỡ phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    SIZE_EXISTS(2602, "Tên kích cỡ đã tồn tại", HttpStatus.BAD_REQUEST),
    SIZE_NOT_EXISTS(2602, "Kích cỡ không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ATTRIBUTE END =======================

    //=================== SIZE BEGIN =====================
    PRODUCT_REVIEW_NOT_BLANK(2501, "Kích cỡ sản phẩm là bắt buộc", HttpStatus.BAD_REQUEST),
    PRODUCT_REVIEW_INVALID(2502, "ID kích cỡ phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    PRODUCT_REVIEW_EXISTS(2602, "Bạn đã đánh giá sản phẩm này rồi." ,HttpStatus.BAD_REQUEST),
    PRODUCT_REVIEW_NOT_EXISTS(2602, "Đánh giá không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== ATTRIBUTE END =======================

    //=================== PROMOTION BEGIN =====================
    PROMOTION_NOT_BLANK(2501, "Khuyễn mãi là bắt buộc", HttpStatus.BAD_REQUEST),
    PROMOTION_INVALID(2502, "ID khuyến mãi phải lớn hơn {value}", HttpStatus.BAD_REQUEST),

    PROMOTION_EXISTS(2602, "Tên khuyến mãi đã tồn tại." ,HttpStatus.BAD_REQUEST),
    PROMOTION_NOT_EXISTS(2602, "Khuyến mãi không tồn tại", HttpStatus.BAD_REQUEST),
    //=================== PROMOTION END =======================
    ;


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
