import java.util.Arrays;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		ChainedTable myTable = new ChainedTable();

		System.out.println("Constructing chained hash table from text file...");
		myTable.getList();
		System.out.println("Complete.\n");
		
		myTable.walker(0);
		
		System.out.print("Transylavania occurs: ");
		myTable.search("transylvania");
		System.out.print("herker occurs: ");
		myTable.search("harker");
		System.out.print("renfield occurs: ");
		myTable.search("renfield");
		System.out.print("vampire occurs: ");
		myTable.search("vampire");
		System.out.print("expostulate occurs: ");
		myTable.search("expostulate");
		System.out.print("flangoriously occurs: ");
		myTable.search("flangoriously");
		System.out.println("Longest chain is " + myTable.getLongestChainInList());
		System.out.println("Shortest chain is " + myTable.getShortestChainInList());
		System.out.println("Total distinct words: " + myTable.getNumberOfUniqueWords());
		System.out.println("Word at head of bucket 0: " + myTable.theChainedTable[0].getWord());
		System.out.println("Word at head of bucket 27: " + myTable.theChainedTable[27].getWord());
		System.out.println("Word at head of bucket 43: " + myTable.theChainedTable[43].getWord());
		System.out.println("Word at head of bucket 126: " + myTable.theChainedTable[126].getWord());
		System.out.println("Text contains: " + myTable.getTotalWordCount() + " total words");
		System.out.println("Most frequent word is: \"" + myTable.getMostFrequentWord() + "\" occuring " + myTable.getMostFrequentWordCount() + " times.");
		System.out.println("__________________________");
	
		Scanner kb = new Scanner(System.in);
		String type ="";
	
		while(!type.toLowerCase().matches("exit")){
			System.out.println("What would you like to search for?");
					
			type = kb.nextLine();
			myTable.search(type);
		}
	}
}
