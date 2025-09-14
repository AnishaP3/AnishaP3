/*
  * Prints any outages with the time(duration), description, and location
  * Suha Khan 
*/


public class WaterServiceOutage{

  //Fields
  private String description;
  private String time;

  //Accessor - Description 
  public String getDescription() {
    return description;
  }//END Accessor

  //Mutator - Description 
  public void setDescription(String d) {
    this.description = d;
  }//END Mutator 

  //Accessor - Time 
  public String getTime() {
    return time;
  }//END Accessor
  
  //Mutator - Time 
  public void setTime(String t) {
    this.time = t;
  }//END Mutator 

  //toString to print the outage
  public String toString() {
    return "\n" + description + "\n" + time;
  }//END toString

  
}//END Class