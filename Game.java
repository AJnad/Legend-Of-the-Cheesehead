// Ajay Nadhavajhala
// April - May 2013
// Game.java
// Mr.Dong
// This is the final program of the year. This game is a variation of the beloved arcade game
// Pacman! But this time, you must embark on a journey to find the long lost city of Chesselantis
// which is a city made of cheese. But you must go past the Guardians who protect Cheeselantis
// and you must deactivate the traps. When deactivating the traps, you will come across a question
// and you must be able to answer it properly. If you do, you will successfully deactivate the trap
// and you can continue to deactivate the others. But if you do not answer the question correctly, the trap
// will go off and you will die. When you beat the game, you will type in your name so you become the legend
// of who defeated the monsters and the first to reach Cheeselantis. Keep in mind the monsters can attack you!
// You can only die if they come in contact with you, not if you come in contact with them. You need to
// answer 5 questions in a row to beat this game.


import java.awt.*; // graphics
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner; // to scan the text files
import java.io.FileNotFoundException; // import this class if the file is not found
import java.io.*;
import java.io.File; // the files and images 
import java.io.PrintWriter; // you want to print out the text from the file
import java.io.IOException;

public class Game extends JFrame implements FocusListener   {
	private Image image, instructions, endim, backim, earthiscrushed, winimage, topbar, rocket, legback, ghost, ghost2, ghost3, ghost4, ghost5, ghost6, ghost7, ghost8; // consists of the main screen image, 
        // the instructions image, background image, the "END" image, as well as the monsters and pacman himself
        
        private Image asteroid; // picture of the trap
	private int index, max;
	private JTextField legendname; // the name of the previous person who beat the game
	private IntroPanel introp; // this is the panel of where you see the home screen
	private JPanel panel1, panel2, panel3; // start panel, instructions, previous legend
	private File outname; // name of the file you are trying to catch
	private PrintWriter out;
	private String lname; // name of the legend
	private GamePanel gme; // where the game takes place
	private String legname;
	private QuestionPanel1 preguntar1; // question panel 1
	private QuestionPanel2 preguntar2;
	private QuestionPanel3 preguntar3;
	private QuestionPanel4 preguntar4;
	private QuestionPanel5 preguntar5;
	private WinPanel ganar; // this panel shows up when you win
	private EndPanel terminar; // this panel shows up if you die
	private CardLayout cards; // using card layout
	private Container c, i; // needed for the cardlayout
	private String firstname, lastname, str;
	private int agenum;
	private Font font1, font2;
	private File questionfile, legendfile; // calling the question file which consists of all the questions
        // legend file is a text file which has the name of the person who beat the game before
	private File answerfile; // all the answers to the question file
	private File qa, qb, qc, qd, correctanswer; // qa, qb, qc, qd, are all the button choices...A B C D
	private boolean drawIm;
	private int lifeleft;
	private String [] quest; // the questions
	private int [] answ; // the answers to the question
	private String [] choicea; // the option you have for choice A
	private String [] choiceb; // the option you have for choice B
	private String [] choicec;// the option you have for choice C
	private String [] choiced;// the option you have for choice D
	private String [] leg;
	//private JButton starttimer;
	//private JButton legends;
	//private boolean startgame;
	private int rando1, rando2, rando3, rando4, rando5; // random question from the text files question1,2,3,4,5
	private int count;
        private int movecount; // Int that tells us the number of moves the user has made.
	private int questionsAnswered; // you must answer 5 questions correctly to move on.
	private JLabel equation; // shows the math problems on the screen
	private int answernumber;
	private int grid[][];
	private boolean correctans;
	private boolean rules, startime;
	private Image dibujo; // draw the image
	private Image radarim; // gif. for the radar
	private boolean failgame;
	private boolean showQuestion1, showQuestion2, showQuestion3, showQuestion4, showQuestion5;
	private int q1x, q1y, q2x, q2y, q3x, q3y, q4x, q4y, q5x, q5y, g1x, g1y, g2x, g2y, g3x, g3y, g4x, g4y, g5x, g5y, g6x, g6y, g7x, g7y, g8x, g8y;	// These integers represent the x and y coordinates of the asteroids.
	
	public static void main ( String[] args )   { // main method where you call the methods in order to run the game
		new Game( );
	}

	public Game( )   {
		super( "The Legend of Pacman" ); // head title on the JPanel
		questionfile = new File ( "questions.txt" ); // text file that contains all of the questions
		answerfile = new File ("correctanswer.txt"); // text file that contains all of the correct answers to the questions
		legendfile = new File ("legends.txt"); // text file that contains the name of the latest legend who beat the game
		qa = new File ("qa.txt"); // all of the options you have for A
		qb = new File ("qb.txt"); // all of the options you have for B
		qc = new File ("qc.txt");// all of the options you have for C
		qd = new File ("qd.txt");// all of the options you have for D
		outname = new File ( "legends.txt" ); // name of the legend
		out = null;
		index = 0;
		startime = true; // beginning of the timer
		correctans = false; // how many questions the user has answered properly
		failgame = false;
		count = 0;
		max = 3;
		lifeleft = 0;
		movecount = 0;
		questionsAnswered = 0; // how many questions has the user gotten to
		grid = new int [20][20]; // how big the grid is
		quest = new String[100];
		answ = new int[100]; // the answers and questions
		choicea = new String[100]; // choice you have for the A button
		leg = new String[20];
		choiceb = new String[100]; // choices you have for the B button
		choicec = new String[100];// choices you have for the C button
		choiced = new String[100];// choices you have for the D button
				
		Scanner fromfile = null;							
		String legends; // name of the text file you are trying to catch
		try   {
			fromfile = new Scanner ( legendfile );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1); // print line if the program cannot find the program
		}
		legends = fromfile.nextLine(); // print the name in the text fiel
		legname = legends;
		Scanner fromfile1 = null;							// Gets the questions from the textfile
		String questions;
		try   {
			fromfile1 = new Scanner ( questionfile );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int a = 1; fromfile1.hasNext() && a <100; a++) // 100 questions	
                {
			questions = fromfile1.nextLine();
			quest[a] = questions;
		}
		Scanner fromfile2 = null;
		int answers;
		try   {
			fromfile2 = new Scanner ( answerfile ); // text file that has all the answers
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int b = 1; b < 100; b++)	{
			answers = fromfile2.nextInt();
			answ[b] = answers;
		}
		Scanner fromfile3 = null;
		String ans1;
		try   {
			fromfile3 = new Scanner ( qa );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int c = 1; fromfile3.hasNext() && c < 100; c++)	{
			ans1 = fromfile3.nextLine();
			choicea[c] = ans1;
		}
		Scanner fromfile4 = null;
		String ans2;
		try   {
			fromfile4 = new Scanner ( qb );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int d = 1; fromfile4.hasNext() && d < 100; d++)	{
			ans2 = fromfile4.nextLine();
			choiceb[d] = ans2;
		}
		Scanner fromfile5 = null;
		String ans3;
		try   {
			fromfile5 = new Scanner ( qc );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int e = 1; e < 100; e++)	{
			ans3 = fromfile5.nextLine();
			choicec[e] = ans3;
		}
		Scanner fromfile6 = null;
		String ans4;
		try   {
			fromfile6 = new Scanner ( qd );
		}
		catch ( FileNotFoundException e )   {
			System.exit(1);
		}
		for (int f = 1; fromfile6.hasNext() && f < 100; f++)	{
			ans4 = fromfile6.nextLine();
			choiced[f] = ans4;
		}
		
