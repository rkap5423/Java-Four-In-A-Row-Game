import javafx.scene.control.Button;

public class GameButton extends Button {
    private int playerOnBtn;

    GameButton(int player){ //which player currently "controls" this place on board
        playerOnBtn = player;
    }

    public int getPlayerOnBtn() {
        return playerOnBtn;
    }

    public void setPlayerOnBtn(int playerOnBtn) {
        this.playerOnBtn = playerOnBtn;
    }
}
