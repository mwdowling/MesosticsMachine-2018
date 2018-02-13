package mesosticSystemOOP;


/**
 * @author Martin Dowling
 *
 * Within a JFrame lies a navigable hierarchy of JPanel "panels":
 * 
 *         (1) A Welcome panel with links to setup, run, and view cards 
 *         (2) A Setup panel for choosing the project directory, mesostic row, 
 *         		and target chapters 
 *         (3) Run panel to call methods in the system's core objects 
 *         		to create mesostics with and without syllable filtering 
 *         (4) A panel card to view files produced by the run functions
 * 
 * Please note the following:
 * 
 * (1) The following Prefixes used for panel and filechooser objects: 
 * JPxxx = a JPanel object xxx 
 * JFCxxx = a JFileChooser xxx
 *         
 * (2) There are TEMPORAL COUPLING WARNINGS in the code generated because 
 * this GUI will only function properly under these conditions (see guidance notes):
 * 
 *         (1) The user must choose the project directory first        
 *         (2) The user must choose the target chapter(s) after the project directory is set
 *         (3) The user must establish the mesostic row after the project directory is set
 *         (4) The project directory, target chapter(s) and mesostic row must be set 
 *         	   before the user engages the functions on the Run panel and its sub-panels 
 *         
 * (3) The location notepad.exe is hard coded in this GUI. 
 * If the user of the GUI stores notepad.exe in another location
 * or prefers to use another program to open .txt files, this code must be changed
 * 
 * @TODO As of 12 February 2018 this refactored class has not been tested.
 */

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;

public class MesosticGUIOOP {

	// the frame within which all panels are placed
	private JFrame frame;

	// the panels in the frame
	private JPanel JPWelcome;// the welcome panel
	private JPanel JPSetup;// the setup panel
	private JPanel JPMesosticRow;// sup-panel to the setup panel
	private JPanel JPChapters;
	private JPanel JPInfo;// sub-panel to the setup panel
	private JPanel JPRun;// the run panel
	private JPanel JPMesosticsLonger; // sub-panel to the run panel
	private JPanel JPMesosticsShorter; // sub-panel to the run panel
	private JPanel JPFinish;// the finish panel
	private JPanel JPView;// the view panel

	// Default directory for the GUI
	static String Directory = "C:\\Users\\";
	private JFileChooser JFCDirectory;
	
	//Static variables for constructors requiring the target chapters
	static String ChapterAddress;
	static String[] ChapterArray;
	static String ChapterArrayIndex;
	private ArrayList<String> ChapterQueue = new ArrayList<String>();

	// field for user input of the mesostic row in GUI
	private JTextField RowtextField;
	// Static variables for constructors requiring the mesostic row
	static String RowAddress;
	static String[] RowArray;
	static int RowArrayIndex;

	// mesostics output file:
	static String Mesostics = Directory + "\\Mesostics.txt";

	// sounds files:
	private String OEDSounds = Directory + "\\Sounds and Places\\OEDSounds.txt";
	private String Sounds = Directory + "\\Sounds and Places\\Chapter Sounds.txt";
	private String SoundsSentences = Directory + "Sounds and Places\\Chapter Sounds Sentences.txt";

	// places files:
	private String NotPlaces = Directory + "\\Sounds and Places\\Not A Place.txt";
	private String Places = Directory + "\\Sounds and Places\\Chapter Places.txt";
	private String PlacesSentences = Directory + "\\Sounds and Places\\Chapter Places Sentences.txt";

	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MesosticGUIOOP window = new MesosticGUIOOP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Application constructor with its huge initialize method.
	public MesosticGUIOOP() {
		initialize();
	}

