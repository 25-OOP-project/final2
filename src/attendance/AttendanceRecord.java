package attendance;

import java.io.Serializable;

public class AttendanceRecord implements Serializable {
    private String lectureName;
    private String date;
    private String status; // "출석", "결석", "지각" 등
    // 한 건의 출석 데이터를 나타내는 클래스

    public AttendanceRecord(String lectureName, String date, String status) {
        this.lectureName = lectureName;
        this.date = date;
        this.status = status;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toCSV() {
        return lectureName + "," + date + "," + status;
    }

    public static AttendanceRecord fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        return new AttendanceRecord(parts[0], parts[1], parts[2]);
    }

    @Override
    public String toString() {
        return lectureName + " | " + date + " | " + status;
    }
}