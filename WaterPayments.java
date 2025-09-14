import java.util.*;
import java.io.*;
import java.time.LocalDate;

/*
 * Asks the user to enter their credit card information so they can pay 
   their bills
 * Prints the amount due and the amount paid in a file
 * Anisha Penikalapati
 */

public class WaterPayments {
  
  //Fields
  private double amountPaid;
  private double balance;
  private User user;
  private String creditCardNumber;
  private double totalCost;
  private double totalAmountPaid;
  private String updatedDetails;
  private static final String WATER_BILL_FILE = "waterBill.txt";

  //Accessor for amountPaid
  public double getAmountPaid() {
    return amountPaid;
  }

  //Mutator for amountPaid
  public void setAmountPaid(double ap) {
    if(ap >= 0 ) {
      this.amountPaid = ap;
    }
    else{
      throw new IllegalArgumentException("The amount paid should be greater than 0 and all digits should be positive integers");
    }
  }

  //Accessor for totalAmountPaid
  public double getTotalAmountPaid() {
    return totalAmountPaid;
  }

  //Mutator for totalAmountPaid
  public void setTotalAmountPaid(double tap) {
    this.totalAmountPaid = tap;
  }

  //Accessor for totalCost
  public double getTotalCost() {
    return totalCost;
  }

  //Mutator for totalCost
  public void setTotalCost(double tc) {
    this.totalCost = tc;
  }
  
  //Accessor for User
  public User getUser() {
    return user;
  }

  //Mutator for User
  public void setUser(User user) {
    if(user != null) {
      this.user = user;
    }
    else{
      throw new IllegalArgumentException("There is no user");
    }
  }
  
  //Accessor for balance
  public double getBalance() {
    return balance;
  }

  //Mutator for currentElectricityReading
  public void setBalance(String b) {
    if(b.matches("[0-9]+") && Integer.parseInt(b) > 0 ) {
      this.balance = Integer.parseInt(b);
    }
    else {
      throw new IllegalArgumentException("The amount paid should be greater than 0 and all digits should be positive integers");
    }
  }

  //Accessor for creditCardNumber
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  //Mutator for creditCardNumber
  public void setCreditCardNumber(String ccn) {
    if(ccn.length() == 16 && ccn.matches("[0-9]+")) {
      this.creditCardNumber = ccn;
    }
    else {
      throw new IllegalArgumentException("The card number should be 16 numbers");
    }
  }

  //Accessor for updatedDetails
  public String getUpdatedDetails() {
    return updatedDetails;
  }
  
  //Mutator for updatedDetails
  public void setUpdatedDetails(String updatedDetails) {
    this.updatedDetails = updatedDetails;
  }
  
  //Overriding Constructor
  public WaterPayments() {
  }

  //Method to get cost of the water bill from the waterBill file
  public ArrayList<String[]> getCostOfWaterBillFromFile() {

    //Variables
    String line = "";
    String[] splitLine, commaSplitLine, colonSplitLine;

    //Creating array lists
    ArrayList<String []> nameValues = new ArrayList<String[]>();;
    ArrayList<String[]> rows = new ArrayList<String[]>();

    //Reading the waterBill file to get the cost of the water bill
    try {
      FileReader fr = new FileReader (WATER_BILL_FILE);
      BufferedReader br = new BufferedReader(fr);
      line = br.readLine();
      splitLine = new String[line.split(",").length];
      while(line != null) {
        if (line.contains(user.getUsername())) {
              //Split the line by the commas
              commaSplitLine = new String[line.split(",").length];
              commaSplitLine = line.split(",");
              //loop through the array & split at the colon if the user's username is found
              for (int i = 0; i < commaSplitLine.length; i++) {
                if(commaSplitLine[i].contains("Username")) {
                  colonSplitLine = new String [2];
                  colonSplitLine = commaSplitLine[i].split(":");
                  // If the username is equal to the username that is split from its       
                  // heading, and print the line
                  if(colonSplitLine[1].trim().equalsIgnoreCase(user.getUsername())) {
                    rows.add(commaSplitLine);
                  }//END colonSplit
                }//END commaSplit
              }//END for
            }//END if
           //If the lines doesn't have the username then print the menu again 
           if (!line.contains(user.getUsername())) {
             System.out.println("No reading was found");
             break;
           }
            line = br.readLine();
          }//END While
          br.close();
        }//END Try
       
    catch(IOException e) {
      System.out.println("Could not read the file");
    }
  
    //Loop to split the name from the data
    for(String[] row : rows) {
      nameValues = new ArrayList<String[]>();
      for(int i = 0; i < row.length; i++) {
        nameValues.add(row[i].split(":"));
      }
    }

    return nameValues;
  }

