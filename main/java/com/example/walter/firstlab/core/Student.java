package com.example.walter.firstlab.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by walter on 27.09.14.
 */
public class Student implements Parcelable{
    private String name, mail, phoneNumber;
    private Integer id;

    public Student(){}

    public Student(Parcel in){
        readFromParcel(in);
    }

    public Student(String name, String mail, String phoneNumber){
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }

    public Student(Integer id, String name, String mail, String phoneNumber){
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mail);
        dest.writeString(phoneNumber);
    }

    private void readFromParcel(Parcel in){
        name = in.readString();
        mail = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator CREATOR = new Creator<Student>(){
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (!mail.equals(student.mail)) return false;
        if (!name.equals(student.name)) return false;
        if (!phoneNumber.equals(student.phoneNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + mail.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        return result;
    }
}
