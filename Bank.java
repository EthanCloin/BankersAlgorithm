import java.util.Scanner;

public class Bank {
  private int n;
  private int m;

  int[] available;
  int[][] maximum;
  int[][] allocation;
  int[][] need;
  boolean[] finish;

  // Constructor
  Bank (){
    // Initialize as -1
    n = -1;
    m = -1;
  }
  public void test(){
    n = 5;
    m = 3;

    int[] maxAvailable = {10, 5, 7};
    //allocation = new int[n][m];

    allocation = new int[][]{
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2},
    };

    maximum = new int[][]{
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3},
    };

    displayAllocation();
    buildNeed();
    displayNeed();

    buildAvailable(maxAvailable);

    displayVector(safetyAlgorithm());

    int[] resourceRequest = new int[] {1, 0, 2};
    resourceRequest(1, resourceRequest);
  }
  // Setter and Getters
  private void setM(int m) {
    this.m = m;
  }
  private void setN(int n) {
    this.n = n;
  }
  private int getM() {
    return m;
  }
  private int getN() {
    return n;
  }
  /*
  prints contents of available to sysout
   */
  private void displayAvailable(){
    System.out.print("Contents of Available: ");
    for (int i = 0; i < m; i++){
      System.out.printf("%d ", available[i]);
    }
    System.out.println();
  }

  /*
  prints contents of work to sysout
   */
  private void displayVector(int[] vector){
    System.out.print("[ ");
    int len = vector.length;
    for (int i = 0; i < len; i++){
      System.out.printf("%d ", vector[i]);
    }
    System.out.print("]");
  }

  /*
  prints contents of allocation to sysout
   */
  private void displayAllocation(){

    for (int i = 0; i < n; i++){
      System.out.println();
      for (int j = 0; j < m; j++) {
        System.out.printf("%d ", allocation[i][j]);
      }
    }
    System.out.println();
  }

  /*
  prints contents of allocation to sysout
   */
  private void displayMaximum(){
    System.out.print("Contents of Maximum: ");
    for (int i = 0; i < n; i++){
      System.out.println();
      for (int j = 0; j < m; j++) {
        System.out.printf("%d ", maximum[i][j]);
      }
    }
    System.out.println();
  }

  /*
  prints contents of need to sysout
   */
  private void displayNeed(){
    System.out.print("Contents of Need: ");
    for (int i = 0; i < n; i++){
      System.out.println();
      for (int j = 0; j < m; j++) {
        System.out.printf("%d ", need[i][j]);
      }
    }
    System.out.println();
  }

  /*
  populates the available array with m integers calculated from the
  maximum and allocation matrices
   */
  private void buildAvailable(int[] maxAvailable){
    int[] totalUsed = new int[n];
    int[] newAvailable = new int[m];

    for (int j = 0; j < m; j++){
      for (int i = 0; i < n; i++) {
        totalUsed[j] += allocation[i][j];
      }
    }
    for (int i = 0; i < m; i++){
      newAvailable[i] = maxAvailable[i] - totalUsed[i];
    }
    available = newAvailable.clone();
  }
  /*
  populates the allocation matrix
   */
  private void buildAllocation(Scanner input) {
    allocation = new int[n][m];

    // accept m rows of integers as strings
    for (int i = 0; i < n; i++){
      System.out.printf("> allocation[%d]: ", i);
      for (int j = 0; j < m; j++){
        allocation[i][j] = input.nextInt();
      }
    }

  }

  /*
  populates the maximum matrix
   */
  private void buildMaximum(Scanner input) {
    maximum = new int[n][m];

    // accept m rows of integers as strings
    for (int i = 0; i < n; i++){
      System.out.printf("> maximum[%d]: ", i);
      for (int j = 0; j < m; j++){
        maximum[i][j] = input.nextInt();
      }
    }

  }

  /*
  populate need matrix
   */
  private void buildNeed(){
    need = new int[n][m];

    for (int i = 0; i < n; i++){
      for (int j = 0; j < m; j++){
        need[i][j] = maximum[i][j] - allocation[i][j];
      }
    }
  }

  /*
  initialize finish to false
   */
  private void buildFinish(){
    finish = new boolean[n];
    for (int i = 0; i < n; i++){
      finish[i] = false;
    }
  }
/*
returns index of first false finish, or -1 for all true
 */
  private int checkFinish(boolean[] finish, int current){

    // check the contents of finish
    System.out.println("\nChecking for safe state...");
    for (int i = 0; i < n; i++) {
      System.out.printf("finish[%d] = %b\n", i, finish[i]);
      // if false, continue loop
      if (!finish[i]) {
        current = i;
        return current;
      }
      if (i == n-1 && finish[i]){
        return -1;
      }
    }
    return current;
  }
  private int[] safetyAlgorithm(){
    // initialize finish array to false
    buildFinish();
    // initialize work array to available
    int[] work = available.clone();
    // initialize current value
    int current = 0;

    // initialize to track order
    int[] processOrder = new int[n];
    int successCounter = 0;

    while (true) {

      // display customer number, work, and need
      System.out.println("\nCustomer: " + current);
      System.out.printf("A: finish[%d] = %b\n",current, finish[current]);

      if (!finish[current]) {
        System.out.println("B: Is need <= work?");
        System.out.printf("\tneed[%d] = ", current);
        displayVector(need[current]);
        System.out.printf("; work = ");
        displayVector(work);
      }else{
        System.out.printf("work = ");
        displayVector(work);
      }
      System.out.println();


      if (!finish[current]) {
        // check whether need is <= work
        boolean enough = true;
        for (int i = 0; i < m; i++) {
          if (need[current][i] > work[i]) {
            enough = false;
          }
        }

        // update work to += allocation
        if (enough) {
          finish[current] = true;
          updateWork(finish, work, current);

          // add process to array representing OoO
          processOrder[successCounter] = current;
          successCounter++;

        } else {
          System.out.println("FAILED, need > work!");
        }
      }
      current++;

      // reached end of finish array
      int maxRetries = 5;
      int retryCount = 0;
      if (current == n) {
        current = checkFinish(finish, current);
        retryCount++;

        if (retryCount == maxRetries) {
          System.out.println("Unable to process request!");
          int[] fail = new int[]{-1};
          return fail;
        }
        if (current == -1) {
          System.out.println("System is in a safe state!");
          return processOrder;
        }
      }
    }
  }

