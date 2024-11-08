import java.util.Scanner;

public class Table {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Black Jack!");
        System.out.print("Enter the amount of money you want to put in: ");
        double totalIn = scan.nextDouble();
        scan.nextLine(); // Consume the newline character
        String choice = "";

        Deck deck = new Deck();

        while (totalIn > 0 && !choice.equalsIgnoreCase("Collect")) {
            System.out.println("\nYour current balance: $" + totalIn);
            System.out.print("Enter your bet (or type 'all in'): ");
            String betInput = scan.nextLine();
            double bet = betInput.equalsIgnoreCase("all in") ? totalIn : Double.parseDouble(betInput);

            if (bet > totalIn) {
                System.out.println("You don't have enough money to make that bet. Try again.");
                continue;
            }

            Player player = new Player();
            Player dealer = new Player();

            // Deal initial cards
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());

            System.out.println("Your cards: " + player.getHand() + " (Total: " + player.getTotal() + ")");
            System.out.println("Dealer's face-up card: " + dealer.getFaceUpCard());

            // Check for dealer blackjack
            if (dealer.getFaceUpCard().equals("1/11") && dealer.hasHiddenTen()) {
                System.out.println("Dealer has a blackjack!");
                if (player.hasBlackjack()) {
                    System.out.println("It's a tie! Both have blackjack.");
                } else {
                    System.out.println("You lose this round.");
                    totalIn -= bet;
                }
                continue;
            }

            // Check for player blackjack
            if (player.hasBlackjack()) {
                System.out.println("Blackjack! You win 1.5x your bet.");
                totalIn += bet * 1.5;
                continue; // Round ends immediately
            }

            // Player's turn
            boolean playerTurn = true;
            while (playerTurn && player.getTotal() < 21) {
                System.out.print("Do you want to 'Hit', 'Stand', or 'Double Down'? ");
                String action = scan.nextLine();

                if (action.equalsIgnoreCase("Hit")) {
                    int newCard = deck.drawCard();
                    player.addCard(newCard);
                    System.out.println("You drew a " + formatCard(newCard) + ". Your cards: " + player.getHand() + " (Total: " + player.getTotal() + ")");
                } else if (action.equalsIgnoreCase("Stand")) {
                    playerTurn = false;
                } else if (action.equalsIgnoreCase("Double Down")) {
                    if (bet > totalIn - bet) {
                        System.out.println("You don't have enough money to double down. Choose another action.");
                        continue;
                    }
                    totalIn -= bet;
                    bet *= 2;
                    int newCard = deck.drawCard();
                    player.addCard(newCard);
                    System.out.println("You drew a " + formatCard(newCard) + ". Your cards: " + player.getHand() + " (Total: " + player.getTotal() + ")");
                    playerTurn = false;
                } else {
                    System.out.println("Invalid choice. Please choose 'Hit', 'Stand', or 'Double Down'.");
                }
            }

            if (player.getTotal() > 21) {
                System.out.println("You busted with a total of " + player.getTotal() + "! You lose this round.");
                totalIn -= bet;
                continue;
            }

            System.out.println("Your final hand: " + player.getHand() + " (Total: " + player.getTotal() + ")");

            // Dealer's turn
            System.out.println("Dealer's turn:");
            while (dealer.getTotal() < 17) {
                int newCard = deck.drawCard();
                dealer.addCard(newCard);
                System.out.println("Dealer drew a " + formatCard(newCard) + ". Dealer's hand: " + dealer.getHand() + " (Total: " + dealer.getTotal() + ")");
            }

            System.out.println("Dealer's final hand: " + dealer.getHand() + " (Total: " + dealer.getTotal() + ")");

            // Determine winner
            if (dealer.getTotal() > 21 || player.getTotal() > dealer.getTotal()) {
                System.out.println("You win this round!");
                totalIn += bet;
            } else if (player.getTotal() < dealer.getTotal()) {
                System.out.println("You lose this round.");
                totalIn -= bet;
            } else {
                System.out.println("It's a tie! Your bet is returned.");
            }

            // Prompt to continue or collect
            if (totalIn > 0) {
                System.out.print("Do you want to play another round or 'Collect' your winnings? ");
                choice = scan.nextLine();
            }
        }

        System.out.println("\nYou leave the table with $" + totalIn);
        System.out.println("Thanks for playing!");
        scan.close();
    }

    private static String formatCard(int card) {
        return card == 1 ? "1/11" : String.valueOf(card);
    }
}
