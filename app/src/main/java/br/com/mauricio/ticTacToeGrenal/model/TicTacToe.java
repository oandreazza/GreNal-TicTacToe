package br.com.mauricio.ticTacToeGrenal.model;


import br.com.mauricio.ticTacToeGrenal.types.Player;

public class TicTacToe implements Game {

    private int stage[] = {2,2,2,2,2,2,2,2,2};
    private int winnerPositions[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void play(Player player, int position) {
        this.stage[position] = player.getPlayerNumber();
    }

    public Integer getPlayerNumberByLocationStage(int position) {
        return this.stage[position];
    }

    public boolean canPlay(int position) {
        return stage[position] == 2;
    }

    public boolean hasWinner() {
        boolean hasWinner = false;
        for (int[] winnerPosition: winnerPositions) {
            hasWinner = (stage[winnerPosition[0]] == stage[winnerPosition[1]])
                    && (stage[winnerPosition[1]] == stage[winnerPosition[2]])
                    && stage[winnerPosition[0]] != 2;

            if(hasWinner)
                break;
        }

        return hasWinner;
    }

    public void playFirstRow(){
        this.stage[0] = Player.GREMIO.getPlayerNumber();
        this.stage[1] = Player.GREMIO.getPlayerNumber();
        this.stage[2] = Player.GREMIO.getPlayerNumber();

    }
}
