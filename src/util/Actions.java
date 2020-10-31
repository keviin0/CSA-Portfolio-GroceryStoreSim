package util;

import java.util.List;

public class Actions {

    public static enum ACTIONS {DRAW, PLACE}
    public static Card topCard;

    public static Card pop(Deck d){
        Card result = d.cardDeck.get(0);
        d.cardDeck.remove(0);
        return result;
    }

    public static void push(Player p, Card selected){
        int index = 0;
        for(int i = 0; i < p.hand.size(); i++){
            if (p.hand.get(i).getColor() == selected.getColor()){
                if (p.hand.get(i).getCardNum() == selected.getCardNum()){
                    index = i;
                    break;
                }
            }
        }
        topCard = p.hand.get(index);
        p.hand.remove(selected);
    }

    public static Deck newDeck(){
        Deck test = new Deck(); //Create deck
        //System.out.println(test.cardDeck);
        test.shuffle();
        //System.out.println(test.cardDeck);
        return test;
    }

    public static Card getTopCard(){
        return topCard;

    }

    public static boolean doAction(ACTIONS choice, Player p, int num, Deck d, Card c){
        switch(choice){
            case DRAW:
                for(int i = 0; i < num; i++){
                    p.hand.add(pop(d));
                }
                return true;
            case PLACE:
                if(c.getColor() == Card.SPECIAL_COLOR){
                    //
                }
                if(topCard.getColor() != c.getColor()){ //Check if card is unable to be played
                    if(topCard.getCardNum() == c.getCardNum()){
                        return false;
                    }
                }

                push(p, c);
                return true;
        }
        return true;
    }
}
