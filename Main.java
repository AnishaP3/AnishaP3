import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.text.NumberFormat; 


/*
 * Anisha Penikalapati, Imaan Dar, Suha Khan
 * December 13, 2023
 * Purpose: purpose of this program is to act as a utilities website, 
   calculating the costs of utility usage for each user. It will allow 
   them to pay their gas, water, and/or electricty bills and give them 
   the option to pay in installments. This program will make paying 
   bills easier and safer. 
 */

class Main {
  
  public static void main(String[] args) {

    // Variables
    User user;
    int option;

    //Header
    System.out.println("\n▁ ▂ ▄ ▅ ▆ ▇ █ 𝗘𝗻𝗲𝗿𝗴𝘆 𝗦𝗽𝗲𝗰𝘁𝗿𝗮 █ ▇ ▆ ▅ ▄ ▂ ▁ ");

    //Calling methods
    user = loginOrRegister();

    //Loop to ask the user for a menu option until they choose to exit
    do {  
      option = getUserOption();
      callingUtilityServicesUsingOption(option, user);
    } while(option != 14);
  
  
  }//End main method

  //Method to decide if the user wants to login or register
  public static User loginOrRegister() {
  
    Scanner input = new Scanner(System.in);
  
    //Variables
    int haveAccount = 0;
    User user;
    boolean valid = false;
    
    //Asking user if they have already have an account
    System.out.println("\nEnter 1 to login");
    System.out.println("\nEnter 2 to register");
    do {
      System.out.print("\nEnter your choice: ");
      try {
        valid = true;
        haveAccount = input.nextInt();
        if((haveAccount != 1 && haveAccount != 2)) {
          System.out.println("\nThe choice must be either 1 or 2");
        }
      }
      catch(InputMismatchException e) {
        System.out.println("\nIt must be either 1 or 2");
        input.nextLine();
      }
    } while (!valid || (haveAccount != 1 && haveAccount != 2));

    //Login or register 
    if(haveAccount == 1) {
      user = checkUsernameAndPassword();
    }
    else {
     user = createAccountDetails();
    }
    //return user;
    return user;
  }
  
  //Method to get the user's option from the menu
  public static int getUserOption() {
  
    Scanner input = new Scanner(System.in);

    //Variables
    int menuOption = 0;

    //Asking user to choose an utility option
    System.out.println("\n----------------------------------");
    System.out.println(" ~  𝑴​​  𝒆​​  𝒏​​  𝒖   ~");
    System.out.println("---------------------------------- ");
    System.out.println("1. Enter water meter reading ");
    System.out.println("2. Enter gas meter reading ");
    System.out.println("3. Enter electricity meter reading ");
    System.out.println("4. View water billing history");
    System.out.println("5. View gas billing history");
    System.out.println("6. View electricity billing history");
    System.out.println("7. View water service outages");
    System.out.println("8. View gas service outages");
    System.out.println("9. View electricity service outages");
    System.out.println("10. Pay Water bill");
    System.out.println("11. Pay Gas bill");
    System.out.println("12. Pay Electricity bill");
    System.out.println("13. Change Account Details");
    System.out.println("14. Exit");

    //Asking the user which menu option they want
    do {
      System.out.print("\nEnter an option from the menu above: ");
      try {
        menuOption = input.nextInt();
        if(menuOption <= 0 || menuOption > 14) {
          System.out.println("\nThe option should be from 1 to 14");
        }
      }
      catch(InputMismatchException e) {
        System.out.println("\nThe option should be from 1 to 14");
      }
    } while (menuOption < 1 && menuOption > 14);
    
    return menuOption;
  }

