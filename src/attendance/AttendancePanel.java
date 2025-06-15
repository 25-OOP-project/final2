package attendance;

import javax.swing.*; // JFrame, JPanel, JButton, JOptionPane 등
import java.awt.*; // Layout, Window, Font 등
import java.awt.event.*; // ActionListener 등 이벤트 관련
import java.time.LocalDate; // 날짜 처리용
import main.MainWindow;

public class AttendancePanel extends JPanel {

	private MainWindow win;
	
    public AttendancePanel(MainWindow win, String lectureName, AttendanceManager attManager) {
    	this.win = win;
    	setLayout(new GridLayout(2, 1));
        // 사용자가 출석 정보를 입력하거나 확인하는 GUI 구성 요소.

        JLabel label = new JLabel("출석 상태를 선택하세요:", JLabel.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        add(label);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton attendBtn = new JButton("출석");
        JButton absentBtn = new JButton("결석");
        JButton lateBtn = new JButton("지각");

        Font btnFont = new Font("맑은 고딕", Font.PLAIN, 14);
        attendBtn.setFont(btnFont);
        absentBtn.setFont(btnFont);
        lateBtn.setFont(btnFont);

        attendBtn.addActionListener(e -> {
            System.out.println("출석 버튼 클릭됨");
            처리하기(attManager, lectureName, "출석");
        });
        absentBtn.addActionListener(e -> {
            System.out.println("결석 버튼 클릭됨");
            처리하기(attManager, lectureName, "결석");
        });
        lateBtn.addActionListener(e -> {
            System.out.println("지각 버튼 클릭됨");
            처리하기(attManager, lectureName, "지각");
        });

        btnPanel.add(attendBtn);
        btnPanel.add(absentBtn);
        btnPanel.add(lateBtn);

        add(btnPanel);
    }

    private void 처리하기(AttendanceManager manager, String lectureName, String 상태) {
        String 오늘 = LocalDate.now().toString();
        manager.addRecord(new AttendanceRecord(lectureName, 오늘, 상태));

        JOptionPane.showMessageDialog(win, 상태 + " 처리 완료!");
        System.out.println(상태 + " 처리됨 (" + 오늘 + ")");
        //window.dispose(); // 창 닫기
        win.showHomePanel(); // HOME으로 돌아가기
    }

}