package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	private ArrayList<Board> boards;
	
	public Game(int boardSize){
		this.boards=new ArrayList<>();
		this.boards.add(new Board(boardSize));
		this.boards.add(new Board(boardSize));
	}

	public Board getSinglePlayerBoard() {
		return boards.get(0);
	}

	public Board getOtherBoard() {
		return boards.get(1);
	}

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public void setBoards(ArrayList<Board> boards) {
		this.boards = boards;
	}
}