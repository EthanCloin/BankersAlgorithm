import java.util.Scanner;

public class Bank {
  private int m;
  private int n;

  int[] available;
  int[][] maximum;
  int[][] allocation;
  int[][] need;

  // Constructor
  Bank (){
    // Initialize as -1
    m = -1;
    n = -1;
  }

  // Setter and Getters
  public void setM(int m) {
    this.m = m;
  }
  public void setN(int n) {
    this.n = n;
  }
  public int getM() {
    return m;
  }
  public int getN() {
    return n;
  }

  // build arrays and matrices

  /*
  populates the available array with m integers read from input
   */
  public void buildAvailable(Scanner input){
    available = new int[m];

    for (int i = 0; i < m; i++){
      System.out.printf("Resource %d: ", i);
        if (input.hasNextInt()) {
          //accept integer
          available[i] = input.nextInt();
          System.out.println();
        }

    }
  }

  public static void main(String[] args){
    Bank myBank = new Bank();
    int m;
    int n;

    Scanner input = new Scanner(System.in);
    boolean validInput = false;

    // Prompt user for valid N Value
    while(!validInput) {
      System.out.println("Enter the number of customers (N):");
      if (input.hasNextInt()){
        n = input.nextInt();
        System.out.printf("> N: %d", n);
        myBank.setN(n);
        validInput = true;
      }else {
        System.out.println("Please enter a valid integer!");
        input.next();
      }
    }
    validInput = false;
    System.out.println();
    // Prompt user for valid M Value
    while(!validInput) {
      System.out.println("Enter the number of resources (M): ");
      if (input.hasNextInt()){
        m = input.nextInt();
        System.out.printf("> M: %d\n", m);
        myBank.setM(m);
        validInput = true;
      }else {
        System.out.println("Please enter a valid integer!");
        input.next();
      }
    }

    // populate maximum
    System.out.printf("Enter the available amount for all %d resources in your Bank:\n", myBank.getM());
    myBank.buildAvailable(input);

    } // end main method
    
  }