	/*
	 * This method initializes the contents of the frame by:
	 * 
	 * (1) initializing the frame 
	 * (2) initializing the panels for the frame 
	 * (3) placing the panels in the frame 
	 * (4) initializing buttons and labels on the panels 
	 * (5) setting the visibility sequence 
	 * 	   for navigating between the panels
	 */
	private void initialize() {

		// intialize the frame
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);// make it bigger!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		// initialize the panels
		JPWelcome = new JPanel();
		JPSetup = new JPanel();
		JPMesosticRow = new JPanel();// sub-panel of Setup
		JPChapters = new JPanel();// sub panel of Setup
		JPInfo = new JPanel();
		JPRun = new JPanel();
		JPMesosticsLonger = new JPanel();
		JPMesosticsShorter = new JPanel();
		JPFinish = new JPanel();
		JPView = new JPanel();

		// add the panels to the frame
		frame.getContentPane().add(JPWelcome);
		frame.getContentPane().add(JPSetup);
		frame.getContentPane().add(JPMesosticRow);
		frame.getContentPane().add(JPChapters);
		frame.getContentPane().add(JPInfo);
		frame.getContentPane().add(JPRun);
		frame.getContentPane().add(JPMesosticsLonger);
		frame.getContentPane().add(JPMesosticsShorter);
		frame.getContentPane().add(JPFinish);
		frame.getContentPane().add(JPView);

//START OF WELCOME PANEL CODE
		
		/*
		 * Welcome panel is the GUI home 
		 * All "done" buttons return here
		 */

		// the Welcome panel layout, label and buttons
		JPWelcome.setLayout(null);
		JPWelcome.setVisible(true);// default state has this panel visible
		JLabel lblWelcome = new JLabel("Welcome to the MesosticMachine");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcome.setBounds(133, 12, 638, 64);
		JPWelcome.add(lblWelcome);

		// the Setup button on Welcome panel makes the Setup panel visible
		JButton btnWelcomeSetup = new JButton("Setup");
		btnWelcomeSetup.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPSetup.setVisible(true);
				JPWelcome.setVisible(false);
			}
		});
		btnWelcomeSetup.setBounds(365, 110, 160, 64);
		JPWelcome.add(btnWelcomeSetup);

		// the Run button on Welcome panel makes the Run panel visible
		JButton btnWelcomeRun = new JButton("Run");
		btnWelcomeRun.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPRun.setVisible(true);
				JPWelcome.setVisible(false);
			}
		});
		btnWelcomeRun.setBounds(365, 205, 160, 64);
		JPWelcome.add(btnWelcomeRun);

		// The View button on Welcome panel makes the View panel visible
		JButton btnWelcomeView = new JButton("View");
		btnWelcomeView.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPView.setVisible(true);
				JPWelcome.setVisible(false);
			}
		});
		btnWelcomeView.setBounds(365, 392, 161, 64);
		JPWelcome.add(btnWelcomeView);

		// The Finish button on Welcome panel makes the Finish panel visible
		JButton btnWelcomeFinish = new JButton("Finish");
		btnWelcomeFinish.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPFinish.setVisible(true);
				JPWelcome.setVisible(false);
			}
		});
		btnWelcomeFinish.setBounds(365, 297, 161, 64);
		JPWelcome.add(btnWelcomeFinish);

