package edu.northeastern.MrManage.roomApi.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "Order", foreignKeys = {@ForeignKey(
        entity = Product.class,
        parentColumns = "product_id",
        childColumns = "product_id",
        onDelete = ForeignKey.CASCADE
), @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "assigned_manufacturer",
        onDelete = ForeignKey.CASCADE)
}

)
public class Order implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private Long orderId;

    @ColumnInfo(name = "product_id")
    private Long productId;

    @ColumnInfo(name = "stage")
    private int stage = -1; // created -1, received 0, delivered 1

    @ColumnInfo(name = "is_paid")
    private boolean isPaid = false;

    @ColumnInfo(name = "ordered_quantity")
    private double orderedQuantity;

    @ColumnInfo(name = "received_quantity")
    private double receivedQuantity = 0.0d;

    @ColumnInfo(name = "proposed_end_date")
    private String proposedEndDate;

    @ColumnInfo(name = "end_date")
    private String endDate = null ;

    public String getProposedEndDate() {
        return proposedEndDate;
    }

    public void setProposedEndDate(String proposedEndDate) {
        this.proposedEndDate = proposedEndDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @ColumnInfo(name = "assigned_manufacturer")
    private Long manufacturer;

    @ColumnInfo(name = "order_date")
    private String orderDate;

    public Order(long productId, long manufacturer, String orderDate, double orderedQuantity, String proposedEndDate) {
        this.productId = productId;
        this.manufacturer = manufacturer;
        this.orderDate = orderDate;
        this.orderedQuantity = orderedQuantity;
        this.proposedEndDate = proposedEndDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getStage() {
        return stage;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public double getOrderedQuantity() {
        return orderedQuantity;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setOrderedQuantity(double orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(double receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    protected Order(Parcel in) {
        if (in.readByte() == 0) {
            orderId = null;
        } else {
            orderId = in.readLong();
        }
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readLong();
        }
        stage = in.readInt();
        isPaid = in.readByte() != 0;
        orderedQuantity = in.readDouble();
        if (in.readByte() == 0) {
            manufacturer = null;
        } else {
            manufacturer = in.readLong();
        }
        orderDate = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (orderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(orderId);
        }
        if (productId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(productId);
        }
        dest.writeInt(stage);
        dest.writeByte((byte) (isPaid ? 1 : 0));
        dest.writeDouble(orderedQuantity);
        if (manufacturer == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(manufacturer);
        }
        dest.writeString(orderDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
