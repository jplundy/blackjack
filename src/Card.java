public class Card
{
	private String suit;
	private String rank;
	private int pointValue; 
	private boolean isAce;
	
	public Card(String cardRank, String cardSuit, int cardPointValue)
	{
		suit = cardSuit;
		rank = cardRank;
		pointValue = cardPointValue; 
		if (cardRank.equals("ace"))
			isAce = true;
		else
			isAce = false;
	}

	public String suit() { return suit; }
	public String rank() { return rank; }
	public int pointValue() { return pointValue; }
	public boolean matches(Card otherCard) { return ((otherCard.pointValue() == this.pointValue()) && (otherCard.suit().equals(this.suit())) && (otherCard.rank().equals(this.rank()))); }
	public String toString() { return (this.rank + " of " + this.suit + " (point value = " + this.pointValue + ")"); }
	public boolean isAce() { return isAce; }
}