package schedule;

import com.toedter.calendar.JCalendar;

import lecture.LectureManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class ScheduleManagerUI extends JPanel {
    private final JCalendar calendar;
    private final JList<String> scheduleList;
    private final DefaultListModel<String> listModel;
    private final JButton addScheduleBtn, deleteScheduleBtn, editScheduleBtn, searchBtn;
    private final JTextField searchField;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final ScheduleManager scheduleManager;
    private final LectureManager lectureManager;

    public ScheduleManagerUI(LectureManager lectureManager, ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
        this.lectureManager = lectureManager;

        calendar = new JCalendar();
        setLayout(new BorderLayout());
        add(calendar, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        scheduleList = new JList<>(listModel);
        scheduleList.setCellRenderer(new HighlightSelectedRenderer());
        scheduleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(scheduleList);

        scheduleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = scheduleList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Rectangle bounds = scheduleList.getCellBounds(index, index);
                    if (!bounds.contains(e.getPoint())) {
                        scheduleList.clearSelection();
                    }
                } else {
                    scheduleList.clearSelection();
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchField = new JTextField(18);
        searchBtn = new JButton("검색");
        searchPanel.add(new JLabel("일정 검색:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        addScheduleBtn = new JButton("일정 추가");
        editScheduleBtn = new JButton("일정 수정");
        deleteScheduleBtn = new JButton("일정 삭제");
        buttonPanel.add(addScheduleBtn);
        buttonPanel.add(editScheduleBtn);
        buttonPanel.add(deleteScheduleBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        int spacingAboveSearch = 5;
        int spacingBelowSearch = 10;
        int spacingBelowList = 10;

        centerPanel.add(Box.createVerticalStrut(spacingAboveSearch));
        centerPanel.add(searchPanel);
        centerPanel.add(Box.createVerticalStrut(spacingBelowSearch));
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalStrut(spacingBelowList));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        addScheduleBtn.addActionListener(e -> {
            String date = dateFormat.format(calendar.getDate());
            String schedule = JOptionPane.showInputDialog("일정 내용을 입력하세요:");
            if (schedule != null && !schedule.trim().isEmpty()) {
                scheduleManager.addSchedule(date, schedule);
                JOptionPane.showMessageDialog(this, "일정이 추가되었습니다.");
                updateScheduleList(date);
            }
        });

        deleteScheduleBtn.addActionListener(e -> {
            String date = dateFormat.format(calendar.getDate());
            List<String> schedules = scheduleManager.getSchedulesForDate(date);
            List<String> selectedValues = scheduleList.getSelectedValuesList();

            if (selectedValues.isEmpty()) {
                JOptionPane.showMessageDialog(this, "삭제할 일정을 선택하세요.");
                return;
            }

            for (String selected : selectedValues) {
                schedules.remove(selected);
            }

            if (schedules.isEmpty()) {
                scheduleManager.getScheduleMap().remove(date);
            }

            searchField.setText("");
            JOptionPane.showMessageDialog(this, "선택된 일정이 삭제되었습니다.");
            updateScheduleList(date);
        });

        editScheduleBtn.addActionListener(e -> {
            String date = dateFormat.format(calendar.getDate());
            List<String> schedules = scheduleManager.getSchedulesForDate(date);
            String selectedSchedule = scheduleList.getSelectedValue();

            if (selectedSchedule == null || selectedSchedule.equals("일정이 없습니다.") || selectedSchedule.equals("검색 결과가 없습니다.")) {
                JOptionPane.showMessageDialog(this, "수정할 일정을 선택하세요.");
                return;
            }

            String newContent = JOptionPane.showInputDialog("수정할 내용을 입력하세요:", selectedSchedule);
            if (newContent != null && !newContent.trim().isEmpty()) {
                schedules.set(schedules.indexOf(selectedSchedule), newContent);
                searchField.setText("");
                JOptionPane.showMessageDialog(this, "일정이 수정되었습니다.");
                updateScheduleList(date);
            }
        });

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "검색어를 입력하세요.");
                return;
            }

            String date = dateFormat.format(calendar.getDate());
            List<String> schedules = scheduleManager.getSchedulesForDate(date);

            listModel.clear();
            boolean found = false;

            for (String schedule : schedules) {
                if (schedule.contains(keyword)) {
                    listModel.addElement(schedule);
                    found = true;
                }
            }

            if (!found) {
                listModel.addElement("검색 결과가 없습니다.");
                scheduleList.setEnabled(false);
                deleteScheduleBtn.setEnabled(false);
                editScheduleBtn.setEnabled(false);
            } else {
                scheduleList.setEnabled(true);
                deleteScheduleBtn.setEnabled(true);
                editScheduleBtn.setEnabled(true);
            }
        });

        calendar.addPropertyChangeListener("calendar", e -> {
            String selectedDate = dateFormat.format(calendar.getDate());
            updateScheduleList(selectedDate);
        });

        updateScheduleList(dateFormat.format(calendar.getDate()));
    }

    private void updateScheduleList(String date) {
        List<String> schedules = scheduleManager.getSchedulesForDate(date);
        listModel.clear();

        if (schedules.isEmpty()) {
            listModel.addElement("일정이 없습니다.");
            scheduleList.setEnabled(false);
            deleteScheduleBtn.setEnabled(false);
            editScheduleBtn.setEnabled(false);
        } else {
            for (String s : schedules) {
                listModel.addElement(s);
            }
            scheduleList.setEnabled(true);
            deleteScheduleBtn.setEnabled(true);
            editScheduleBtn.setEnabled(true);
        }
    }

    private class HighlightSelectedRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText(isSelected ? "✔ " + value.toString() : value.toString());
            return label;
        }
    }
}