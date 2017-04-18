package ai.legendary.squad.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	public static void log(String message) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("output.txt", true), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write(message);
		out.close();
	}
}
