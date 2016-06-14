package br.com.mauricio.ticTacToeGrenal.validator;

import br.com.mauricio.ticTacToeGrenal.exception.GameNotFoundException;
import br.com.mauricio.ticTacToeGrenal.exception.PlayersNotFoundException;
import br.com.mauricio.ticTacToeGrenal.model.Match;

/**
 * Created by mauricio on 6/13/16.
 */
public class MatchValidator {


    public void validate(Match match) {
        hasGame(match);
        hasAvailablePlayers(match);

    }

    public void hasGame(Match match) {
        if(match.getGame() == null)
            throw new GameNotFoundException();
    }

    public void hasAvailablePlayers(Match match) {
        if(!match.hasPlayers())
            throw new PlayersNotFoundException();
    }
}