		q1x = (int)(Math.random() *19+1); // x and y position of the asteroid
                q1y = (int)(Math.random() *19+1);
		do	{
			q2x = (int)(Math.random() *19+1); 
			q2y = (int)(Math.random() *19+1);
		} while (q1x == q2x && q2x == q2y);

		do	{
			q3x = (int)(Math.random() *19+1); 
			q3y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q2x == q2y) && (q3x == q2x && q3y == q2y)); // it will do this loop until two asteroid do not overlap each other

		do	{
			q4x = (int)(Math.random() *19+1);
			q4y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q2x == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y));

		do	{
			q5x = (int)(Math.random() *19+1);
			q5y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q2x == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y));


		rando1 = (int)(Math.random() * 99 + 1);
		do	{
			rando2 = (int)(Math.random() * 99 + 1);
		}while(rando2 == rando1 );
		do	{
			rando3 = (int)(Math.random() * 99 + 1);
		}while(rando3==rando2 && rando3==rando1);
		do	{
			rando4 = (int)(Math.random() * 99 + 1);
		}while(rando4==rando3 && rando4==rando2 && rando4==rando1);
		do	{
			rando5 = (int)(Math.random() * 99 + 1);
		}while(rando5==rando4 && rando5==rando3 && rando5==rando2 && rando5==rando1);
                
                                do	{
			g1x = (int)(Math.random() *19+1); // it will make sure that two monsters do not overlap each other
			g1y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y));
                do	{
			g2x = (int)(Math.random() *19+1);
			g2y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y));
                do	{
			g3x = (int)(Math.random() *19+1);
			g3y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y));
                do	{
			g4x = (int)(Math.random() *19+1);
			g4y = (int)(Math.random() *19+1);
		} while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));
                
                   do	{
			g4x = (int)(Math.random() *19+1);
			g4y = (int)(Math.random() *19+1);
		}while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));
		
		          do{
                       g5x = (int) (Math.random() * 19 + 1);
                       g5y = (int) (Math.random() * 19 + 1);
                   }while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));
                
                   
                                      do{
                       g6x = (int) (Math.random() * 19 + 1);
                       g6y = (int) (Math.random() * 19 + 1);
                   }while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));
                         
                                        do{
                       g6x = (int) (Math.random() * 19 + 1);
                       g6y = (int) (Math.random() * 19 + 1);
                   }while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));
                     
                                 do{
                       g7x = (int) (Math.random() * 19 + 1);
                       g7y = (int) (Math.random() * 19 + 1);
                   }while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));                          
                                        
                  do{
                       g8x = (int) (Math.random() * 19 + 1);
                       g8y = (int) (Math.random() * 19 + 1);
                   }while ((q1x == q2x && q1y == q2y) && (q3x == q2x && q3y == q2y) && (q4x == q3x && q4y == q3y) && (q5x == q4x && q5y == q4y) && (g1x == q5x && g1y == q5y) && (g2x == g1x) && (g2y == g1y) && (g3x == g2x) && (g3y == g2y) && (g4x == g3x) && (g4y == g3y));    
                   
                   
                   
                   
                c = this.getContentPane();
		c.addFocusListener(this);
		rando1 = (int)(Math.random() * 8 + 1);
		introp = new IntroPanel(); // introductory panel
		gme = new GamePanel();
		preguntar1 = new QuestionPanel1(); // question panel 1
		preguntar2 = new QuestionPanel2(); // question panel 2
		preguntar3 = new QuestionPanel3(); // question panel 3
		preguntar4 = new QuestionPanel4(); // question panel 4
		preguntar5 = new QuestionPanel5(); // question panel 5
		ganar = new WinPanel(); // this panel is shown if you answer all the questions properly
		terminar = new EndPanel(); // this panel is shown if you lose
		cards = new CardLayout(); // using the card layout
		c.setLayout (cards);
		c.add(introp,"FIRST");
		c.add(gme, "GAME");
		c.add(preguntar1, "QUESTION1");
		c.add(preguntar2, "QUESTION2");
		c.add(preguntar3, "QUESTION3");
		c.add(preguntar4, "QUESTION4");
		c.add(preguntar5, "QUESTION5");
		c.add(ganar, "WIN");
		c.add(terminar, "FINISH");
		setDefaultCloseOperation ( DISPOSE_ON_CLOSE );      
		setLocation ( 20, 50 );
		setSize ( 635, 650 ); // how big the JFrame is
		setVisible(true);
	}

	class IntroPanel extends JPanel  {
		JLabel intro, title;
		JTextField introuse, fname, lname;
		JSlider slideage;
		JButton gotonext;
		private boolean showLegend;
		private PicturePanel pict;
		private Font font1;
		private ButtonsPanel button;
		private Image fire;
		boolean drawstring;

		public IntroPanel(){
			font1 = new Font("Serif", Font.BOLD + Font.ITALIC, 40); // size and font of the text
			fire = Toolkit.getDefaultToolkit().getImage("fire.gif");
			image = Toolkit.getDefaultToolkit().getImage("atlantis.jpg");
			legback = Toolkit.getDefaultToolkit().getImage("legend.jpg");
			
			instructions = Toolkit.getDefaultToolkit().getImage("instructions.JPG");
                        
                        ghost = Toolkit.getDefaultToolkit().getImage("ghost.gif");                      // red ghost picture
                        ghost2 = Toolkit.getDefaultToolkit().getImage("ghost2.gif");    // pink monster
                        ghost3 = Toolkit.getDefaultToolkit().getImage("ghost3.gif");   // yellow monster
                        ghost4 = Toolkit.getDefaultToolkit().getImage("ghost4.gif");  // pink monster
                        
			pict = new PicturePanel();
			button = new ButtonsPanel(); // button panel which has the 3 buttons

			setBackground(Color.black);
			setLayout(new BorderLayout());
			add(pict, BorderLayout.CENTER);
			add(button, BorderLayout.SOUTH);
		}
		public void WaitForImage ( JApplet component, Image image )   {
			MediaTracker tracker = new MediaTracker ( component );
			try  {
				tracker.addImage ( image, 0 );
				tracker.waitForID ( 0 );
			}
			catch ( InterruptedException e )   {
				e.printStackTrace ( );
			}
		}
		class PicturePanel extends JPanel	{
			public PicturePanel()	{
			}
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(image, 0, 0, this); 
				if(rules == true)	{
					g.drawImage(instructions, 0 , 0, this); // instructions panel
				}
				if(showLegend==true)	{
					g.drawImage(legback, 0, 0, this); // picture of LEGEND
					g.drawImage(fire, 200 , 300, this);
					g.setFont(font1);
					g.setColor(Color.white); // the color of the text will be white
					g.drawString(legname, 250 , 500);
				}
			}
		}
		class ButtonsPanel extends JPanel implements ActionListener	{
			public ButtonsPanel()	{
                                setBackground(Color.ORANGE);
				JButton Start = new JButton("START"); // start button so the user can play the game
                              //    button.setBackground(Color.BLACK);
				Start.addActionListener ( this );
				add(Start);
				JButton Instructions = new JButton("INSTRUCTIONS"); // instructions button which has the instructions of how to play the game
				Instructions.addActionListener ( this );
				add(Instructions);
				JButton legends = new JButton("previous LEGEND"); // if you click on this panel, it will show you the previous person who beat the game
				legends.addActionListener ( this ); // you are adding this to the program
				add(legends);
			}
			public void actionPerformed(ActionEvent evt){
				String command = evt.getActionCommand();
				if ( command.equals ( "INSTRUCTIONS" ) )	{
	 	        	rules = true;
	 	        	showLegend = false;
	 	        	pict.repaint();
				}
				if ( command.equals ( "START" ) )	// if you press on the start button, it will start the game
                                {		
					cards.next(c);
					rules = false;
					showLegend = false;
					startime = rules;
				}
				if ( command.equals ( "previous LEGEND" ) )	{
	 	        	showLegend = true;
	 	        	rules = false;
	 	        	pict.repaint();
				}
			}
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	class GamePanel extends JPanel 	{
		private LifePanel life;
		private int hiddenx, hiddeny, userx, usery;
		private RadarPanel radar;
		private Image lives;
		private int move;
		private int boxheight;
		private Timer timer;
		private boolean showGrid;
		private int bcount;
		private int astrand;
		public GamePanel ()	{
			move = 1;			
			userx = 10;
			usery = 10;
			bcount = movecount;
			astrand = (int)(Math.random() * 2 +1);
			showGrid = false;
			life = new LifePanel();
			radar = new RadarPanel();
			setBackground(Color.darkGray);
			setLayout(new BorderLayout());
			add(life, BorderLayout.NORTH);
			add(radar, BorderLayout.CENTER);
			radarim = Toolkit.getDefaultToolkit().getImage("radar.gif");			//radar picture
			topbar = Toolkit.getDefaultToolkit().getImage("topbar.gif");			// the gif for the topbar
			backim = Toolkit.getDefaultToolkit().getImage("backim.jpg");			// background image(stars)
			rocket = Toolkit.getDefaultToolkit().getImage("pacman.png");			// the main gamepiece 
			asteroid = Toolkit.getDefaultToolkit().getImage("asteroid.gif");		// the asteroids that are located in the radar
                        ghost = Toolkit.getDefaultToolkit().getImage("ghost.gif");                      // ghost picture
                        
                        ghost5 = Toolkit.getDefaultToolkit().getImage("ghost.gif");
                        ghost6 = Toolkit.getDefaultToolkit().getImage("ghost2.gif");   
                        ghost7 = Toolkit.getDefaultToolkit().getImage("ghost3.gif");  
                        ghost8 = Toolkit.getDefaultToolkit().getImage("ghost4.gif");
                        
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(backim, 0, 0, this);
		}
		class LifePanel extends JPanel	{
			public LifePanel()	{
			}
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.black);
			}
		}

		class RadarPanel extends JPanel	implements KeyListener, FocusListener, MouseListener, ActionListener {
			private int [][] piecegrid = new int[20][20];
			private int [][] meteorgrid = new int[20][20];
			public RadarPanel()	{
				addFocusListener(this);
				addKeyListener(this);
				addMouseListener(this);	
				boxheight = 400;
				if (timer == null) {
					timer = new Timer ( 750, this );// This is the place where i can adjust how long the timer is
					timer.start ( );						
				}
			}
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				requestFocus();
				g.drawImage(backim, 0, 0, this);
				g.drawImage(topbar, 70, 5, this);
				g.drawImage(radarim, 500, 20, this);				// draws the radar image
				g.setColor ( Color.white );
				g.fillRect ( 65, 15, 410, 50 );
				g.setColor ( Color.blue );
				g.fillRect ( 70, 20, boxheight, 40 );
				g.setColor(Color.red);
				for ( int row = 1; row <= 20; row++ )
					for ( int col = 1; col <= 20; col++ )
						g.drawRect ( 20 * col + 48, 20 * row + 48, 23, 23 );
				g.setColor(Color.black);								//	Draws the grid here.
				for ( int row = 1; row <= 20; row++ )
					for ( int col = 1; col <= 20; col++ )
						g.fillRect ( 20 * col + 50, 20 * row + 50, 20, 20 );
				g.setColor ( Color.orange );								//	Draws the space shuttle.			
				g.drawImage(rocket, 20 * userx + 50, 20 * usery + 50, this);
				if (movecount%1 == 0)	{
					g.drawImage(asteroid, 20 * q1x +50, 20 * q1y + 50, this);		// draws the asteroids
					g.drawImage(asteroid, 20 * q2x +50, 20 * q2y + 50, this);               // there are 5 asteroids
					g.drawImage(asteroid, 20 * q3x +50, 20 * q3y + 50, this);
					g.drawImage(asteroid, 20 * q4x +50, 20 * q4y + 50, this);
					g.drawImage(asteroid, 20 * q5x +50, 20 * q5y + 50, this);
				}
                                
                                  if(movecount%2 == 0) // every 2 times you move the pacman, it will show the monsters on the grid
                                  {
                                      // there are a total of 8 Guardians...
                                    g.drawImage(ghost, 20 * g1x +50, 20 * g1y + 50, this);
                                    g.drawImage(ghost2, 20 * g2x + 50, 20 * g2y + 50, this);
                                    g.drawImage(ghost3, 20 * g3x + 50, 20 * g3y + 50, this);
                                    g.drawImage(ghost4, 20 * g4x + 50, 20 * g4y + 50, this);
                                    g.drawImage(ghost5, 20 * g5x + 50, 20 * g5y + 50, this);
                                    g.drawImage(ghost6, 20 * g6x + 50, 20 * g6y + 50, this);
                                    g.drawImage(ghost7, 20 * g7x + 50, 20 * g7y + 50, this);
                                    g.drawImage(ghost8, 20 * g8x + 50, 20 * g8y + 50, this);
                                    
                                }
                              // if the x & y coordinate is the same as the trap, it will show a question
				if (userx == q1x && usery == q1y )	{
					showQuestion1 = true;
					q1x = q1x + 50;
					q1y = q1y + 50;
					count++;
				}
                                // if the x & y coordinate is the same as the trap, it will show a question
				if (userx == q2x && usery == q2y ){
					showQuestion2 = true;
					q2x = q2x + 50;
					q2y = q2y + 50;
					count++;
				}
                                // if the x & y coordinate is the same as the trap, it will show a question
				if (userx == q3x && usery == q3y )	{
					showQuestion3 = true;
					q3x = q3x + 50;
					q3y = q3y + 50;
					count++;
				}
                                // if the x & y coordinate is the same as the trap, it will show a question
				if (userx == q4x && usery == q4y )	{
					showQuestion4 = true;
					q4x = q4x + 50;
					q4y = q4y + 50;
					count++;
				}
                                // if the x & y coordinate is the same as the trap, it will show a question
				if (userx == q5x && usery == q5y )	{
					showQuestion5 = true;
					q5x = q5x + 50;
					q5y = q5y + 50;
					count++;
				}
                               
                                // if you come in contact with the monsters, you are dead and it will take you to the "END" panel
                                  if(userx == g1x && usery == g1y){
                                    cards.last(c);       
                                 }
                                if(userx == g2x && usery == g2y){
                                    cards.last(c);                            
                                }
                                if(userx == g3x && usery == g3y){
                                    cards.last(c);
                                }
                                if(userx == g4x && usery == g4y){
                                    cards.last(c);
                                }
                                    if(userx == g5x && usery == g5y){
                                    cards.last(c);
                                }
                                if(userx == g6x && usery == g6y){
                                    cards.last(c);
                                }
                                if(userx == g7x && usery == g7y){
                                    cards.last(c);
                                }
                                if(userx == g8x && usery == g8y){
                                    cards.last(c);
                                }
                                
                                
                                
				if (showQuestion1 == true)	
                                // if you are taken to the questions, it will show a question panel corresponding to which trap you are on
                                {
					cards.show(c, "QUESTION1");
					showQuestion1 = false;
				}
				if (showQuestion2 == true)	{
					cards.show(c, "QUESTION2");
					showQuestion2 = false;
				}
				if (showQuestion3 == true)	{
					cards.show(c, "QUESTION3");
					showQuestion3 = false;
				}
				if (showQuestion4 == true)	{
					cards.show(c, "QUESTION4");
					showQuestion4 = false;
				}
				if (showQuestion5 == true)	{
					cards.show(c, "QUESTION5");
					showQuestion5 = false;
				}
		  	}
		  	public void actionPerformed(ActionEvent evt) {
				if ( boxheight > 0 )   {
					boxheight--;
					repaint ( );
				}
				else   {
					timer.stop(); // it will stop the timer when you are done
					timer = null;
				}
				if (timer == null)
					cards.last(c);
			}
		  	public void focusGained(FocusEvent evt) {
				radar.repaint(); // keep repainting the radar
			}

			public void focusLost(FocusEvent evt) {
				radar.repaint();
			}

			public void mousePressed(MouseEvent evt) {
				radar.requestFocus();
			}
		   	public void mouseEntered(MouseEvent evt) { }
			public void mouseExited(MouseEvent evt) { }
			public void mouseReleased(MouseEvent evt) { }
			public void mouseClicked(MouseEvent evt) { }
			public void keyTyped ( KeyEvent e )   {}
			public void keyPressed ( KeyEvent e )   {
				int value = e.getKeyCode();
                                
                                
                                int randval1 = (int)(Math.random() * 9) + 1;         // produces random value between 1 & 8. Used for moving ghosts
                                int randval2 = (int)(Math.random() * 9) + 1;        // each randval represents each Guardian
                                int randval3 = (int)(Math.random() * 9) + 1;
                                int randval4 = (int)(Math.random() * 9) + 1;
                                int randval5 = (int)(Math.random() * 9) + 1;
                                int randval6 = (int)(Math.random() * 9) + 1;
                                int randval7 = (int)(Math.random() * 9) + 1;
                                int randval8 = (int)(Math.random() * 9) + 1;
                                
                                // VK is used for the arrow keys, (Virtual Keyboard)
				if ( value == KeyEvent.VK_DOWN )	{
					move++;
					usery++;
					movecount++;
				}
				if ( value == KeyEvent.VK_UP )	{
					usery--;
					move++;
					movecount++;
				}
				if ( value == KeyEvent.VK_RIGHT ){
					userx++;
					move++;
					movecount++;
				}
				if ( value == KeyEvent.VK_LEFT )	{
					userx--;
					move++;
					movecount++;
				}
                                // if the user comes to an end of the gameboard, it will just go to the other side of the game board
				if ( userx > 20 )
					userx = 1;
				if ( usery > 20 )
					usery = 1;
				if ( userx < 1 )
					userx = 20;
				if ( usery < 1 )
					usery = 20;
                                // it will go one space up, and to the left
                                if(randval1 == 1){
                                    g1y--;
                                    g1x--;
                                }
                                // one space up
                                if(randval1 == 2)    {
                                    g1y--;
                                }
                                // one space up, and one to the right
                                if(randval1 == 3){
                                    g1y--;
                                    g1x++;
                                }
                                // one space to the left
                                if(randval1 == 4)    {
                                    g1x--;
                                }
                                // one space down, and one space to the left
                                if(randval1 == 5){
                                    g1y++;
                                    g1x--;
                                }
                                // one space down
                                if(randval1 == 6)    {                                    
                                    g1y++;
                                }
                                // one space down, and one space to the right
                                if(randval1 == 7){
                                    g1y++;
                                    g1x++;
                                }        
                                // one space to the right
                                if(randval1 == 8)    {
                                    g1x++;
                                }
                                
                                //for ghost2----------------------------
                                
                                // these if-else statements are for that it will move the monsters up, down, left, right, or do the diagonals
                                if(randval2 == 1){  // if the random value between 1 and 8 equals to 1, it will move 1 up, 1 left
                                    g2y--;
                                    g2x--;
                                }
                                if(randval2 == 2)    { // if the random value between 1 and 8 equals to 2, it will move 1 up
                                    g2y--;
                                }
                                if(randval2 == 3){ // if the random value between 1 and 8 equals to 3, it will move 1 up, 1 right
                                    g2y--;
                                    g2x++;
                                }
                                if(randval2 == 4)    { // if the random value between 1 and 8 equals to 1, it will move 1 left
                                    g2x--;
                                }
                                if(randval2 == 5){ // if the random value between 1 and 8 equals to 1, it will move 1 down, 1 left
                                    g2y++;
                                    g2x--;
                                }
                                if(randval2 == 6)    {               // if the random value between 1 and 8 equals to 1, it will move 1 down                    
                                    g2y++;
                                }
                                if(randval2 == 7){ // if the random value between 1 and 8 equals to 1, it will move 1 down, 1 right
                                    g2y++;
                                    g2x++;
                                }
                                if(randval2 == 8)    { // if the random value between 1 and 8 equals to 1, it will move 1 right
                                    g2x++;
                                }   
                                
                                //for ghost3--------------------------
                                if(randval3 == 1){
                                    g3y--;
                                    g3x--;
                                }
                                if(randval3 == 2)    {
                                    g3y--;
                                }
                                if(randval3 == 3){
                                    g3y--;
                                    g3x++;
                                }
                                if(randval3 == 4)    {
                                    g3x--;
                                }
                                if(randval3 == 5){
                                    g3y++;
                                    g3x--;
                                }
                                if(randval3 == 6)    {                                    
                                    g3y++;
                                }
                                if(randval3 == 7){
                                    g3y++;
                                    g3x++;
                                }
                                if(randval3 == 8)    {
                                    g3x++;
                                }
                                
                                //for ghost4------------------------
                                if(randval4 == 1){
                                    g4y--;
                                    g4x--;
                                }
                                if(randval4 == 2)    {
                                    g4y--;
                                }
                                if(randval4 == 3){
                                    g4y--;
                                    g4x++;
                                }
                                if(randval4 == 4)    {
                                    g4x--;
                                }
                                if(randval4 == 5){
                                    g4y++;
                                    g4x--;
                                }
                                if(randval4 == 6)    {                                    
                                    g4y++;
                                }
                                if(randval4 == 7){
                                    g4y++;
                                    g4x++;
                                }
                                if(randval4 == 8)    {
                                    g4x++;
                                }
                                // ---------------------------------- ghost 5
                                
                                 if(randval5 == 1){
                                    g5y--;
                                    g5x--;
                                }
                                if(randval5 == 2)    {
                                    g5y--;
                                }
                                if(randval5 == 3){
                                    g5y--;
                                    g5x++;
                                }
                                if(randval5 == 4)    {
                                    g5x--;
                                }
                                if(randval5 == 5){
                                    g5y++;
                                    g5x--;
                                }
                                if(randval5 == 6)    {                                    
                                    g5y++;
                                }
                                if(randval5 == 7){
                                    g5y++;
                                    g5x++;
                                }
                                if(randval5 == 8)    {
                                    g5x++;
                                }
                                //---------------------------------- ghost 6
                                
                                  if(randval6 == 1){
                                    g6y--;
                                    g6x--;
                                }
                                if(randval6 == 2)    {
                                    g6y--;
                                }
                                if(randval6 == 3){
                                    g6y--;
                                    g6x++;
                                }
                                if(randval6 == 4)    {
                                    g6x--;
                                }
                                if(randval6 == 5){
                                    g6y++;
                                    g6x--;
                                }
                                if(randval6 == 6)    {                                    
                                    g6y++;
                                }
                                if(randval6 == 7){
                                    g6y++;
                                    g6x++;
                                }
                                if(randval6 == 8)    {
                                    g6x++;
                                }
                                //------------------------------  ghost 7
                                
                                  if(randval7 == 1){
                                    g7y--;
                                    g7x--;
                                }
                                if(randval7 == 2)    {
                                    g7y--;
                                }
                                if(randval7 == 3){
                                    g7y--;
                                    g7x++;
                                }
                                if(randval7 == 4)    {
                                    g7x--;
                                }
                                if(randval7 == 5){
                                    g7y++;
                                    g7x--;
                                }
                                if(randval7 == 6)    {                                    
                                    g7y++;
                                }
                                if(randval7 == 7){
                                    g7y++;
                                    g7x++;
                                }
                                if(randval7 == 8)    {
                                    g7x++;
                                }
                                //------------------------------  ghost 8
                                  if(randval8 == 1){
                                    g8y--;
                                    g8x--;
                                }
                                if(randval8 == 2)    {
                                    g8y--;
                                }
                                if(randval8 == 3){
                                    g8y--;
                                    g8x++;
                                }
                                if(randval8 == 4)    {
                                    g8x--;
                                }
                                if(randval8 == 5){
                                    g8y++;
                                    g8x--;
                                }
                                if(randval8 == 6)    {                                    
                                    g8y++;
                                }
                                if(randval8 == 7){
                                    g8y++;
                                    g8x++;
                                }
                                if(randval8 == 8)    {
                                    g8x++;
                                }
                                
                                
                             /*   do{
                                     g1x = (int) (Math.random() * 19 + 1);
                                     g1y = (int) (Math.random() * 19 + 1);
                                }while(userx == g1x && usery == g1y);
                                
                                  do{
                                     g2x = (int) (Math.random() * 19 + 1);
                                     g2y = (int) (Math.random() * 19 + 1);
                                }while(userx == g2x && usery == g2y);
                                  
                                    do{
                                     g3x = (int) (Math.random() * 19 + 1);
                                     g3y = (int) (Math.random() * 19 + 1);
                                }while(userx == g3x && usery == g3y);
                                    
                                      do{
                                     g4x = (int) (Math.random() * 19 + 1);
                                     g4y = (int) (Math.random() * 19 + 1);
                                }while(userx == g4x && usery == g4y);
                                      
                                do{
                                     g5x = (int) (Math.random() * 19 + 1);
                                     g5y = (int) (Math.random() * 19 + 1);
                                }while(userx == g5x && usery == g5y);
                                
                                
                                         
                                do{
                                     g6x = (int) (Math.random() * 19 + 1);
                                     g6y = (int) (Math.random() * 19 + 1);
                                }while(userx == g6x && usery == g6y);
                                
                                             
                                do{
                                     g7x = (int) (Math.random() * 19 + 1);
                                     g7y = (int) (Math.random() * 19 + 1);
                                }while(userx == g7x && usery == g7y);
                                
                                             
                                do{
                                     g8x = (int) (Math.random() * 19 + 1);
                                     g8y = (int) (Math.random() * 19 + 1);
                                }while(userx == g8x && usery == g8y);*/
                                
                                if(userx == g1x && usery == g1y){
                                    cards.last(c);
                                }
                                 if(userx == g2x && usery == g2y){
                                    cards.last(c);
                                }
                                  if(userx == g3x && usery == g3y){
                                    cards.last(c);
                                }
                               if(userx == g4x && usery == g4y){
                                    cards.last(c);
                                }
                                if(userx == g5x && usery == g5y){
                                    cards.last(c);
                                }
                                
                               if(userx == g6x && usery == g6y){
                                    cards.last(c);
                                }
                               
                               if(userx == g7x && usery == g7y){
                                    cards.last(c);
                                }
                               
                                if(userx == g8x && usery == g8y){
                                    cards.last(c);
                                }
                                
                                 
                                // these if else statements ensure that the pacman do not go out of the game board 
                                
                                // ------------------------ ghost 1
                                   if ( g1x > 20 )
					g1x = 1;
				if ( g1y > 20 )
					g1y = 1;
				if ( g1x < 1 )
					g1x = 20;
				if ( g1y < 1 )
					g1y = 20;
                                //--------------------------------- ghost 2
                                if ( g2x > 20 )
					g2x = 1;
				if ( g2y > 20 )
					g2y = 1;
				if ( g2x < 1 )
					g2x = 20;
				if ( g2y < 1 )
					g2y = 20;
                                //-------------------------------- ghost 3
                                if ( g3x > 20 )
					g3x = 1;
				if ( g3y > 20 )
					g3y = 1;
				if ( g3x < 1 )
					g3x = 20;
				if ( g3y < 1 )
					g3y = 20;
                                //------------------------------ ghost 4
                                if ( g4x > 20 )
					g4x = 1;
				if ( g4y > 20 )
					g4y = 1;
				if ( g4x < 1 )
					g4x = 20;
				if ( g4y < 1 )
					g4y = 20;
                                //------------------------------  ghost 5
                                  if ( g5x > 20 )
					g5x = 1;
				if ( g5y > 20 )
					g5y = 1;
				if ( g5x < 1 )
					g5x = 20;
				if ( g5y < 1 )
					g5y = 20;
                                //-----------------------------  ghost 6
                                
                                  if ( g6x > 20 )
					g6x = 1;
				if ( g6y > 20 )
					g6y = 1;
				if ( g6x < 1 )
					g6x = 20;
				if ( g6y < 1 )
					g6y = 20;
                                //---------------------------  ghost 7
                                  if ( g7x > 20 )
					g7x = 1;
				if ( g7y > 20 )
					g7y = 1;
				if ( g7x < 1 )
					g7x = 20;
				if ( g7y < 1 )
					g7y = 20;
                                //-------------------------  ghost 8
                                  if ( g8x > 20 )
					g8x = 1;
				if ( g8y > 20 )
					g8y = 1;
				if ( g8x < 1 )
					g8x = 20;
				if ( g8y < 1 )
					g8y = 20;
                                
                               
				radar.repaint ( ); // the radar will keep repainting
			}
			public void keyReleased ( KeyEvent e )   {}
		}
	}

	class QuestionPanel1 extends JPanel	{
		private GetAnswerPanel respuesta;
		private JRadioButton a1, a2, a3, a4, redRadio; // a, b , c, d buttons
		private int myAnswer;
		public QuestionPanel1()	{
			respuesta = new GetAnswerPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			equation = new JLabel("",JLabel.CENTER); // it will show the problem in the center of the screen
			add(respuesta, BorderLayout.SOUTH);
			myAnswer = questionsAnswered;
			equation.setText(quest[rando1]);
			equation.setForeground(Color.white);
			equation.setBackground(Color.black);
			equation.setFont(new Font("Serif",Font.BOLD,40));
			equation.setOpaque(true);
			equation.setPreferredSize ( new Dimension(200, 50) );
       		this.add ( equation );
		}
		class GetAnswerPanel extends JPanel implements ActionListener	{
			public GetAnswerPanel()	{
				getContentPane().setLayout( new GridLayout(1,5) ); // grid layout
				ButtonGroup answerGroup = new ButtonGroup();
				a1 = new JRadioButton(choicea[rando1]);
				answerGroup.add(a1);
				a1.addActionListener(this);
				this.add ( a1 );
				a2 = new JRadioButton(choiceb[rando1]);
				answerGroup.add(a2);
				a2.addActionListener(this);
				this.add ( a2 );
				a3 = new JRadioButton(choicec[rando1]);
				answerGroup.add(a3);
				a3.addActionListener(this);
				this.add ( a3 );
				a4 = new JRadioButton(choiced[rando1]);
				answerGroup.add(a4);
				a4.addActionListener(this);
				this.add ( a4 );
			}
			public void actionPerformed(ActionEvent evt) {
				if ( a1.isSelected() ) {
					answernumber = 1;
				}
				if ( a2.isSelected() ) {
					answernumber = 2;
				}
				if ( a3.isSelected() ) {
					answernumber = 3;
				}
				if ( a4.isSelected() ) {
					answernumber = 4;
				}
				if (answernumber == answ[rando1])	{   // if the answer you have chosen is correct, then you will go back
                                    // to the game and the amount of questions you have answered will be increased
					questionsAnswered++;
					showQuestion1 = false;
					correctans = true;
					cards.show(c, "GAME");
				}
				if (answernumber != answ[rando1])	{
                                    // if the answer you have chosen is incorrect, you will be taken to the last panel where it shows that you have died!
					cards.last(c);
				}
				if (questionsAnswered == 5) // if this question was the 5th question you have answered correctly, then you will be taken to the win panel
					cards.show(c, "WIN");
			}
		}
	}
	class QuestionPanel2 extends JPanel	{
		private GetAnswerPanel respuesta;
		private JRadioButton a1, a2, a3, a4, redRadio;
		private int myAnswer;
		public QuestionPanel2()	{
			respuesta = new GetAnswerPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			equation = new JLabel("",JLabel.CENTER); // print line for the equation
			add(respuesta, BorderLayout.SOUTH);
			myAnswer = questionsAnswered;
			equation.setText(quest[rando2]);
			equation.setForeground(Color.white);
			equation.setBackground(Color.black);
			equation.setFont(new Font("Serif",Font.BOLD,40));
			equation.setOpaque(true);
			equation.setPreferredSize ( new Dimension(200, 50) );
       		this.add ( equation );
		}

		class GetAnswerPanel extends JPanel implements ActionListener	{
			public GetAnswerPanel()	{
				getContentPane().setLayout( new GridLayout(1,5) );
				ButtonGroup answerGroup = new ButtonGroup();
				a1 = new JRadioButton(choicea[rando2]);
				answerGroup.add(a1);
				a1.addActionListener(this);
				this.add ( a1 );
				a2 = new JRadioButton(choiceb[rando2]);
				answerGroup.add(a2);
				a2.addActionListener(this);
				this.add ( a2 );
				a3 = new JRadioButton(choicec[rando2]);
				answerGroup.add(a3);
				a3.addActionListener(this);
				this.add ( a3 );
				a4 = new JRadioButton(choiced[rando2]);
				answerGroup.add(a4);
				a4.addActionListener(this);
				this.add ( a4 );
			}
			public void actionPerformed(ActionEvent evt) {
				if ( a1.isSelected() ) {
					answernumber = 1; // choice 1
				}
				if ( a2.isSelected() ) {
					answernumber = 2; // choice 2
				}
				if ( a3.isSelected() ) {
					answernumber = 3; // choice 3
				}
				if ( a4.isSelected() ) {
					answernumber = 4; // choice 4
				}
				if (answernumber == answ[rando2])	{
					questionsAnswered++;
					showQuestion1 = false;
					correctans = true;
					cards.show(c, "GAME");
				}
				if (answernumber != answ[rando2])	{  // if the answer you have chosen is incorrect, you will be taken to the last panel where it shows that you have died!
					cards.last(c);
				}
				if (questionsAnswered == 5)// if this question was the 5th question you have answered correctly, then you will be taken to the win panel
					cards.show(c, "WIN");
			}
		}
	}
	class QuestionPanel3 extends JPanel	{
		private GetAnswerPanel respuesta;
		private JRadioButton a1, a2, a3, a4, redRadio;
		private int myAnswer;
		public QuestionPanel3()	{
			respuesta = new GetAnswerPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			equation = new JLabel("",JLabel.CENTER); // shows the equation
			add(respuesta, BorderLayout.SOUTH);
			myAnswer = questionsAnswered;
			equation.setText(quest[rando3]);
			equation.setForeground(Color.white);
			equation.setBackground(Color.black);
			equation.setFont(new Font("Serif",Font.BOLD,40)); // font and size
			equation.setOpaque(true);
			equation.setPreferredSize ( new Dimension(200, 50) );
       		this.add ( equation );
		}
		class GetAnswerPanel extends JPanel implements ActionListener	{
			public GetAnswerPanel()	{
				getContentPane().setLayout( new GridLayout(1,5) );
				ButtonGroup answerGroup = new ButtonGroup();
				a1 = new JRadioButton(choicea[rando3]);
				answerGroup.add(a1);
				a1.addActionListener(this);
				this.add ( a1 );
				a2 = new JRadioButton(choiceb[rando3]);
				answerGroup.add(a2);
				a2.addActionListener(this);
				this.add ( a2 );
				a3 = new JRadioButton(choicec[rando3]);
				answerGroup.add(a3);
				a3.addActionListener(this);
				this.add ( a3 );
				a4 = new JRadioButton(choiced[rando3]);
				answerGroup.add(a4);
				a4.addActionListener(this);
				this.add ( a4 );
			}
			public void actionPerformed(ActionEvent evt) {
				if ( a1.isSelected() ) {
					answernumber = 1;
				}
				if ( a2.isSelected() ) {
					answernumber = 2;
				}
				if ( a3.isSelected() ) {
					answernumber = 3;
				}
				if ( a4.isSelected() ) {
					answernumber = 4;
				}
				if (answernumber == answ[rando3])	{ // if the question is answered correctly, you will be taken back to the game
					questionsAnswered++;
					showQuestion1 = false;
					correctans = true;
					cards.show(c, "GAME");
				}
				if (answernumber != answ[rando3])	{// if the answer you have chosen is incorrect, you will be taken to the last panel where it shows that you have died!
					cards.last(c);
				}
				if (questionsAnswered == 5)// if this question was the 5th question you have answered correctly, then you will be taken to the win panel
					cards.show(c, "WIN");
			}
		}
	}
	class QuestionPanel4 extends JPanel	{
		private GetAnswerPanel respuesta;
		private JRadioButton a1, a2, a3, a4, redRadio; // radio buttons for the questions
		private int myAnswer;
		public QuestionPanel4()	{
			respuesta = new GetAnswerPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			equation = new JLabel("",JLabel.CENTER);
			add(respuesta, BorderLayout.SOUTH);
			myAnswer = questionsAnswered;
			equation.setText(quest[rando4]);
			equation.setForeground(Color.white);
			equation.setBackground(Color.black);
			equation.setFont(new Font("Serif",Font.BOLD,40));
			equation.setOpaque(true);
			equation.setPreferredSize ( new Dimension(200, 50) );
       		this.add ( equation );
		}
		class GetAnswerPanel extends JPanel implements ActionListener	{
			public GetAnswerPanel()	{
				getContentPane().setLayout( new GridLayout(1,5) ); // grid layout
				ButtonGroup answerGroup = new ButtonGroup();
				a1 = new JRadioButton(choicea[rando4]); // choice a 
				answerGroup.add(a1);
				a1.addActionListener(this);
				this.add ( a1 );
				a2 = new JRadioButton(choiceb[rando4]); // choice b
				answerGroup.add(a2);
				a2.addActionListener(this);
				this.add ( a2 );
				a3 = new JRadioButton(choicec[rando4]); // choice c
				answerGroup.add(a3);
				a3.addActionListener(this);
				this.add ( a3 );
				a4 = new JRadioButton(choiced[rando4]); // choice d
				answerGroup.add(a4);
				a4.addActionListener(this);
				this.add ( a4 );
			}
			public void actionPerformed(ActionEvent evt) {
				if ( a1.isSelected() ) {
					answernumber = 1;
				}
				if ( a2.isSelected() ) {
					answernumber = 2;
				}
				if ( a3.isSelected() ) {
					answernumber = 3;
				}
				if ( a4.isSelected() ) {
					answernumber = 4;
				}
				if (answernumber == answ[rando4])	{// if the question is answered correctly, you will be taken back to the game
					questionsAnswered++;
					showQuestion1 = false;
					correctans = true;
					cards.show(c, "GAME");
				}

				if (answernumber != answ[rando4])	{// if the answer you have chosen is incorrect, you will be taken to the last panel where it shows that you have died!
					cards.last(c);
				}
				if (questionsAnswered == 5)// if this question was the 5th question you have answered correctly, then you will be taken to the win panel
					cards.show(c, "WIN");
			}
		}
	}

	class QuestionPanel5 extends JPanel	{
		private GetAnswerPanel respuesta;
		private JRadioButton a1, a2, a3, a4, redRadio;
		private int myAnswer;
		public QuestionPanel5()	{
			respuesta = new GetAnswerPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			equation = new JLabel("",JLabel.CENTER);
			add(respuesta, BorderLayout.SOUTH);
			myAnswer = questionsAnswered;
			equation.setText(quest[rando5]);
			equation.setForeground(Color.white);
			equation.setBackground(Color.black);
			equation.setFont(new Font("Serif",Font.BOLD,40));
			equation.setOpaque(true);
			equation.setPreferredSize ( new Dimension(200, 50) );
       		this.add ( equation );
		}
		class GetAnswerPanel extends JPanel implements ActionListener	{
			public GetAnswerPanel()	{
				getContentPane().setLayout( new GridLayout(1,5) ); // grid layout
				ButtonGroup answerGroup = new ButtonGroup();
				a1 = new JRadioButton(choicea[rando5]); // choice a
				answerGroup.add(a1);
				a1.addActionListener(this);
				this.add ( a1 );
				a2 = new JRadioButton(choiceb[rando5]); // choice b
				answerGroup.add(a2);
				a2.addActionListener(this);
				this.add ( a2 );
				a3 = new JRadioButton(choicec[rando5]); // choice c
				answerGroup.add(a3);
				a3.addActionListener(this);
				this.add ( a3 );
				a4 = new JRadioButton(choiced[rando5]); // choice d
				answerGroup.add(a4);
				a4.addActionListener(this);
				this.add ( a4 );
			}
			public void actionPerformed(ActionEvent evt) {
				if ( a1.isSelected() ) {
					answernumber = 1;
				}
				if ( a2.isSelected() ) {
					answernumber = 2;
				}
				if ( a3.isSelected() ) {
					answernumber = 3;
				}
				if ( a4.isSelected() ) {
					answernumber = 4;
				}
				if (answernumber == answ[rando5])	{// if the question is answered correctly, you will be taken back to the game
					questionsAnswered++;
					showQuestion1 = false;
					correctans = true;
					cards.show(c, "GAME");
				}
				if (answernumber != answ[rando5])	{// if the answer you have chosen is incorrect, you will be taken to the last panel where it shows that you have died!
					failgame = true;
					cards.last(c);
				}
				if (questionsAnswered == 5)// if this question was the 5th question you have answered correctly, then you will be taken to the win panel
					cards.show(c, "WIN");
			}
		}
	}

	class WinPanel extends JPanel implements ActionListener	{
		public WinPanel()	{
			dibujo = Toolkit.getDefaultToolkit().getImage("cheeselantis.jpg"); // this picture is shown when you win
			setBackground(Color.black);
			setLayout(new BorderLayout());			
			legendname = new JTextField(20); // this is where the user would type in his/her name to become the legend
			add(legendname, BorderLayout.SOUTH); // border layout
			legendname.addActionListener (this);			
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(dibujo, 0, 0, this);
		}		
		public void actionPerformed (ActionEvent evt)   {
			String command = evt.getActionCommand();
			if (evt.getSource() == legendname){
				lname = legendname.getText();	// catch the text file that contains the name of the previous legend		
			}
			try {
				out = new PrintWriter ( outname );
			} 
			catch ( IOException e )   {
				System.out.println("Cannot create file to be written to."); // this print line will be shown if the file cannot be found
				System.exit ( 1 );
			}		
			out.println ( "" + lname );
			out.close();			
		}
	}
	class EndPanel extends JPanel	{
		private DisplayEndPanel terminamos;
		public EndPanel()	{
			endim = Toolkit.getDefaultToolkit().getImage("over.jpg"); // this picture is shown if you get killed by the monsters or if you answer 
                        // a question incorrectly
			terminamos = new DisplayEndPanel();
			setBackground(Color.black);
			setLayout(new BorderLayout());
			add(terminamos, BorderLayout.CENTER);	
		}
		class DisplayEndPanel extends JPanel	{
			public DisplayEndPanel()	{
			}
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(endim, 0, 0, this); // you are drawing the imgage
			}
		}
	}
	public void focusGained(FocusEvent evt) {
		repaint(); 
	}
	public void focusLost(FocusEvent evt) {
		repaint();
	}
}
