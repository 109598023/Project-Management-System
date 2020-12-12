package edu.ntut.se1091.team1.pms.dto;

import org.springframework.http.HttpStatus;

public class Response {

    private Integer status;
    private String message;

    public Response() {
        this.status = HttpStatus.OK.value();
        this.message = "success";
    }

    public Response(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
