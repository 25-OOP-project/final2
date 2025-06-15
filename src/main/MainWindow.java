package main;

import javax.swing.*;	
import attendance.*;
import filio.FileIO;
import lecture.*;
import managercontainer.ManagerContainer;
import memo.*;
import schedule.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
	private ManagerContainer managers;
    public LectureManager lecmanager;
    private AttendanceManager attmanager;
    private MemoManager memomanager;
    private ScheduleManager schmanager;

    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel menuPanel;
    private CardLayout cardLayout;
    public DefaultListModel<String> listModel;
    public JList<String> lectureList;
    
    public JTextField tflecture;
    public JTextField tfprofessor;
    public JTextField tftime;
    public JTextField tfview;
    JButton addbtn, viewbtn, editbtn, deletebtn;

    public MainWindow(ManagerContainer managers) {
        this.managers = managers;
        this.lecmanager = managers.getLectureManager();
        this.attmanager = managers.getAttendanceManager();
        this.memomanager = managers.getMemoManager();
        this.schmanager = managers.getScheduleManager();

        setTitle("수업 관리 시스템");
        setLayout(new BorderLayout());

        initLectureList();
        initCenter();
        initMenu();
        initTopPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                List<Lecture> current = LectureManager.toList(lecmanager);
                FileIO.saveLectures(current);
                FileIO.saveAttendance(attmanager.getRecords());
                FileIO.saveSchedule(schmanager.getScheduleMap());
            }
        });
    }

    private void initLectureList() {
        listModel = new DefaultListModel<>();
        for (int i = 0; i < lecmanager.lecturecnt; i++) {
            Lecture lec = lecmanager.lecture[i];
            listModel.addElement(String.format("%-20s %-20s %-10s",
                lec.getLecturename(), lec.getProfessorname(), lec.getTime()));
        }
    }

    private void initTopPanel() {
    	topPanel = new JPanel(new BorderLayout());

        // 입력 패널
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));
        tflecture = new JTextField();
        tfprofessor = new JTextField();
        tftime = new JTextField();
        addbtn = new JButton("추가");

        inputPanel.add(new JLabel("과목명"));
        inputPanel.add(new JLabel("교수명"));
        inputPanel.add(new JLabel("시간"));
        inputPanel.add(new JLabel(" "));
        inputPanel.add(tflecture);
        inputPanel.add(tfprofessor);
        inputPanel.add(tftime);
        inputPanel.add(addbtn);

        // 액션 패널    
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfview = new JTextField(15);
        viewbtn = new JButton("조회");
        editbtn = new JButton("수정");
        deletebtn = new JButton("삭제");

        JLabel hintLabel = new JLabel("※ 위 입력창에 수정할 내용을 입력한 뒤 수정 버튼을 누르세요.");
        hintLabel.setForeground(Color.BLACK);
        hintLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        
        actionPanel.add(new JLabel("Search:"));
        actionPanel.add(tfview);
        actionPanel.add(viewbtn);
        actionPanel.add(editbtn);
        actionPanel.add(deletebtn);
        actionPanel.add(hintLabel);

        // 리스트
        lectureList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(lectureList);

        // 패널 조립
        JPanel upperPanel = new JPanel(new GridLayout(2, 1));
        upperPanel.add(inputPanel);
        upperPanel.add(actionPanel);

        topPanel.add(upperPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        

        // 버튼에 리스너 연결
        addbtn.addActionListener(new AddListener(this));
        viewbtn.addActionListener(new ViewListener(this));
        editbtn.addActionListener(new EditListener(this));
        deletebtn.addActionListener(new DeleteListener(this));
    }

    private void initCenter() {
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // HOME 패널 (기본 안내)
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("좌측에서 강의를 선택한 후 하단의 버튼을 사용해 기능을 실행하세요.", JLabel.CENTER);
        welcomeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        homePanel.add(welcomeLabel, BorderLayout.CENTER);
        centerPanel.add(homePanel, "HOME");

        // Attendance 안내 패널 (버튼 누르기 전 안내용)
        JPanel attendanceWrapper = new JPanel(new BorderLayout());
        JLabel attLabel = new JLabel("출석을 시작하려면 강의를 선택하고 '출석' 버튼을 누르세요.", JLabel.CENTER);
        attLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        attendanceWrapper.add(attLabel, BorderLayout.CENTER);
        centerPanel.add(attendanceWrapper, "ATTENDANCE_INFO");

        // 메모
        MemoPanel memoPanel = new MemoPanel(memomanager.getLectureNames());
        centerPanel.add(memoPanel, "MEMO");

        // 일정
        ScheduleManagerUI scheduleUI = new ScheduleManagerUI(lecmanager, schmanager);
        JPanel scheduleWrapper = new JPanel(new BorderLayout());
        scheduleWrapper.add(scheduleUI, BorderLayout.CENTER);
        centerPanel.add(scheduleUI, "SCHEDULE");
    }
    
    public void showHomePanel() {
        cardLayout.show(centerPanel, "HOME");
    }

    private void initMenu() {
        menuPanel = new JPanel(new GridLayout(1, 3));

        JButton btnAttendance = new JButton("출석");
        JButton btnAttStats = new JButton("출석 통계");
        JButton btnMemo = new JButton("메모");
        JButton btnSchedule = new JButton("일정");

        btnAttendance.addActionListener(e -> {
            int selectedIndex = lectureList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "먼저 강의를 선택하세요.");
                return;
            }
            String lectureName = lecmanager.lecture[selectedIndex].getLecturename();
            AttendancePanel attPanel = new AttendancePanel(this, lectureName, attmanager);
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.add(attPanel, BorderLayout.CENTER);
            centerPanel.add(wrapper, "ATTENDANCE_DYNAMIC");
            cardLayout.show(centerPanel, "ATTENDANCE_DYNAMIC");
        });

        btnAttStats.addActionListener(e -> {
            AttendanceStatisticsFrame statsFrame = new AttendanceStatisticsFrame(attmanager, lecmanager);
            statsFrame.setVisible(true);  // 새 프레임 열기
        });
        
        btnMemo.addActionListener(e -> {
            MemoPanel newMemoPanel = new MemoPanel(memomanager.getLectureNames());
            centerPanel.add(newMemoPanel, "MEMO_DYNAMIC");
            cardLayout.show(centerPanel, "MEMO_DYNAMIC");
        });
        btnSchedule.addActionListener(e -> cardLayout.show(centerPanel, "SCHEDULE"));

        menuPanel.add(btnAttendance);
        menuPanel.add(btnAttStats);
        menuPanel.add(btnMemo);
        menuPanel.add(btnSchedule);
    }
}