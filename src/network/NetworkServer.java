package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

import game.Board;
import game.MultiPlayerServer;

public class NetworkServer extends Network {

	private MultiPlayerServer multiPlayerServer;
	private ServerSocket serverSocket = null;

	private ArrayList<Board> boardsToSendAtInit = new ArrayList<>();

	public NetworkServer(MultiPlayerServer multiPlayerServer) {
		this.multiPlayerServer = multiPlayerServer;
	}


	@Override
	public void connect() {
		disconnect();
		try {
			serverSocket = new ServerSocket(PORT);

			Thread receive = new Thread(() -> {
				try {
					System.out.println("Waiting for client...");
					socket = serverSocket.accept();
				} catch (IOException e) {
					System.err.println("Could not accept client socket with server socket");
					e.printStackTrace();
					disconnect();
					return;
				}

				try {
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream());
					out.flush();
				} catch (IOException e) {
					System.err.println("Error while getting streams");
					e.printStackTrace();
					disconnect();
					return;
				}

				this.boardsToSendAtInit.forEach(this::sendBoard);

				try {
					while(true) {
						Board board = (Board) in.readObject();
						System.out.println("Board received:");
						board.prettyPrint();
						this.multiPlayerServer.onBoardReceive(board);

					}
				} catch (Exception e) {
					System.err.println("Some error occurred when receiving board");
					e.printStackTrace();
				} finally {
					disconnect();
				}


			});
			receive.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + PORT);
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() {
		super.disconnect();
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close server socket");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendBoard(Board board) {
		if (this.out == null) {
			boardsToSendAtInit.add(board);
		} else {
			super.sendBoard(board);
		}
	}
}