  //Method to call a class depending on the option
  public static void callingUtilityServicesUsingOption(int option, User user) {

    //Variables
    String currentWaterReading = "", prevWaterReading = "";
    double costOfWaterBill = 0, amountPaid = 0, costOfGasBill = 0, costOfElectricityBill = 0;
    String currentGasReading = "", prevGasReading = "", gasReading = ""; 
    String currentElectricityReading = "", prevElectricityReading = "",     
           electricityReading = "";
    String creditCard = "", updatedCurrentLine = "";
    String[] splitUpdatedLines;
    String fName = "", lName = "", address = "", username = "", number = "", email = "", password = "";
    
    //Creating ArrayLists
    ArrayList<String []> nameValues = new ArrayList<String[]>();
    
    //Money formatting 
    NumberFormat money = NumberFormat.getCurrencyInstance();
    
    //Calling the corresponding methods based on the option the user chooses
    switch(option) {
        
    //Calling the WaterReading object and calling the methods
    case 1:
      System.out.println("\nE̳̿͟͞N̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ W̳̿͟͞A̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ M̳̿͟͞E̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ R̳̿͟͞E̳̿͟͞A̳̿͟͞D̳̿͟͞I̳̿͟͞N̳̿͟͞G̳̿͟͞");
      //Getting the water reading from the user
      currentWaterReading = getWaterReadingFromUser(); 
      WaterReading wr = new WaterReading();
      //Setting the user
      wr.setUser(user);
      //Get the previous water reading from the file
      prevWaterReading = wr.getPrevWaterReadingFromFile();
      //Set the current water reading 
      wr.setCurrentWaterReading(currentWaterReading);
      //Set the previous water reading
      wr.setPrevWaterReading(prevWaterReading);
      //Calculates the cost and is assigned to a variable
      costOfWaterBill = wr.calculateWaterBill();
      //Set the cost of the water bill
      wr.setCostOfWaterBill(costOfWaterBill);
      //Printing the total for the user 
      System.out.println("\nYour total comes up to " + money.format(costOfWaterBill));
      //printing the water details into a file
      wr.printingWaterDetailsIntoFile();
      break;
        
    //Calling the GasReading object and calling the methods
    case 2:
      System.out.println("\nE̳̿͟͞N̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ G̳̿͟͞A̳̿͟͞S̳̿͟͞ M̳̿͟͞E̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ R̳̿͟͞E̳̿͟͞A̳̿͟͞D̳̿͟͞I̳̿͟͞N̳̿͟͞G̳̿͟͞");
      //Getting the gas reading from the user
      currentGasReading = getGasReadingFromUser();
      GasReading gr = new GasReading();
      //Setting the user
      gr.setUser(user);
      //Get the previous gas reading from the file
      prevGasReading = gr.getPrevGasReadingFromFile();
      //Set the current gas reading 
      gr.setCurrentGasReading(currentGasReading);
      //Set the previous gas reading
      gr.setPrevGasReading(prevGasReading);
      //Calculates the cost and is assigned to a variable
      costOfGasBill = gr.calculateGasBill();
      //Set the cost of the gas bill
      gr.setCostOfGasBill(costOfGasBill);
      //Printing the total for the user
      System.out.println("\nYour total comes up to " + money.format(costOfGasBill));
      //printing the gas details into a file
      gr.printingGasDetailsIntoFile();
      break;
        
    //Calling the ElectricityReading object and calling the methods
    case 3: 
      //Header
      System.out.println("\nE̳̿͟͞N̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ E̳̿͟͞L̳̿͟͞E̳̿͟͞C̳̿͟͞T̳̿͟͞R̳̿͟͞I̳̿͟͞C̳̿͟͞I̳̿͟͞T̳̿͟͞Y̳̿͟͞ M̳̿͟͞E̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ R̳̿͟͞E̳̿͟͞A̳̿͟͞D̳̿͟͞I̳̿͟͞N̳̿͟͞G̳̿͟͞");
      //Getting the electricity reading from the user
      currentElectricityReading = getElectricityReadingFromUser();
      ElectricityReading er = new ElectricityReading();
      er.setUser(user);
      //Get the previous electricity reading from the file
      prevElectricityReading = er.getPrevElectricityReadingFromFile();
      //Set the current electricity reading 
      er.setCurrentElectricityReading(currentElectricityReading);
      //Set the previous electricity reading
      er.setPrevElectricityReading(prevElectricityReading);
      //Calculates the cost and is assigned to a variable
      costOfElectricityBill = er.calculateElectricityBill();
      //Set the cost of the gas bill
      er.setCostOfElectricityBill(costOfElectricityBill);
      //Printing the total for the user
      System.out.println("\nYour total comes up to " + money.format(costOfElectricityBill));
      //printing the electricity details into a file
      er.printingElectricityDetailsIntoFile();
      break;
        
    //Calling the WaterBillHistory object and calling the methods
    case 4:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ W̳̿͟͞A̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞ H̳̿͟͞I̳̿͟͞S̳̿͟͞T̳̿͟͞O̳̿͟͞R̳̿͟͞Y̳̿͟͞\n");
      WaterBillHistory wbh = new WaterBillHistory();
      wbh.setUser(user);
      wbh.printingWaterBillHistory();
      System.out.println("\n");
      break;
        
    //Calling the GasBillHistory object and calling the methods
    case 5:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ G̳̿͟͞A̳̿͟͞S̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞ H̳̿͟͞I̳̿͟͞S̳̿͟͞T̳̿͟͞O̳̿͟͞R̳̿͟͞Y̳̿͟͞\n");
      GasBillHistory gbh = new GasBillHistory();
      gbh.setUser(user);
      gbh.printingGasBillHistory();
      System.out.println("\n");
      break;
        
    //Calling the ElectricityBillHistory object and calling the methods
    case 6:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ E̳̿͟͞L̳̿͟͞E̳̿͟͞C̳̿͟͞T̳̿͟͞R̳̿͟͞I̳̿͟͞C̳̿͟͞I̳̿͟͞T̳̿͟͞Y̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞ H̳̿͟͞I̳̿͟͞S̳̿͟͞T̳̿͟͞O̳̿͟͞R̳̿͟͞Y̳̿͟͞\n");
      ElectricityBillHistory ebh = new ElectricityBillHistory();
      ebh.setUser(user);
      ebh.printingElectricityBillHistory();
      System.out.println("\n");
      break;
        
    //Calling the WaterServiceOutage object and calling the methods
    case 7:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ W̳̿͟͞A̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ S̳̿͟͞E̳̿͟͞R̳̿͟͞V̳̿͟͞I̳̿͟͞C̳̿͟͞E̳̿͟͞ O̳̿͟͞U̳̿͟͞T̳̿͟͞A̳̿͟͞G̳̿͟͞E̳̿͟͞S̳̿͟͞\n");
      WaterServiceOutage wso = new WaterServiceOutage();
      wso.setTime("Time: January 18, 2024 to January 19, 2024\n");
      wso.setDescription("\nDescription: Water services will have an outage due to a pipe blockage.");
      System.out.println(wso);
      break;

    //Calling the GasServiceOutage object and calling the methods
    case 8:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ G̳̿͟͞A̳̿͟͞S̳̿͟͞ S̳̿͟͞E̳̿͟͞R̳̿͟͞V̳̿͟͞I̳̿͟͞C̳̿͟͞E̳̿͟͞ O̳̿͟͞U̳̿͟͞T̳̿͟͞A̳̿͟͞G̳̿͟͞E̳̿͟͞S̳̿͟͞\n");
      GasServiceOutage gso = new GasServiceOutage();
      gso.setTime("Time: January 28, 2024 to January 29, 2024\n");
      gso.setDescription("\nDescription: Gas services will have an outage due to maintenance in the website.");
      System.out.println(gso);
      break;

    //Calling the ElectricityServiceOutage object and calling the methods
    case 9:
      //Header
      System.out.println("\nV̳̿͟͞I̳̿͟͞E̳̿͟͞W̳̿͟͞ E̳̿͟͞L̳̿͟͞E̳̿͟͞C̳̿͟͞T̳̿͟͞R̳̿͟͞I̳̿͟͞C̳̿͟͞I̳̿͟͞T̳̿͟͞Y̳̿͟͞ S̳̿͟͞E̳̿͟͞R̳̿͟͞V̳̿͟͞I̳̿͟͞C̳̿͟͞E̳̿͟͞ O̳̿͟͞U̳̿͟͞T̳̿͟͞A̳̿͟͞G̳̿͟͞E̳̿͟͞S̳̿͟͞\n");
      ElectricityServiceOutage eso = new ElectricityServiceOutage();
      eso.setTime("Time: January 28, 2024 to January 29, 2024\n");
      eso.setDescription("\nDescription: Electricity services will have an outage due to maintenance in the website.");
      System.out.println(eso);
      break;

    //Calling the WaterPayments object and calling the methods
    case 10:
      //Header
      System.out.println("\nP̳̿͟͞A̳̿͟͞Y̳̿͟͞ W̳̿͟͞A̳̿͟͞T̳̿͟͞E̳̿͟͞R̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞\n");
      WaterPayments wp = new WaterPayments();
      wp.setUser(user);
      nameValues = wp.getCostOfWaterBillFromFile();
      // Loops through the names and values and if the cost of the water bill 
      // and month paid is found, it will print them
      for(String[] nameValue : nameValues) {
          if(nameValue[0].contains("Cost of the water bill")) {
            System.out.println("the cost of the water bill is $"+ nameValue[1].trim());
          }
          if(nameValue[0].contains("Month Paid")) {
            System.out.print(" For the month" + nameValue[1].toLowerCase() + ", ");
          }
      }
      amountPaid = getAmountPaidFromUser();
      creditCard = getCreditCardFromUser();
      wp.setUser(user);
      wp.setAmountPaid(amountPaid);
      wp.setCreditCardNumber(creditCard);
      wp.updateTheAmountInWaterFile();
        
      //Printing the total cost, the amount paid, and the total amount due
      //Printing the updated amount 
      System.out.println("\nAfter the payment, these changes were made:");
      updatedCurrentLine = wp.getUpdatedDetails();
      splitUpdatedLines = updatedCurrentLine.split(",");
      System.out.println();
      for(String splitUpdatedLine : splitUpdatedLines) {
        System.out.println(splitUpdatedLine);
      }

      System.out.println("\nSUMMARY");
      System.out.println("Your total balance amount for all the readings entered is  " + money.format(wp.getTotalCost()));
      System.out.println("Your total amount paid for all the readings entered is  " + money.format(wp.getTotalAmountPaid()));
      break;

    //Calling the GasPayments object and calling the methods
    case 11:
        //Header
        System.out.println("\nP̳̿͟͞A̳̿͟͞Y̳̿͟͞ G̳̿͟͞A̳̿͟͞S̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞\n");
        GasPayments gp = new GasPayments();
        gp.setUser(user);
        nameValues = gp.getCostOfGasBillFromFile();
        // Loops through the names and values and if the cost of the gas bill 
        // and month paid is found, it will print them
        for(String[] nameValue : nameValues) {
            if(nameValue[0].contains("Cost of the gas bill")) {
              System.out.println("the cost of the gas bill is $" + nameValue[1].trim());
            }
            if(nameValue[0].contains("Month Paid")) {
              System.out.print(" For the month" + nameValue[1].toLowerCase() + ", ");
            }
        }
        amountPaid = getAmountPaidFromUser();
        creditCard = getCreditCardFromUser();
        gp.setUser(user);
        gp.setAmountPaid(amountPaid);
        gp.setCreditCardNumber(creditCard);
        gp.updateTheAmountInGasFile();

        //Printing the total cost, the amount paid, and the total amount due
        //Printing the updated amount 
        System.out.println("\nAfter the payment, these changes were made:");
        updatedCurrentLine = gp.getUpdatedDetails();
        splitUpdatedLines = updatedCurrentLine.split(",");
        System.out.println();
        for(String splitUpdatedLine : splitUpdatedLines) {
          System.out.println(splitUpdatedLine);
        }
        System.out.println("\nSUMMARY");
        System.out.println("Your total balance amount for all the readings entered is " + money.format(gp.getTotalCost()));
        System.out.println("Your total amount paid for all the readings entered is  " + money.format(gp.getTotalAmountPaid()));
        break;
        
    //Calling the ElectricityPayments object and calling the methods
    case 12:
       //Header
        System.out.println("\nP̳̿͟͞A̳̿͟͞Y̳̿͟͞ E̳̿͟͞L̳̿͟͞E̳̿͟͞C̳̿͟͞T̳̿͟͞R̳̿͟͞I̳̿͟͞C̳̿͟͞I̳̿͟͞T̳̿͟͞Y̳̿͟͞ B̳̿͟͞I̳̿͟͞L̳̿͟͞L̳̿͟͞\n");
        ElectricityPayments ep = new ElectricityPayments();
        ep.setUser(user);
        nameValues = ep.getCostOfElectricityBillFromFile();
        // Loops through the names and values and if the cost of the electricity bill 
        // and month paid is found, it will print them
        for(String[] nameValue : nameValues) {
            if(nameValue[0].contains("Cost of the electricity bill")) {
              System.out.println("the cost of the electricity bill is $" +nameValue[1].trim());
            }
            if(nameValue[0].contains("Month Paid")) {
              System.out.print("\nFor the month" + nameValue[1].toLowerCase() + ", ");
            }
        }
        amountPaid = getAmountPaidFromUser();
        creditCard = getCreditCardFromUser();
        ep.setUser(user);
        ep.setAmountPaid(amountPaid);
        ep.setCreditCardNumber(creditCard);
        ep.updateTheAmountInElectricityFile();

        //Printing the total cost, the amount paid, and the total amount due
        //Printing the updated amount 
        System.out.println("\nAfter the payment, these changes were made:");
        updatedCurrentLine = ep.getUpdatedDetails();
        splitUpdatedLines = updatedCurrentLine.split(",");
        System.out.println();
        for(String splitUpdatedLine : splitUpdatedLines) {
          System.out.println(splitUpdatedLine);
        }
        System.out.println("\nSUMMARY");
        System.out.println("Your total balance amount for all the readings entered is  " + money.format(ep.getTotalCost()));
        System.out.println("Your total amount paid for all the readings entered is  " + money.format(ep.getTotalAmountPaid()));
        break;
    //Calling the method to allow the user to change their account details
    case 13:
      //Header
      System.out.println("\nC̳̿͟͞H̳̿͟͞A̳̿͟͞N̳̿͟͞G̳̿͟͞E̳̿͟͞ A̳̿͟͞C̳̿͟͞C̳̿͟͞O̳̿͟͞U̳̿͟͞N̳̿͟͞T̳̿͟͞ D̳̿͟͞E̳̿͟͞T̳̿͟͞A̳̿͟͞I̳̿͟͞L̳̿͟͞S̳̿͟͞");
      updateAccountDetails(user);
      break;
     
    //Allowing the user to exit
    case 14:
      System.out.println("YOU ARE BEING SIGNED OUT...");
      System.exit(0);
      break;         
    }
  
  }

