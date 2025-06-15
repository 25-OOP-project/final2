package schedule;

import java.util.*;

import lecture.LectureManager;

public class ScheduleManager {
    private Map<String, List<String>> scheduleMap = new HashMap<>();
    private final LectureManager lectureManager;

    public ScheduleManager(LectureManager lectureManager) {
        this.lectureManager = lectureManager;
    }

    public void setScheduleMap(Map<String, List<String>> map) {
        this.scheduleMap = map;
    }

    public Map<String, List<String>> getScheduleMap() {
        return scheduleMap;
    }

    public void addSchedule(String date, String content) {
        scheduleMap.computeIfAbsent(date, k -> new ArrayList<>()).add(content);
    }

    public List<String> getSchedulesForDate(String date) {
        return scheduleMap.getOrDefault(date, new ArrayList<>());
    }
}
