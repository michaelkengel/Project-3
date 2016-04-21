import java.util.Arrays;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		

		ChainedTable myTable = new ChainedTable();

		System.out.println("Constructing chained hash table from text file...");
		myTable.getList();
		System.out.println("Complete.\n");
		
		myTable.walker(0);
		System.out.println(myTable.getLongestChainInList() + " Longest chain");
		System.out.println(myTable.getShortestChainInList() + " Shortest chain");
		System.out.println("\""+myTable.getMostFrequentWord() + "\" is the most frequent word");
		System.out.println("It was used " + myTable.getMostFrequentWordCount() + " times.");	
		System.out.println(myTable.getTotalWordCount() + " Total number of words");
		System.out.println(myTable.getNumberOfUniqueWords() + " Number of unique words");
		
		
		
		System.out.println("\nPlease note contractions are mapped differently\r"
					+ "ie. Harker vs Harker's\n");

		Scanner kb = new Scanner(System.in);
		String type ="";
	
		while(!type.toLowerCase().matches("exit")){
			System.out.println("What would you like to search for?");
					
			type = kb.nextLine();
			myTable.search(type);
		}
	}
}
