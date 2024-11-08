import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Integer> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(int card) {
        hand.add(card);
    }

    public int getTotal() {
        int total = 0;
        int aceCount = 0;

        for (int card : hand) {
            total += card;
            if (card == 1) {
                aceCount++;
            }
        }

        // Adjust for Aces (1 or 11)
        while (aceCount > 0 && total <= 11) {
            total += 10; // Upgrade Ace from 1 to 11
            aceCount--;
        }

        return total;
    }

    public List<String> getHand() {
        List<String> formattedHand = new ArrayList<>();
        for (int card : hand) {
            formattedHand.add(card == 1 ? "1/11" : String.valueOf(card));
        }
        return formattedHand;
    }

    public String getFaceUpCard() {
        return hand.get(0) == 1 ? "1/11" : String.valueOf(hand.get(0));
    }

    public boolean hasHiddenTen() {
        return hand.size() == 2 && (hand.get(1) == 10 || hand.get(1) >= 11 && hand.get(1) <= 13);
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && getTotal() == 21;
    }
}

