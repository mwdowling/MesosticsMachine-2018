package mesosticSystemOOP;

/**
 * @author Martin Dowling
 *
 * Within a JFrame lies a navigable hierarchy of JPanel "panels":
 * 
 *		(1) Welcome panel with links to setup, run, and view cards 
 *		(2) Setup panel for choosing the mesostic row, 
 *         	and target chapters (with linked FileChoosers and text fields)
 *		(3) Run panel to call methods in the system's core objects 
 *         	to create mesostics with and without syllable filtering 
 *		(4) View panel to view output files in directory
 * 
 * Please note the following:
 * 
 * 		(1) Prefixes used for the names of panel and filechooser objects: 
 * 			JPxxx = a JPanel object xxx 
 * 			JFCxxx = a JFileChooser xxx
 *         
 * 		(2) There are TEMPORAL COUPLING WARNINGS in the code generated because 
 * 			the user must choose the target chapter(s) and establish the mesostic row 
 *         	on the Setup panel before invoking the functions on the Run panel and its sub-panels. 
 *         
 * 		(3) From the View panel, the location notepad.exe is hard coded in this GUI. 
 * 			If the user of the GUI stores notepad.exe in another location
 * 			or prefers to use another program to open .txt files, this code must be changed
 * 
 * 		(4) This GUI creates a default directory oc C:\mesosticMachine, with subdirectories.
 * 			See the guidance notes for proper setup of the directories.
 * 
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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import javax.swing.JTextArea;

public class MesosticsMachineGUI {

	// the frame within which all panels are placed
	private JFrame frame;

	// the panels in the frame
	private JPanel JPWelcome;// the welcome panel
	
	private JPanel JPSetup;// the setup panel
	//panels behind the setup panel buttons
	private JPanel JPMesosticRow;
	private JPanel JPChapters;
	private JPanel JPInfo;
	
	private JPanel JPRun;// the run panel
	//panels behind the ru panel buttons
	private JPanel JPMesosticsLonger; 
	private JPanel JPMesosticsShorter;
	private JPanel JPFinish;
	private JPanel JPSentence;
	
	private JPanel JPView;// the view panel

	// Default directory for the GUI
	static String Directory = "C:\\MesosticsMachine";

	// Static variables for target chapter(s)
	static String ChapterAddress;
	static String[] ChapterArray;
	static String ChapterArrayIndex = "0";
	private ArrayList<String> ChapterQueue = new ArrayList<String>();

	// Static variables the mesostic row
	static String RowAddress = Directory + "\\Mesotic Row.txt";
	static String[] RowArray;
	static int RowArrayIndex = 0;

	// mesostics output files:
	static String Mesostics = Directory + "\\Mesostics.txt";
	static String MesosticsFinished = Directory + "\\MesosticsFinished.txt";

	// sounds files:
	static String OEDSounds = Directory + "\\Sounds and Places\\OEDSounds Final.txt";
	static String Sounds = Directory + "\\Sounds and Places\\Chapter Sounds.txt";
	static String SoundsSentences = Directory + "\\Sounds and Places\\ChapterSoundsSentences.txt";;

	// places files:
	static String NotPlaces = Directory + "\\Sounds and Places\\Not A Place.txt";
	static String Places = Directory + "\\Sounds and Places\\Chapter Places.txt";
	static String PlacesSentences = Directory + "\\Sounds and Places\\Chapter Places Sentences.txt";

	// text fields for user input of the mesostic row in GUI
	private JTextField RowtextField;
	private JTextField IndexTextField;

	// Application constructor with its huge initialize method (see below)
	public MesosticsMachineGUI() {
		initialize();
	}

	// main method to launch the GUI.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MesosticsMachineGUI window = new MesosticsMachineGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Summary of the initialize() method:
	 * (1) initializes the frame 
	 * (2) initializes the panels for the frame 
	 * (3) places the panels in the frame 
	 * (4) initializes buttons and labels on the panels 
	 * (5) specifies behaviour of ActionListeners behind buttons 
	 * (6) sets the visibility of panels
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
		JPSentence = new JPanel();
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
		frame.getContentPane().add(JPSentence);

		// START OF WELCOME PANEL CODE

		// Welcome panel layout, label and buttons
		JPWelcome.setLayout(null);
		JPWelcome.setVisible(true);// default state has this panel visible
		JLabel lblWelcome = new JLabel("Welcome to the MesosticMachine");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcome.setBounds(133, 12, 638, 64);
		JPWelcome.add(lblWelcome);

		// Setup button on Welcome panel makes Setup panel visible
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

		// Run button on Welcome panel makes Run panel visible
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

		// View button on Welcome panel makes View panel visible
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

		// Finish button on Welcome panel makes Finish panel visible
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

		// END OF WELCOME PANEL CODE
		// START OF SETUP PANEL CODE

		/*
		 * The Setup panel assigns the mesostic row and the target chapter file
		 * These assignments are required for the Run and Finish panel functions
		 */

		// Setup panel layout, label, and buttons
		JPSetup.setLayout(null);
		JPSetup.setVisible(false);// default = not visible
		JLabel lblSetup = new JLabel("Setup Your Project");
		lblSetup.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetup.setBounds(268, 21, 353, 56);
		JPSetup.add(lblSetup);

		// SetupInfo sub-panel layout and label
		JPInfo.setLayout(null);
		JLabel lblSetupInfo = new JLabel("Your Setup Info");
		lblSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetupInfo.setBounds(278, 51, 291, 51);
		JPInfo.add(lblSetupInfo);

		// Text area in the the SetupInfo panel
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textArea.setRows(3);
		textArea.setBounds(51, 160, 787, 234);
		JPInfo.add(textArea);
		textArea.setEditable(false);

		// Set Mesostic Row Button makes SetMesosticRow panel visible
		JButton btnSetMesosticRow = new JButton("Set Mesostic Row");
		btnSetMesosticRow.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetMesosticRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPMesosticRow.setVisible(true);
				JPSetup.setVisible(false);
			}
		});
		btnSetMesosticRow.setBounds(244, 116, 400, 80);
		JPSetup.add(btnSetMesosticRow);

		// SetMesosticRow panel and its label
		JPMesosticRow.setLayout(null);
		JPMesosticRow.setVisible(false);// default = not visible
		JLabel lblTypeYourMesostic = new JLabel("Type your mesostic row in the box:");
		lblTypeYourMesostic.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTypeYourMesostic.setBounds(88, 84, 712, 59);
		JPMesosticRow.add(lblTypeYourMesostic);

		// Text field for entering the mesostic row
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
		 * 
		 * Then, calling the RepositoryWriter() method of a Repository object:
		 * (3) Creates a syllable repository file for each letter of the row
		 * adding an identifying string to the first line of each file
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
					out.print(Row);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				//TODO Add a warning here. This will overwrite existing repositories.
				// create a syllable repository for each letter in the row
				new SyllableRepository(Row, Directory).RepositoryWriter();

				// return to Welcome panel
				JPMesosticRow.setVisible(false);
				JPSetup.setVisible(true);
				JPWelcome.setVisible(false);

			}
		});
		btnMesosticRowSubmit.setBounds(352, 312, 183, 59);
		JPMesosticRow.add(btnMesosticRowSubmit);

		// Set Chapter Files button makes Chapter Setup panel visible
		JButton btnSetChapterFile = new JButton("Set Chapter Files");
		btnSetChapterFile.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetChapterFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPSetup.setVisible(false);
				JPChapters.setVisible(true);

			}
		});
		btnSetChapterFile.setBounds(244, 256, 400, 80);
		JPSetup.add(btnSetChapterFile);

		// Chapter Setup Panel layout and label
		JPChapters.setLayout(null);
		JLabel lblChapters = new JLabel("Chapter Setup");
		lblChapters.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblChapters.setBounds(301, 12, 287, 59);
		JPChapters.add(lblChapters);

		/*
		 * Set Single Chapter button on Chapter Setup Panel opens up a
		 * "txt file only" JFileChooser which opens the default directory
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
					JPChapters.setVisible(false);
				}

				else if (JFCChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					JFCChapter.setVisible(false);
					JPChapters.setVisible(false);
					JPSetup.setVisible(false);
					JPWelcome.setVisible(true);
				}
			}
		});
		JPChapters.add(btnSelectSingleChapter);

		/*
		 * Set Chapter Queue button uses a Filechooser to populate an array of
		 * chapters
		 */
		JButton btnSetChapterQueue = new JButton("Setup Chapter Queue");
		btnSetChapterQueue.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetChapterQueue.setBounds(214, 234, 460, 68);
		btnSetChapterQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				JPSetup.setVisible(false);
				JFileChooser JFCChapter = new JFileChooser();
				JFCChapter.setBounds(0, 0, 438, 241);
				JFCChapter.setCurrentDirectory(new File(Directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				JFCChapter.setFileFilter(filter);

				if (JFCChapter.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// assign the chosen file path to the Chapter Queue
					ChapterQueue.add(JFCChapter.getSelectedFile().getAbsolutePath());
					System.out.println(ChapterQueue.get(0));
					// put setup info into the text area of the SetupInfo panel
					textArea.append("Chapter added to Queue: " + ChapterQueue.get(ChapterQueue.size() - 1) + "\n\n");
					JPSetup.setVisible(true);
					JPWelcome.setVisible(false);
					JPChapters.setVisible(false);
				} else if (JFCChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					JFCChapter.setVisible(false);
					JPSetup.setVisible(false);
					JPWelcome.setVisible(true);
					JPChapters.setVisible(false);
				}
			}
		});
		JPChapters.add(btnSetChapterQueue);

		// SetupInfo button makes SetUpInfo sub-panel visible
		JButton btnSetupInfo = new JButton("Your Setup Info");
		btnSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetupInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPInfo.setVisible(true);
				JPSetup.setVisible(false);
			}
		});
		btnSetupInfo.setBounds(244, 395, 400, 74);
		JPSetup.add(btnSetupInfo);

		// "ok" button on SetupInfo panel returns to the Welcome panel
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

		// all the done buttons make Welcome panel visible
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

		// run panel and its label
		JPRun.setLayout(null);
		JPRun.setVisible(false);// default = not visible
		JLabel lblWelcomeToRun = new JLabel("Run Your Project");
		lblWelcomeToRun.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcomeToRun.setBounds(285, 21, 319, 62);
		JPRun.add(lblWelcomeToRun);

		// Make Mesostics (Longer) button makes MesosticsLonger sub-panel visible
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

		// MakeMesostics (Longer) sub-panel and its label
		JPMesosticsLonger.setLayout(null);
		JLabel lblMakeMesosticsLonger = new JLabel("Make Longer Mesostics");
		lblMakeMesosticsLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMakeMesosticsLonger.setBounds(215, 12, 459, 69);
		JPMesosticsLonger.add(lblMakeMesosticsLonger);

		// Next Word (Longer) button
		JButton btnNextWordLonger = new JButton("Next Word");
		btnNextWordLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextWordLonger.setBounds(278, 130, 307, 74);
		btnNextWordLonger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					// write the next word to mesostics file and advance the indexes
					Word nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
					ArrayList<String[]> output = nextWord.NextItem();
					nextWord.Write(output);
					ChapterArrayIndex = nextWord.AdvanceChapterWord(output.get(0)[0]);
					RowArrayIndex = nextWord.AdvanceMesosticLetter();

				// catches redirect user to the setup window
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}
			}
		});
		JPMesosticsLonger.add(btnNextWordLonger);

		// Next Mesostic (Longer) button
		JButton btnNextMesosticLonger = new JButton("Next Mesostic");
		btnNextMesosticLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextMesosticLonger.setBounds(278, 216, 307, 74);
		btnNextMesosticLonger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					//write the next whole mesostic to the mesotics file and advance the chapter index
					Mesostic nextMesostic = new Mesostic(RowAddress, ChapterAddress, Mesostics);
					ArrayList<String[]> output = nextMesostic.NextItem();
					System.out.println("Output length is: " + output.size());
					nextMesostic.Write(output);
					ChapterArrayIndex = nextMesostic
							.AdvanceChapterWord(output.get(new Integer(RowArray.length).intValue() - 1)[0]);

				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
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
					/*
					 * Write all the mesostics in a chapter to file, 
					 * stopping 100 words before the end of the chapter 
					 */
					Chapter nc = new Chapter(RowAddress, ChapterAddress, Mesostics);
					ArrayList<String[]> output = nc.NextItem();
					nc.Write(output);

				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}
			}
		});
		JPMesosticsLonger.add(btnNextChapterLonger);

		// The Multiple Chapters (Longer) button
		JButton btnAllChaptersLonger = new JButton("Multiple Chapters");
		btnAllChaptersLonger.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnAllChaptersLonger.setBounds(278, 388, 307, 74);
		btnAllChaptersLonger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(ChapterQueue.get(0));
					for (String chapter : ChapterQueue) {
						ChapterAddress = chapter;
						Chapter nc = new Chapter(RowAddress, ChapterAddress, Mesostics);
						ArrayList<String[]> output = nc.NextItem();
						nc.Write(output);
						
						/*
						 * TODO Assign ChapterArrayIndex = ChapterArray.length
						 * here to give one continuous index for entire book?
						 */
					}

				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}
			}
		});
		JPMesosticsLonger.add(btnAllChaptersLonger);

		// Done (with Mesostics Longer) button
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

		// Make Mesostics (Shorter) button makes MesosticsShorter sub-panel visible
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

		// Make Mesostics (Shorter) sub-panel and its label
		JPMesosticsShorter.setLayout(null);
		JLabel lblMakeMesosticsShorter = new JLabel("Make Shorter Mesostics");
		lblMakeMesosticsShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMakeMesosticsShorter.setBounds(228, 31, 435, 51);
		JPMesosticsShorter.add(lblMakeMesosticsShorter);

		// Next Word (Shorter) button
		JButton btnNextWordShorter = new JButton("Next Word");
		btnNextWordShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextWordShorter.setBounds(300, 125, 290, 61);
		btnNextWordShorter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					// As above but "decorated" with a syllable filter
					Word nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
					ItemFiltered nextWordFiltered = new WordFiltered(nextWord, Directory);
					ArrayList<String[]> output = nextWordFiltered.NextItem();
					nextWordFiltered.Write(output);
					ChapterArrayIndex = nextWordFiltered.AdvanceChapterWord(output.get(0)[0]);
					if (output.get(0)[1] != "don't write it") {
						RowArrayIndex = nextWord.AdvanceMesosticLetter();
					}

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
					//As above but "decorated" with a syllable filter
					Item nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
					MesosticFiltered nextMesosticFiltered = new MesosticFiltered(RowAddress, ChapterAddress, Mesostics, nextWord);
					ArrayList<String[]> outputList = nextMesosticFiltered.NextItem();
					nextMesosticFiltered.Write(outputList);
					ChapterArrayIndex = nextMesosticFiltered.AdvanceChapterWord(outputList.get(new Integer(RowArray.length).intValue() - 1)[0]);

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

		// The Next Chapter (Shorter) button
		JButton btnNextChapterShorter = new JButton("Next Chapter");
		btnNextChapterShorter.setBounds(300, 313, 290, 61);
		btnNextChapterShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnNextChapterShorter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
						//As above but "decorated" with a syllable filter
						Item nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
						ChapterFiltered nextChapterFiltered = new ChapterFiltered(RowAddress, ChapterAddress, Mesostics, nextWord);
						nextChapterFiltered.NextItem();				
						
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

		// Multiple Chapters button
		JButton btnAllChaptersShorter = new JButton("All Chapters");
		btnAllChaptersShorter.setBounds(300, 404, 290, 61);
		btnAllChaptersShorter.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnAllChaptersShorter.addActionListener(new ActionListener() {

	@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"This function not operational." + "\nCurrent functioning of webdriver is too slow and fragile."
								+ "\nBetter to make one chapter at a time.");
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

		// this button calls the Sounds object
		JButton btnGetSounds = new JButton("Get Sounds");
		btnGetSounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					Sounds sound = new Sounds(OEDSounds, ChapterAddress, Sounds);
					ArrayList<String[]> outputList = new ArrayList<String[]>();
					outputList = sound.NextItem();
					sound.Write(outputList);

				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
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
					Places place = new Places(ChapterAddress, NotPlaces, Places);
					ArrayList<String[]> outputList = new ArrayList<String[]>();
					outputList = place.NextItem();
					place.Write(outputList);

					// catch redirects user to the setup window
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
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

		// END OF RUN PANELS CODE; BEGINNING OF FINISH PANEL CODE

		// the Finish panel and its label
		JPFinish.setLayout(null);
		JLabel lblWelcomeToFinish = new JLabel("Finish Mesostic File");
		lblWelcomeToFinish.setBounds(262, 32, 365, 51);
		lblWelcomeToFinish.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(lblWelcomeToFinish);

		// this button calls the AdjacentWordAdder object
		JButton btnAddWords = new JButton("Add Adjacent Words");
		btnAddWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mesosticsFinished = new MesosticsFinished(Mesostics, MesosticsFinished, ChapterAddress);
					mesosticsFinished.WithAdjacentWords();
				} catch (IOException e1) {
					e1.printStackTrace();
					// redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}

			}
		});
		btnAddWords.setBounds(22, 108, 409, 61);
		btnAddWords.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(btnAddWords);

		// this button calls the MesosticCentredLines object
		JButton btnCentreMesosticLines = new JButton("Centre Lines");
		btnCentreMesosticLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mesosticsFinished = new MesosticsFinished(Mesostics, MesosticsFinished, ChapterAddress);
					mesosticsFinished.WithCentredLines();

					// catches redirect user to the setup window
				} catch (IOException e1) {
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}
			}
		});
		btnCentreMesosticLines.setBounds(22, 181, 271, 61);
		btnCentreMesosticLines.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPFinish.add(btnCentreMesosticLines);

		// this button calls the Collation object for sounds
		JButton btnCollateSounds = new JButton("Collate Sounds");
		btnCollateSounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mesosticsFinished = new MesosticsFinished(Mesostics, MesosticsFinished, ChapterAddress);
					mesosticsFinished.WithItemsCollated(Sounds);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
					e1.printStackTrace();
				}
			}
		});
		btnCollateSounds.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnCollateSounds.setBounds(555, 181, 307, 61);
		JPFinish.add(btnCollateSounds);

		// this button calls the Collation object for places
		JButton btnCollatePlaces = new JButton("Collate Places");
		btnCollatePlaces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticsFinished mesosticsFinished = new MesosticsFinished(Mesostics, MesosticsFinished, ChapterAddress);
					mesosticsFinished.WithItemsCollated(Places);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
					e1.printStackTrace();
				}
			}
		});
		btnCollatePlaces.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnCollatePlaces.setBounds(555, 108, 307, 61);
		JPFinish.add(btnCollatePlaces);

		// this button opens the Sentence subpanel
		JButton btnGetSentence = new JButton("Get a Sentence");
		btnGetSentence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPSentence.setVisible(true);
				JPFinish.setVisible(false);
			}
		});
		btnGetSentence.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetSentence.setBounds(266, 282, 358, 61);
		JPFinish.add(btnGetSentence);

		// the Sentence subpanel and its label
		JPSentence.setLayout(null);
		JLabel lblTypeIndexOf = new JLabel("Type index of target word in the box:");
		lblTypeIndexOf.setBounds(76, 74, 693, 51);
		lblTypeIndexOf.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPSentence.add(lblTypeIndexOf);

		// the textField for the Sentence subpanel
		IndexTextField = new JTextField();
		IndexTextField.setBounds(334, 149, 94, 43);
		IndexTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		IndexTextField.setColumns(10);
		JPSentence.add(IndexTextField);

		// the submit button runs a Sentence object
		JButton btnWordIndexSubmit = new JButton("submit");
		btnWordIndexSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int input = 0;

				// save input string as integer
				try {
					input = Integer.parseInt(IndexTextField.getText());
				} catch (NumberFormatException e1) {
					System.out.println("Not a number");
					JOptionPane.showMessageDialog(null, "Please enter a number.");
					e1.printStackTrace();
				}

				// Return the sentence for the input index
				try {
					Sentence s = new Sentence(input, Mesostics, ChapterAddress);
					ArrayList<String[]> outputList = new ArrayList<String[]>();
					outputList = s.NextItem();
					s.Write(outputList);
					JOptionPane.showMessageDialog(null, s.ReturnSentence(outputList));

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "An error has occurred." + "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
					e1.printStackTrace();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}

				// return to Finish panel
				JPSentence.setVisible(false);
				JPFinish.setVisible(true);
			}
		});
		btnWordIndexSubmit.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWordIndexSubmit.setBounds(303, 240, 183, 59);
		JPSentence.add(btnWordIndexSubmit);
		frame.getContentPane().add(JPView);

		/*
		 * The Get Sentences button writes all the sentences for indexed words
		 * in sounds and places files
		 */
		JButton btnGetSentences = new JButton("Get All Sentences");
		btnGetSentences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Sentences sentencesOfPlaces = new Sentences(Places, ChapterAddress, PlacesSentences);
					sentencesOfPlaces.Write();
					Sentences sentencesOfSounds = new Sentences(Sounds, ChapterAddress, SoundsSentences);
					sentencesOfSounds.Write();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnGetSentences.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetSentences.setBounds(266, 364, 358, 61);
		JPFinish.add(btnGetSentences);

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
