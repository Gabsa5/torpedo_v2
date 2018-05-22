package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.Board;

public abstract class Network {
	protected Socket socket = null;
	protected ObjectOutputStream out = null;
	protected ObjectInputStream in = null;
	protected static final int PORT = 2524;

	public abstract void connect();

	public void disconnect() {
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			System.err.println("Error while closing connection");
			e.printStackTrace();
		}
	}

	public void sendBoard(Board board) {
		if (out == null) {
			System.out.println("Tried to send data but not initialized yet.");
			return;
		}

		System.out.println("Sending Board:");
		board.prettyPrint();

		try {
			out.writeObject(board);
			out.flush();
			out.reset();
		} catch (IOException e) {
			System.err.println("Error when trying to send my board");
			e.printStackTrace();
		}
	}

}
