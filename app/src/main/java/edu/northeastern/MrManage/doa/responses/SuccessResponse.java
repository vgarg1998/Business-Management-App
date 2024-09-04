package edu.northeastern.MrManage.doa.responses;


public class SuccessResponse<T> extends BaseResponse {


  private T data;
  public SuccessResponse() {
    super();
  }
  public static class Builder<T> {
    private final SuccessResponse<T> successResponse;

    public Builder(){
      successResponse = new SuccessResponse<>();

    }

    public Builder status(String status){
      successResponse.setStatus(status);
      return this;
    }

    public Builder message(String message){
      successResponse.setMessage(message);
      return this;
    }

    public Builder<T> data(T data) {
      successResponse.setData(data);
      return this;
    }


    public SuccessResponse<T> build() {
      return successResponse;
    }

  }
  public static <T> Builder<T> successBuilder() {
    return new Builder<>();
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