/*

 */
  private void updateWork(boolean[] finish, int[] work, int current) {
    for (int i = 0; i < m; i++) {
      work[i] += allocation[current][i];
      allocation[current][i] = 0;
    }
    System.out.printf("finish[%d] is now = %b\n", current, finish[current]);
    System.out.printf("work is now = ");
    displayVector(work);
    System.out.println();
  }

  public static void main(String[] args){
    Bank myBank = new Bank();
//    int m = 1;
//    int n = 1;
//
//    Scanner input = new Scanner(System.in);
//    boolean validInput = false;
//
//    // Prompt user for valid N Value
//    while(!validInput) {
//      System.out.println("Enter the number of customers (N):");
//      if (input.hasNextInt()){
//        n = input.nextInt();
//        System.out.printf("> N: %d", n);
//        myBank.setN(n);
//        validInput = true;
//      }else {
//        System.out.println("Please enter a valid integer!");
//        input.next();
//      }
//    }
//    validInput = false;
//    System.out.println();
//    // Prompt user for valid M Value
//    while(!validInput) {
//      System.out.println("Enter the number of resources (M): ");
//      if (input.hasNextInt()){
//        m = input.nextInt();
//        System.out.printf("> M: %d\n", m);
//        myBank.setM(m);
//        validInput = true;
//      }else {
//        System.out.println("Please enter a valid integer!");
//        input.next();
//      }
//    }
//
//    // populate available
//    System.out.printf("Enter the maximum available amount for all M = %d resources in your Bank:\n", myBank.getM());
//
//    int[] maxAvailable = new int[m];
//
//    for (int i = 0; i < m; i++){
//      System.out.printf("> Resource %d: ", i);
//      if (input.hasNextInt()) {
//        //accept integer
//        maxAvailable[i] = input.nextInt();
//      }
//    }
//
//    // populate allocation
//    System.out.printf("Enter M = %d values for resources allocated to each of the %d customers:\n", m, n);
//    myBank.buildAllocation(input);
//
//    // populate maximum
//    System.out.printf("Enter M = %d values for the maximum demand on each of the resources\n", m);
//    myBank.buildMaximum(input);
//
//    // populate availability
//    myBank.buildAvailable(maxAvailable);
//
//    // populate need
//    myBank.buildNeed();
//    myBank.displayNeed();
//
//
//    myBank.safetyAlgorithm();



    myBank.test();

    } // end main method

  /*
  receives a customerNumber and array representing resource request, and returns
  true if the request is valid
   */
  private void resourceRequest(int customerNumber, int[] resourcesRequested) {
    // ensure that the provided request is within the limits of customer's
    // maximum needs

    if(validateRequest(resourcesRequested, customerNumber)){
      // attempt to execute request
      int[] oldAvailable = available.clone();
      int[] oldAllocation = allocation[customerNumber].clone();
      int[] oldNeed = need[customerNumber].clone();

      for (int i = 0; i < available.length; i++){
        available[i] -= resourcesRequested[i];
        allocation[customerNumber][i] += resourcesRequested[i];
        need[customerNumber][i] -= resourcesRequested[i];
      }

      System.out.println("\nUpdated Allocation Matrix:"); displayAllocation();
      System.out.println("\nUpdated Need Matrix: "); displayNeed();
      System.out.println("\nUpdated Available Vector: "); displayAvailable();



    }else {
      System.out.println("Invalid Request!");
      System.exit(-1);
    }

  }

/*
performs validity checks against customer maximum need and current available resources
 */

  private boolean validateRequest(int[] resourcesRequested, int customerNumber) {
    boolean ltNeed = false;
    int len = resourcesRequested.length;
    int[] customerNeed = need[customerNumber];

    System.out.println("\nCheck 1: Ensuring request is <= need...");
    System.out.printf("request = "); displayVector(resourcesRequested);
    System.out.printf(" need = "); displayVector(customerNeed);
    System.out.println();

    for (int i = 0; i < len; i++){
      if (resourcesRequested[i] > customerNeed[i]){
        System.out.println("Failed check 1, Request is NOT less than Need!");
        return false;
      }
    }
    ltNeed = true;
    System.out.println("Passed check 1, Request is less than Need!");

    if (ltNeed){
      System.out.println("\nCheck 2: Ensuring request is <= available...");
      System.out.printf("request = "); displayVector(resourcesRequested);
      System.out.printf(" available = "); displayVector(available);
      System.out.println();

      len = available.length;
      for (int i = 0; i < len; i++){
        if (resourcesRequested[i] > available[i]){
          System.out.println("Failed check 2, Request is NOT less than Available!");
          return false;
        }
      }
      System.out.println("Passed check 2, Request is less than Available!");
      return true;
    }
    return false;
  }

}
