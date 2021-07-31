public class BlackjackHand extends Hand 
{
	public int getBlackjackValue()
	{
		int value = 0;      
		int aces = 0;
		
		for (int i = 0;  i < getCardCount();  i++) 
		{ 
			int cardVal = getCard(i).pointValue();  
			if (getCard(i).rank().equals("ace")) 
			{
				aces++;
			}
			value = value + cardVal;
		}

		while (aces > 0)
		{
			if (value > 21) { value = value - 10; }
			aces--;
		}
		System.out.println("blackjack value : " + value);
		return value;
	}

	public int dealerInitalValue()
	{
		int value = getCard(1).pointValue();
		if (value == 1) { value = 11; }
		System.out.println("dealer int value: " + value);
		return value;
	}

	public boolean hasBlackjack() 
	{ 
		boolean hasAce = false;
		boolean hasTen = false;
		for (int i = 0; i < 2; i++)
		{
			if (getCard(i).pointValue() == 10)
			{ hasTen = true; }
			if (getCard(i).pointValue() == 11)
			{ hasAce = true; }
		}
		return (hasAce && hasTen);
	}
	
	public int checkWinner(BlackjackHand aHand)
	{
		if (this.getBlackjackValue() > aHand.getBlackjackValue())
			return 2;
		else if (this.getBlackjackValue() < aHand.getBlackjackValue())
			return -1;
		else
			return 0;
	}
}