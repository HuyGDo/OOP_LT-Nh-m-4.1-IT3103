package entity;

import java.util.Date;

public class Reader {
    private int readerId;
    private String fullName;
    private Date birthDate;
    private String address;
    
    // Constructor
    public Reader(int readerId, String fullName, Date birthDate, String address) {
        this.readerId = readerId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
    }
    
    // Getters and Setters
    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "readerId=" + readerId +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                '}';
    }
} 