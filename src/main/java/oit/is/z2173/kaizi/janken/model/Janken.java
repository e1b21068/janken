package oit.is.z2173.kaizi.janken.model;

public class Janken {
    private String PlayerHand;
    private String ComputerHand;

    public Janken(String PlayerHand){
        this.PlayerHand = PlayerHand;
        this.ComputerHand = "gu";
    }

    public Janken(String PlayerHand, String ComputerHand){
        this.PlayerHand = PlayerHand;
        this.ComputerHand = ComputerHand;
    }

    public String getResult(){
        switch (this.PlayerHand) {
          case "gu":
            return "Draw";
          case "choki":
            return "You Lose";
          case "pa":
            return "You Win";
        }
        return "Error";
    }

    public String getPlayerHand(){
        return this.PlayerHand;
    }

    public String getComputerHand(){
        return this.ComputerHand;
    }
}
