/*
 * An object of type Hand represents a hand of cards.  
 * The maximum number of cards in the hand can be specified in the constructor, but by default
 * is 5.  A utility function is provided for computing the value of the
 * hand in the game of Blackjack.
 */
import java.util.Vector;
public class Hand 
{
	private Vector hand;
	
	public Hand() 
	{
		hand = new Vector();
	}
	
	public void clear() 
	{
		hand.removeAllElements();
	}
	
	public void addCard(Card c) 
	{
		if (c!= null)
			hand.addElement(c);
	}
	
	public void removeCard(Card c) 
	{
		hand.removeElement(c);
	}
	
	public void removeCard(int position) 
	{
		if (position>= 0 && position< hand.size())
			hand.removeElementAt(position);
	}
	
	public int getCardCount() 
	{
		return hand.size();
	}
	
	public Card getCard(int position) 
	{
		if (position >= 0 && position < hand.size())
			return (Card) hand.elementAt(position);
		else
			return null;
	}
	
	public void sortBySuit() 
	{
		Vector newHand= new Vector();
		while (hand.size() > 0) 
		{
			int pos= 0;
			Card c = (Card) hand.elementAt(0);
			for (int i = 1; i < hand.size(); i++) 
			{
				Card c1= (Card) hand.elementAt(i);
				if (c1.suit().compareTo(c.suit()) < 0 || (c1.suit() == c.suit() && c1.pointValue() < c.pointValue())) 
				{
					pos= i;
					c = c1;
				}
			}
			hand.removeElementAt(pos);
			newHand.addElement(c);
		}
		hand = newHand;
	}
	
	public void sortByValue() 
	{
		Vector newHand = new Vector();
		while (hand.size() > 0) 
		{
			int pos = 0;
			Card c = (Card) hand.elementAt(0);  
			for ( int i = 1; i < hand.size(); i++) 
			{
				Card c1 = (Card) hand.elementAt(i);
				if (c1.pointValue() < c.pointValue() || (c1.pointValue() == c.pointValue() && c1.suit().compareTo(c.suit()) < 0)) 
				{
					pos= i;
					c = c1;
				}
			}
			hand.removeElementAt(pos);
			newHand.addElement(c);
		}
		hand= newHand;
	}
}