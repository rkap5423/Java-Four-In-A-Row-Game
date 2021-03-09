public class Player {

    private String name;
    private int color;
    private int score;
    private String colorString;

    public Player(String pname, String pcolor, int s) {
        name = pname;
        score = s;
        updateColor(pcolor);
    }

    public void updateColor(String col){ //change color of players chips based on theme
        colorString = col;
        if (col.equalsIgnoreCase("yellow")) {
            color = 2;
        }
        else if (col.equalsIgnoreCase("green")) {
            color = 3;
        }
        else if (col.equalsIgnoreCase("red")) {
            color = 4;
        }
        else {
            color = 1;
        }
    }

    public String getColorString() {
        return colorString;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    public void updateScore(){
        score++;
    }

    public int getColor() {
        return color;
    }

}
