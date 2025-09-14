
/*
  * Prints any outages with the time(duration), description, and location
  * Suha Khan
*/


public class ElectricityServiceOutage {

  //Fields
  private String description;
  private String time;

  //Accessor for description
  public String getDescription() {
    return description;
  }

  //Mutator for description
  public void setDescription(String d) {
    this.description = d;
  }

  //Accessor for time
  public String getTime() {
    return time;
  }

  //Mutator for time
  public void setTime(String t) {
    this.time = t;
  }

//To string method to print the outage 
  public String toString(){
    return description + "\n" + time;
  }
  
}//Class End