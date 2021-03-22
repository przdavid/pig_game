package game;
import java.util.Scanner;
import utilities.Dice;

/**
 * Clase que representa a los objetos de tipo Jugador.
 * @author David Pérez Ruiz.
 * 
 * @param name nombre del jugador.
 * @param score puntuación del jugador.
 * @param victories número de partidas ganadas por el jugador.
 */

public class Player {
  // Atributos
  private static int nextCode = 1;
  private int code;
  public String name;
  private int score;
  private int victories;
  private Dice dice = new Dice();
  
  // Constructor
  public Player(String name) {
    this.name = name;
    this.score = 0;
    this.victories = 0;
    this.code = Player.nextCode++;
  }
  
  // Constructor de búsqueda del jugador
  protected Player(int code) {
    this.code = code;
  }
  
  // Constructor para añadir jugador a partir de CSV
  protected Player(int code, String name, int victories) {
    this.code = code;
    this.name = name;
    this.victories = victories;
  }

  // Métodos
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the score
   */
  public int getScore() {
    return score;
  }
  
  /**
   * @param score the score to set
   */
  protected void setScore(int score) {
    this.score = score;
  }

  /**
   * @return the victories
   */
  public int getVictories() {
    return victories;
  }
  
  /**
   * @return the code
   */
  public int getCode() {
    return code;
  }
  
  /**
   * Método: play
   * Propósito: realiza la jugada. En él, el jugador continúa sus tiradas, se planta o pierde
   * los puntos acumulados. Al final de la tirada, suma los puntos conseguidos al total.
   */
  public void play() {
    int points = 0;
    boolean stick = false;
    
    do {
      int d = dice.throwDice();
      Scanner s = new Scanner(System.in);
      System.out.println("Resultado de lanzar el dado: " + d);
      
      if (d == 1) {
        System.out.println(this.name + " pierde todos los puntos acumulados en la tirada.");
        points = 0;
        stick = true;
      } else {
        points += d;
        System.out.println("Con esta tirada acumularía " + points + " puntos.");
        
        // Si con la tirada consigue 100 puntos o más, automáticamente gana la partida
        if (points + score >= 100) {
          stick = true;
          break;
          
        // En caso contrario, deberá seguir jugando
        } else {
          System.out.print("¿Desea seguir tirando o se planta? (T, tirar / P, plantarse): ");
          String option = s.next();
          while(option.equalsIgnoreCase("T") && option.equalsIgnoreCase("P")) {
            System.out.println("Opción incorrecta. Introdúzcala de nuevo.");
            System.out.print("(T, tirar / P, plantarse): ");
            option = s.next();
          }
          
          if (option.equalsIgnoreCase("P")) {
            stick = true;
          }
        }
      }
      s.nextLine();
      System.out.print("\u001b[2J");
    } while (!stick);
    
    this.score += points;
  }
  
  /**
   * Método: win
   * Propósito: indica que ha ganado un jugador, por lo que se suma una victoria.
   */
  public void win() {
    this.victories++;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + victories;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Player other = (Player) obj;
    if (victories != other.victories)
      return false;
    return true;
  }
  
}
