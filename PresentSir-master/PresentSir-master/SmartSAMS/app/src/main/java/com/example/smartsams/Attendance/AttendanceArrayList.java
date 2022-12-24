package com.example.smartsams.Attendance;

public class AttendanceArrayList {

    String   name, enrollment,status;
    String number, date;

    public AttendanceArrayList(String number, String name, String enrollment, String status, String date)
    {
        this.number = number;
        this.name = name;
        this.enrollment = enrollment;
        this.status = status;
        this.date = date;

    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public String getStatus() {
        return status;
    }
    public String getDate() {
        return date;
    }

}
