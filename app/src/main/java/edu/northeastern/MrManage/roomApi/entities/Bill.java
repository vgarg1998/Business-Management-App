package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;

public class Bill {

    private Long billId;


    @ColumnInfo(name = "order_date")
    private String billDate;


}
