
/*
  * Prints any outages with the time(duration), description, and location
  * Imaan Dar
*/


public class GasServiceOutage {

  //Fields 
  private String description;
  private String time;

  //Accessor for description
  public String getDescription() {
    return description;
  }//end get description 

  //Mutator for description
  public void setDescription(String d) {
    this.description = d;
  }//end set description 

  //Accessor for time
  public String getTime() {
    return time;
  }//end get time 

  //Mutator for time
  public void setTime(String t) {
    this.time = t;
  }//end set time

  //toString to print the outage
  public String toString(){
    return description + "\n" + time;
  }//end to string 

}//Class End