  //Method to update the amount paid in the water file
  public void updateTheAmountInWaterFile() {

    //Variables
    int matchCounter = 0;
    String line = "", beforeLines = "", afterLines = "", currentLine = "";
    String[] splitLine, splitUpdatedLines;
    String  updatedCurrentLine = "";
    double totalAmountPaid = 0, totalCost=0, amountPaid = 0, finalCost = 0;
    User user = getUser();
    
    //Creating array lists
    ArrayList<String []> nameValues = new ArrayList<String[]>();
    ArrayList<String[]> rows = new ArrayList<String[]>();
    ArrayList<String[]> userSpecificRows = new ArrayList<String[]>();
    ArrayList<String []> userNameValues = new ArrayList<String[]>();
    
    // Reading the waterBill file and finding the matching username and   
    // month to get the lines that need to be changed and save the rest
    // of the lines
    try {
      FileReader fr = new FileReader (WATER_BILL_FILE);
      BufferedReader br = new BufferedReader(fr);
      line = br.readLine();
      splitLine = new String[line.split(",").length];
      
      while(line!= null) {
        // if the username matches add all user lines to calculate the totals
        if (line.contains(user.getUsername())) {
          userSpecificRows.add(line.split(","));
        }      
        //if username and month matches, then it is the current line
          if (line.contains(user.getUsername()) && line.contains(LocalDate.now().getMonth().name())) {
          matchCounter += 1;
             // If there is a duplicate user, add the 
             // user lines to the before lines and change the 
             // current line to the new current line
             if (matchCounter > 1) {
               beforeLines += currentLine;
               currentLine = line + "\n";
               //resetting match counter
               matchCounter = matchCounter -1;
             } 
             //Updated line for payments
             else {
              currentLine = line + "\n";
             }
          }
        // After the user is matched, the rest of the lines are added
        else if (matchCounter == 1) {
          afterLines += line + "\n";
        } 
        // All the lines before the user is matched are saved here
        else if (matchCounter < 1) {  
          beforeLines += line + "\n";
        }
        line = br.readLine();
      }
      br.close();
    } 
    catch(IOException e) {
      System.out.println("Could not read the file");
    }

    //Loop to split the name from the data
    for(String[] userRow : userSpecificRows) {  
       userNameValues = new ArrayList<String[]>();
      for(int i = 0; i < userRow.length; i++) {
          userNameValues.add(userRow[i].split(":"));
      }
    }

    // Loop to find the cost of the water bill and amount paid in the 
    // array and add the total cost and total amount paid 
    for(String[] userNameValue : userNameValues) {
      if(userNameValue[0].contains("Cost of the water bill")) {
        totalCost += Double.parseDouble(userNameValue[1]);
      } 
      else if(userNameValue[0].contains("Amount Paid")) {
          totalAmountPaid += Double.parseDouble(userNameValue[1]);
      }
    }

    //Calculating the final cost
    finalCost = (totalCost-totalAmountPaid);
    setTotalCost(totalCost);
    amountPaid = getAmountPaid();
    setTotalAmountPaid(totalAmountPaid + amountPaid);
    
    // Splitting the current line into name and values
    rows.add(currentLine.split(","));

    //Splitting name from the values
    for(String[] row : rows) {
      for(int i = 0; i < row.length; i++) {
        nameValues.add(row[i].split(":"));
      }
    }

    //Updating the amount paid if the user pays their water bill
    for(String[] nameValue : nameValues) {
      if(nameValue[0].contains("Amount Paid")) {
        amountPaid += Double.parseDouble(nameValue[1]);
        nameValue[1] = " " + String.valueOf(amountPaid);
      }
      if(updatedCurrentLine != "") {
          updatedCurrentLine += ",";
      }
      updatedCurrentLine += nameValue[0] + ":" + nameValue[1];
    }

    //Setting the updated details
    setUpdatedDetails(updatedCurrentLine);
    
    //Printing the updated water bill into a file
    try {
        FileWriter fw = new FileWriter(WATER_BILL_FILE);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(beforeLines + updatedCurrentLine + afterLines + "\r\n");
        pw.close();
        fw.close();
    }
    catch(IOException e) {
      System.out.println("The water bill details could not be printed into a file");
    }
  
  }
} 