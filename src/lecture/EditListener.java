package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.MainWindow;

public class EditListener implements ActionListener {

	MainWindow win = null;

	public void actionPerformed(ActionEvent e) { // 수정 리스너
		int selectedIndex = win.lectureList.getSelectedIndex(); // 선택된 항목 인덱스 확인
		if (selectedIndex != -1) {
			// 강의 수정
			String newLectureName = win.tflecture.getText();
			String newProfessorName = win.tfprofessor.getText();
			String newTime = win.tftime.getText();
			// LectureManager 배열 업데이트
			win.lecmanager.lecture[selectedIndex].editLecturename(newLectureName);
			win.lecmanager.lecture[selectedIndex].editProfessorname(newProfessorName);
			win.lecmanager.lecture[selectedIndex].editTime(newTime);
			// JList 모델 업데이트
			win.listModel.set(selectedIndex, String.format("%-20s %-20s %-10s", newLectureName, newProfessorName, newTime));
			// 입력 필드 초기화
			win.tflecture.setText("");
			win.tfprofessor.setText("");
			win.tftime.setText("");

		}
	}

	public EditListener(MainWindow w) {
		win = w;
	}
}