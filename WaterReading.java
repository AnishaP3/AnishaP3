import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.text.NumberFormat;

/*
 * Asks user for the water reading and then calculates the cost
 * Prints the month, consumption, and the cost into a file
 * Anisha Penikalapati
 */

public class WaterReading {
  
  //Fields
  private String currentWaterReading;
  private String prevWaterReading;
  private User user;
  private double costOfWaterBill;
  private static final String WATER_BILL_FILE = "waterBill.txt";

  //Accessor for currentWaterReading
  public String getCurrentWaterReading() {
    return currentWaterReading;
  }

  //Mutator for currentWaterReading
  public void setCurrentWaterReading(String cwr) {
    if(cwr.matches("[0-9]+") && cwr.length() == 6 && Integer.parseInt(cwr) >= 0 ) {
      this.currentWaterReading = cwr;
    }
    else {
      throw new IllegalArgumentException("The water reading should be a 6 digit number");
    }
  }

  //Accessor for costOfWaterBill
  public double getCostOfWaterBill() {
    return costOfWaterBill;
  }

  //Mutator for costOfWaterBill
  public void setCostOfWaterBill(double cwb) {
    if(cwb >= 0 ) {
      this.costOfWaterBill = cwb;
    }
    else {
      throw new IllegalArgumentException("The cost of the water bill cannot be less than 0");
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
  
  //Accessor for prevWaterReading
  public String getPrevWaterReading() {
    return prevWaterReading;
  }

  //Mutator for prevWaterReading
  public void setPrevWaterReading(String pwr) {
     if (pwr != null) {
      this.prevWaterReading = pwr;
     } 
     else {
       this.prevWaterReading = "0";  
     }
  }

  //Overriding Constructor
  public WaterReading() {
  }

  //Method to get the previous water reading from the waterBill file
  public String getPrevWaterReadingFromFile(){
  
      //Variables
      double costOfWaterBill = 0;
      String line = "";
      String[] splitLine = null, commaSplitLine = null, colonSplitLine = null;
      int prevWaterReading = 0;
      User user = getUser();
    
      //Creating array lists
      ArrayList<String []> nameValues = new ArrayList<String[]>();
      ArrayList<String[]> rows = new ArrayList<String[]>();

      //Reading the waterBill file to get previous water bill
      try {
        FileReader fr = new FileReader (WATER_BILL_FILE);
        BufferedReader br = new BufferedReader(fr);
        line = br.readLine();
        while(line != null) {
          // If the line contains the username and the previous month
          if (line.contains(user.getUsername()) && line.contains(LocalDate.now().minusMonths(1).getMonth().name())) {
            //Split the line by the commas
            commaSplitLine = new String[line.split(",").length];
            commaSplitLine = line.split(",");
            // Looping through the array and if the username heading is found, we split it at the colon
            for (int i = 0; i < commaSplitLine.length; i++) {
              if(commaSplitLine[i].contains("Username")) {
                colonSplitLine = new String [2];
                colonSplitLine = commaSplitLine[i].split(":");
                // If the new split line equals the username, then we split
                // it by the comma and add it to the splitLine array to be used
                // to get the previous water reading later on
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
      // water bill and month paid is found, it will save it in prevWaterReading
      for(String[] nameValue : nameValues) {
          if(nameValue[0].contains("Water reading")) {
            prevWaterReading = Integer.parseInt(nameValue[1].trim().substring(0,6));
            break;
          }
      }
    return String.valueOf(prevWaterReading);
  }

  //Method to calculate the water bill
  public double calculateWaterBill() {
    
    //Variables
    double costOfWaterBill = 0;
    int prevWaterReading = 0;
    int currentWaterReading = 0;
    int diffWaterReading = 0;

    // Assigning the previous water reading and current water reading to variables
    prevWaterReading = Integer.parseInt(getPrevWaterReading());
    currentWaterReading = Integer.parseInt(getCurrentWaterReading());
  
    //Assigning the cost to the water bill depending on the previous usage
    //If there is no previous reading, the default prevWaterReading is 300 cubic meters 
    if (prevWaterReading < 0 || prevWaterReading == 0) {
        prevWaterReading = currentWaterReading -  230;
    }
    // If there is a previous reading, then subtract it from the current 
    // to get the diffReading
    diffWaterReading = currentWaterReading - prevWaterReading;
    //Discount if difference is less than 230 cubic meters
    if(diffWaterReading < 000230 && diffWaterReading > 000000) {
      costOfWaterBill = (1.27 * diffWaterReading) * 0.95;
    }
    //Calculating the cost if there is a previous reading
    else{
      costOfWaterBill = (1.27) * Math.abs(currentWaterReading - prevWaterReading);
    }
    return costOfWaterBill;  
  }
  
  //Method to print the water details into a file
  public void printingWaterDetailsIntoFile() {
    
    //Money formatting
    NumberFormat money = NumberFormat.getCurrencyInstance();

    //Printing the water bill into a file
    try {
        FileWriter fw = new FileWriter(WATER_BILL_FILE, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.print("Username: " + user.getUsername() + ", Month Paid: " + LocalDate.now().getMonth().name() + ", Water reading: " + currentWaterReading + " cubic meters, " + "Cost of the water bill: " + getCostOfWaterBill() +  ", Amount Paid: " + 0.00); 
        pw.print("\r\n");
        fw.close();
        pw.close();
    }
    catch(IOException e) {
      System.out.println("\nThe water bill details could not be printed into a file");
    }
  
  } 
}