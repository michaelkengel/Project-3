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
	List<String> temporaryWordLibrary = null; // Will load all text to a mapped list
	private static final int tableSize = 127; // Set the size of the chained hash table
	Node[] theChainedTable = new Node[tableSize]; // Here is the actual table

	/// Walkers library

	private int uniqueWords= 0;
	private String longestWord = "";
	private int longestChainInList = 0;
	private int shortestChainInList = 99999999;
	private int totalWordCount = 0;
	private String mostFrequentWord;
	private int mostFrequentWordCount = 0;


	/// End walkers library


	private int hashFunction(String word){ // Here is the hash function
		int asciiValue = 0;
		char[] wordArray = word.toLowerCase().toCharArray();
		for(char x:wordArray){
			asciiValue += (int) x;
		}
		// Return ascii values mod 127
		return asciiValue % 127; 
	}


	// Search function calls recursive search
	public boolean search(String word){
		word = word.toLowerCase(); // Just in case
		int startNode = hashFunction(word);
		// See if bucket is empty
		if (theChainedTable[startNode] == null){
			System.out.println("Not found");
			return false;
		}
		else{ // Call search function
			return recursiveFind(word, theChainedTable[startNode]);
		}
	}

	// Recursive search for node, if found list the iterations
	private boolean recursiveFind(String word, Node nodeAt){ 
		if (word.matches(nodeAt.getWord())){
			System.out.println(nodeAt.getWordCount() + " times");
			return true;
		}
		if (nodeAt.getNext() == null){
			System.out.println("Not found");
			return false;
		} // Recurse
		else return recursiveFind(word, nodeAt.getNext());
	}

	// Add record function
	private void addRecord(String wordToAdd){

		wordToAdd = wordToAdd.toLowerCase();
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
					nodePointer.incrementWordCount();
					break;
				}
				else{
					// Else if we are at the end
					if (nodePointer.getNext() == null){
						// Set to head
						Node pointerOldHead = theChainedTable[index];
						theChainedTable[index] = new Node(wordToAdd);
						theChainedTable[index].setNext(pointerOldHead);
						break;
					}
				}
				nodePointer = nodePointer.getNext();
			}
		}
	}

	// Walk the table and get values
	public boolean walker(int index){

		Node pointerNode = theChainedTable[index];
		int chainLength = 0;

		while(pointerNode != null){
			totalWordCount += pointerNode.getWordCount();
			// Increment counters 
			chainLength++;
			uniqueWords++;
			// Set longest word if hit
			if (pointerNode.getWord().length() > longestWord.length()){
				longestWord = pointerNode.getWord();
			}
			// Set most frewuent if hit
			if (pointerNode.getWordCount() > mostFrequentWordCount){
				mostFrequentWord = pointerNode.getWord();
				mostFrequentWordCount = pointerNode.getWordCount();
			}
			pointerNode = pointerNode.getNext();
		}
		// Check the current chain length for longest
		if (chainLength > longestChainInList){
			longestChainInList = chainLength;
		}
		// Check the chain length for shortest
		if (chainLength < shortestChainInList){
			shortestChainInList = chainLength;
		}
		
		// If theres more to go, keep walking
		if (index < 126){
			return walker(index+1);  // Recurse 
		}
		return false;		
	}	

	private void removePunctuationAndSendToHashTable(){
		String outPut; // Remove all non number or letters
		int index = 0;
		while(!temporaryWordLibrary.isEmpty()){
			outPut = temporaryWordLibrary.get(index).toString();
			outPut = outPut.replaceAll("[^a-zA-Z0-9]","");
			if (!outPut.isEmpty()){
				addRecord(outPut.toLowerCase());
			}
			temporaryWordLibrary.remove(index);
		}
	}

	// Will read from the list using java 8 features
	public void getList(){
		// Create a stream
		try(Stream<String> fileReadByLine = Files.lines(thePath)){		
			temporaryWordLibrary = fileReadByLine
					// Here is java 8 features
					.map(eachWord -> eachWord.split("\\s+"))
					.flatMap(Arrays::stream) 
					.collect(Collectors.toList());
		}
		catch(Exception e){
			System.out.println("Error " + e.getMessage());
		}// Send to punctuation function
		removePunctuationAndSendToHashTable();	
	}

	public int getNumberOfUniqueWords(){
		return uniqueWords;
	}

	public String getLongestWord() {
		return longestWord;
	}

	public int getLongestChainInList() {
		return longestChainInList;
	}

	public int getShortestChainInList() {
		return shortestChainInList;
	}

	public int getTotalWordCount() {
		return totalWordCount;
	}

	public String getMostFrequentWord() {
		return mostFrequentWord;
	}

	public int getMostFrequentWordCount() {
		return mostFrequentWordCount;
	}
}

