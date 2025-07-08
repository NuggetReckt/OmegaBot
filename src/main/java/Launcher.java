import fr.nuggetreckt.omegabot.OmegaBot;

public class Launcher {
    public static void main(String[] args) {
        try {
            new OmegaBot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
