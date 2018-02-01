package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public interface Line {

	void WriteLine(ArrayList<String[]> outputList) throws IOException;
	void WriteMesostic(ArrayList<String[]> outputList) throws IOException;
	void WriteChapter(ArrayList<String[]> outputList) throws IOException;
	
	
}
