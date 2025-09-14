import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.text.NumberFormat;


/*
 * Asks user for the electricity reading and then calculates the cost
 * Prints the month, consumption, and the cost into a file
 * Anisha Penikalapati
 */
  
public class ElectricityReading {

    //Fields
    private String currentElectricityReading;
    private String prevElectricityReading;
    private User user;
    private double costOfElectricityBill;
    private static final String ELECTRICITY_BILL_FILE = "electricityBill.txt";

    //Accessor for currentElectricityReading
    public String getCurrentElectricityReading() {
      return currentElectricityReading;
    }

    //Mutator for currentElectricityReading
    public void setCurrentElectricityReading(String cer) {
      if(cer.matches("[0-9]+") && cer.length() == 5 && Integer.parseInt(cer) >= 0 ) {
        this.currentElectricityReading = cer;
      }
      else {
        throw new IllegalArgumentException("The electricity reading should be a 5 digit number");
      }
    }

    //Accessor for costOfElectricityBill
    public double getCostOfElectricityBill() {
      return costOfElectricityBill;
    }

    //Mutator for costOfElectricityBill
    public void setCostOfElectricityBill(double ceb) {
      if(ceb >= 0 ) {
        this.costOfElectricityBill = ceb;
      }
      else {
        throw new IllegalArgumentException("The cost of the electricity bill cannot be less than 0");
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

    //Accessor for prevElectricityReading
    public String getPrevElectricityReading() {
      return prevElectricityReading;
    }

    //Mutator for prevElectricityReading
    public void setPrevElectricityReading(String per) {
       if (per != null) {
        this.prevElectricityReading = per;
       } 
       else {
         this.prevElectricityReading = "0";  
       }
    }

    //Overriding Constructor
    public ElectricityReading() {
    }

    //Method to get the previous electricity reading from the electricityBill file
    public String getPrevElectricityReadingFromFile(){

        //Variables
        double costOfElectricityBill = 0;
        String line = "";
        String[] splitLine = null, commaSplitLine = null, colonSplitLine = null;
        int prevElectricityReading = 0;
        User user = getUser();

        //Creating array lists
        ArrayList<String []> nameValues = new ArrayList<String[]>();
        ArrayList<String[]> rows = new ArrayList<String[]>();

        //Reading the electricityBill file to get previous electricity bill
        try {
          FileReader fr = new FileReader (ELECTRICITY_BILL_FILE);
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
                    if(colonSplitLine[1].trim().equals(user.getUsername())) {
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
        // electricity bill and month paid is found, it will save it in prevElectricityReading
        for(String[] nameValue : nameValues) {
            if(nameValue[0].contains("Electricity reading")) {
              prevElectricityReading = Integer.parseInt(nameValue[1].trim().substring(0,5));
              break;
            }
        }
      return String.valueOf(prevElectricityReading);
    }

    //Method to calculate the electricity bill
    public double calculateElectricityBill() {

      //Variables
      double costOfElectricityBill = 0;
      int prevElectricityReading = 0;
      int currentElectricityReading = 0;
      int diffElectricityReading = 0;
      
      // Assigning the previous electricity reading and current electricity reading to variables
      prevElectricityReading = Integer.parseInt(getPrevElectricityReading());
      currentElectricityReading = Integer.parseInt(getCurrentElectricityReading());
      System.out.println("Your previous reading: " + prevElectricityReading);
      System.out.println("Your current reading: " + currentElectricityReading);
      
      // Assigning the cost to the electricity bill depending on the previous 
      // usage. If there is no previous reading the default diffReading is 150 cubic meters
      if (prevElectricityReading < 0 || prevElectricityReading == 0) {
           prevElectricityReading = currentElectricityReading -  150;
      }
      //Calculating the diffReading if there is a previous reading
      diffElectricityReading = currentElectricityReading - prevElectricityReading;
      
      //Calculating the cost of the electricity bill
      costOfElectricityBill = (0.08) * Math.abs(currentElectricityReading - prevElectricityReading);
      
      //Discount if difference is less than 150 cubic meters
      if(diffElectricityReading < 00150 && diffElectricityReading > 00000) {
        System.out.println("\nYou are eligible for a discount ");
        costOfElectricityBill = (0.08 * (currentElectricityReading - prevElectricityReading)) * 0.95;
      }
      return costOfElectricityBill;  
    }

    //Method to print the electricity details into a file
    public void printingElectricityDetailsIntoFile() {

      //Money formatting
      NumberFormat money = NumberFormat.getCurrencyInstance();

      //Printing the electricity bill into a file
      try {
          FileWriter fw = new FileWriter(ELECTRICITY_BILL_FILE, true);
          PrintWriter pw = new PrintWriter(fw);
          pw.print("Username: " + user.getUsername() + ", Month Paid: " + LocalDate.now().getMonth().name() + ", Electricity reading: " + currentElectricityReading + " cubic meters, " + "Cost of the electricity bill: " + getCostOfElectricityBill() +  ", Amount Paid: " + 0.00); 
          pw.print("\r\n");
          fw.close();
          pw.close();
      }
      catch(IOException e) {
        System.out.println("\nThe electricity bill details could not be printed into a file");
      }

    }
  }