  //Method to ask the user for their water meter reading
  public static String getWaterReadingFromUser() {

    Scanner input = new Scanner (System.in);

    //Variables
    String waterReading = "";
    boolean valid = false;

    //Loop to get the water meter reading 
    do {
          System.out.print("\nEnter your 6 digit water meter reading: ");
          waterReading = input.nextLine();
          if(!waterReading.matches("[0-9]+")) {
            valid = false;
            System.out.println("\nThe water reading should be 6 numbers");
          }
          else if(waterReading.length() < 6) {
            valid = false;
            System.out.println("\nThe water reading entered is too short");
          }
          else if(waterReading.length() > 6) {
            valid = false;
            System.out.println("\nThe water reading entered is too long ");
          }
          else if(Integer.parseInt(waterReading) < 0) {
            valid = false;
            System.out.print("\nThe water reading must be larger than 0");   
          }
          else {
            valid = true;
          }
    } while (!valid || waterReading.length() < 6 || 
        waterReading.length() > 6 || !waterReading.matches("[0-9]+") || Integer.parseInt(waterReading) < 0);
    return waterReading;
  
  
  }

  //Method to ask the user for their gas meter reading
  public static String getGasReadingFromUser() {

      Scanner input = new Scanner (System.in);

      //Variables
      String gasReading = "";
      boolean valid = false;

      //Loop to get the gas meter reading from the user
      do {
            System.out.print("\nEnter your 6 digit gas meter reading: ");
            gasReading = input.nextLine();
            if(!gasReading.matches("[0-9]+")) {
              valid = false;
              System.out.print("\nThe gas reading must have 6 numbers\n ");
            }
            else if(gasReading.length() < 6) {
              valid = false;
              System.out.print("\nThe gas reading entered is too short\n ");
            }
            else if(gasReading.length() > 6) {
              valid = false;
              System.out.print("\nThe gas reading entered is too long\n ");
            }
            else if (Integer.parseInt(gasReading) < 0) {
              valid = false;
              System.out.print("\nThe gas reading must be larger than 0\n");   
            }
            else {
              valid = true;
            }
      } while (!valid || gasReading.length() < 6 || 
                gasReading.length() > 6 || Integer.parseInt(gasReading) < 0 || !gasReading.matches("[0-9]+"));
    return gasReading;
  
  
  }

