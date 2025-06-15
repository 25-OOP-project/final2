package attendance;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.util.List;
import java.util.Map;

import lecture.*;

public class AttendanceStatisticsFrame extends JFrame {
    private AttendanceManager attendanceManager;
    // 하나의 독립된 창으로 열리며, 출석 통계, 입력 화면 등을 보여줌

    public AttendanceStatisticsFrame(AttendanceManager attendanceManager, LectureManager lectureManager) {
        this.attendanceManager = attendanceManager;

        setTitle("출석 통계");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);

        StringBuilder sb = new StringBuilder();

        // LectureManager에서 강의 리스트 받아옴
        List<Lecture> lectures = LectureManager.toList(lectureManager);

        for (Lecture lec : lectures) {
            String lectureName = lec.lecturename;
            double rate = attendanceManager.getAttendanceRate(lectureName);

            sb.append(lectureName)
                    .append(": ")
                    .append(String.format("%.2f%%", rate * 100))
                    .append("\n");

            // 날짜별 출석 상태 출력
            Map<String, String> attendanceByDate = attendanceManager.getAttendanceByDate(lectureName);
            for (Map.Entry<String, String> entry : attendanceByDate.entrySet()) {
                sb.append("    ") // 들여쓰기
                        .append(entry.getKey()) // 날짜
                        .append(" : ")
                        .append(entry.getValue()) // 출석 상태
                        .append("\n");
            }
            sb.append("\n"); // 강의별 구분을 위한 빈 줄
        }

        statsArea.setText(sb.toString());

        add(new JScrollPane(statsArea));
    }
}