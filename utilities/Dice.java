package utilities;

/**
 * Clase que representa a los objetos de tipo Dado.
 * @author David Pérez Ruiz.
 * 
 * @param numberFace número de caras que tiene el dado.
 * @param face cara visible del dado.
 */

public class Dice {
  // Atributos
  protected int numberFace;
  private int face;
  
  // Constructor
  public Dice() {
    this.numberFace = 6;
  }
  
  // Métodos
  /**
   * Método: throwDice
   * Propósito: lanza el dado.
   * @return valor entre 1 y 6.
   */
  public int throwDice() {
    this.face = (int) (Math.random() * this.numberFace) + 1;
    return this.face;
  }
  
  /**
   * Método: getFace
   * Propósito: muestra la cara visible del dado.
   * @return cara visible del dado.
   */
  public int getFace() {
    return face;
  }
}

