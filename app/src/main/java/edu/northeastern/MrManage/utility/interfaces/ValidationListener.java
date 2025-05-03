package edu.northeastern.MrManage.utility.interfaces;

import edu.northeastern.MrManage.utility.RoomResponse;

@FunctionalInterface
public interface ValidationListener {
    void onValidationResult(RoomResponse roomResponse);
}