  //Method to ask the user for their elecricity meter reading
  public static String getElectricityReadingFromUser() {

      Scanner input = new Scanner (System.in);

      //Variables
      String electricityReading = "";
      boolean valid = false;

      //Loop to get the electricity meter reading 
      do {
        System.out.print("\nEnter your 5 digit electricity meter reading: ");
          electricityReading = input.nextLine();
            if(!electricityReading.matches("[0-9]+")) {
              valid = false;
              System.out.print("\nThe electricity reading must have 5 numbers\n ");
            }
            else if(Integer.parseInt(electricityReading) < 0) {
              valid = false;
              throw new IllegalArgumentException("The electricity reading must be larger than 0\n");   
            }
            else if(electricityReading.length() < 5) {
              valid = false;
              System.out.print("\nThe electricity reading entered is too short\n");
            }
            else if(electricityReading.length() > 5) {
              valid = false;
              System.out.print("\nThe electricity reading entered is too long\n ");
            }
            else {
              valid = true;
            }
      } while (!valid || electricityReading.length() < 5 
                ||electricityReading.length() > 5 || Integer.parseInt(electricityReading) < 0 || !electricityReading.matches("[0-9]+"));
      
    return electricityReading;
  
  
  }

  //Method to get the amount paid by the user
  public static double getAmountPaidFromUser() {

    Scanner input = new Scanner(System.in);

    //Variables
    boolean valid = false;
    double amount = 0.00;

    //Loop to ask the user how much they are going to pay of the bill
    do {
      System.out.print("\nEnter the amount you are going to pay: ");
        try {
          amount = input.nextDouble();
          valid = true;
          if(amount < 0) {
            valid = false;  
            System.out.println("The amount should be greater than 0");
          }
          else {
            valid = true;
          }
        }
        catch(InputMismatchException e) {
          System.out.println("The amount must be a number");
          input.next();
        }
    } while (!valid || amount < 0);

    return amount;
  
  
  }
  
