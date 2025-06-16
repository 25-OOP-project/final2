package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.io.File;
import main.MainWindow;

public class DeleteListener implements ActionListener {

    MainWindow win = null;

    public DeleteListener(MainWindow w) {
        win = w;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = win.lectureList.getSelectedIndex();
        if (selectedIndex != -1) {
            // 강의 이름 가져오기 (메모 디렉토리 이름과 동일)
            String lectureName = win.listModel.getElementAt(selectedIndex);

            // 삭제 전 사용자 확인
            int confirm = JOptionPane.showConfirmDialog(
                    win,
                    "해당 강의의 모든 메모도 함께 삭제됩니다.\n계속하시겠습니까?",
                    "강의 및 메모 삭제 확인",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // 모델에서 제거
                win.listModel.remove(selectedIndex);

                // LectureManager 배열에서도 삭제
                for (int i = selectedIndex; i < win.lecmanager.lecturecnt - 1; i++) {
                    win.lecmanager.lecture[i] = win.lecmanager.lecture[i + 1];
                }
                win.lecmanager.lecture[win.lecmanager.lecturecnt - 1] = null;
                win.lecmanager.lecturecnt--;

                // 메모 디렉토리 삭제
                File memoDir = new File("./memo/" + lectureName);
                if (memoDir.exists() && memoDir.isDirectory()) {
                    boolean deleted = deleteDirectoryRecursively(memoDir);
                    if (deleted) {
                        JOptionPane.showMessageDialog(win, "강의와 관련된 메모가 모두 삭제되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(win, "메모 일부 또는 전체 삭제 실패 😢\n폴더를 수동으로 확인해주세요.");
                    }
                }
            }
        }
    }

    // 디렉토리 및 내부 파일 재귀 삭제
    private boolean deleteDirectoryRecursively(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (!deleteDirectoryRecursively(file)) return false;
            }
        }
        return dir.delete();
    }
}
