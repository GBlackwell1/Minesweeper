//Written by Gabriel Blackwell
import java.util.Scanner;
import java.util.Random;
public class AIOMinesweeper {
	//All my needed variables
	private final static int MAX_SIZE = 15;
	private final static char OPEN = '~';
	private final static char BORDER = '|';
	private final static char BORDER_2 = '-';
	private final static char FLAG = '^';
	private final static char CLOSE = 'X';
	private static int[][] BOMB_SPACES;
	private static int[][] NUM_FIELD = new int[MAX_SIZE][MAX_SIZE];
	private static char[][] PLAY_FIELD = new char[MAX_SIZE][MAX_SIZE];
	private static Scanner keyboard = new Scanner(System.in);
	private final static char[] LETTER = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static Random RNG = new Random();
	public static void main(String args[]) {
		System.out.println("Welcome user to MineSweeper "+MAX_SIZE+"x"+MAX_SIZE+"!");
		System.out.println("\nPlease choose a starting location by putting in a letter followed by a number!");
		initPlayField(keyboard.nextLine());
		printMineGrid();
		System.out.println();
		System.out.println("Mine field initialized!\n"
				+ "In this game you have three commands.\nThese two commands are \"flag:\" and \"open:\" followed by the space you would like to open, and \"quit\" which exits the program."
				+ "\nYou win by clearing the whole mine field and flagging every bomb!");
		boolean win = false;
		//Keep checking till user has won
		while(!win) {
			System.out.println("\nPlease choose your next command!");
			String choice = keyboard.nextLine();
			updatePlayField(choice);
			printMineGrid();
			win = hasWon();
		}
		//has won and close
		System.out.println("\nYou've won!");
		System.out.println("Thanks for playing!");
		keyboard.close();
		System.exit(0);
	}
	//Print the top letters and top border underneath that
	private static void printLetterBorder() {
		System.out.print("   ");
		for(int i = 0;i<MAX_SIZE;i++) {
			System.out.print(LETTER[i]);
			System.out.print(' ');
		}
		System.out.println();
		System.out.print("   ");
		for(int i = 0;i<MAX_SIZE;i++) {
			System.out.print(BORDER_2);
			System.out.print(' ');
		}
		System.out.println();
	}
	//Print the bottom border
	private static void printFinalBorder(){
		System.out.print("   ");
		for(int i = 0;i<MAX_SIZE;i++) {
			System.out.print(BORDER_2);
			System.out.print(' ');
		}
	}
	//Print the mine grid once the play field has been updated
	private static void printMineGrid() {
		//Update the field 15 times
		int count = 0;
		while(count < MAX_SIZE) {
			for(int i = 0;i<MAX_SIZE;i++) {
				//Algorithm that checks surrounding spaces in a 3x3 grid and opens them based on if statements
				//This is hell but this is the only way I could figure out how to do it
				for(int j = 0;j<MAX_SIZE;j++) {
						if(PLAY_FIELD[i][j] == OPEN) {
							//Check top left
							if(i-1 != -1 && j-1 != -1 && NUM_FIELD[i-1][j-1] == 0) 
								PLAY_FIELD[i-1][j-1] = OPEN;
							if(i-1 != -1 && j-1 != -1 
									&& NUM_FIELD[i-1][j-1] > 0 
									&& NUM_FIELD[i-1][j-1] < 9 ) 
								PLAY_FIELD[i-1][j-1] = Character.forDigit(NUM_FIELD[i-1][j-1],10);
							//Check top middle
							if(j-1 != -1 && NUM_FIELD[i][j-1] == 0) 
								PLAY_FIELD[i][j-1] = OPEN;
							if(j-1 != -1 
									&& NUM_FIELD[i][j-1] > 0
									&& NUM_FIELD[i][j-1] < 9) 
								PLAY_FIELD[i][j-1] = Character.forDigit(NUM_FIELD[i][j-1],10);
							//Check top right
							if(i+1 < MAX_SIZE && j-1 != -1 && NUM_FIELD[i+1][j-1] == 0)
								PLAY_FIELD[i+1][j-1] = OPEN;
							if(i+1 < MAX_SIZE && j-1 != -1 
									&& NUM_FIELD[i+1][j-1] > 0
									&& NUM_FIELD[i+1][j-1] < 9)
								PLAY_FIELD[i+1][j-1] = Character.forDigit(NUM_FIELD[i+1][j-1],10);
							//Check middle left
							if(i-1 != -1 && NUM_FIELD[i-1][j] == 0)
								PLAY_FIELD[i-1][j] = OPEN;
							if(i-1 != -1 
									&& NUM_FIELD[i-1][j] > 0
									&& NUM_FIELD[i-1][j] < 9)
								PLAY_FIELD[i-1][j] = Character.forDigit(NUM_FIELD[i-1][j],10);
							//Check middle right
							if(i+1 < MAX_SIZE && NUM_FIELD[i+1][j] == 0)
								PLAY_FIELD[i+1][j] = OPEN;
							if(i+1 < MAX_SIZE 
									&& NUM_FIELD[i+1][j] > 0
									&& NUM_FIELD[i+1][j] < 9)
								PLAY_FIELD[i+1][j] = Character.forDigit(NUM_FIELD[i+1][j],10);
							//Check bottom left
							if(i-1 != -1 && j+1 < MAX_SIZE && NUM_FIELD[i-1][j+1] == 0)
								PLAY_FIELD[i-1][j+1] = OPEN;
							if(i-1 != -1 && j+1 < MAX_SIZE 
									&& NUM_FIELD[i-1][j+1] > 0
									&& NUM_FIELD[i-1][j+1] < 9)
								PLAY_FIELD[i-1][j+1] = Character.forDigit(NUM_FIELD[i-1][j+1],10);
							//Check bottom middle
							if(j+1 < MAX_SIZE && NUM_FIELD[i][j+1] == 0)
								PLAY_FIELD[i][j+1] = OPEN;
							if(j+1 < MAX_SIZE 
									&& NUM_FIELD[i][j+1] > 0
									&& NUM_FIELD[i][j+1] < 9)
								PLAY_FIELD[i][j+1] = Character.forDigit(NUM_FIELD[i][j+1],10);
							//Check bottom right
							if(i+1 < MAX_SIZE && j+1 < MAX_SIZE && NUM_FIELD[i+1][j+1] == 0)
								PLAY_FIELD[i+1][j+1] = OPEN;
							if(i+1 < MAX_SIZE && j+1 < MAX_SIZE 
									&& NUM_FIELD[i+1][j+1] > 0
									&& NUM_FIELD[i+1][j+1] < 9)
								PLAY_FIELD[i+1][j+1] = Character.forDigit(NUM_FIELD[i+1][j+1],10);	
						}
				}
			}
			count++;
		}
		//Actually print the board
		printLetterBorder();
		//Print front numbers, spaces, and border
		for(int i = 0;i<MAX_SIZE;i++) {
			System.out.print(i);
			if(i<= 9)
				System.out.print(" ");
			System.out.print(BORDER);
			for(int j = 0;j<MAX_SIZE;j++) {
				System.out.print(PLAY_FIELD[i][j]);
				if(j != MAX_SIZE-1)
					System.out.print(' ');
			}
			//Print back border
			System.out.println(BORDER);
		}
		//Print actual internal play field
		printFinalBorder();
	}
	//Initialize the bombs and the startSpace
	private static void initPlayField(String startSpace) {
		//Populate play field
		for(int i = 0;i<MAX_SIZE;i++) {
			for(int j=0;j<MAX_SIZE;j++) 
				PLAY_FIELD[i][j] = CLOSE;
		}
		//Find the starting space
		char xStartChar = startSpace.charAt(0);
		int startX = -1;
		for(int i=0;i<MAX_SIZE;i++) {
			if(LETTER[i] == xStartChar) 
				{ startX = i; }
		}
		int startY = Integer.parseInt(startSpace.substring(1));
		//Create and populate bombSpaces array
		BOMB_SPACES = new int[(int)((MAX_SIZE*MAX_SIZE)*0.10)][2];
		//TODO find fix if bomb equal other bomb
		//0.4% chance but still can happen, has happened before
		for(int i = 0; i<BOMB_SPACES.length;i++) {
			//Roll for random bomb spaces
			int xNum = RNG.nextInt(MAX_SIZE);
			int yNum = RNG.nextInt(MAX_SIZE);
			while(xNum == startX && yNum == startY) {
				xNum = RNG.nextInt(MAX_SIZE);
				yNum = RNG.nextInt(MAX_SIZE);
			}
			//Check to see if the bombs are equal to each other
			if(i>0) {
				for(int j = i+1;j<i;j++) {
					while(BOMB_SPACES[i][0] == BOMB_SPACES[j][0]
							&& BOMB_SPACES[i][1] == BOMB_SPACES[j][1]) {
						xNum = RNG.nextInt(MAX_SIZE);
						yNum = RNG.nextInt(MAX_SIZE);
					}
				}
			}
			//Eventually delete this but this is to check and see the spaces the bombs are generating at
			BOMB_SPACES[i][0] = xNum;
			BOMB_SPACES[i][1] = yNum;
		}
		//Make number field array using a similar method for printing the mine field, 3x3 grid
		//This will be used in tandem with play field to change open spaces to numbers
		//Any number > 8 is considered a bomb
		for(int i=0;i<BOMB_SPACES.length;i++) {
			int xBomb = BOMB_SPACES[i][0];
			int yBomb = BOMB_SPACES[i][1];
			NUM_FIELD[xBomb][yBomb] = 9;
			//Fill in the fields
			if(xBomb-1 != -1 && yBomb-1 != -1) 
				NUM_FIELD[xBomb-1][yBomb-1]++;
			if(yBomb-1 != -1) 
				NUM_FIELD[xBomb][yBomb-1]++;
			if(xBomb+1 < MAX_SIZE && yBomb-1 != -1)
				NUM_FIELD[xBomb+1][yBomb-1]++;
			if(xBomb-1 != -1)
				NUM_FIELD[xBomb-1][yBomb]++;
			if(xBomb+1 < MAX_SIZE)
				NUM_FIELD[xBomb+1][yBomb]++;
			if(xBomb-1 != -1 && yBomb+1 < MAX_SIZE)
				NUM_FIELD[xBomb-1][yBomb+1]++;
			if(yBomb+1 < MAX_SIZE)
				NUM_FIELD[xBomb][yBomb+1]++;
			if(xBomb+1 < MAX_SIZE && yBomb+1 < MAX_SIZE)
				NUM_FIELD[xBomb+1][yBomb+1]++;
		}
		//If starting space chosen is open then set space to open but if it isnt then set number
		if(NUM_FIELD[startY][startX] == 0)
			PLAY_FIELD[startY][startX] = OPEN;
		else if(NUM_FIELD[startY][startX] > 0 
				&& NUM_FIELD[startY][startX] < 9)
			PLAY_FIELD[startY][startX] = Character.forDigit(NUM_FIELD[startY][startX],10);
	}
	//Update the play field each time the user inputs a command
	private static void updatePlayField(String command) {
		//Quit if the length of the command is 4
		//Because quit is the only command available that has that length
		if(command.length() == 4) {
			System.out.println("Thanks for playing!");
			keyboard.close();
			System.exit(0);
		}
		//Make the command and the space wanted to execute that command on
		String toDo = command.substring(0,4);
		String spaceWant = command.substring(5);
		//Conditions for flag, find the space by the string given and set that space equal to FLAG constant and update board
		if(toDo.equalsIgnoreCase("flag")) {
			char xChar = spaceWant.charAt(0);
			int xNum = -1;
			for(int i=0;i<MAX_SIZE;i++) {
				if(LETTER[i] == xChar) 
					{ xNum = i; }
			}
			int yNum = Integer.parseInt(spaceWant.substring(1));
			PLAY_FIELD[yNum][xNum] = FLAG;
		}
		//Check to see if the place chosen is a bomb but if it isn't then you try and update the board
		else if(toDo.equalsIgnoreCase("open")) {
			char xChar = spaceWant.charAt(0);
			int xNum = -1;
			for(int i=0;i<MAX_SIZE;i++) {
				if(LETTER[i] == xChar) 
					{ xNum = i; }
			}
			int yNum = Integer.parseInt(spaceWant.substring(1));
			for(int i=0;i<BOMB_SPACES.length;i++) {
				if(BOMB_SPACES[i][0] == yNum) {
					if(BOMB_SPACES[i][1] == xNum) {
						System.out.println("You explodededed!");
						System.out.println("Thanks for playing!");
						keyboard.close();
						System.exit(0);
					}	
				}
			}
			//This checks to see if the space the character opened is an open space or a number space
			//If number than put number from num field onto play field if num field 0 then open
			if(PLAY_FIELD[yNum][xNum] == CLOSE && NUM_FIELD[yNum][xNum] == 0)
				PLAY_FIELD[yNum][xNum] = OPEN;
			else if (PLAY_FIELD[yNum][xNum] == CLOSE && NUM_FIELD[yNum][xNum] > 0)
				PLAY_FIELD[yNum][xNum] = Character.forDigit(NUM_FIELD[yNum][xNum],10);
		}
		//Not valid should run vvv
		else
			System.out.println("Command Invalid!");
	}
	//Check to see if the player has won the game
	private static boolean hasWon() {
		//Count the number of close characters and if they more than 0 then player has not won
		int count = 0;
		for(int i=0;i<PLAY_FIELD.length;i++) {
			for(int j=0;j<PLAY_FIELD[i].length;j++) {
				if(PLAY_FIELD[i][j] == CLOSE)
					count++;
			}
		}
		if(count == 0)
			return true;
		else
			return false;
	}
}