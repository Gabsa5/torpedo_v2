package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import game.Board;
import game.MultiPlayerClient;

public class NetworkClient extends Network {

	private MultiPlayerClient multiPlayerClient;
	private String serverIP;

	public NetworkClient(MultiPlayerClient multiPlayerClient, String serverIP) {
		this.multiPlayerClient = multiPlayerClient;
		this.serverIP = serverIP;
	}

	@Override
	public void connect() {
		disconnect();
		try {
			socket = new Socket(serverIP, PORT);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();

			Thread receiver = new Thread(() -> {
				System.out.println("Waiting for board...");
				try {
					while(true) {
						Board board = (Board) in.readObject();
						System.out.println("Board received:");
						board.prettyPrint();
						this.multiPlayerClient.onBoardReceive(board);
					}
				} catch (Exception e) {
					System.err.println("Some error occurred when receiving board");
					e.printStackTrace();
				} finally {
					disconnect();
				}
			});
			receiver.start();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Couldn't get IO for the connection.");
			e.printStackTrace();
		}

	}
}
