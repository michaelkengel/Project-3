import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainedTable {
	
	String workingDirectory = System.getProperty("user.dir"); // Get the path of the user
	Path thePath = Paths.get(workingDirectory, "/nodeSaver"); // Playing with path
	List<String> temporaryWordLibrary = null;
	private static final int tableSize = 127;
	Node[] theChainedTable = new Node[tableSize];
	
	ArrayList<String> wordArray = new ArrayList<String>();



	public int hashFunction(String word){
		int asciiValue = 0;
		char[] wordArray = word.toLowerCase().toCharArray();
		for(char x:wordArray){
			asciiValue += (int) x;
		}
		return asciiValue % 127;
	}

	public void search(String word){
		
		int startNode = hashFunction(word);
		if (theChainedTable[startNode] == null){
			System.out.println("No start node");
		}
		else{
			recursiveFind(word, theChainedTable[startNode]);
		}
	}
	
	private boolean recursiveFind(String word, Node nodeAt){ // Because recursion is fun
		if (word.matches(nodeAt.getWord())){
			System.out.println("found");
			return true;
		}
		if (nodeAt.getNext() == null){
			System.out.println("Not found");
			return false;
		}
		else return recursiveFind(word, nodeAt.getNext());
	}
	
	public void addRecord(String wordToAdd){

		int index = hashFunction(wordToAdd);

		//Check if bucket is empty
		if (theChainedTable[index] == null){
			theChainedTable[index] = new Node(wordToAdd);
		}
		else{

			//If its not, step through

			Node nodePointer = theChainedTable[index];

			while (nodePointer != null){
				// Look for node
				if (nodePointer.getWord().matches(wordToAdd)){
					System.out.println("Found the word " + wordToAdd);
				}
				// Else if we are at the end

				if (nodePointer.getNext() == null){
					nodePointer.setNext(new Node(wordToAdd));
					System.out.println("Added to list index " + index);
					break;
				}
				nodePointer = nodePointer.getNext();
			}
		}
	}
	
	private void removePunctuationAndStack(){
		String outPut; // Remove all non number or letters
		int index = 0;
		while(!temporaryWordLibrary.isEmpty()){
			outPut = temporaryWordLibrary.get(index).toString();

			outPut = outPut.replaceAll("[^a-zA-Z0-9]","");
			if (!outPut.isEmpty()){
				wordArray.add(outPut.toLowerCase());
			}
			temporaryWordLibrary.remove(index);
		}
	}

	public void getList(){

		try(Stream<String> fileReadByLine = Files.lines(thePath)){		
			temporaryWordLibrary = fileReadByLine
					.map(eachWord -> eachWord.split("\\s+"))
					.flatMap(Arrays::stream)
					.collect(Collectors.toList());
		}
		catch(Exception e){
			System.out.println("Error " + e.getMessage());
		}
		removePunctuationAndStack();	
	}

	
}
