package game;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Clase que representa a los objetos de la clase Juego.
 * @author David Pérez Ruiz.
 *
 *@param players lista de jugadores.
 */

public class Game {
  // Atributos
  public static final String CSV_HEAD = "Código,Nombre,Victorias";
  private ArrayList<Player> players = new ArrayList<Player>();
  public static Scanner s = new Scanner(System.in);

  // Métodos
  /**
   * Método: isPlayers
   * Propósito: indica si hay jugadores en el juego.
   * @return true si hay jugadores, false en caso contrario.
   */
  public boolean isPlayers() {
    if (players.size() != 0) {
      return true;
    }
    return false;
  }

  /**
   * Método: add
   * Propósito: añade un jugador.
   * @param name nombre del jugador.
   */
  public void add(String name) {
    players.add(new Player(name));
  }
  
  public void add(Player player) {
    players.add(player);
  }

  /**
   * Método: remove
   * Propósito: elimina a un jugador del juego.
   * @param code código del jugador.
   */
  public void remove(int code) {
    if(!players.contains(new Player(code))) {

    } else {
      players.removeIf(c -> c.getCode() == code);
    }
  }

  /**
   * Método: newGame
   * Propósito: comienza un nuevo juego, por lo que añade jugadores y sus puntos se reinician.
   */
  public void newGame() {
    setPlayers(isPlayers());
    for(int i = 0; i < this.players.size(); i++) {
      players.get(i).setScore(0);
    }
  }

  /**
   * Método: setPlayers
   * Propósito: añade o elimina jugadores al juego.
   * @param startGame indica si el juego empieza (aún no hay jugadores) o ya ha empezado (se agregan jugadores).
   */
  private void setPlayers(boolean startGame) throws InputMismatchException {
    s.nextLine();
    String option;
    int numberPlayers = 0;

    if(startGame) {
      // Comprobar si algún jugador se agrega a la nueva partida
      System.out.print("¿Algún jugador se incorpora al juego? (S, sí / N, no): ");
      option = s.nextLine();

      while(!option.equalsIgnoreCase("S") && !option.equalsIgnoreCase("N")) {
        System.out.println("Opción incorrecta. Introdúzcala de nuevo.");
        System.out.print("(S, sí / N, no): ");
        option = s.next();
      }

      if(option.equalsIgnoreCase("S")) {
        System.out.print("Introduzca el número de nuevos jugadores: ");
        numberPlayers = s.nextInt();

        while (numberPlayers < 0) {
          System.out.print("Número de jugadores insuficiente. Introdúzcalo de nuevo: ");
          numberPlayers = s.nextInt();
        }

        s.nextLine();
        for(int i = 0; i < numberPlayers; i++) {
          System.out.print("Introduzca el nombre del jugador " + (i+1) + ": ");
          add(s.nextLine());
        }

        // Comprobar si algún jugador deja la partida
        System.out.print("¿Algún jugador abandona la partida? (S, sí / N, no): ");
        option = s.nextLine();
        
        do {
          while(!option.equalsIgnoreCase("S") && !option.equalsIgnoreCase("N")) {
            System.out.println("Opción incorrecta. Introdúzcala de nuevo.");
            System.out.print("(S, sí / N, no): ");
            option = s.nextLine();
          }
          
          if(option.equalsIgnoreCase("S")) {
            // Mostrar lista de jugadores
            playerList();
            
            System.out.print("Introduzca el código del jugador que abandona la partida: ");
            remove(s.nextInt());
            
            s.nextLine();
            System.out.print("¿Algún otro jugador abandona la partida? (S, sí / N, no):");
            option = s.nextLine();
          }
        } while(!option.equalsIgnoreCase("N"));
      }
    } else {
      System.out.print("Introduzca el número de jugadores: ");
      numberPlayers = s.nextInt();

      while (numberPlayers < 2) {
        System.out.print("Número de jugadores insuficiente. Introdúzcalo de nuevo: ");
        numberPlayers = s.nextInt();
      }
      
      s.nextLine();
      for(int i = 0; i < numberPlayers; i++) {
        System.out.print("Introduzca el nombre del jugador " + (i+1) + ": ");
        add(s.nextLine());
      }
    }
  }

  /**
   * Método: playerList
   * Propósito: muestra una lista de los jugadores con sus respectivos códigos.
   */
  private void playerList() {
    System.out.println("CÓDIGO\tNOMBRE");
    System.out.println("------\t------");
    for(int i = 0; i < players.size(); i++) {
      System.out.println(players.get(i).getCode() + ".\t" + players.get(i).getName());
    }
  }

  /**
   * Método: round
   * Propósito: lleva a cabo la ronda.
   */
  public void round() {
    int round = 1;


    do {
      if (round < 10) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    Ronda " + round + "                                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
      } else {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    Ronda " + round + "                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
      }

      for(int i = 0; i < players.size(); i++) {
        System.out.println("\nTurno de " + players.get(i).getName() + ":");
        System.out.print("Pulsa intro para comenzar...");
        s.nextLine();
        players.get(i).play();

        // Si el jugador ha obtenido 100 puntos o más, se acaba el juego
        if (players.get(i).getScore() >= 100) {
          break;
        }
        System.out.print("\u001b[2J");
      }

      actualScore();
      round++;
    } while (!isWinner());
  }

  /**
   * Método: actualScore
   * Propósito: muestra la puntuación actual de la partida.
   */
  public void actualScore() {
    System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                               Puntuación actual                               ║");
    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
    for(int  i = 0; i < players.size(); i++) {
      System.out.println((i+1) + ". " + players.get(i).getName() + ": " + players.get(i).getScore() + " puntos.");
    }
  }

  /**
   * Método: isWinner
   * Propósito: indica si hay un ganador en la partida.
   * @return true si hay un ganador, false en caso de no haberlo aún.
   */
  public boolean isWinner() {
    for(int i = 0; i < players.size(); i++) {
      if(players.get(i).getScore() >= 100) {
        return true;
      }
    }
    return false;
  }

  /**
   * Método: winner
   * Propósito: indica el nombre del ganador y suma una victoria.
   * @return el nombre del ganador.
   */
  public String winner() {
    String name = "";

    for(int i = 0; i < players.size(); i++) {
      if(players.get(i).getScore() >= 100) {
        name = players.get(i).getName();
        players.get(i).win();
      }
    }

    return name;
  }

  /**
   * Método: toString
   * Propósito: muestra las puntuaciones de los jugadores.
   * @return cadena formateada con el código, nombre y victorias del jugador.
   */
  public String toString() {
    String result = "";
    for(int i = 0; i < players.size(); i++) {
      if (players.get(i).getVictories() == 1) {
        result += "Código " + players.get(i).getCode() + ", " + players.get(i).getName()
            + ": " + players.get(i).getVictories() + " victoria.\n";
      } else {
        result += "Código " + players.get(i).getCode() + ", " + players.get(i).getName()
            + ": " + players.get(i).getVictories() + " victorias.\n";
      }
    }
    return result;
  }
  
  /**
   * Guardar las puntuaciones es un archivo CSV.
   */
  
  public void saveCSV(String fileName) throws IOException {
    var file = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE);
    headCSV(file);
    for (int i = 0; i < players.size(); i++) {
      file.write("\"" + players.get(i).getCode() + "\",");
      file.write("\"" + players.get(i).getName() + "\",");
      file.write("\"" + players.get(i).getVictories() + "\""); 
      file.newLine();
    }
    file.close();
  }
  
  // Cabeza del archivo CSV
  private void headCSV(BufferedWriter file) throws IOException {
    file.write(CSV_HEAD);
    file.newLine();
  }
}
