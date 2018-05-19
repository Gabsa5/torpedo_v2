package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	private ArrayList<Player> players;
	private int boardSize;
	private boolean multi;
	
	
	public Game(){
		
		this.multi=true;
		this.boardSize=10;
		this.players=new ArrayList<>();
		this.players.add(new Player("P1",true, boardSize));
		if(this.multi){
		this.players.add(new Player("P2", false, boardSize));
		}else{
			this.players.add(new Player("AI",false, boardSize));
		}
		
	}
	
	public Game(int boardSize, boolean multi){
		this.multi=multi;
		this.boardSize=boardSize;
		this.players=new ArrayList<>();
		this.players.add(new Player("P1",true, boardSize));
		if(this.multi){
		this.players.add(new Player("P2", false, boardSize));
		}else{
			this.players.add(new Player("AI",false, boardSize));
		}
		
		
		
		
	}
	
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public void shipPlacement(){
		Scanner in = new Scanner(System.in);
		for(int i=0; i<this.players.size();i++){
			System.out.println("It is "+this.getPlayers().get(i).getName()+"'s turn");
			while(this.players.get(i).getPlayerBoard().getShips().size()<1){
				System.out.println("Type in the start index of the ship");
				int startIndex = in.nextInt();
				System.out.println("Type in the end index of the ship");
				int endIndex = in.nextInt();
				this.players.get(i).placeShip(startIndex, endIndex);
				
			}
		}
		
		
	}
	
	public void gamePlay(){
		System.out.println("Let's play some shooting");
		Player P1 = this.getPlayers().get(0);
		Player P2 = this.getPlayers().get(1);
		Scanner in = new Scanner(System.in);
		
		while(P1.getLife()>0 && P2.getLife()>0){
			System.out.println("It is Player 1's turn");
			int shootindex = in.nextInt();
			P2.shoot(shootindex);
			if(P2.getLife()>0){
			System.out.println("It is Player 2's turn");
			shootindex = in.nextInt();
			P1.shoot(shootindex);
			}else{
				
			}
		}
		if(P1.getLife()==0){
			System.out.println("P2 is the winner");
		}else{
			System.out.println("P1 is the winner");
		}
		
	}
	
	
	
	
	
}