  //Method to get the credit card number from the user
  public static  String getCreditCardFromUser() {

    Scanner input = new Scanner(System.in);

    //Variables
    String cardNumber = "";
    boolean valid = false;

    //Loop to get the credit card number
    do {
      System.out.print("\nEnter your 16 digit credit card number:");
      cardNumber = input.nextLine();

        if(cardNumber.length() < 16) {
          System.out.println("\nThe card number entered is too short ");
        }
        else if(cardNumber.length() > 16) {
          System.out.println("\nThe card number entered is too long ");
        }
        else if(cardNumber.length() == 16 && cardNumber.matches("[0-9]+")) {
          valid = true;
        }
        else if(!cardNumber.matches("[0-9]+")) {
          System.out.println("\nThe card number should be 16 numbers ");
        }
    } while (!valid || cardNumber.length() < 16 || cardNumber.length() > 16 || !cardNumber.matches("[0-9]+"));

    return cardNumber;
  
  
  }
  
  //Method to let the user create an account
  public static User createAccountDetails() {

    Scanner input = new Scanner(System.in);

    //variables
    String fName = "", lName = "", address = "", username ="";
    String number = "", email = "", password = "";
    boolean valid = false;

    //ArrayLists
    ArrayList <String> allUsers = new ArrayList<String>();

    //Header
    System.out.println("\nCREATE ACCOUNT DETAILS");

    //User input for first name
    do {
      System.out.print("\nFirst name: ");
      fName = input.nextLine();
        if(fName.trim().length() < 1) {
          System.out.println("- The first name must have at least one character");
        }
    } while(fName.trim().length() < 1);
   
    //User input for last name
    do {
      System.out.print("\nLast name: ");
      lName = input.nextLine();
        if(lName.trim().length() < 1) {
          System.out.println("- The last name must have at least one character");
        }
    } while(lName.trim().length() < 1);
  
    //User input for email
    do {
      System.out.print("\nEmail:");
      email = input.nextLine();
      if(!email.contains("@")) {
        System.out.println("- Email must have @");
      }//end if
      if(!email.contains(".com") && !email.contains(".ca")) {
        System.out.println("- Email must have .com or .ca");
      }//end if 
    } while(!email.contains("@") || (!email.contains(".com") && !email.contains(".ca")));

    //User input for address
    do {
      System.out.print("\nAddress:");
      address = input.nextLine();
        if(address.trim().length() < 1) {
          System.out.println("- The address must have at least one character");
        }
    } while(address.trim().length() < 1);
    

    //User input for phone number
    do {
      System.out.print("\nPhone number:");
      number = input.nextLine();
  
      if(number.length() != 10) {
        System.out.println("The phone number should have 10 digits");
      }//end if 

      if(!number.matches("[0-9]+")) {
        System.out.println("The phone number should be positive integers from 0 to 9");
      }//end if 

    } while(number.length() != 10 || !number.matches("[0-9]+"));

    //User input for username
    allUsers = getUsersFromFile();
    
    do {
      System.out.print("\nUsername: ");
      username = input.nextLine();
        if(username.trim().length() < 1) {
          System.out.println("- Username must have at least one character");
        }//end if 
      if(allUsers.contains(username)) {
         System.out.println("- Username already exists");
         username = "";
      }
    } while(username.trim().length() < 1);

    //set valid to false
    valid = false;

    //User input for password
    do {
      System.out.print("\nPassword (Must include a of minimum 8 characters and 1 capital): ");
      password = input.nextLine();
      if(password.equals(password.toLowerCase())) {
        System.out.println("- Password must have at least 1 capital");
      }//end if 
      if(password.trim().length() < 8) {
        System.out.println("- Password must have a minimum of 8 characters");
      }//end if 

      if(password.trim().contains(" ") ) {
        System.out.println("- Password can't have any spaces");
      }//end if

    } while(password.equals(password.toLowerCase()) || password.trim().length() < 8 || (password.trim().contains(" ")));


    //Writing to file
    try {
      FileWriter fw = new FileWriter("registration.txt", true);
      PrintWriter pw = new PrintWriter(fw);
      pw.println("First name:" + fName);
      pw.println("Last name:" + lName);
      pw.println("Email:" + email);
      pw.println("Address:" + address);
      pw.println("Phone number:" + number);
      pw.println("Username:" + username);
      pw.println("Password:" + password);
      pw.close();
    }//end try

    catch(IOException e) {
      System.out.println("Could not write to file.");
    }//end catch 

    //Setting the user object with those details
    User user = new User();
    user.setFirstName(fName);
    user.setLastName(lName);
    user.setAddress(address);
    user.setPhoneNumber(number);
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(password);

    return user;
  
  }
  
