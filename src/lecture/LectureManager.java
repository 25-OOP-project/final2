package lecture;

import java.util.ArrayList;
import java.util.List;

//배열 기반 LectureManager
public class LectureManager {
	public Lecture[] lecture = new Lecture[10]; // 강의를 리스트로 추가
	public int lecturecnt = 0; // 강의 개수

 //fileio와 연결
	// 배열 → 리스트
 public static List<Lecture> toList(LectureManager mgr) {
     List<Lecture> temp = new ArrayList<>();
     for (int i = 0; i < mgr.lecturecnt; i++) {
         temp.add(mgr.lecture[i]);
     }
     return temp;
 }
 // 리스트 → 배열
 public static void fromList(LectureManager mgr, List<Lecture> list) {
     int size = Math.min(list.size(), mgr.lecture.length);
     for (int i = 0; i < size; i++) {
         mgr.lecture[i] = list.get(i);
     }
     mgr.lecturecnt = size;
 }



	void addLecture(Lecture newLecture) { // 강의명 추가 함수
		if (lecturecnt < lecture.length) {
			lecture[lecturecnt++] = newLecture;
		}
	}

	void deleteLecture(int index) { // 강의명 삭제 함수
		if (index >= 0 && index < lecturecnt) {
			// 삭제된 강의를 배열의 뒤로 이동
			for (int i = index; i < lecturecnt - 1; i++) {
				lecture[i] = lecture[i + 1];
			}
			lecture[--lecturecnt] = null; // 마지막 요소 제거
		}
	}

	/*
	 * void sortLecture() { // 강의명 정렬 함수(아직 미실행) int i, j; Lecture temp;
	 * 
	 * for (i=0; i<lecturecnt-1; i++) { for (j=i+1; j<lecturecnt; j++) {
	 * if(lecture[i].time > lecture[j].time) { temp = lecture[i]; lecture[i] =
	 * lecture[j]; lecture[j] = temp; } } } }
	 */

}
