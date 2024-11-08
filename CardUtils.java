public class CardUtils {
    public static int mapFaceCardToValue(int card) {
        if (card >= 11 && card <= 13) {
            return 10; // J, Q, K all map to 10
        }
        return card; // Number cards and Aces remain as-is
    }
}
