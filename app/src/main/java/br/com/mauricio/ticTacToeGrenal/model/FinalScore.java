package br.com.mauricio.ticTacToeGrenal.model;

public class FinalScore {


    private String winner;
    private String date;
    private String email;

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getWinner() {
        return winner;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
