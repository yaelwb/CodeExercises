package threads;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordCountManager {
	private static final int NTHREDS = 10;
	HashMap<String, Integer> occurences = new HashMap<String, Integer>();

	public static void main(String[] args) {
		WordCountManager manager = new WordCountManager();
		manager.scanFiles();
	}

	private void mergeTempResult(HashMap<String, Integer> tempResult) {
		for(Entry<String, Integer> entry: tempResult.entrySet()) {
			Integer currCount = occurences.get(entry.getKey());
			if(currCount == null) 
				currCount = 0;
			occurences.put(entry.getKey(), currCount+entry.getValue());
		}
	}
	
	private void scanFiles() {
		Path target = Paths.get("text/filenames.txt");
		List<String> filenames = null;
		try {
			filenames = Files.readAllLines(target, StandardCharsets.US_ASCII);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		List<Future<HashMap<String, Integer>>> list = new ArrayList<Future<HashMap<String, Integer>>>();

		for(String filename:filenames) {
			Callable<HashMap<String, Integer>> worker = new ScanFileCallable("text/" +filename);
			Future<HashMap<String, Integer>> submit = executor.submit(worker);
			list.add(submit);
		}
		
		for (Future<HashMap<String, Integer>> future : list) {
			try {
				mergeTempResult(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	    System.out.println(occurences);
	    executor.shutdown();
	}

}