  //Method to check if username and password match in file
  public static User checkUsernameAndPassword() {

    Scanner input = new Scanner(System.in);

    //Variables
    int counter = 0;
    String line = "", username = "", password = "";
    String fName = "", lName = "", address = "", number = "", email = "";
    String name = "", pass = "";
    boolean usernameFound = false;
    boolean passwordFound = false;

    //Header
    System.out.println("\nLOG IN");

    //User input for username and password
    do {
      counter += 1;
      do {
      System.out.print("\nUsername: ");
      username = input.nextLine();
        if(username.trim().length() < 1) {
          System.out.println("- Username must have at least one character");
        }
      } while(username.trim().length() < 1);
      do {
        System.out.print("\nPassword (Must include a of minimum 8 characters and 1 capital): ");
        password = input.nextLine();
        if(password.equals(password.toLowerCase())) {
          System.out.println("- Password must have at least 1 capital");
        }//end if
        
        if(password.trim().length() < 8) {
          System.out.println("- Password must have a minimum of 8 characters");
        }//end if 
        if(password.trim().contains(" ") ) {
          System.out.println("- Password can't have any spaces");
        }//end if
      } while(password.equals(password.toLowerCase()) || password.trim().length() < 8 || (password.trim().contains(" ")));
      
      // Reading the resgistration file to find the username and password entered by the user
      try {
        FileReader fr = new FileReader("registration.txt");
        BufferedReader br = new BufferedReader(fr);
        line =  br.readLine();
        while(line != null) {
          fName = line;
          lName = br.readLine();
          email = br.readLine();
          address = br.readLine();
          number = br.readLine();
          name = br.readLine();
          pass = br.readLine();
          usernameFound = name.substring(9).equals(username);
          passwordFound = pass.substring(9).equals(password);
          line = br.readLine();
          if(usernameFound && passwordFound) {
            System.out.println("\nLogging in...");
            break;
          } 
          else if (line == null && counter > 2 && (!(usernameFound && passwordFound))) {
            System.out.println("The system is exiting...");
            System.exit(0);
          }//end else if 
        }//end while
        br.close();
      }//end try
      catch(IOException e ) {
        System.out.println("Could not read from file");
      }//end catch

      //Printing that the username and password are incorrect
      if (!(usernameFound && passwordFound) && counter <= 2) {
        System.out.println("\nUsername or password is incorrect");
        System.out.println("Please enter again");
      }
    } while (counter <= 3  && !((usernameFound && passwordFound)));

    //Creating an user object and setting the details into the object
    User user = new User();    
    user.setFirstName(fName.substring(fName.indexOf(":") + 1));
    user.setLastName(lName.substring(lName.indexOf(":") + 1));
    user.setAddress(address.substring(address.indexOf(":") + 1));
    user.setPhoneNumber(number.substring(number.indexOf(":") + 1));
    user.setEmail(email.substring(email.indexOf(":") + 1));
    user.setUsername(username.substring(username.indexOf(":") + 1));
    user.setPassword(password.substring(password.indexOf(":") + 1));

    return user;
  
  
  }
  
