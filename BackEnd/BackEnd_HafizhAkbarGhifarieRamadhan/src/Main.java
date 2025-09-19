import Model.Player;
import Model.Score;

public class Main {
    public static void main(String[] args) {
        // Create Players
        Player player1 = new Player("Kakek backflip");
        Player player2 = new Player("Nenek pargoy");

        // Create Scores
        Score score1 = new Score(player1, 1500, 250, 5000);
        Score score2 = new Score(player2, 3200, 750, 12000);
        Score score3 = new Score(player1, 1800, 300, 6000);

        // Update player1 with score1 & score3
        player1.updateHighScore(score1.getValue());
        player1.addCoins(score1.getCoinsCollected());
        player1.addDistance(score1.getDistance());

        player1.updateHighScore(score3.getValue());
        player1.addCoins(score3.getCoinsCollected());
        player1.addDistance(score3.getDistance());

        // Update player2 with score2
        player2.updateHighScore(score2.getValue());
        player2.addCoins(score2.getCoinsCollected());
        player2.addDistance(score2.getDistance());

        // Show details
        System.out.println("===== Player Details =====");
        player1.showDetail();
        player2.showDetail();

        System.out.println("===== Score Details =====");
        score1.showDetail();
        score2.showDetail();
        score3.showDetail();
    }
}


