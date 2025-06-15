//package Attend2;
package attendance;

import java.util.*;
import java.util.stream.Collectors;

import lecture.*;

public class AttendanceManager {
    private final List<AttendanceRecord> records = new ArrayList<>();
    private final LectureManager lectureManager; // LectureManager와 연동됨

    // 생성자에서 LectureManager를 받아옴
    public AttendanceManager(LectureManager lectureManager) {
        this.lectureManager = lectureManager;
    }

    // 출석 기록 전체 설정 (파일에서 불러올 때 사용)
    public void setRecords(List<AttendanceRecord> newRecords) {
        records.clear(); // 기존 기록 제거
        for (AttendanceRecord r : newRecords) {
            if (lectureExists(r.getLectureName())) {
                records.add(r);
            } else {
                System.out.println("[경고] 존재하지 않는 강의: " + r.getLectureName() + " → 무시됨");
            }
        }
    }

    // 전체 출석 기록 반환
    public List<AttendanceRecord> getRecords() {
        return records;
    }

    // 출석 기록 추가
    public void addRecord(AttendanceRecord record) {
        if (!lectureExists(record.getLectureName())) {
            throw new IllegalArgumentException("존재하지 않는 강의입니다: " + record.getLectureName());
        }
        records.add(record);
    }

    // 날짜 중복 여부 확인 메서드 추가 (같은 날짜에 동일 강의 출석 기록 있는지 확인)
    public boolean hasRecord(String lectureName, String date) {
        return records.stream().anyMatch(r -> r.getLectureName().equals(lectureName) && r.getDate().equals(date));
    }

    // 특정 강의의 출석 기록 모두 가져오기
    public List<AttendanceRecord> getRecordsByLecture(String lectureName) {
        return records.stream()
                .filter(r -> r.getLectureName().equals(lectureName))
                .collect(Collectors.toList());
    }

    // 날짜별 출석 상태 반환
    public Map<String, String> getAttendanceByDate(String lectureName) {
        Map<String, String> map = new TreeMap<>();
        for (AttendanceRecord r : getRecordsByLecture(lectureName)) {
            map.put(r.getDate(), r.getStatus());
        }
        return map;
    }

    // 출석률 계산
    public double getAttendanceRate(String lectureName) {
        List<AttendanceRecord> lectureRecords = getRecordsByLecture(lectureName);
        if (lectureRecords.isEmpty())
            return 0.0;

        long attended = lectureRecords.stream()
                .filter(r -> r.getStatus().equals("출석"))
                .count();
        return (double) attended / lectureRecords.size();
    }

    // 기록 초기화
    public void clearRecords() {
        records.clear();
    }

    // 내부 메서드: 강의가 존재하는지 확인
    private boolean lectureExists(String name) {
        for (int i = 0; i < lectureManager.lecturecnt; i++) {
            Lecture lec = lectureManager.lecture[i];
            if (lec != null && lec.getLecturename().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
