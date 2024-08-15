package edu.northeastern.bhavyaplasticinventory.doa.entities;

public class Client {

    private Long id;
    private String name;
    private String phone_number;
    private String email_id;
    private String gst_number;

    public Client(ClientBuilder clientBuilder) {
        this.id = clientBuilder.id;
        this.name =clientBuilder.name;
        this.phone_number = clientBuilder.phone_number;
        this.email_id = clientBuilder.email_id;
        this.gst_number = clientBuilder.gst_number;
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

    public static class ClientBuilder{
        private Long id;
        private String name;
        private String phone_number;
        private String email_id;
        private String gst_number;

        public ClientBuilder() {
        }

        public ClientBuilder id(Long id){
            this.id = id;
            return this;
        }

        public ClientBuilder name(String name){
            this.name = name;
            return this;
        }

        public ClientBuilder phone_number(String phone_number){
            this.phone_number = phone_number;
            return this;
        }

        public ClientBuilder email_id(String email_id){
            this.email_id = email_id;
            return this;
        }

        public ClientBuilder gst_number(String gst_number){
            this.gst_number = gst_number;
            return this;
        }

        public Client build(){
            return new Client(this);
        }
    }
}
