package com.airfrance.ums.dto;

public enum UserResultCode {
    SUCCESS(200,"Success"),
    USER_ERROR(500,"Error during update/create user"),
    USER_UPDATE_ERROR(501,"Error update user info"),
    USER_ALREADY_EXIST_ERROR(409,"Error update user - Already existing "),
    EMAIL_ALREADY_EXIST_ERROR(409,"Error update/create user - email Already existing "),
    INVALID_REQUEST_FORMAT(400,"Request format is not valid"),
    REQUEST_NOT_FOUND(404,"Id request not found"),
    USER_NOT_AUTHORIZED(401,"User unauthorized");

    private Integer statusCode;

    private String statusDescription;

    UserResultCode(Integer statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public boolean isOk() {
        return statusCode.equals(SUCCESS.statusCode);
    }
}