// END OF WELCOME PANEL CODE; START OF SETUP PANEL CODE

		/*
		 * The Setup panel assigns the project file directory, the mesostic row,
		 * the target chapter file, and the output files. These assignments are 
		 * required for the Run and Finish panel functions
		 */

		// the Setup panel layout, label, and buttons
		JPSetup.setLayout(null);
		JPSetup.setVisible(false);// default = not visible
		JLabel lblSetup = new JLabel("Setup Your Project");
		lblSetup.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetup.setBounds(267, 21, 353, 56);
		JPSetup.add(lblSetup);

		// the SetupInfo sub-panel layout and label
		JPInfo.setLayout(null);
		JLabel lblSetupInfo = new JLabel("Your Setup Info");
		lblSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetupInfo.setBounds(278, 51, 291, 51);
		JPInfo.add(lblSetupInfo);

		// A text area in the the SetupInfo panel
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textArea.setRows(3);
		textArea.setBounds(51, 160, 787, 234);
		JPInfo.add(textArea);
		textArea.setEditable(false);

		/*
		 * The Set Project Folder button on the Setup Panel does a lot of work: 
		 * (1) opens a "directories only" JFileChooser.
		 * (2) Sets the home directory for the project 
		 * (2) Assigns all the output files to the chosen directory.
		 * (3) Appends the directory to the SetupInfo panel text area
		 * 
		 * TEMPORAL COUPLING WARNING: The user must use this button first
		 */
		
		JButton btnSetProjectFolder = new JButton("Step 1: Set Project Folder");
		btnSetProjectFolder.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetProjectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPSetup.setVisible(false);
				JFileChooser JFCDirectory = new JFileChooser();
				JFCDirectory.setBounds(0, 0, 438, 241);
				JFCDirectory.setPreferredSize(new java.awt.Dimension(800, 600));
				JFCDirectory.setCurrentDirectory(new File(Directory));
				JFCDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				JFCDirectory.setDialogTitle("Set the Directory");

				if (JFCDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					// choose the project directory
					Directory = JFCDirectory.getSelectedFile().getAbsolutePath();

					// put setup info into the text area of the SetupInfo panel
					textArea.append("Project folder: " + Directory + "\n\n");

					// assign the file paths for row and mesostics output files
					RowAddress = Directory + "\\Mesotic Row.txt";
					Mesostics = Directory + "\\Mesostics.txt";

					// assign the file paths for the sounds and places files
					OEDSounds = Directory + "\\Sounds and Places\\OEDSounds Final.txt";
					Sounds = Directory + "\\Sounds and Places\\ChapterSounds.txt";
					SoundsSentences = Directory + "\\Sounds and Places\\ChapterSoundsSentences.txt";
					NotPlaces = Directory + "\\Sounds and Places\\Not A Place.txt";
					Places = Directory + "\\Sounds and Places\\ChapterPlaces.txt";
					PlacesSentences = Directory + "\\Sounds and Places\\ChapterPlacesSentences.txt";

					// return to Setup Panel
					JPSetup.setVisible(true);
					JPWelcome.setVisible(false);

				} else if (JFCDirectory.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					// return to Setup Panel
					JFCDirectory.setVisible(false);
					JPSetup.setVisible(false);
				}
			}
		});
		btnSetProjectFolder.setBounds(239, 98, 409, 74);
		JPSetup.add(btnSetProjectFolder);

		// the Set Mesostic Row Button makes the SetMesosticRow panel visible
		JButton btnSetMesosticRow = new JButton("Step 2: Set Mesostic Row");
		btnSetMesosticRow.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetMesosticRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPMesosticRow.setVisible(true);
				JPSetup.setVisible(false);
			}
		});
		btnSetMesosticRow.setBounds(239, 205, 409, 80);
		JPSetup.add(btnSetMesosticRow);

		// the SetMesosticRow panel and its label
		JPMesosticRow.setLayout(null);
		JPMesosticRow.setVisible(false);// default = not visible
		JLabel lblTypeYourMesostic = new JLabel("Type your mesostic row in the box:");
		lblTypeYourMesostic.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTypeYourMesostic.setBounds(88, 84, 712, 59);
		JPMesosticRow.add(lblTypeYourMesostic);

		// add a text field for entering the mesostic row
		RowtextField = new JTextField();
		RowtextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		RowtextField.setColumns(10);
		RowtextField.setBounds(86, 241, 715, 59);
		JPMesosticRow.add(RowtextField);

		/*
		 * The submit button on the SetMesosticRow panel does a lot of work:
		 * 
		 * (1) Assigns the properly formatted user input to a variable 
		 * (2) Writes the formatted row to a text file in the project directory
		 * Then, calling the RepositoryWriter() method of a Repository object:
		 * (3) Creates a syllable repository file for each letter of the row 
		 *     adding an identifying string to the first line of each file
		 *     
		 * TEMPORAL COUPLING WARNING: This button uses the Directory variable,
		 * which must be previously set by the user with the Set Project Folder button    
		 */
		JButton btnMesosticRowSubmit = new JButton("submit");
		btnMesosticRowSubmit.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnMesosticRowSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// save input as formatted string
				String Row = RowtextField.getText().toLowerCase().replaceAll("\\s+", "");
				
				// put setup info into the text area of the SetupInfo panel
				textArea.append("Mesostic Row: " + Row + "\n\n");

				// save the mesosticRow to a file
				try (PrintWriter out = new PrintWriter(RowAddress)) {
					out.println(Row);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				//create a syllable repository for each letter in the row
				new SyllableRepository(Row, Directory).RepositoryWriter();
						
				// return to Welcome panel
				JPMesosticRow.setVisible(false);
				JPSetup.setVisible(true);
				JPWelcome.setVisible(false);

			}
		});
		btnMesosticRowSubmit.setBounds(352, 312, 183, 59);
		JPMesosticRow.add(btnMesosticRowSubmit);

		// The Set Chapter Files button makes the Chapter Setup panel visible
		JButton btnSetChapterFile = new JButton("Step 3: Set Chapter Files");
		btnSetChapterFile.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetChapterFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPSetup.setVisible(false);
				JPChapters.setVisible(true);

			}
		});
		btnSetChapterFile.setBounds(239, 306, 409, 80);
		JPSetup.add(btnSetChapterFile);

		// The Chapter Setup Panel layout and label
		JPChapters.setLayout(null);
		JLabel lblChapters = new JLabel("Chapter Setup");
		lblChapters.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblChapters.setBounds(301, 12, 287, 59);
		JPChapters.add(lblChapters);

		/*
		 * The Set Single Chapter button on the Chapter Setup Panel 
		 * opens up a "txt file only" JFileChooser
		 * which opens the directory set by JFCDirectory above
		 * 
		 * TEMPORAL COUPLING WARNING: This button uses the Directory variable,
		 * which must be previously set by the user with the Set Project Folder button  
		 */
		JButton btnSelectSingleChapter = new JButton("Select a Single Chapter");
		btnSelectSingleChapter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSelectSingleChapter.setBounds(214, 124, 460, 80);
		btnSelectSingleChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPSetup.setVisible(false);
				JFileChooser JFCChapter = new JFileChooser();
				JFCChapter.setBounds(0, 0, 438, 241);
				JFCChapter.setCurrentDirectory(new File(Directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				JFCChapter.setFileFilter(filter);

				if (JFCChapter.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// assign the chosen file path to its address variable
					ChapterAddress = JFCChapter.getSelectedFile().getAbsolutePath();
					// put setup info into the text area of the SetupInfo panel
					textArea.append("Chapter file: " + ChapterAddress + "\n\n");
					JPSetup.setVisible(true);
					JPWelcome.setVisible(false);

				}

				else if (JFCChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					JFCChapter.setVisible(false);
					JPSetup.setVisible(false);
					JPWelcome.setVisible(true);
				}
			}
		});
		JPChapters.add(btnSelectSingleChapter);

		/*
		 * The Set Chapter Queue button uses filechooser to populate an array of
		 * chapters
		 * 
		 * TEMPORAL COUPLING WARNING: This button uses the Directory variable,
		 * which must be previously set by the user with the Set Project Folder button  
		 */
		JButton btnSetChapterQueue = new JButton("Setup Chapter Queue");
		btnSetChapterQueue.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetChapterQueue.setBounds(214, 234, 460, 68);
		btnSetChapterQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TEST THIS CODE
				JPSetup.setVisible(false);
				JFileChooser JFCChapter = new JFileChooser();
				JFCChapter.setBounds(0, 0, 438, 241);
				JFCChapter.setCurrentDirectory(new File(Directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				JFCChapter.setFileFilter(filter);

				if (JFCChapter.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// assign the chosen file path to the Chapter Queue
					ChapterQueue.add(JFCChapter.getSelectedFile().getAbsolutePath());
					// put setup info into the text area of the SetupInfo panel
					textArea.append("Chapter added to Queue: " + ChapterQueue.get(ChapterQueue.size()- 1) + "\n\n");
					JPSetup.setVisible(true);
					JPWelcome.setVisible(false);

				}

				else if (JFCChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					JFCChapter.setVisible(false);
					JPSetup.setVisible(false);
					JPWelcome.setVisible(true);
				}
				
			}
		});
		JPChapters.add(btnSetChapterQueue);

		// The SetupInfo button makes the SetUpInfo sub-panel visible
		JButton btnSetupInfo = new JButton("Your Setup Info");
		btnSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetupInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPInfo.setVisible(true);
				JPSetup.setVisible(false);
			}
		});
		btnSetupInfo.setBounds(239, 398, 409, 74);
		JPSetup.add(btnSetupInfo);

		// The "ok" button on the SetupInfo panel returns to the Welcome panel
		JButton btnpanelSetupOK = new JButton("ok");
		btnpanelSetupOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPInfo.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnpanelSetupOK.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnpanelSetupOK.setBounds(384, 444, 78, 61);
		JPInfo.add(btnpanelSetupOK);

		// all the done buttons make the Welcome panel visible
		JButton btnSetupDone = new JButton("Done");
		btnSetupDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnSetupDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPSetup.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnSetupDone.setBounds(770, 477, 97, 53);
		JPSetup.add(btnSetupDone);

