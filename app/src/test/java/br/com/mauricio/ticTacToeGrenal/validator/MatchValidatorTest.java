package br.com.mauricio.ticTacToeGrenal.validator;

import org.junit.Before;
import org.junit.Test;

import br.com.mauricio.ticTacToeGrenal.exception.GameNotFoundException;
import br.com.mauricio.ticTacToeGrenal.exception.PlayersNotFoundException;
import br.com.mauricio.ticTacToeGrenal.model.Match;
import br.com.mauricio.ticTacToeGrenal.model.TicTacToe;
import br.com.mauricio.ticTacToeGrenal.types.Player;


public class MatchValidatorTest {

    private MatchValidator validator;

    @Before
    public void setUp(){
        this.validator = new MatchValidator();
    }


    @Test(expected = GameNotFoundException.class)
    public void shouldThrownGameNotFoundExceptionWhenNotFindGameInMatch(){
        Match match = new Match();
        validator.hasGame(match);
    }

    @Test
    public void shouldNotThrowGameNotFoundExceptionWhanFinGameInMatch(){
        Match match = new Match(new TicTacToe(), null, null);
        validator.validate(match);

    }

    @Test(expected = PlayersNotFoundException.class)
    public void shouldThrowPlayersNotFoundWhenGameHasNoPlayers(){
        Match match = new Match(new TicTacToe(),null,null);

        validator.validate(match);

    }

    @Test(expected = PlayersNotFoundException.class)
    public void shouldThrowPlayersNotFoundWhenGameHasOnlyPlayerOne(){
        Match match = new Match(new TicTacToe(), Player.GREMIO,null);

        validator.validate(match);

    }


}
