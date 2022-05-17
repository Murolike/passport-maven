package models;

public class Passport {
    private final String serial;
    private final String number;
    private String createdAt;

    public Passport(String serial, String number) {
        this.serial = serial;
        this.number = number;
    }

    public Passport(String serial, String number, String createdAt) {
        this.serial = serial;
        this.number = number;
        this.createdAt = createdAt;
    }

    public String getSerial() {
        return serial;
    }

    public String getNumber() {
        return number;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
