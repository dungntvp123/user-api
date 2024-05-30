package com.microservice.user.common;

public enum ResponseStatus {
    AUTHENTICATION_SUCCESSFUL(200001, "Successfully Authenticated", "ok"),
    ACCOUNT_VERIFICATION_SUCCESSFUL(200002, "Successfully verify account", "ok"),
    RESET_PASSWORD_SUCCESSFUL(200003, "Successfully reset password", "ok"),
    DATA_LOADED_SUCCESSFUL(200004, "Successfully load data", "ok"),
    USER_INFO_UPDATE_SUCCESSFUL(200005, "Successfully update user info", "ok"),
    VALIDATE_TOKEN_SUCCESSFUL(200006, "Successfully validate token", "ok"),
    LOCK_ACCOUNT_SUCCESSFUL(200007, "Successfully lock account", "ok"),
    DELETE_ACCOUNT_SUCCESSFUL(200008, "Successfully delete account", "ok"),
    RESTORE_ACCOUNT_SUCCESSFUL(200009, "Successfully restore account", "ok"),
    LOAD_USER_DETAIL_SUCCESSFUL(200010, "Successfully load user detail", "ok"),
    LOAD_USER_LIST_SUCCESSFUL(200011, "Successfully load user list", "ok"),
    ADD_FRIEND_SUCCESSFUL(200012, "Successfully add friend", "ok"),
    LOAD_ACCOUNT_LIST_SUCCESSFUL(200013, "Successfully load account list", "ok"),
    LOAD_ACCOUNT_DETAIL_SUCCESSFUL(200014, "Successfully load account detail", "ok"),
    GET_VERIFY_TOKEN_SUCCESSFUL(200015, "Successfully get verify token", "ok"),

    REGISTRATION_SUCCESSFUL(201001, "Successfully Registered", "ok"),

    REGISTRATION_INFORMATION_CONSTRAINT_VIOLATE(400001, "Registration information violate constraint", "error"),
    RESET_PASSWORD_INFORMATION_CONSTRAINT_VIOLATE(400002, "Reset password information violate constraint", "error"),

    INCORRECT_AUTHENTICATION_INFORMATION(401001, "Username or password incorrect", "error"),
    DISABLED_ACCOUNT(401002, "Account has been disabled", "error"),

    ACCOUNT_VERIFICATION_EXPIRED(403001, "Account verify token has been expired", "error"),
    JWT_TOKEN_EXPIRED(403002, "JWT token has been expired", "error"),
    ACCOUNT_HAS_BEEN_LOCKED(403003, "Account has been lock", "error"),
    ACCOUNT_HAS_BEEN_EXPIRED(403004, "Account has been expired", "error"),

    DATA_NOT_FOUND(404001, "Data not found", "error"),

    ;



    public long code;
    public String message;
    public String status;

    ResponseStatus(long code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
