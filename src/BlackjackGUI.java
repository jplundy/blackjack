import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BlackjackGUI extends JFrame implements ActionListener
{
	private static final int DEFAULT_HEIGHT= 584;
	private static final int DEFAULT_WIDTH = 751;
	private static final int CARD_WIDTH = 73;
	private static final int CARD_HEIGHT = 97;
	private JPanel panel;
	
	private JButton hit;
	private JButton stand;
	private JButton bet;
	private JButton deal;
	private JButton betFive;
	
	private Point[] dealerCardCoords;
	private Point[] myCardCoords;
	
	private JLabel[] displayDealer;
	private JLabel[] displayMine;
	private JLabel displayMyMoney;
	private JLabel displayMyBet;
	private JLabel displayMyValue;
	private JLabel displayDealerValue;
	
	private JLabel messageToUser;
	private String output;
	
	private BlackjackBoard board;
	private boolean gameInProgress;
	private boolean showingBoth;
	
	private Color background = new Color(0, 102, 0);
	
	public BlackjackGUI(BlackjackBoard thisBoard)
	{
		showingBoth = false;
		board = thisBoard;
		output = board.getMessage();
		gameInProgress = false;
		myCardCoords = new Point[7];
		dealerCardCoords = new Point[7];
		for(int i = 0; i < myCardCoords.length; i++)
		{
			dealerCardCoords[i] = new Point(30 + 103 * i, 30);
			myCardCoords[i] = new Point(30 + 103 * i, 337);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initDisplay();
		repaint();
	}
	
	public void displayGame() 
	{
		java.awt.EventQueue.invokeLater(new Runnable() { 
		public void run() 
			{
				setVisible(true);
			}
		});
	}
	
	public void repaint() 
	{
		messageToUser = new JLabel(board.getMessage());
		panel.add(messageToUser);
		messageToUser.setFont(new Font("SansSerif", Font.BOLD, 20));
		messageToUser.setOpaque(true);
		messageToUser.setBackground(background);
		messageToUser.setForeground(Color.red);
		messageToUser.setBounds(30, 217, 600, 30);
		
		displayMyMoney = new JLabel("Your money: " + board.getMyMoney());
		panel.add(displayMyMoney);
		displayMyMoney.setFont(new Font("SansSerif", Font.BOLD, 20));
		displayMyMoney.setOpaque(true);
		displayMyMoney.setBackground(background);
		displayMyMoney.setForeground(Color.red);
		displayMyMoney.setBounds(30, 250, 600, 30);

		displayMyBet = new JLabel("Current bet: " + board.getMyBet());
		panel.add(displayMyBet);
		displayMyBet.setFont(new Font("SansSerif", Font.BOLD, 20));
		displayMyBet.setOpaque(true);
		displayMyBet.setBackground(background);
		displayMyBet.setForeground(Color.red);
		displayMyBet.setBounds(30, 283, 600, 30);
		
		if (!showingBoth && board.getDealerHand().getCardCount() == 2 && board.getDealerHand().getBlackjackValue() != 21)
		{
			displayDealerValue = new JLabel("Dealer has "+ board.getDealerHand().getCard(1).pointValue());
		}
		else
		{
			displayDealerValue = new JLabel("Dealer has "+ board.getDealerHand().getBlackjackValue());
		}
		panel.add(displayDealerValue);
		displayDealerValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		displayDealerValue.setOpaque(true);
		displayDealerValue.setBackground(background);
		displayDealerValue.setForeground(Color.RED);
		displayDealerValue.setBounds(30, 142, 200, 30);
		
		
		displayMyValue = new JLabel("You have " + (board.getMyHand().getBlackjackValue()));
		
		panel.add(displayMyValue);
		displayMyValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		displayMyValue.setOpaque(true);
		displayMyValue.setBackground(background);
		displayMyValue.setForeground(Color.RED);
		displayMyValue.setBounds(30, 172, 200, 30);
		
		displayDealer = new JLabel[board.getDealerHand().getCardCount()];
		displayMine = new JLabel[board.getMyHand().getCardCount()];
		for(int d = 0; d < board.getDealerHand().getCardCount(); d++)
		{
			displayDealer[d] = new JLabel();
			panel.add(displayDealer[d]);
			displayDealer[d].setBounds(dealerCardCoords[d].x, dealerCardCoords[d].y, CARD_WIDTH, CARD_HEIGHT);
		}
		for(int m = 0; m < board.getMyHand().getCardCount(); m++)
		{
			displayMine[m] = new JLabel();
			panel.add(displayMine[m]);
			displayMine[m].setBounds(myCardCoords[m].x, myCardCoords[m].y, CARD_WIDTH, CARD_HEIGHT);
		}
		for(int k = 0; k < board.getDealerHand().getCardCount(); k++) 
		{
			String cardImageFileName= imageFileName(board.getDealerHand().getCard(k));
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) 
			{
				ImageIcon icon= new ImageIcon(imageURL);
				displayDealer[k].setIcon(icon);
				if (!showingBoth)
				{
					String cardImageFileName2 = "cards/back1.GIF";
					URL imageURL2 = getClass().getResource(cardImageFileName2);
					ImageIcon icon2= new ImageIcon(imageURL2);
					displayDealer[0].setIcon(icon2);
				}
				displayDealer[k].setOpaque(true);
				displayDealer[k].setVisible(true);
			} 
			else
			{
				throw new RuntimeException("Card image not found: \""+ cardImageFileName+ "\"");
			}
		}
		for (int k = 0; k < board.getMyHand().getCardCount(); k++) 
		{
			String cardImageFileName= imageFileName(board.getMyHand().getCard(k));
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) 
			{
				ImageIcon icon= new ImageIcon(imageURL);
				displayMine[k].setIcon(icon);
				displayMine[k].setVisible(true);
			} 
			else
			{
				throw new RuntimeException("Card image not found: \""+ cardImageFileName+ "\"");
			}
		}
		pack();
	}

	private String imageFileName(Card c) 
	{
		String str= "cards/";
		if(c == null) 
		{
			return "cards/back1.GIF";
		}
		str += c.rank() + c.suit();
		str += ".GIF";
		return str;
	}
	
	private void initDisplay()
	{
		panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
			}
		};
		panel.setLayout(null);
		panel.setBackground(background);
		panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		hit = new JButton();
		hit.setText("Hit!");
		panel.add(hit);
		hit.setBounds(30, 464, 100, 30);
		hit.setBackground(Color.ORANGE);
		hit.addActionListener(this);
		
		stand = new JButton();
		stand.setText("Stand!");
		panel.add(stand);
		stand.setBounds(150, 464, 100, 30);
		stand.setBackground(Color.ORANGE);
		stand.addActionListener(this);
	
		bet = new JButton();
		bet.setText("Bet $1!");
		panel.add(bet);
		bet.setBounds(270, 464, 100, 30);
		bet.setBackground(Color.RED);
		bet.addActionListener(this);

		betFive = new JButton();
		betFive.setText("Bet $5!");
		panel.add(betFive);
		betFive.setBounds(390, 464, 100, 30);
		betFive.setBackground(Color.RED);
		betFive.addActionListener(this);
		
		deal = new JButton();
		deal.setText("Deal!");
		panel.add(deal);
		deal.setBounds(510, 464, 100, 30);
		deal.setBackground(Color.MAGENTA);
		deal.addActionListener(this);

		pack();
		getContentPane().add(panel);
		getRootPane().setDefaultButton(deal);
		panel.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		if (command.equals("Hit!"))
		{
			gameInProgress = board.hit(gameInProgress);
			if (!gameInProgress)
				showingBoth = true;
			repaint();
		}
		else if (command.equals("Stand!"))
		{
			gameInProgress = board.stand(gameInProgress);
			showingBoth = true;
			repaint();
		}
		else if (command.equals("Deal!"))
		{
			initDisplay();
			if (!gameInProgress)
				showingBoth = false;
			gameInProgress = board.dealNewGame(gameInProgress);
			repaint();
		}
		else if (command.equals("Bet $1!"))
		{
			board.bet(gameInProgress);
			repaint();
		}
		else if (command.equals("Bet $5!"))
		{
			board.betFive(gameInProgress);
			repaint();
		}
	}
}