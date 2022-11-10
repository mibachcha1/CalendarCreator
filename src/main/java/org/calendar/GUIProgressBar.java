package org.calendar;

import javax.swing.*;

public class GUIProgressBar {
	JFrame frame = new JFrame();
	static JProgressBar progressBar = new  JProgressBar();
	
	public GUIProgressBar() {

		frame.add(progressBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);
		
		progressBar.setValue(0);
		progressBar.setBounds(0, 0, 420, 50);
		progressBar.setStringPainted(true);
		
	}
	
	public static void main(String[] args) {
		GUIProgressBar testBar = new GUIProgressBar();

	}
	
	public static void fill() {
		int current = 0;
		while(current <= 100) {
			progressBar.setValue(current);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
