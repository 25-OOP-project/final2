package main;

import javax.swing.*;

import attendance.AttendanceRecord;
import filio.FileIO;
import lecture.Lecture;
import lecture.LectureManager;
import managercontainer.ManagerContainer;

import java.util.*;

public class MainUI {

	public static void main(String[] args) {
        ManagerContainer managers = new ManagerContainer();

        // 데이터 불러오기
        List<Lecture> lectures = FileIO.loadLectures();
        LectureManager.fromList(managers.getLectureManager(), lectures);

        List<AttendanceRecord> attendanceList = FileIO.loadAttendance();
        managers.getAttendanceManager().setRecords(attendanceList);

        Map<String, List<String>> schedules = FileIO.loadSchedule();
        managers.getScheduleManager().setScheduleMap(schedules);

        // CardLayout 기반 메인 UI 실행
        SwingUtilities.invokeLater(() -> {
            MainWindow win = new MainWindow(managers);
            win.setSize(900, 650);
            win.setLocationRelativeTo(null);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setVisible(true);
        });
    }
}
