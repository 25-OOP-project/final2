package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.MainWindow;

public class DeleteListener implements ActionListener {
	
	MainWindow win = null;
	
	public void actionPerformed(ActionEvent e) {	//삭제 리스너
		int selectedIndex = win.lectureList.getSelectedIndex();
		if (selectedIndex != -1) {
          // 모델에서 제거
          win.listModel.remove(selectedIndex);
          // LectureManager 배열에서도 삭제 (간단하게 구현)
          for (int i = selectedIndex; i < win.lecmanager.lecturecnt - 1; i++) {
              win.lecmanager.lecture[i] = win.lecmanager.lecture[i + 1];
          }
          win.lecmanager.lecture[win.lecmanager.lecturecnt - 1] = null;
          win.lecmanager.lecturecnt--;
      }
	}
	
	public DeleteListener (MainWindow w)
	{
		win = w;
	}
}
