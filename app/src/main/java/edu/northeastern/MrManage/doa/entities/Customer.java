package edu.northeastern.MrManage.doa.entities;

import java.io.Serializable;


public class Customer implements Serializable {

    private Long id;
    private String name;
    private String phone_number;
    private String email_id;
    private String gst_number;

    public Customer(CustomerBuilder customerBuilder) {
        this.id = customerBuilder.id;
        this.name =customerBuilder.name;
        this.phone_number = customerBuilder.phone_number;
        this.email_id = customerBuilder.email_id;
        this.gst_number = customerBuilder.gst_number;
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

    public static class CustomerBuilder{
        private Long id;
        private String name;
        private String phone_number;
        private String email_id;
        private String gst_number;

        public CustomerBuilder() {
        }

        public CustomerBuilder id(Long id){
            this.id = id;
            return this;
        }

        public CustomerBuilder name(String name){
            this.name = name;
            return this;
        }

        public CustomerBuilder phone_number(String phone_number){
            this.phone_number = phone_number;
            return this;
        }

        public CustomerBuilder email_id(String email_id){
            this.email_id = email_id;
            return this;
        }

        public CustomerBuilder gst_number(String gst_number){
            this.gst_number = gst_number;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}
