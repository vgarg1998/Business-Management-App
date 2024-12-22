package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "User",
        indices = {@Index(value = "email_id", unique = true), @Index(value = "name",unique = true),@Index(value = "phone_number", unique = true),
                @Index(value = "gst_number",unique = true)})
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phone_number")
    private String phone_number;

    @ColumnInfo(name = "email_id")
    private String email_id;

    @ColumnInfo(name = "gst_number")
    private String gst_number;

    @ColumnInfo(name = "isCustomer")
    private boolean isCustomer;

    public User() {
    }
    @Ignore
    public User(User.UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.name =userBuilder.name;
        this.phone_number = userBuilder.phone_number;
        this.email_id = userBuilder.email_id;
        this.gst_number = userBuilder.gst_number;
        this.isCustomer = userBuilder.isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getGst_number() {
        return gst_number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setGst_number(String gst_number) {
        this.gst_number = gst_number;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public static class UserBuilder{
        private Long id;
        private String name;
        private String phone_number;
        private String email_id;
        private String gst_number;

        private boolean isCustomer;

        public UserBuilder() {
        }

        public User.UserBuilder id(Long id){
            this.id = id;
            return this;
        }

        public User.UserBuilder name(String name){
            this.name = name;
            return this;
        }

        public User.UserBuilder phone_number(String phone_number){
            this.phone_number = phone_number;
            return this;
        }

        public User.UserBuilder email_id(String email_id){
            this.email_id = email_id;
            return this;
        }

        public User.UserBuilder gst_number(String gst_number){
            this.gst_number = gst_number;
            return this;
        }

        public User.UserBuilder isCustomer(boolean isCustomer){
            this.isCustomer = isCustomer;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
