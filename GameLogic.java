public class GameLogic {
    private final JavaFXTemplate javaFXTemplate;

    public GameLogic(JavaFXTemplate javaFXTemplate) {
        this.javaFXTemplate = javaFXTemplate;
    }

    public int checkUp(int row, int col) { //checks for adjacent valid chips to add to winning count, in upwards direction
        if (0 > row) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkUp(row - 1, col);
    }

    public int checkDown(int row, int col) { //checks for adjacent valid chips to add to winning count, in downwards direction
        if (row > 5) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }
        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkDown(row + 1, col);
    }

    public int checkVertical(int row, int col) {//checks for adjacent valid chips to add to winning count, up and down

        int countCounter = 0;

        countCounter = checkUp(row - 1, col) + checkDown(row + 1, col);

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);

        return countCounter;
    }

    public int checkRight(int row, int col) {//checks for adjacent valid chips to add to winning count, to the right
        if (col > 6) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkRight(row, col + 1);
    }

    public int checkLeft(int row, int col) {//checks for adjacent valid chips to add to winning count, to the left
        if (0 > col) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkLeft(row, col - 1);
    }

    public int checkHorizontal(int row, int col) {//checks for adjacent valid chips to add to winning count, to the left and right

        int countCounter = 0;

        countCounter = checkRight(row, col + 1) + checkLeft(row, col - 1);
        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);

        return countCounter;
    }

    public int checkDownLeft(int row, int col) {//checks for adjacent valid chips to add to winning count, down-left direction
        if (row > 5 || 0 > col) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkDownLeft(row + 1, col - 1);
    }

    public int checkUpRight(int row, int col) {//checks for adjacent valid chips to add to winning count, in up-right direction
        if (0 > row || col > 6) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkUpRight(row - 1, col + 1);
    }

    public int checkDiagonalPositive(int row, int col) {//checks for adjacent valid chips to add to winning count, in up-right and down-left directions
        int countCounter = 0;

        countCounter = checkDownLeft(row + 1, col - 1) + checkUpRight(row - 1, col + 1);

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);

        return countCounter;
    }

    public int checkDownRight(int row, int col) {//checks for adjacent valid chips to add to winning count, in down-right direction
        if (row > 5 || col > 6) {
            return 0;
        }
        if (javaFXTemplate.getGameBtns()[row][col].getPlayerOnBtn() != javaFXTemplate.getPlayer()) {
            return 0;
        }

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);
        return 1 + checkDownRight(row + 1, col + 1);
    }
    public int checkUpLeft(int row, int col){//checks for adjacent valid chips to add to winning count, in up-left direction
        if(0 > row || 0 > col){
            return 0;
        }
        if(javaFXTemplate.gameBtns[row][col].getPlayerOnBtn() != javaFXTemplate.player){
            return 0;
        }

        javaFXTemplate.winningRow.add(row);
        javaFXTemplate.winningCol.add(col);
        return 1 + checkUpLeft(row-1, col-1);
    }
    public int checkDiagonalNegative(int row, int col) {//checks for adjacent valid chips to add to winning count, in up-left and down-right directions
        int countCounter = 0;

        countCounter = checkDownRight(row + 1, col + 1) + javaFXTemplate.gameLogic.checkUpLeft(row - 1, col - 1);

        javaFXTemplate.getWinningRow().add(row);
        javaFXTemplate.getWinningCol().add(col);

        return countCounter;
    }

    public void checkWin(int row, int col) {//checks if there's a winning combinations of chips on the board, using above functions

        if (checkVertical(row, col) > 2 && javaFXTemplate.isGameOver() == false) {
            javaFXTemplate.setGameOver(true);
        }
        javaFXTemplate.getWinningRow().clear();
        javaFXTemplate.getWinningCol().clear();

        if (checkHorizontal(row, col) > 2 && javaFXTemplate.isGameOver() == false) {
            javaFXTemplate.setGameOver(true);
        }
        javaFXTemplate.getWinningRow().clear();
        javaFXTemplate.getWinningCol().clear();

        if (checkDiagonalPositive(row, col) > 2 && javaFXTemplate.isGameOver() == false) {
            javaFXTemplate.setGameOver(true);
        }
        javaFXTemplate.getWinningRow().clear();
        javaFXTemplate.getWinningCol().clear();

        if (checkDiagonalNegative(row, col) > 2 && javaFXTemplate.isGameOver() == false) {
            javaFXTemplate.setGameOver(true);
        }
        javaFXTemplate.getWinningRow().clear();
        javaFXTemplate.getWinningCol().clear();

    }

    /**
     * If the whole board is filled and no one has four in a row, then the game ends in a tie. Users can then choose to restart the game.
     */
    void checkTie() {
        int count = 0;
        for (int i = 0; i < javaFXTemplate.getGameBtns().length; i++) {
            for (int j = 0; j < javaFXTemplate.getGameBtns()[0].length; j++) {
                if (javaFXTemplate.getGameBtns()[i][j].getPlayerOnBtn() == -1) {
                    count++;
                }
            }
        }
        if (count == 0) {
            javaFXTemplate.setGameOver(true);
            javaFXTemplate.setTieGame(true);
            javaFXTemplate.getLblPlayerMove().setText("No more moves, Tie Game!");
            javaFXTemplate.getPause().play();
        }
    }


}