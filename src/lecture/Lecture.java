package lecture;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


// Lecture 클래스
public class Lecture implements Serializable {
	public String lecturename = null;
	String professorname = null;
	String time = null;
	boolean attend = false;

	Lecture() {
	} // 빈 생성자

	Lecture(String l, String p, String t) { // 인수를 받는 생성자
		lecturename = l;
		professorname = p;
		time = t;
	}

	void editLecturename(String s) { // 강의명 수정 함수
		lecturename = s;

	}

	void editProfessorname(String s) { // 교수명 수정 함수
		professorname = s;
	}

	void editTime(String s) { // 강의시간 수정 함수
		time = s;
	}

	void attendCheck() { // 출석 체크 함수
		attend = true;
	}

	void abscentCheck() { // 결석 체크 함수
		attend = false;
	}

	// FileIO용 get()/set()
	public String getLecturename() {
		return lecturename;
	}

	public void setLecturename(String lecturename) {
		this.lecturename = lecturename;
	}

	public String getProfessorname() {
		return professorname;
	}

	public void setProfessorname(String professorname) {
		this.professorname = professorname;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}