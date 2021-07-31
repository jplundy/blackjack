public class BlackjackBoard
{
	private Deck deck;
	private BlackjackHand myHand;
	private BlackjackHand dealerHand;
	private int myTotal;
	private int dealerTotal;
	private int myMoney;
	private int myBet;
	private int winner;
	
	private String message;

	private static final String[] RANKS = { "ace", "2", "3", "4", "5", "6", "7",
											"8", "9", "10", "jack", "queen", 
											"king" };
	
	private static final String[] SUITS = { "spades", "clubs", "hearts",
											"diamonds" };
	
	private static final int[] POINT_VALUES = { 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
												10, 10, 10 };
	
	public BlackjackBoard()
	{
		deck = new Deck(RANKS, SUITS, POINT_VALUES);
		myHand = new BlackjackHand();
		dealerHand = new BlackjackHand();
		myTotal = 0;
		dealerTotal = 0;
		myMoney = 100;
		myBet = 0;
		message = "Place your bet.";
	}
	
	public void bet(boolean gameInProgress)
	{
		if (!gameInProgress)
		{
			if (myMoney <= 0)
			{
				message = "You cannot bet any more.";
			}
			else
			{ 
				myBet = myBet + 1;
				myMoney = myMoney - 1;
				message = "Continue to bet or press \"Deal New\".";
			}
		}
		else
		{
			message = "You cannot change your bet.";
		}
	}

	public void betFive(boolean gameInProgress)
	{
		if (!gameInProgress)
		{
			if (myMoney <= 4)
			{
				message = "You cannot bet any more.";
			}
			else
			{ 
				myBet = myBet + 5;
				myMoney = myMoney - 5;
				message = "Continue to bet or press \"Deal New\".";
			}
		}
		else
		{
			message = "You cannot change your bet.";
		}
	}
	
	public boolean dealNewGame(boolean gameInProgress)
	{
		if (!gameInProgress && myBet > 0)
		{
			myHand.clear();
			dealerHand.clear();
			myTotal = 0;
			dealerTotal = 0;
			
			message = "";
			gameInProgress = true; 
			
			deck.shuffle();
			myHand.addCard(deck.deal());
			dealerHand.addCard(deck.deal());
			myHand.addCard(deck.deal());
			dealerHand.addCard(deck.deal());
			
			if (dealerHand.hasBlackjack()) 
			{ 
				if (myHand.hasBlackjack()) 
				{ 
					push();
					gameInProgress = false;
				} 
				else
				{

					dealerBlackjack();
					gameInProgress = false;
				}
			}
			else 
			{ 
				if (myHand.hasBlackjack()) 
				{ 
					myBlackjack();
					gameInProgress = false;
				} 
				else 
				{ 
					myTotal = myHand.getBlackjackValue();
					dealerTotal = dealerHand.dealerInitalValue();
				}
			}
		}
		else if (!gameInProgress && myBet == 0)
		{
			message = "Please place a bet first.";
		}
		else
		{
			message = "You must finish this game first.";
		}
		return gameInProgress;
	}

	public void clear(boolean gameInProgress)
	{
		if (gameInProgress)
		{
			message = "You must finish this game first.";
		}
		else
		{
			myHand.clear();
			dealerHand.clear();
			myBet = 0;
			myTotal = 0;
			dealerTotal = 0;
			message = "Place your bet";
		}
	}

	public boolean hit(boolean gameInProgress)
	{
		if (gameInProgress)
		{ 
			myHand.addCard(deck.deal());
			myTotal = myHand.getBlackjackValue();
			message = "";
			
			if (myTotal > 21) 
			{ 
				message = "Bust. "; 
				dealerWins();
				return false;
			}
			else if (myTotal == 21)
			{
				iWin();
				return false;
			}
		}
		else
		{
			message = "You must bet and deal first.";
		}
		return true;
	}

	public boolean stand(boolean gameInProgress)
	{
		if (gameInProgress)
		{ 
			dealerTotal = dealerHand.getBlackjackValue();
			myTotal = myHand.getBlackjackValue();
			while (dealerTotal < 17) 
			{ 
				dealerHand.addCard(deck.deal());
				dealerTotal = dealerHand.getBlackjackValue();
				
				if (dealerTotal > 21) 
				{
					winner = 2; 
					message = "Dealer busts. "; 
					iWin();
					return false; 
				} 
			} 
			message = ""; 
			winner = myHand.checkWinner(dealerHand);
			if (winner == -1)
			{ 
				dealerWins();
			} 
			else 
				if (winner == 2) 
				{ 
					iWin();
				} 
				else 
				{
					push();	
				}
		}
		else
		{
			message = "You must bet and deal first.";
		}		
		return false;
	}
	
	private void payout() 
	{
		myMoney = myMoney + (winner * myBet);
		winner = 0;
		myBet = 0;

	}
	
	private void dealerBlackjack()
	{
		message = "Dealer has Blackjack! ";
		this.dealerWins();
	}
	
	private void myBlackjack()
	{
		message = "You have Blackjack! ";
		this.iWin();
	}
	
	private void dealerWins()
	{
		message = message + "Dealer wins.";
		winner = 0;
		this.payout();
	}

	private void iWin()
	{
		message = message + "You win.";
		winner = 2;
		this.payout();
	}

	private void push()
	{
		message = "Push.";
		winner = 1;
		this.payout();
	}

	public String getMessage() 
	{ 
		return message;
	} 
	
	public int getMyMoney()
	{ 
		return myMoney;
	}
	
	public int getMyBet() 
	{ 
		return myBet;
	} 
	
	public BlackjackHand getMyHand()
	{ 
		return myHand;
	} 
	
	public BlackjackHand getDealerHand() 
	{ 
		return dealerHand;
	} 
	
	public Card getCard(BlackjackHand theHand, int location) 
	{ 
		return theHand.getCard(location);
	} 
}