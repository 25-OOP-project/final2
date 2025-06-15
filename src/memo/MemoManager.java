package memo;

import javax.swing.*;

import lecture.LectureManager;

import java.awt.*;

public class MemoManager {
    private LectureManager lectureManager;

    public MemoManager(LectureManager lectureManager) {
        this.lectureManager = lectureManager;
    }

    public String[] getLectureNames() {
        int count = lectureManager.lecturecnt;
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            names[i] = lectureManager.lecture[i].getLecturename();
        }
        return names;
    }
}
