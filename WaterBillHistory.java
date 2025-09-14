import java.util.*;
import java.io.*;

/*
 * Reads from the file "waterReading.txt" and then prints the file
 * Suha Khan
*/

public class WaterBillHistory {
      
   //Fields
   private User user;
  
   //Accessor for user
   public User getUser() {
     return user;
   }

   //Mutator for user
   public void setUser(User u) {
      if(u == null) {
        throw new IllegalArgumentException("The User doesn't exist");
      }
      else {
        this.user = u;
      }
   }

    //Method to print the water bill history to the user
    public void printingWaterBillHistory() {
      
      //Variables 
        String line = "";
        String[] splitLine = null, commaSplitLine = null, colonSplitLine = null;

        // Reading the water bill file to find the username of the user 
        // and printing those lines to the user
        try {
          FileReader fr = new FileReader("waterBill.txt");
          BufferedReader br = new BufferedReader(fr);
          line = br.readLine();
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
                    System.out.println(line + "\n");
                  }//END colonSplit
                }//END commaSplit
              }//END for
            }//END if
           //If the lines doesn't have the username then print the menu again 
           if (!line.contains(user.getUsername())) {
             System.out.println("The billing history wasn't found");
             break;
           }
            line = br.readLine();
          }//END While
          br.close();
        }//END Try

        catch(IOException e) {
           System.out.println("Could not read from file.");
        }//END catch
    }//END Bill History 
}//END Class