/*
Created by Eric Wang
Based on John Conway's Game of Life
*/

import java.io.*;

public class game {
	
	static char[][] BOARD;
	static int y;
	static int x;
	static char dead = ' ';
	static char alive = 'x';
	
	static void play(){
		char[][] temp = new char[y][x];
		temp = clearBoard(temp);
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				if(BOARD[i][j] != dead){
					if(find(i, j) == 2 || find(i, j) == 3)  temp[i][j] = alive;
				} else {
					if(find(i, j) == 3) temp[i][j] = alive;
				}
			}
		}
		BOARD = temp;
		printBoard();
	}
	
	static int find(int i, int j){
		int count = 0;
		if(i == 0 && j == 0){
			if(BOARD[i][j+1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
			if(BOARD[i+1][j+1] == alive) count++;
		} else if(i == 0 && j == x-1){
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
			if(BOARD[i+1][j-1] == alive) count++;
		} else if(i == y-1 && j == 0){
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i][j+1] == alive) count++;
			if(BOARD[i-1][j+1] == alive) count++;
		} else if(i == y-1 && j == x-1){
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i-1][j-1] == alive) count++;
		} else if(i == 0){
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i+1][j-1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
			if(BOARD[i+1][j+1] == alive) count++;
			if(BOARD[i][j+1] == alive) count++;
		} else if(i == y-1){
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i-1][j-1] == alive) count++;
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i-1][j+1] == alive) count++;
			if(BOARD[i][j+1] == alive) count++;
		} else if(j == 0){
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i-1][j+1] == alive) count++;
			if(BOARD[i][j+1] == alive) count++;
			if(BOARD[i+1][j+1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
		} else if(j == x-1){
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i-1][j-1] == alive) count++;
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i+1][j-1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
		} else {
			if(BOARD[i-1][j-1] == alive) count++;
			if(BOARD[i-1][j] == alive) count++;
			if(BOARD[i-1][j+1] == alive) count++;
			if(BOARD[i][j-1] == alive) count++;
			if(BOARD[i][j+1] == alive) count++;
			if(BOARD[i+1][j-1] == alive) count++;
			if(BOARD[i+1][j] == alive) count++;
			if(BOARD[i+1][j+1] == alive) count++;
		}
		return count;
	}
	
	static void editBoard(BufferedReader br) throws IOException{
		System.out.println("Type the coordinates of the cells you want to toggle (alive -> dead, dead -> alive), "
							+ "or type 'end' to stop editing");
		while(true){
			String[] tokens = br.readLine().split(" ");
			if(!tokens[0].equals("end")){
				int[] coords = {Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])};
				coords = fixCoord(coords[0], coords[1]);
				int i = coords[0];
				int j = coords[1];
				if(BOARD[i][j] == dead) BOARD[i][j] = alive;
				else BOARD[i][j] = dead;
			} else {
				break;
			}
		}
	}
	
	static int[] fixCoord(int i, int j){
		if(i < 0) i = i - i;
		else if(i >= y) i = i - (i - (y-1));
		if(j < 0) j = j - j;
		else if(j >= x) j = j - (j - (x-1));
		int[] fixed = {i, j};
		return fixed;
	}
	
	static char[][] clearBoard(char[][] board){
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				board[i][j] = dead;
			}
		}
		return board;
	}
	
	static char[][] fillBoard(char[][] board){
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				board[i][j] = alive;
			}
		}
		return board;
	}
	
	static void printBoard(){
		for(int i = 0; i < 2*x+3; i++) System.out.print("-");
		System.out.println("");
		for(int i = 0; i < y; i++){
			System.out.print("| ");
			for(int j = 0; j < x; j++){
				System.out.print(BOARD[i][j] + " ");
			}
			System.out.print("|\n");
		}
		for(int i = 0; i < 2*x+3; i++) System.out.print("-");
		System.out.println("");
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to John Conway's Game of Life!\nChoose a configuration: 'glider', 'smallexploder', "
							+ "'exploder', '10cellrow', 'spaceship', 'tumbler', 'glidergun', or 'custom'");
		String q = br.readLine().toLowerCase().trim();
		if(q.equals("custom")){
			System.out.println("How big do you want your board? (Min: 2 x 2, Max: as big as your computer can handle)\nLength:");
			y = Integer.parseInt(br.readLine());
			System.out.print("Width:\n");
			x = Integer.parseInt(br.readLine());
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			System.out.println("How many cells do you want to be alive?");
			int numAlive = Integer.parseInt(br.readLine().trim());
			if(numAlive != 0){
				System.out.println("Type in the coordinates of the cells you want alive.\nEx.\n0 0 (fills the top left corner)\n0 " 
				+ (x-1) + " (fills the top right corner)");
				for(int i = 0; i < numAlive; i++){
					String[] tokens = br.readLine().trim().split(" ");
					BOARD[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] = alive;
				}
			}
		} else if(q.equals("glider")){
			y = 30;
			x = 30;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[3][1] = BOARD[3][2] = BOARD[3][3] = BOARD[2][3] = BOARD[1][2] = alive;
		} else if(q.equals("smallexploder")){
			y = 30;
			x = 30;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[13][13] = BOARD[14][13] = BOARD[15][14] = BOARD[12][14] = BOARD[13][15] = BOARD[14][15] = BOARD[13][14] = alive;
		} else if(q.equals("exploder")){
			y = 40;
			x = 40;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[17][17] = BOARD[18][17] = BOARD[19][17] = BOARD[20][17] = BOARD[21][17] = BOARD[17][19] = 
					BOARD[17][21] = BOARD[18][21] = BOARD[19][21] = BOARD[20][21] = BOARD[21][21] = BOARD[21][19] = alive;
		} else if(q.equals("10cellrow")){
			y = 20;
			x = 30;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[9][13] = BOARD[9][12] = BOARD[9][11] = BOARD[9][10] = BOARD[9][9] = 
					BOARD[9][14] = BOARD[9][15] = BOARD[9][16] = BOARD[9][17] = BOARD[9][18] = alive;
		} else if(q.equals("spaceship")){
			y = 14;
			x = 60;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[5][2] = BOARD[5][3] = BOARD[5][4] = BOARD[5][5] = BOARD[6][1] = 
					BOARD[6][5] = BOARD[7][5] = BOARD[8][1] = BOARD[8][4] = alive;
		} else if(q.equals("tumbler")){
			y = 20;
			x = 20;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[7][7] = BOARD[7][8] = BOARD[7][10] = BOARD[7][11] = BOARD[8][7] = BOARD[8][8] = 
					BOARD[8][10] = BOARD[8][11] = BOARD[9][8] = BOARD[9][10] = BOARD[10][6] = BOARD[10][8] = 
					BOARD[10][10] = BOARD[10][12] = BOARD[11][6] = BOARD[11][8] = BOARD[11][10] = 
					BOARD[11][12] = BOARD[12][6] = BOARD[12][7] = BOARD[12][11] = BOARD[12][12] = alive;
		} else if(q.equals("glidergun")){
			y = 50;
			x = 100;
			BOARD = new char[y][x];
			BOARD = clearBoard(BOARD);
			BOARD[4][24] = BOARD[4][25] = BOARD[4][35] = BOARD[4][36] = BOARD[5][23] = 
					BOARD[5][25] = BOARD[5][35] = BOARD[5][36] = BOARD[6][1] = BOARD[6][2] = 
					BOARD[6][10] = BOARD[6][11] = BOARD[6][23] = BOARD[6][24] = BOARD[7][1] = 
					BOARD[7][2] = BOARD[7][9] = BOARD[7][11] = BOARD[8][9] = BOARD[8][10] = 
					BOARD[8][17] = BOARD[8][18] = BOARD[9][17] = BOARD[9][19] = BOARD[10][17] = 
					BOARD[11][36] = BOARD[11][37] = BOARD[12][36] = BOARD[12][38] = BOARD[13][36] = 
					BOARD[16][25] = BOARD[16][26] = BOARD[16][27] = BOARD[17][25] = BOARD[18][26] = alive;
		} 
		System.out.println("This is your board:\n");
		printBoard();
		while(true){
			System.out.println("Type 'next' to play one step, 'play' to play multiple steps, 'edit' to edit the board, "
								+ "'clear' to make all cells dead, \n'fill' to make all cells alive, 'print' to print the board, "
								+ "or 'end' to end the simulation:");
			String query = br.readLine().toLowerCase().trim();
			if(query.equals("next")){
				play();
			} else if(query.equals("play")){
				System.out.println("How many steps to play?");
				int steps = Integer.parseInt(br.readLine());
				System.out.println("How fast should it animate? (Choices: 'superfast', 'veryfast', 'fast', "
									+ "'normal', 'slow', 'veryslow', 'superslow') *note: 'superfast' may be buggy, "
									+ "depending on the computer!");
				String speed = br.readLine().toLowerCase();
				int sleepTime = 0;
				if(speed.equals("superfast")) sleepTime = 100;
				else if(speed.equals("veryfast")) sleepTime = 300;
				else if(speed.equals("fast")) sleepTime = 600;
				else if(speed.equals("normal")) sleepTime = 900;
				else if(speed.equals("slow")) sleepTime = 1200;
				else if(speed.equals("veryslow")) sleepTime = 1500;
				else if(speed.equals("superslow")) sleepTime = 2000;
				for(int i = steps; i > 0; i--){
					play();
					Thread.sleep(sleepTime);
				}
			} else if(query.equals("edit")){
				editBoard(br);
			} else if(query.equals("clear")){
				BOARD = clearBoard(BOARD);
			} else if(query.equals("fill")){
				BOARD = fillBoard(BOARD);
			} else if(query.equals("print")){
				printBoard();
			} else if(query.equals("end")) {
				break;
			} else {
				System.out.println("Unrecognized command, please enter one of the commands below:");
			}
		}
		System.out.println("Thanks for playing!");
	}
}
