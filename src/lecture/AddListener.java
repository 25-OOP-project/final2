package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.MainWindow; 

//Event Listener 클래스
public class AddListener implements ActionListener {

	MainWindow win = null;

	public void actionPerformed(ActionEvent e) {
		if (win.lecmanager.lecturecnt < 10) {
			String name = win.tflecture.getText();
			String prof = win.tfprofessor.getText();
			String time = win.tftime.getText();

			Lecture lec = new Lecture(name, prof, time);

			win.lecmanager.addLecture(lec);
			win.listModel.addElement(String.format("%-20s %-20s %-10s", name, prof, time));
			win.tflecture.setText("");
			win.tfprofessor.setText("");
			win.tftime.setText("");

			System.out.println("강의 추가됨: " + name);  //콘솔로 확인인
		}
	}
	public AddListener(MainWindow w) {
		win = w;
	}
}