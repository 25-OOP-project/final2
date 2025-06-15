package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.MainWindow;

public class ViewListener implements ActionListener {

	MainWindow win = null;

	public void actionPerformed(ActionEvent e) {
		String keyword = win.tfview.getText().trim().toLowerCase();
		win.listModel.clear();

		if (keyword.isEmpty()) {
			// 검색어가 없으면 모든 강의 보여주기
			for (int i = 0; i < win.lecmanager.lecturecnt; i++) {
				Lecture lec = win.lecmanager.lecture[i];
				if (lec != null) {
					win.listModel.addElement(
							String.format("%-20s %-20s %-10s", lec.lecturename, lec.professorname, lec.time));
				}
			}
			return;
		}

		// keyword(강의명, 교수명, 시간) 포함하는 강의만 보여주기
		for (int i = 0; i < win.lecmanager.lecturecnt; i++) {
			Lecture lec = win.lecmanager.lecture[i];
			if (lec != null) {
				if (lec.lecturename.toLowerCase().contains(keyword) || lec.professorname.toLowerCase().contains(keyword)
						|| lec.time.toLowerCase().contains(keyword)) {
					win.listModel.addElement(
							String.format("%-20s %-20s %-10s", lec.lecturename, lec.professorname, lec.time));
				}
			}
		}
	}

	public ViewListener(MainWindow w) {
		win = w;
	}
}