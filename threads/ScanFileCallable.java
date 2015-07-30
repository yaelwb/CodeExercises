package threads;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

public class ScanFileCallable implements Callable<HashMap<String, Integer>> {

	private String filename;
	private HashMap<String, Integer> occurences = new HashMap<String, Integer>();
	
	public ScanFileCallable(String filename) {
		this.filename = filename;
	}
	@Override
	public HashMap<String, Integer> call() throws Exception {
		File file = new File(filename);
		List<String> lines = null;
		lines = FileUtils.readLines(file);
		
		for(String line:lines) {
			if(line.length() > 0) {
				String[] res = line.trim().split("(\\W+)");
				for (String word : res) {
					Integer count = occurences.get(word.toLowerCase());
					if (count == null)
						count = 0;
					occurences.put(word.toLowerCase(), ++count);
				}
			}
		}
		return occurences;
	}

}
