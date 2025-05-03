package edu.northeastern.MrManage.utility;

public class RoomResponse {
    boolean isValid;
    String message;

    public RoomResponse(boolean b, String s) {
        isValid = b;
        message = s;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
}
