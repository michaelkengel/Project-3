public class Node {

	public static final int NEVER_USED = 0;
	public static final int PREVIOUSLY_USED = 1;
	private Node next;
	private String word;
	private int wordCount;
	private boolean used = false;

	public Node(String word){
		this.word = word.toLowerCase();
		this.used = true;
	}
	
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public boolean getUsed() {
		return used;
	}
	public void setUsed() {
		this.used = true;
	}
	public int getWordCount(){
		return wordCount;
	}
	public void incrementWordCount(){
		wordCount++;
	}

}