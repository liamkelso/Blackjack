import java.util.HashMap;
import java.util.Map;

public class Deck {
    private Map<Integer, Integer> cards;
    private int totalCards;

    public Deck() {
        cards = initializeDeck();
        totalCards = 312; // Six decks of 52 cards
    }

    private Map<Integer, Integer> initializeDeck() {
        Map<Integer, Integer> deck = new HashMap<>();
        for (int i = 2; i <= 10; i++) {
            deck.put(i, 24); // 24 of each card rank (2-10)
        }
        deck.put(1, 24); // Aces
        deck.put(11, 24); // Jacks
        deck.put(12, 24); // Queens
        deck.put(13, 24); // Kings
        return deck;
    }

    public int drawCard() {
        double rand = Math.random() * totalCards;
        int cumulativeCount = 0;

        for (Map.Entry<Integer, Integer> entry : cards.entrySet()) {
            cumulativeCount += entry.getValue();
            if (rand < cumulativeCount) {
                int card = entry.getKey();
                cards.put(card, cards.get(card) - 1); // Decrease count
                totalCards--;
                return CardUtils.mapFaceCardToValue(card);
            }
        }
        return -1; // Should never happen
    }
}
