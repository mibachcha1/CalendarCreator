package org.calendar;

import net.fortuna.ical4j.model.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class GUI implements ActionListener {

	// Initialize ArrayList
	private ArrayList<Assignment> assignments = new ArrayList<Assignment>();

	// Basic Frame & Panel
	private static JFrame frame;
	private static JPanel panel;

	// Home Page:
	private JButton home;
	private JButton manual;
	private JButton fileInput;

	// Manual Calendar Mode:
	// Main.Assignment Class Creation:
	private JLabel className;
	private JLabel assignmentName;
	private JLabel dueDate;

	private JTextField classNameField;
	private JTextField assignmentNameField;
	private JTextField dueDateField;

	private JButton manualAddButton;
	private JButton manualCreateButton;

	private JTextArea sample;
	private JCheckBox testDate;
	private boolean dateState = true; // CHANGE AFTER

	// File Calendar Mode:
	private JButton browse;
	private JTextArea foundFile;
	private JButton readFile;
	private String fileHelperPath;
	private JButton create;
	private String filename;

	private JTextArea sampleFile;

	// Final Step:
	private JLabel finalStepJLabel;
	private JTextField filenameField;
	private JButton createFinal;
	private String filenameString;
	private JTextArea finalNotice;

	private JProgressBar progressBar;

	// TEST
	public static java.util.Calendar calendar = java.util.Calendar.getInstance();

	public GUI() { // This is good; no edits
		splash();
    	panel = new JPanel();
		panel.setLayout(null);

		frame = new JFrame();
		frame.setSize(350, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mohammad's Calendar Creator ");
		frame.add(panel);


		JLabel intro = new JLabel("Welcome!");
		JLabel intro2 = new JLabel("How would you like to use this:");
		intro.setBounds(10, 20, 250, 25);
		intro2.setBounds(10, 40, 250, 25);
		panel.add(intro);
		panel.add(intro2);

		JLabel manualInfo = new JLabel("Manually Enter Your Schedule: ");
		manualInfo.setBounds(10, 80, 400, 25);
		panel.add(manualInfo);

		manual = new JButton("Manual");
		manual.setBounds(10, 100, 80, 25);
		manual.addActionListener(this);
		panel.add(manual);

		JLabel fileInfo = new JLabel("Browse For a Schedule File:");
		fileInfo.setBounds(10, 140, 500, 25);
		panel.add(fileInfo);

		fileInput = new JButton("Browse File");
		fileInput.setBounds(10, 160, 125, 25);
		fileInput.addActionListener(this);
		panel.add(fileInput);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	
	private void splash() {
		
		JFrame frame = new JFrame("test");
        frame.setUndecorated(true);
        frame.setSize(600,500);

        Icon img= new ImageIcon(getClass().getClassLoader().getResource("splash.png"));
		JLabel img2 = new JLabel("", img, SwingConstants.CENTER);
		img2.setOpaque(true);
		img2.setBackground(new Color(0, 0, 0));
        
       
        frame.getContentPane().add(img2);
       // frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		frame.setVisible(false);
	}
	

	private void manual() { // This is good; no edits
		frame.setVisible(false);

		panel = new JPanel();
		panel.setLayout(null);
		frame = new JFrame();
		frame.setBounds(500, 250, 425, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Manual Calendar Creation ");
		frame.add(panel);

		className = new JLabel("Enter Class Name: ");
		className.setBounds(10, 20, 150, 25);
		panel.add(className);

		classNameField = new JTextField();
		classNameField.setBounds(200, 20, 165, 25);
		panel.add(classNameField);

		assignmentName = new JLabel("Enter Assignment Name: ");
		assignmentName.setBounds(10, 50, 175, 25);
		panel.add(assignmentName);

		assignmentNameField = new JTextField();
		assignmentNameField.setBounds(200, 50, 165, 25);
		panel.add(assignmentNameField);

		dueDate = new JLabel("Enter Due Date (mm/dd/yyyy): ");
		dueDate.setBounds(10, 80, 200, 25);
		panel.add(dueDate);

		dueDateField = new JTextField();
		dueDateField.setBounds(200, 80, 165, 25);
		panel.add(dueDateField);

		manualAddButton = new JButton("Add Assignment");
		manualAddButton.setBounds(10, 115, 150, 25);
		manualAddButton.addActionListener(this);
		panel.add(manualAddButton);

		sample = new JTextArea("");
		sample.setBounds(10, 150, 400, 75);
		panel.add(sample);

		testDate = new JCheckBox("Please Uncheck if the Displayed Date is incorrect", true);
		testDate.addActionListener(this);
		testDate.setBounds(10, 200, 400, 75);
		panel.add(testDate);

		manualCreateButton = new JButton("Create My Calendar");
		manualCreateButton.setBounds(10, 285, 175, 25);
		manualCreateButton.addActionListener(this);
		manualCreateButton.setEnabled(false);
		panel.add(manualCreateButton);

		home = new JButton("Home");
		home.setBounds(330, 285, 80, 25);
		home.addActionListener(this);
		panel.add(home);

		frame.setVisible(true);
	}

	private void addManual() throws IOException, ValidationException { // Done
		String classN = classNameField.getText();
		String assignment = assignmentNameField.getText();
		String due = dueDateField.getText();

		assignments.add(new Assignment(classN, assignment, due));

		classNameField.setText("");
		assignmentNameField.setText("");
		dueDateField.setText("");

		createAllDayGUI(new Assignment(classN, assignment, due));
		Date x = calendar.getTime();
		String sampleString = x.toGMTString();
		String[] output = sampleString.split(" ");
		String finalOutput = assignment + " - " + output[0] + " " + output[1] + " " + output[2];
		String oldOutput = sample.getText();
		sample.setText(finalOutput + "\n");
		sample.append(oldOutput);
		// sample.append(sampleString);

		// sample.setText(assignment);

		manualCreateButton.setEnabled(true);
	}

	private static void createAllDayGUI(Assignment hw) throws IOException, ValidationException {

		// Take string inputs from assignment class and parse them
		String date = hw.getDueDate();
		String[] dates = date.split("/");

		// Used substring to get rid of comma after the date

		int DayOfMonth = Integer.parseInt(dates[1]);

		int year = Integer.parseInt(dates[2]);

		// Checks and sets the month appropriately
		if (dates[0].equalsIgnoreCase("01")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
		} else if (dates[0].equalsIgnoreCase("02")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.FEBRUARY);
		} else if (dates[0].equalsIgnoreCase("03")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
		} else if (dates[0].equalsIgnoreCase("04")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		} else if (dates[0].equalsIgnoreCase("05")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.MAY);
		} else if (dates[0].equalsIgnoreCase("06")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JUNE);
		} else if (dates[0].equalsIgnoreCase("07")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JULY);
		} else if (dates[0].equalsIgnoreCase("08")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.AUGUST);
		} else if (dates[0].equalsIgnoreCase("09")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.SEPTEMBER);
		} else if (dates[0].equalsIgnoreCase("10")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
		} else if (dates[0].equalsIgnoreCase("11")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
		} else if (dates[0].equalsIgnoreCase("12")) {
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		}

		// sets date and year based on parsed values above
		calendar.set(java.util.Calendar.DAY_OF_MONTH, DayOfMonth);
		calendar.set(java.util.Calendar.YEAR, year);

	}

	private void file1() { // This is good; no edits
		frame.setVisible(false);

		panel = new JPanel();
		panel.setLayout(null);
		frame = new JFrame();
		frame.setBounds(500, 250, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("File Input Calendar Creation");
		frame.add(panel);

		browse = new JButton("Browse File");
		browse.setBounds(10, 10, 125, 25);
		browse.addActionListener(this);
		panel.add(browse);

		foundFile = new JTextArea("");
		foundFile.setForeground(Color.black);
		foundFile.setBounds(20, 40, 125, 20);
		foundFile.setAutoscrolls(true);

		readFile = new JButton("Add to Calendar");
		readFile.setBounds(10, 85, 175, 25);
		readFile.addActionListener(this);
		readFile.setEnabled(false);
		panel.add(readFile);

		sample = new JTextArea("");
		sample.setSize(275, 125);
		sample.setBounds(10, 125, 430, 175);
		panel.add(sample);

		testDate = new JCheckBox("Please Uncheck if the Displayed Date is incorrect", true);
		testDate.addActionListener(this);
		testDate.setBounds(10, 300, 450, 75);
		panel.add(testDate);

		create = new JButton("Create My Calendar");
		create.setBounds(10, 375, 175, 25);
		create.addActionListener(this);
		create.setEnabled(false);
		panel.add(create);

		home = new JButton("Home");
		home.setBounds(350, 375, 80, 25);
		home.addActionListener(this);
		panel.add(home);

		frame.setVisible(true);

	}

	private void browse() {
		FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("user.home");
		fd.setFile("*.xml");
		fd.setVisible(true);
		fileHelperPath = fd.getDirectory() + fd.getFile();
		filename = fd.getFile();
		foundFile.setText(filename);
		panel.add(foundFile);
		foundFile.setEditable(false);
		readFile.setEnabled(true);

	}

	private void finish() {
		frame.setVisible(false);

		panel = new JPanel();
		panel.setLayout(null);
		frame = new JFrame();
		frame.setBounds(500, 250, 450, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Final Step");
		frame.add(panel);

		finalStepJLabel = new JLabel("Enter a filename: ");
		finalStepJLabel.setBounds(10, 20, 150, 25);
		panel.add(finalStepJLabel);

		filenameField = new JTextField();
		filenameField.setBounds(170, 20, 165, 25);
		panel.add(filenameField);

		progressBar = new JProgressBar();
		// progressBar.setValue(0);
		progressBar.setBounds(10, 100, 420, 50);
		panel.add(progressBar);

		createFinal = new JButton("Create My Calendar");
		createFinal.setBounds(10, 65, 175, 25);
		createFinal.addActionListener(this);
		panel.add(createFinal);

		progressBar.setStringPainted(true);
		
		finalNotice = new JTextArea();
		finalNotice.setBounds(10, 150, 400, 25);
		panel.add(finalNotice);

		home = new JButton("Home");
		home.setBounds(365, 185, 80, 25);
		home.addActionListener(this);
		panel.add(home);
		
		frame.setVisible(true);

	}

	@SuppressWarnings("static-access")
	private void createCalendar() throws IOException, ValidationException {
		dateState = testDate.isSelected();
		AllDayCalendar a1 = new AllDayCalendar(dateState);

		for (Assignment x : assignments) {
			a1.create(x);
		}
		a1.outputAllDay(filenameField.getText());
		home.setEnabled(true);
		progressBar.setValue(100);
		finalNotice.setText("Success! Please check your Desktop for " + filenameField.getText() + ".ics" );
		thread2.interrupt();
	}

	public void fill() {
		int i = 0;
		Random random = new Random();
		
		try {
			while (i <= 90) {
				// fill the menu bar
				progressBar.setValue(i + 5);

				// delay the thread
				Thread.sleep(1000);
				int x = random.nextInt(3) + 1;
				i += x;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	Thread thread1 = new Thread() {
		public void run() {
			try {
				createCalendar();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	Thread thread2 = new Thread() {
		public void run() {
			fill();
		}
	};

	private void readFile() throws IOException, ValidationException {
		BufferedReader fileReader = new BufferedReader(new FileReader(fileHelperPath));
		String line = "";
		String[] values;
		// String headerLine = fileReader.readLine();
		while ((line = fileReader.readLine()) != null) {
			values = line.split(",");
			String className = values[0];
			String name = values[1];
			String date = values[2];
			assignments.add(new Assignment(className, name, date));
		}
		fileReader.close();
		create.setEnabled(true);
		addToSample();
	}

	public void addToSample() throws IOException, ValidationException {
		for (Assignment a1 : assignments) {
			createAllDayGUI(a1);
			Date x = calendar.getTime();
			String sampleString = x.toGMTString();
			String[] output = sampleString.split(" ");
			String finalOutput = a1.getAssignmentName() + " - " + output[0] + " " + output[1] + " " + output[2];
			sample.append(finalOutput + "\n");
			System.out.println(finalOutput + "\n");
		}
	}

	@SuppressWarnings({ "static-access", "resource" })
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == home) {
			frame.dispose();
			new GUI();
		}
		if (e.getSource() == manual) {
			manual();
		}

		if (e.getSource() == fileInput) {
			file1();
		}

		if (e.getSource() == manualAddButton) {
			try {
				addManual();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == manualCreateButton) {
			try {
				finish();
			} catch (Exception e1) {
				frame.dispose();
			}
		}

		if (e.getSource() == create) {
			try {
				// dateState = testDate.isSelected();
				finish();
			} catch (Exception e1) {
				frame.dispose();
			}
		}

		if (e.getSource() == browse) {
			browse();
		}

		if (e.getSource() == readFile) {
			try {
				readFile();
			} catch (Exception e1) {
				System.out.print(e1);
			}
		}

		if (e.getSource() == createFinal) {
			try {
				thread1.start();
				thread2.start();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.print(e1);
			}
		}

	}
}