// END OF SETUP CODE; BEGIN RUN CODE
		
		/*
		 * TEMPORAL COUPLING WARNING: This button uses the Directory variable,
		 * which must be previously set by the user with the Set Project Folder button  
		 */

		// the run panel and its label
		JPRun.setLayout(null);
		JPRun.setVisible(false);// default = not visible
		JLabel lblWelcomeToRun = new JLabel("Run Your Project");
		lblWelcomeToRun.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcomeToRun.setBounds(285, 21, 319, 62);
		JPRun.add(lblWelcomeToRun);

		// the Make Mesostics (Longer) button makes the MesosticsLonger sub-panel visible
		JButton btnMakeMesosticsLonger = new JButton("Make Mesostics (Longer)");
		btnMakeMesosticsLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnMakeMesosticsLonger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPMesosticsLonger.setVisible(true);
				JPRun.setVisible(false);
			}
		});
		btnMakeMesosticsLonger.setBounds(154, 187, 581, 62);
		JPRun.add(btnMakeMesosticsLonger);

		// The MakeMesostics (Longer) sub-panel and its label
		JPMesosticsLonger.setLayout(null);
		JLabel lblMakeMesosticsLonger = new JLabel("Make Longer Mesostics");
		lblMakeMesosticsLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMakeMesosticsLonger.setBounds(215, 12, 459, 69);
		JPMesosticsLonger.add(lblMakeMesosticsLonger);

		// The Next Word (Longer) button
		JButton btnNextWordLonger = new JButton("Next Word");
		btnNextWordLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextWordLonger.setBounds(278, 130, 307, 74);
		btnNextWordLonger.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					//instantiate NextWord and call its methods
					NextWord nw = new NextWord(RowAddress, ChapterAddress, Mesostics);
					ArrayList<String[]> output = nw.Item();
					nw.Write(output);
					nw.AdvanceChapterWord(output.get(0)[0]);
					nw.AdvanceMesosticLetter();
				
				// catches redirect user to the setup window
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");

				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");

				} 
			}
		});
		JPMesosticsLonger.add(btnNextWordLonger);

		// The Next Mesostic (Longer) button
		JButton btnNextMesosticLonger = new JButton("Next Mesostic");
		btnNextMesosticLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextMesosticLonger.setBounds(278, 216, 307, 74);
		btnNextMesosticLonger.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					//instantiate NextMesostic and Item() and Advance() methods
					 NextMesostic nm = new NextMesostic(RowAddress, ChapterAddress, Mesostics);
					 ArrayList<String[]> output = nm.Item();
					 nm.AdvanceChapterWord(output.get(0)[0]);
					 
				//catch redirects user to the setup window	 
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} 
			}
		});
		JPMesosticsLonger.add(btnNextMesosticLonger);
	
		// The Next Chapter (Longer) button
		JButton btnNextChapterLonger = new JButton("Next Chapter");
		btnNextChapterLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextChapterLonger.setBounds(278, 302, 307, 74);
		btnNextChapterLonger.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					//instantiate NextChapter and call Item() and Advance() methods
					 NextChapter nc = new NextChapter(RowAddress, ChapterAddress, Mesostics); 
					 nc.Item();
				//redirect user to the setup window	
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();			
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} 			
			}
		});
		JPMesosticsLonger.add(btnNextChapterLonger);
	
		// The All Chapters (Longer) button
		JButton btnAllChaptersLonger = new JButton("All Chapters");
		btnAllChaptersLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnAllChaptersLonger.setBounds(278, 388, 307, 74);
		btnAllChaptersLonger.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO this action requires a collection of chapters to traverse				
				/*try {
					//instantiate AllChapters
					//call Item() and Advance() methods
					
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");

			} */
			}
		});
		JPMesosticsLonger.add(btnAllChaptersLonger);
		
		//The Done (with Mesostics Longer) button 
		JButton btnMesosticLongerDone = new JButton("Done");
		btnMesosticLongerDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnMesosticLongerDone.setBounds(736, 485, 141, 35);
		btnMesosticLongerDone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPMesosticsLonger.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		JPMesosticsLonger.add(btnMesosticLongerDone);
			
		// the Make Mesostics (Shorter) button makes the MesosticsShorter sub-panel visible
		JButton btnMakeMesosticsShorter = new JButton("Make Mesostics (Shorter)");
		btnMakeMesosticsShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnMakeMesosticsShorter.setBounds(154, 104, 581, 62);
		btnMakeMesosticsShorter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPMesosticsShorter.setVisible(true);
				JPRun.setVisible(false);
			}
		});
		JPRun.add(btnMakeMesosticsShorter);
		
		// The Make Mesostics (Shorter) sub-panel and its label
		JPMesosticsShorter.setLayout(null);
		JLabel lblMakeMesosticsShorter = new JLabel("Make Shorter Mesostics");
		lblMakeMesosticsShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMakeMesosticsShorter.setBounds(228, 31, 435, 51);
		JPMesosticsShorter.add(lblMakeMesosticsShorter);

		// The Next Word (Shorter) button
		JButton btnNextWordShorter = new JButton("Next Word");
		btnNextWordShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextWordShorter.setBounds(300, 125, 290, 61);
		btnNextWordShorter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				try {
					//Instantiate NextWord, its Decorator, and call Item() and Advance() methods
					NextWord nw = new NextWord(RowAddress, ChapterAddress, Mesostics);
					NextItemFiltered nwf = new NextWordFiltered(nw, Directory);
					ArrayList<String[]> output = nwf.Item();
					nwf.Write(output); 
				
				// catches redirect user to the setup window			
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Internet connection interrupted" + "\nClose programme and start over");
				}
				
			}
		});
		JPMesosticsShorter.add(btnNextWordShorter);
	
		// The Next Mesostic (Shorter) button
		JButton btnNextMesosticShorter = new JButton("Next Mesostic");
		btnNextMesosticShorter.setBounds(300, 221, 290, 61);
		btnNextMesosticShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextMesosticShorter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					NextWord nw = new NextWord(RowAddress, ChapterAddress, Mesostics);
					NextMesosticFiltered nmf = new NextMesosticFiltered(RowAddress, ChapterAddress, Mesostics, nw);
					ArrayList<String[]> output = nmf.Item();
					nmf.Write(output);
					nmf.AdvanceChapterWord(output.get(output.size()-1)[0]);
					
				// catches redirect user to the setup window	 
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Internet connection interrupted" + "\nClose programme and start over");
				}
			}
		});
		JPMesosticsShorter.add(btnNextMesosticShorter);
		
		// TODO this requires testing
		// The Next Chapter button
		JButton btnNextChapterShorter = new JButton("Next Chapter");
		btnNextChapterShorter.setBounds(300, 313, 290, 61);
		btnNextChapterShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextChapterShorter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					NextWord nw = new NextWord(RowAddress, ChapterAddress, Mesostics);
					NextMesosticFiltered nmf = new NextMesosticFiltered(RowAddress, ChapterAddress, Mesostics, nw);
					NextChapterFiltered ncf = new NextChapterFiltered(NextWord.RowAddress, NextWord.ChapterAddress, 
							NextItemAbstract.Mesostics, nmf); 
					ArrayList<String[]> output = ncf.Item();
					ncf.Write(output);
					ncf.AdvanceChapterWord(output.get(0)[0]);
				
				// catches redirect user to the setup window
				} catch (IOException e1) {
					e1.printStackTrace();				
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Internet connection interrupted" + "\nClose programme and start over");
				}				
			}
		});
		JPMesosticsShorter.add(btnNextChapterShorter);
		
		//TODO requires a collection of chapters
		// The All Chapters button
		JButton btnAllChaptersShorter = new JButton("All Chapters");
		btnAllChaptersShorter.setBounds(300, 404, 290, 61);
		btnAllChaptersShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnAllChaptersShorter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*try {
					//Instantiate AllChaptersFiltered
					//call Item() and Advance() methods
				} catch (IOException e1) {
					e1.printStackTrace();
					// redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Internet connection interrupted" + "\nClose programme and start over");
				}*/
				
				
			}
		});
		JPMesosticsShorter.add(btnAllChaptersShorter);
		

		// The Done (Mesostics Shorter) button 
		JButton btnMesosticsShorterDone = new JButton("Done");
		btnMesosticsShorterDone.setBounds(782, 474, 82, 36);
		btnMesosticsShorterDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnMesosticsShorterDone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPMesosticsShorter.setVisible(false);
				JPWelcome.setVisible(true);
				
			}
		});
		JPMesosticsShorter.add(btnMesosticsShorterDone);
		

		
		// this button calls the getSounds object
		JButton btnGetSounds = new JButton("Get Sounds");
		btnGetSounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Sounds sound = new Sounds(OEDSounds, ChapterAddress, Sounds);
					ArrayList<String[]> outputList = new ArrayList<String[]>();
					outputList = sound.Item();
					sound.Write(outputList);
					
					Sentence sentence = new Sentence(ChapterArray, Sounds, SoundsSentences);
					
					sentence.WriteSentence(); 

				} catch (IOException e1) {
					e1.printStackTrace();
					// redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGetSounds.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetSounds.setBounds(318, 307, 252, 61);
		JPRun.add(btnGetSounds);

		// this button calls the Places object
		JButton btnGetPlaces = new JButton("Get Places");
		btnGetPlaces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Places p = new Places(ChapterAddress, NotPlaces, Places, PlacesSentences);
					Sentence sentence = new Sentence(ChapterArray, Places, PlacesSentences);
					p.PlaceWord();
					sentence.WriteSentence();
				// catch redirects user to the setup window
				} catch (IOException e1) {
					e1.printStackTrace();				
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}

			}
		});
		btnGetPlaces.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetPlaces.setBounds(318, 391, 252, 56);
		JPRun.add(btnGetPlaces);

		// all the done buttons make the welcome panel visible
		JButton btnRunDone = new JButton("Done");
		btnRunDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnRunDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPRun.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnRunDone.setBounds(744, 478, 123, 42);
		JPRun.add(btnRunDone);

		//TODO Finish the finisher objects
	
		// END OF RUN PANELS CODE; BEGINNING OF FINISH PANEL CODE

		// the Finish panel and its label
		JPFinish.setLayout(null);
		JLabel lblWelcomeToFinish = new JLabel("Finish Your Project Files");
		lblWelcomeToFinish.setBounds(224, 65, 442, 51);
		lblWelcomeToFinish.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(lblWelcomeToFinish);

		// this button calls the AdjacentWordAdder object
		JButton btnAddWords = new JButton("Add Adjacent Words");
		btnAddWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mf = new MesosticsFinished (Mesostics, ChapterArray);
					mf.WithAdjacentWords();
				} catch (IOException e1) {
					e1.printStackTrace();
					// redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} 

			}
		});
		btnAddWords.setBounds(240, 181, 409, 61);
		btnAddWords.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(btnAddWords);

		// this button calls the MesosticCentredLines object
		JButton btnCentreMesosticLines = new JButton("Centre Mesostic Lines");
		btnCentreMesosticLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mf = new MesosticsFinished(Mesostics, ChapterArray);
					mf.WithCentredLines();
				
				// catches redirect user to the setup window	
				} catch (IOException e1) {
					e1.printStackTrace();
					
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} 
			}
		});
		btnCentreMesosticLines.setBounds(228, 307, 434, 61);
		btnCentreMesosticLines.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(btnCentreMesosticLines);

		// this button calls the Collation object
		JButton btnCollate = new JButton("Collate Sounds and Places");
		btnCollate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MesosticsFinished mf = new MesosticsFinished (Mesostics, ChapterArray);
				try {
					mf.WithItemsCollated(Sounds);
					mf.WithItemsCollated(Places);
				} catch (IOException e1) {
					// redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
					e1.printStackTrace();
				}

			}
		});
		btnCollate.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnCollate.setBounds(187, 433, 516, 61);
		JPFinish.add(btnCollate);

		// all done buttons return to Welcome panel
		JButton btnFinishDone = new JButton("Done");
		btnFinishDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPFinish.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnFinishDone.setBounds(777, 498, 82, 36);
		btnFinishDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		JPFinish.add(btnFinishDone);

		// END OF FINISH PANEL CODE; BEGINNING OF VIEW PANELS CODE

		/*
		 * The View card has one button that opens up a "txt file only"
		 * filechooser on the directory and opens the selected file with
		 * notepad.exe
		 * 
		 * NOTE: THE LOCATION OF notepad.exe IS HARD CODED HERE THE USER'S
		 * COMPUTER MAY STORE notepad.exe SOMEWHERE ELSE OR THE USER MAY WISH TO
		 * USE A DIFFERENT PROGRAM To OPEN FILES
		 * 
		 */

		// initialize the View panel and its label
		JPView.setLayout(null);
		JPView.setVisible(false);// default = not visible
		JLabel lblWelcomeToView = new JLabel("Open a file in your directory");
		lblWelcomeToView.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcomeToView.setBounds(172, 0, 544, 63);
		JPView.add(lblWelcomeToView);

		/*
		 * The Open button calls a fileChooser which uses notepad.exe to open
		 * the selected file
		 */
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPView.setVisible(false);
				JFileChooser JFCChapter = new JFileChooser();
				JFileChooser fileChooserOpen = new JFileChooser();
				fileChooserOpen.setBounds(0, 0, 438, 241);
				fileChooserOpen.setCurrentDirectory(new File(Directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				fileChooserOpen.setFileFilter(filter);

				if (fileChooserOpen.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					String selected = fileChooserOpen.getSelectedFile().getAbsolutePath();
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("C:\\windows\\system32\\notepad.exe" + " " + selected);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					JPSetup.setVisible(false);

				}

				else if (JFCChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {

					JFCDirectory.setVisible(false);
					JPView.setVisible(false);
					JPWelcome.setVisible(true);

				}
			}
		});
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnOpen.setBounds(339, 160, 159, 93);
		JPView.add(btnOpen);

		// all the done buttons make the welcome panel visible
		JButton btnViewDone = new JButton("Done");
		btnViewDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPView.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnViewDone.setBounds(651, 485, 143, 35);
		JPView.add(btnViewDone);

	}
}
