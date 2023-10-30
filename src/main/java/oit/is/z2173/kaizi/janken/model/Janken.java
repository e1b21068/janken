package oit.is.z2173.kaizi.janken.model;

public class Janken {
    private String Player1Hand;
    private String Player2Hand;

    public Janken(String Player1Hand){
        this.Player1Hand = Player1Hand;
        this.Player2Hand = "gu";
    }

    public Janken(String Player1Hand, String Computer2Hand){
        this.Player1Hand = Player1Hand;
        this.Player2Hand = Computer2Hand;
    }

    public String getWinner(){
        if(this.Player1Hand==this.Player2Hand){
            return "nothing";
        }else if((this.Player1Hand=="gu" && this.Player2Hand=="choki")
            || (this.Player1Hand=="choki" && this.Player2Hand=="pa")
            || (this.Player1Hand=="pa" && this.Player2Hand=="gu")){
            return "player1";
        }else if((this.Player1Hand=="gu" && this.Player2Hand=="pa")
            || (this.Player1Hand=="choki" && this.Player2Hand=="gu")
            || (this.Player1Hand=="pa" && this.Player2Hand=="choki")){
            return "player2";
        }
        return "error";
    }

    public String getPlayer1Hand(){
        return this.Player1Hand;
    }

    public String getPlayer2Hand(){
        return this.Player2Hand;
    }
}
