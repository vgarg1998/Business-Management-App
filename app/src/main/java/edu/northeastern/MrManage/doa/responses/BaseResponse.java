package edu.northeastern.MrManage.doa.responses;




public abstract class BaseResponse {
  protected String status;
  protected String message;

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
