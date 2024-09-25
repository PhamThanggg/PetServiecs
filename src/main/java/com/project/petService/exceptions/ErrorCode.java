package com.project.petService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    ROOM_NOT_EXITS(1008, "Phòng không tồn tại", HttpStatus.BAD_REQUEST),

    // Dùng chung all
    ADDRESS_NOT_BLANK(1009, "Địa chỉ không được để trống", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID(1010, "Địa chỉ nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),
    PHONE_FORMAT(1011, "Số điện thoại không đúng định dạng", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1012, "Số điện thoại phải là {min} ký tự", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(1013, "Email không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1014, "Tên kinh doanh phải nằm trong khoảng từ {min} đến {max} ký tự", HttpStatus.BAD_REQUEST),



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

    BUSINESS_NAME_EXISTS(1203, "Tên kinh doanh đã tồn tại!", HttpStatus.BAD_REQUEST),
    BUSINESS_NAME_NOT_EXISTS(1203, "Cửa hàng kinh doanh không tồn tại!", HttpStatus.BAD_REQUEST),
    BUSINESS_TYPE(1204, "Không tồn tại loại hình kinh doanh với ID = ", HttpStatus.BAD_REQUEST),
    //=================== BUSINESS END =======================
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
