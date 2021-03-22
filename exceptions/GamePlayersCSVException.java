package exceptions;

@SuppressWarnings("serial")
public class GamePlayersCSVException extends RuntimeException {

  public GamePlayersCSVException(String message) {
    super(message);
  }
}