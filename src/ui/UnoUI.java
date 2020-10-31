package ui;

import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import util.Actions.ACTIONS;

public class UnoUI extends JFrame{

    volatile boolean actionSuccess;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel cardsPanel;
    private JButton button1;
    private JButton button7;
    private JButton button6;
    private JButton button4;
    private JButton button3;
    private JButton button8;
    private JButton button9;
    private JButton button2;
    private JButton button10;
    private JButton button5;
    private JButton mDrawCardButton;
    private JButton mPlaceCardButton;
    private JButton mTopCardButton;
    private JLabel mCurrentPlayerLabel;

    private ACTIONS action;

    private Player mActivePlayer;
    private Deck mActiveDeck;
    private Card mUselessCard = new Card(Color.BLUE, -1);
    public int round = 0;
    private ArrayList<Player> mPlayers = new ArrayList<Player>();
    private Card mTopCard;
    private ArrayList<JButton> mCardButtons;
    private static final Color DEFAULT_COLOR = Color.white;

    /**
     * Launch the application.
     */

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            try {
                UnoUI unoUi = new UnoUI();
                unoUi.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UnoUI() {
        createUIComponents();
    }

    public UnoUI(Player currentPlayer, Card topCard, Deck deck, ArrayList<Player> players){
        createUIComponents();

        mActiveDeck = deck;
        mPlayers =  players;

        // Update the top card
        updateTopCard(mTopCard);

        // Display active player(p)'s hand
        updateActivePlayer(currentPlayer);

        // Handle the draw card action and handle the
        // place card actions
        mDrawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = ACTIONS.DRAW;
                mTopCard = mUselessCard;
                drawCard();
            }
        });

        mPlaceCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = ACTIONS.PLACE;

                JOptionPane.showMessageDialog(null, "Player has place card");
            }
        });



    }

    //method to update current active player's hand and name
    private void updateActivePlayer(Player p) {
        mActivePlayer = p;

        // First clear and reset the current hand
        // to update the cards view
        clearHand();

        for(int i=0; i < mActivePlayer.hand.size(); ++i ) {
            Card card = mActivePlayer.hand.get(i);
            JButton cardButton = mCardButtons.get(i);

            // update the card display
            cardButton.setEnabled(true);
            cardButton.setVisible(true);
            cardButton.setBackground(card.getColor());
            cardButton.setText(card.getLabel());

            // Update text color
            cardButton.setForeground(card.isSpecialCard() ? Color.white : Color.black);

        }

        mCurrentPlayerLabel.setText(mActivePlayer.name);
    }

    private void updateTopCard(Card c){
        if( c == null ) {
            c = mUselessCard;
        }
        mTopCard = c;
        mTopCardButton.setText(c.getLabel());
        mTopCardButton.setBackground(c.getColor());

        // Update text color
        mTopCardButton.setForeground(c.isSpecialCard() ? Color.white : Color.black);
    }

    public void invalid(){
        JOptionPane.showMessageDialog(null, "Invalid move");
    }

    private void drawCard(){ //method to perform action
        Card cardFromTop = Actions.pop(mActiveDeck);
        System.out.println(round + "\n" + mActivePlayer.name + " card " + cardFromTop.getDescription() );
        JOptionPane.showMessageDialog(null, mActivePlayer.name + " has drawn card");
        Actions.doAction(action, mActivePlayer, 1, mActiveDeck, cardFromTop);
        updateTopCard(cardFromTop);
        if(mActivePlayer.hand.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Winner");
        }
        if(mActiveDeck.cardDeck.isEmpty()){
            mActiveDeck = Actions.newDeck();
        }
        round++;
        mActivePlayer = mPlayers.get(round % mPlayers.size());
        updateActivePlayer(mActivePlayer);

    }

    private void createUIComponents() {
        getContentPane().setBackground(new Color(250, 200, 250));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setContentPane(mainPanel);

        mCardButtons = new ArrayList<>();

        mCardButtons.add(button1);
        mCardButtons.add(button2);
        mCardButtons.add(button3);
        mCardButtons.add(button4);
        mCardButtons.add(button5);
        mCardButtons.add(button6);
        mCardButtons.add(button7);
        mCardButtons.add(button8);
        mCardButtons.add(button9);
        mCardButtons.add(button10);

        clearHand();

        // Set top deck as useless card.
        // It should not be clickable.
        mTopCardButton.setEnabled(false);
        mTopCardButton.setVisible(true);
        mTopCardButton.setBackground(mUselessCard.getColor());
        mTopCardButton.setText(mUselessCard.getLabel());

        mCurrentPlayerLabel.setText("");
    }

    private void clearHand() {
        // Hide and disable card buttons
        for(JButton button : mCardButtons) {
            button.setEnabled(false);
            button.setVisible(false);
            button.setText("");
            button.setBackground(DEFAULT_COLOR);
        }
    }
}
