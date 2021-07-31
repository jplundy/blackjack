public class BlackjackGUIRunner 
{
	public static void main(String[] args) 
	{
		BlackjackBoard board = new BlackjackBoard();
		BlackjackGUI gui = new BlackjackGUI(board);
		gui.displayGame();
	}
}