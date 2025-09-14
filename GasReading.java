import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.text.NumberFormat;

/*
 * Asks user for the gas reading and then calculates the cost
 * Prints the month, consumption, and the cost into a file
 * Anisha Penikalapati
 */

public class GasReading {
  
  //Fields
  private String currentGasReading;
  private String prevGasReading;
  private User user;
  private double costOfGasBill;
  private static final String GAS_BILL_FILE = "gasBill.txt";

  //Accessor for currentGasReading
  public String getCurrentGasReading() {
    return currentGasReading;
  }

  //Mutator for currentGasReading
  public void setCurrentGasReading(String cgr) {
    if(cgr.matches("[0-9]+") && cgr.length() == 6 && Integer.parseInt(cgr) >= 0 ) {
      this.currentGasReading = cgr;
    }
    else {
      throw new IllegalArgumentException("The gas reading should be a 6 digit number");
    }
  }

  //Accessor for costOfGasBill
  public double getCostOfGasBill() {
    return costOfGasBill;
  }

  //Mutator for costOfGasBill
  public void setCostOfGasBill(double cgb) {
    if(cgb >= 0 ) {
      this.costOfGasBill = cgb;
    }
    else {
      throw new IllegalArgumentException("The cost of the gas bill cannot be 0");
    }
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
    else {
      throw new IllegalArgumentException("There is no user");
    }
  }

  //Accessor for prevGasReading
  public String getPrevGasReading() {
    return prevGasReading;
  }

  //Mutator for prevGasReading
  public void setPrevGasReading(String pgr) {
     if (pgr != null) {
      this.prevGasReading = pgr;
     } 
     else {
       this.prevGasReading = "0";  
     }
  }

  //Overriding Constructor
  public GasReading() {
  }

  //Method to get the previous gas reading from the gasBill file
  public String getPrevGasReadingFromFile() {

      //Variables
      double costOfGasBill = 0;
      String line = "";
      String[] splitLine = null, commaSplitLine = null, colonSplitLine = null;
      int prevGasReading = 0;
      User user = getUser();

      //Creating array lists
      ArrayList<String []> nameValues = new ArrayList<String[]>();
      ArrayList<String[]> rows = new ArrayList<String[]>();

      //Reading the gasBill file to get previous gas bill
      try {
        FileReader fr = new FileReader (GAS_BILL_FILE);
        BufferedReader br = new BufferedReader(fr);
        line = br.readLine();
        while(line != null) {
          //if the line has the username and previous month, then split it at the commas
          if (line.contains(user.getUsername()) && line.contains(LocalDate.now().minusMonths(1).getMonth().name())) {
            //Split the line by the commas
              commaSplitLine = new String[line.split(",").length];
              commaSplitLine = line.split(",");
              // Looping through the array and if the username heading is found, we split it at the colon
              for (int i = 0; i < commaSplitLine.length; i++) {
                if(commaSplitLine[i].contains("Username")) {
                  colonSplitLine = new String [2];
                  colonSplitLine = commaSplitLine[i].split(":");
                  // If the new split line equals the username, then we split it by the comma and add it to the
                  // splitLine array to be used to get the previous water reading later on
                  if(colonSplitLine[1].trim().equalsIgnoreCase(user.getUsername())) {
                    splitLine = new String[line.split(",").length];
                    splitLine = line.split(",");
                    break;
                  }
                }
              }
          }
          line = br.readLine();
        }
        br.close();
        if (splitLine != null) {
          rows.add(splitLine);
        } 
      }
      catch(IOException e) {
        System.out.println("Could not read the file");
      }

      //Loop to split the name of the data from the data
      for(String[] row : rows) {
        for(int i = 0; i < row.length; i++) {
          nameValues.add(row[i].split(":"));
        }
      }

      // Loops through the names and values and if the cost of the 
      // gas bill and month paid is found, it will save it in prevGasReading
      for(String[] nameValue : nameValues) {
          if(nameValue[0].contains("Gas reading")) {
            prevGasReading = Integer.parseInt(nameValue[1].trim().substring(0,6));
            break;
          }
      }
    return String.valueOf(prevGasReading);
  
  }

  //Method to calculate the gas bill
  public double calculateGasBill() {

    //Variables
    double costOfGasBill = 0;
    int prevGasReading = 0;
    int currentGasReading = 0;
    int diffGasReading = 0;
    
    // Assigning the previous gas reading and current gas reading to variables
    prevGasReading = Integer.parseInt(getPrevGasReading());
    currentGasReading = Integer.parseInt(getCurrentGasReading());
    System.out.println("Your previous reading: " + prevGasReading);
    System.out.println("Your current reading: " + currentGasReading);
    
    // Assigning the cost to the gas bill depending on the previous usage
    // If there is no previous reading the default diffReading is 150 cubic meters
    if (prevGasReading < 0 || prevGasReading == 0) {
       prevGasReading = currentGasReading -  150;
    }
    //Calculating the diffReading if there is a previous reading 
    diffGasReading = currentGasReading - prevGasReading;
    
    //Calculating the cost if there is a previous reading
    costOfGasBill = (0.14) * Math.abs(diffGasReading);
  
    //Discount if difference is less than 150 cubic meters
    if(diffGasReading < 000150 && diffGasReading > 000000) {
      System.out.println("You are eligible for a discount ");
      costOfGasBill = (0.14 * diffGasReading) * 0.95;
    }
    return costOfGasBill;  
  }

  //Method to print the gas details into a file
  public void printingGasDetailsIntoFile() {

    //Money formatting
    NumberFormat money = NumberFormat.getCurrencyInstance();

    //Printing the gas bill into a file
    try {
        FileWriter fw = new FileWriter(GAS_BILL_FILE, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.print("Username: " + user.getUsername() + ", Month Paid: " + LocalDate.now().getMonth().name() + ", Gas reading: " + currentGasReading + " cubic meters, " + "Cost of the gas bill: " + getCostOfGasBill() +  ", Amount Paid: " + 0.00); 
        pw.print("\r\n");
        fw.close();
        pw.close();
    }
    catch(IOException e) {
      System.out.println("\nThe gas bill details could not be printed into a file");
    }
    
  }
}