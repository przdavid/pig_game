package utilities;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa a los objetos de tipo Menú
 * @author David Pérez Ruiz
 * Fecha: 22/02/2021
 *
 * El formato de la clase es: Menu(options...)
 */

public class Menu {
  // Atributos
  ArrayList<String> options = new ArrayList<String>();
  
  // Constructor
  public Menu(String ... options) {
    for (int i = 0; i < options.length; i++) {
      this.options.add(options[i]);
    }
  }
  
  // Métodos
  /**
   * Método: chooseOption
   * Propósito: permite al usuario escoger la opción deseada del menú.
   * @return la opción que desea el usuario.
   */
  public int chooseOption() {
    // Mostrar menú
    System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                                     MENÚ                                      ║");
    System.out.println("╠════╦══════════════════════════════════════════════════════════════════════════╣");
    
    for (int i = 0; i < (this.options.size()-1); i++) {
      if (i < 9) {
        System.out.println("║ " + (i+1) + "  ║ " + this.options.get(i) + " ".repeat(73-this.options.get(i).length()) + "║");
        System.out.println("╠════╬══════════════════════════════════════════════════════════════════════════╣");
      } else {
        System.out.println("║ " + (i+1) + " ║ " + this.options.get(i) + " ".repeat(73-this.options.get(i).length()) + "║");
        System.out.println("╠════╬══════════════════════════════════════════════════════════════════════════╣");
      }
    }
    
    if (this.options.size() < 10) {
      System.out.println("║ " + this.options.size() + "  ║ " + this.options.get(this.options.size()-1) + " ".repeat(73-this.options.get(this.options.size()-1).length()) + "║");
      System.out.println("╚════╩══════════════════════════════════════════════════════════════════════════╝");
    } else {
      System.out.println("║ " + this.options.size() + " ║ " + this.options.get(this.options.size()-1) + " ".repeat(73-this.options.get(this.options.size()-1).length()) + "║");
      System.out.println("╚════╩══════════════════════════════════════════════════════════════════════════╝");
    }
    
    
    // Leer la opción
    Scanner s = new Scanner(System.in);
    int choose;
    
    System.out.print("\nSelecciona una opción: ");
    choose = s.nextInt();
    
    // Comprobar si la opción introducida es correcta
    while(choose <= 0 || choose > this.options.size()) {
      System.out.println("\nError: opción no válida.");
      System.out.print("Selecciona una opción de nuevo: ");
      choose = s.nextInt();
    }

    return choose;
  }
}
