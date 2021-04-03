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
  /*
  prints contents of available to sysout
   */
  public void displayAvalilable(){
    System.out.print("Contents of Available: ");
    for (int i = 0; i < m; i++){
      System.out.printf("%d ", available[i]);
    }
    System.out.println();
  }

  /*
  prints contents of allocation to sysout
   */
  public void displayAllocation(){
    System.out.print("Contents of Allocation: ");
    for (int i = 0; i < m; i++){
      System.out.println();
      for (int j = 0; j < n; j++) {
        System.out.printf("%d ", allocation[i][j]);
      }
    }
    System.out.println();
  }

  /*
  populates the available array with m integers read from input
   */
  public void buildAvailable(Scanner input){
    available = new int[m];

    for (int i = 0; i < m; i++){
      System.out.printf("> Resource %d: ", i);
        if (input.hasNextInt()) {
          //accept integer
          available[i] = input.nextInt();
        }

    }
  }
  /*
  populates the allocation matrix
   */
  private void buildAllocation(Scanner input) {
    allocation = new int[m][n];

    // accept m rows of integers as strings
    for (int i = 0; i < m; i++){
      System.out.printf("> allocation[%d]: ", i);
      for (int j = 0; j < n; j++){
        allocation[i][j] = input.nextInt();
      }
    }

  }

  public static void main(String[] args){
    Bank myBank = new Bank();
    int m = 1;
    int n = 1;

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

    // populate available
    System.out.printf("Enter the available amount for all M = %d resources in your Bank:\n", myBank.getM());
    myBank.buildAvailable(input);
    myBank.displayAvalilable();

    // populate allocation
    System.out.printf("For each of the N = %d lines, enter the number of " +
    "instances allocated for each of the M = %d resources:\n", n, m);
    myBank.buildAllocation(input);
    myBank.displayAllocation();
    } // end main method



}