  //Method to get all the users from the registration file
  public static ArrayList<String> getUsersFromFile() {

    //variables
    String fNameLine = "", lNameLine = "", addressLine = "", usernameLine = "";
    String numberLine = "", emailLine = "", passwordLine = "";

    //Creating an arrayList
    ArrayList<String> allUsers = new ArrayList<String>();
    
    //Reading the file
    try {
      FileReader fr = new FileReader("registration.txt");
      BufferedReader br = new BufferedReader(fr);
      fNameLine = br.readLine(); 
      while(fNameLine != null) {
        //Reading all the lines and adding the usernames into an arrayList
        lNameLine = br.readLine();
        emailLine = br.readLine();
        addressLine = br.readLine();
        numberLine = br.readLine();
        usernameLine = br.readLine();
        passwordLine = br.readLine();
        allUsers.add(usernameLine.substring(9));
        fNameLine = br.readLine();
      }
      br.close();
    }
    catch(IOException e) {
      System.out.println("Could not read the file");
    }

    return allUsers;
  
  }
  
  //Method to update the account details in the registration file
  public static void updateAccountDetails(User user) {

    Scanner input = new Scanner(System.in);

    //variables
    int update = 0;
    String more = "", line = "";
    String fNameLine = "", lNameLine = "", addressLine = "", usernameLine = "";
    String numberLine = "", emailLine = "", passwordLine = "";
    String fNameUpdated = "", lNameUpdated = "", addressUpdated = "";
    String numberUpdated = "", emailUpdated = "", passwordUpdated = "";
    String updatedLine = "";
    boolean usernameFound = false;

    //Creating Array Lists
    ArrayList<String> beforeLines = new ArrayList<String>();
    ArrayList<String> afterLines = new ArrayList<String>();
    ArrayList<String> matchedLines = new ArrayList<String>();

    //Asking the user what they would like to update
    do {
      do {
        System.out.println("\nOptions");
        System.out.println("1. New first name");
        System.out.println("2. New last name");
        System.out.println("3. New email");
        System.out.println("4. New address");
        System.out.println("5. New phone number");
        System.out.println("6. New password");
        System.out.print("\nWhat would you like to update: ");
        try {
          update = input.nextInt();
        }
        catch(InputMismatchException e) {
          System.out.println("The option should be from 1 to 6");
        }
      } while(update < 1 || update > 6);

      //updating info

      //Option 1: updating first name 
      if(update == 1) {
        input.nextLine();
        do {
          System.out.print("\nFirst name:");
          fNameUpdated = input.nextLine();
            if(fNameUpdated.trim().length() < 1) {
              System.out.println("- The first name must have at least one character");
            }
        } while(fNameUpdated.trim().length() < 1);
      }

      //Option 2: updating last name 
      else if(update == 2) {
        input.nextLine();
        do {
          System.out.print("\nLast name:");
          lNameUpdated = input.nextLine();
            if(lNameUpdated.trim().length() < 1) {
              System.out.println("- The last name must have at least one character");
            }
        } while(lNameUpdated.trim().length() < 1);
      }

      //Option 3: updating email 
      else if(update == 3) {
        input.nextLine();
        do {
          System.out.print("\nEmail:");
          emailUpdated = input.nextLine();
          if(!emailUpdated.contains("@")) {
            System.out.println("- Email must have @");
          }
          if(!emailUpdated.contains(".com") && !emailUpdated.contains(".ca")) {
            System.out.println("- Email must have .com or .ca");
          }
        } while(!emailUpdated.contains("@") || (!emailUpdated.contains(".com") && !emailUpdated.contains(".ca")));
      }
        
      //Option 4: updating address 
      else if(update == 4) {
        System.out.print("\nAddress:");
        input.nextLine();
        addressUpdated = input.nextLine();
      }

      //Option 5: updating phone number
      else if(update == 5) {
        input.nextLine();
        do {
          System.out.print("\nPhone number:");
          numberUpdated = input.nextLine();
          if(numberUpdated.length() != 10) {
            System.out.println("The phone number should have 10 digits");
          }
          if(!numberUpdated.matches("[0-9]+")) {
            System.out.println("The phone number should be positive integers from 0 to 9");
          }
        } while(numberUpdated.length() != 10 || !numberUpdated.matches("[0-9]+"));
      }
        
      //Option 6: updating password
      else if(update == 6) {
        input.nextLine();
        do {
          System.out.print("\nPassword (Must include a of minimum 8 characters and 1 capital): ");
            passwordUpdated = input.nextLine();
  if(passwordUpdated.equals(passwordUpdated.toLowerCase())) {
            System.out.println("- Password must have at least 1 capital");
          }
          if(passwordUpdated.trim().length() < 8) {
            System.out.println("- Password must have a minimum of 8 characters");
          }
          if(passwordUpdated.trim().contains(" ") ) {
            System.out.println("- Password can't have any spaces");
          }
        } while(passwordUpdated.equals(passwordUpdated.toLowerCase()) || passwordUpdated.trim().length() < 8 || (passwordUpdated.trim().contains(" ")));
      }
      
      //Reading the file
      try {
        FileReader fr = new FileReader("registration.txt");
        BufferedReader br = new BufferedReader(fr);
        beforeLines = new ArrayList<String>();
        afterLines = new ArrayList<String>();
        matchedLines = new ArrayList<String>();
        usernameFound = false;
        fNameLine = br.readLine(); 
        while(fNameLine != null) {
          lNameLine = br.readLine();
          emailLine = br.readLine();
          addressLine = br.readLine();
          numberLine = br.readLine();
          usernameLine = br.readLine();
          passwordLine = br.readLine();
          if((usernameLine.substring(9)).equals(user.getUsername())) {
            usernameFound = true;
            matchedLines.add(fNameLine);
            matchedLines.add(lNameLine);
            matchedLines.add(emailLine);
            matchedLines.add(addressLine);
            matchedLines.add(numberLine);
            matchedLines.add(usernameLine);
            matchedLines.add(passwordLine);
          }
          else if (usernameFound) {
            afterLines.add(fNameLine);
            afterLines.add(lNameLine);
            afterLines.add(emailLine);
            afterLines.add(addressLine);
            afterLines.add(numberLine);
            afterLines.add(usernameLine);
            afterLines.add(passwordLine);
          }
          else {
            beforeLines.add(fNameLine);
            beforeLines.add(lNameLine);
            beforeLines.add(emailLine);
            beforeLines.add(addressLine);
            beforeLines.add(numberLine);
            beforeLines.add(usernameLine);
            beforeLines.add(passwordLine);
          }
            fNameLine = br.readLine();
        }
        br.close();
        fr.close();
      }
        catch(IOException e) {
          System.out.println("Could not read the file");
        }

      //Writing to file
      try {
          FileWriter fw = new FileWriter("registration.txt");
          PrintWriter pw = new PrintWriter(fw);
        
          //Writing all the before lines into the file
          for(String beforeLine : beforeLines) {
             pw.println(beforeLine);
          }
          //Writing the matched lines depending on if they were changed or not
          for(String matchedLine : matchedLines) {
             //If the first name was changed, write the new one or else keep it the same
             if(matchedLine.contains("First name")) {
               if(!fNameUpdated.equals("")) {
                  updatedLine = "First name:" + fNameUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
             }
              //If the last name was changed, write the new one or else keep it the same
              if(matchedLine.contains("Last name")) {
               if(!lNameUpdated.equals("")) {
                  updatedLine = "Last name:" + lNameUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
              }
              //If the email was changed, write the new one or else keep it the same
              if(matchedLine.contains("Email")) {
               if(!emailUpdated.equals("")) {
                  updatedLine = "Email:" + emailUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
              }
              //If the address was changed, write the new one or else keep it the same
              if(matchedLine.contains("Address")) {
               if(!addressUpdated.equals("")) {
                  updatedLine = "Address:" + addressUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
              }
              //If the phone number was changed, write the new one or else keep it the same
              if(matchedLine.contains("Phone number")) {
               if(!numberUpdated.equals("")) {
                  updatedLine = "Phone number:" + numberUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
              }
              //If the line contains "username", keep it the same
              if(matchedLine.contains("Username")) {
                 updatedLine = matchedLine;
              }
              //If the password was changed, write the new one or else keep it the same
              if(matchedLine.contains("Password")) {
               if(!passwordUpdated.equals("")) {
                  updatedLine = "Password:" + passwordUpdated;
                }
               else {
                 updatedLine = matchedLine;
               }
              }
            pw.println(updatedLine);
          }
          //Printing the after lines
          for(String afterLine : afterLines) {
             pw.println(afterLine);
          }
          pw.close();
      }//end try
      catch(IOException e) {
          System.out.println("Could not write to file.");
      }//end catch 

      do {
        System.out.println("\nWould you like to make more changes(Yes/No)");
        more = input.next();
      } while(!more.equalsIgnoreCase("Yes") && !more.equalsIgnoreCase("No"));

    } while(more.equalsIgnoreCase("Yes"));
   
  
  
  }//end update account details 
 
}//End Main Class 