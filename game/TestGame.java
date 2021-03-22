package game;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import exceptions.GamePlayersCSVException;
import utilities.Menu;

public class TestGame {

  final static Scanner s = new Scanner(System.in);

  public static void main(String[] args) {
    int option;
    Menu menu = new Menu("Jugar", "Reglas del juego", "Mostrar puntuaciones", "Guardar puntuaciones", "Salir");
    Game game = new Game();

    System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                       Bienvenidos al juego de El Cerdo.                       ║");
    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

    do {
      option = menu.chooseOption();

      switch(option) {
        case 1:
          try {
            System.out.print("\u001b[2J");
            // Comenzar partida
            game.newGame();
            game.round();

            // Mostrar ganador de la partida
            System.out.println("\n¡" + game.winner() + " ganó la partida!");
            s.nextLine();
          } catch (InputMismatchException ime) {
            System.out.println("\nSe ha producido un error.");
            System.out.print("Error: debe introducir un valor numérico.");
            s.nextLine();
          }
          break;
        case 2:
          rules();
          break;
        case 3:
          if(!game.isPlayers()) {
            System.out.println("Aún no se han añadido jugadores a la partida.");
          } else {
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                  PUNTUACIONES                                 ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");
            System.out.println(game);
          }
          break;
        case 4:
          try {
            System.out.print("Introduzca el nombre del archivo donde desea guardar las puntuaciones: ");
            String fileName = s.nextLine();

            game.saveCSV(fileName);
            System.out.println("Archivo guardado exitosamente.");
          } catch (IOException e) {
            System.out.println("\nSe ha producido un error.");
            System.out.print("No se ha podido abrir el archivo.");
            s.nextLine();
          }
          break;
      }
      clear();
    } while(option != 5);
  }

  public static void rules() {
    System.out.print("\u001b[2J");
    System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║ Los jugadores su turnan para lanzar un dado tantas veces como quieran.        ║");
    System.out.println("║ Cada tirada le proporciona al jugador tantos puntos como el resultado de la   ║");
    System.out.println("║ cara obtenida, a excepción del 1. En cuyo caso, el jugador perderá todos los  ║");
    System.out.println("║ puntos obtenido en su turno. En cada tirada, el jugador puede elegir seguir   ║");
    System.out.println("║ tirando o plantarse con los puntos que ha acumulado.                          ║");
    System.out.println("║                                                                               ║");
    System.out.println("║ El primer jugador en obtener 100 puntos o más gana la partida.                ║");
    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
  }

  public static void clear() {
    System.out.print("\nPulsa intro para continuar...");
    s.nextLine();
    System.out.print("\u001b[2J");
  }
}
