package managercontainer;

import attendance.AttendanceManager;
import lecture.LectureManager;
import memo.MemoManager;
import schedule.ScheduleManager;

public class ManagerContainer {
    private LectureManager lectureManager;
    private AttendanceManager attendanceManager;
    private MemoManager memoManager;
    private ScheduleManager scheduleManager;

    public ManagerContainer() {
        lectureManager = new LectureManager();
        //각 매니저 생성 시 LectureManager를 전달함
        attendanceManager = new AttendanceManager(lectureManager);
        memoManager = new MemoManager(lectureManager);
        scheduleManager = new ScheduleManager(lectureManager);
    }

    public LectureManager getLectureManager() {
        return lectureManager;
    }

    public AttendanceManager getAttendanceManager() {
        return attendanceManager;
    }

    public MemoManager getMemoManager() {
        return memoManager;
    }

    public ScheduleManager getScheduleManager() {
        return scheduleManager;
    }
}


