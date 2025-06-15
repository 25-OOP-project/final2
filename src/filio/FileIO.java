package filio;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import attendance.AttendanceRecord;
import lecture.Lecture;


// 클래스가 속한 패키지에 따라 아래와 같이 추가 필요
// import com.yourpackage.Lecture;
// import com.yourpackage.LectureManager;
// import com.yourpackage.AttendanceRecord;
// import com.yourpackage.AttendanceManager;
// import com.yourpackage.ScheduleManager;



public class FileIO {

    private static final String BASE_PATH = "./data/";
    private static final String LECTURE_FILE = BASE_PATH + "lecture.txt";
    private static final String ATTENDANCE_FILE = BASE_PATH + "attendance.csv";
    private static final String MEMO_FOLDER = BASE_PATH + "memo/";
    private static final String SCHEDULE_FILE = BASE_PATH + "schedule.json";




    // 강의 저장
    public static void saveLectures(List<Lecture> lectures) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LECTURE_FILE))) {
            oos.writeObject(lectures);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 강의 불러오기
    public static List<Lecture> loadLectures() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LECTURE_FILE))) {
            return (List<Lecture>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    
    // 출석 저장 (CSV)
    public static void saveAttendance(List<AttendanceRecord> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (AttendanceRecord r : records) {
                writer.write(r.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 출석 불러오기 (CSV)
    public static List<AttendanceRecord> loadAttendance() {
        List<AttendanceRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                AttendanceRecord record = AttendanceRecord.fromCSV(line);
                if (record != null) {
                    records.add(record);
                }
            }
        } catch (IOException e) {
            // 파일이 없으면 빈 리스트 반환
        }
        return records;
    }


    // 메모 저장 (텍스트 파일)
    public static void saveMemo(String lectureName, String date, String content) {
        try {
            Files.createDirectories(Paths.get(MEMO_FOLDER));
            String filename = MEMO_FOLDER + lectureName + "_" + date + ".txt";
            Files.write(Paths.get(filename), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 메모 불러오기
    public static String loadMemo(String lectureName, String date) {
        String filename = MEMO_FOLDER + lectureName + "_" + date + ".txt";
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            return "";
        }
    }

    
    // 일정 저장 (JSON)
    public static void saveSchedule(Map<String, List<String>> scheduleMap) {
        try (Writer writer = new FileWriter(SCHEDULE_FILE)) {
            Gson gson = new Gson();
            gson.toJson(scheduleMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 일정 불러오기 (JSON)
    public static Map<String, List<String>> loadSchedule() {
        try (Reader reader = new FileReader(SCHEDULE_FILE)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<Map<String, List<String>>>() {}.getType());
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
