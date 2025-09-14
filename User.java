/*
  * Creates an user object
  * Imaan Dar
*/


public class User {

  //Fields
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;
  private String email;

  
  //Gets username
  public String getUsername() {
    return username;
  }

  //Gets password
  public String getPassword() {
    return password;
  }
  
  //Gets firstname
  public String getFirstName() {
    return firstName;
  }
  
  //Gets lastname
  public String getLastName() {
    return lastName;
  }
  
  //Gets address
  public String getAddress() {
    return address;
  }
  
  //Gets phoneNumber
  public String getPhoneNumber() {
    return phoneNumber;
  }

  //Gets email
  public String getEmail() {
    return email;
  }

  
  //Sets username
  public void setUsername(String u) {
    this.username = u;
  }
  
  //Sets password
  public void setPassword(String p) {
    if(p.equals(p.toLowerCase()) || p.length() < 8 || (p.trim().contains(" "))) {
      throw new IllegalArgumentException("Password has to be minimum 8 characters and 1 capital letter.");
    } 
    else {
      this.password = p;
    }
  }

  //Sets first name 
  public void setFirstName(String f) {
    this.firstName = f;
  } 

  //Sets last name 
  public void setLastName(String l) {
    this.lastName = l;
  }

  //sets address
  public void setAddress(String a){
    this.address = a;
  }

  //Sets phone number
  public void setPhoneNumber(String pn) {
    if(pn.length() != 10 && !pn.matches("[0-9]+")) {
      throw new IllegalArgumentException("Invalid phone number.");
    }
    else {
      this.phoneNumber = pn;
    }
  }

  //Sets email
  public void setEmail(String e) {
    if(!e.contains("@") || (!e.contains(".com") && !e.contains(".ca"))) {
      throw new IllegalArgumentException("Invalid email.");
    }
    else {
      this.email = e;
    }
  }

}//end user class