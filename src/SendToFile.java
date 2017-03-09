import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SendToFile {

	/**
	 * This class is to send the chat information to the file
	 * The file name and writers are declared below
	 */

	private String fileName = "log.txt";
	BufferedWriter bw = null;
	FileWriter fw = null;

	/**
	 * In chatLog the file writer writes to the fileName given above
	 * Then the buffered writer feeds into file writer
	 * The buffered writer sends the chatLine sent in as a parameter 
	 * then a new line is added so each line in the chat has a line in the file.
	 * Once the file is written to, the writers are closed
	 * @param chatLine
	 */
	public void chatLog(String chatLine) {
		
		try{
			fw = new FileWriter(fileName, true);
			bw = new BufferedWriter(fw);
			bw.write(chatLine + "\r\n");
		} catch (IOException e){
			System.err.println("File Writer messed up");
		}  finally {

			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}