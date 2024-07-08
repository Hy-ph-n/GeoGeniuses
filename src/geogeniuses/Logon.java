package geogeniuses;

public class Logon {
    
    int personID;
    String logonName;
    String password;
    String positionTitle;
    
    Logon(int personID, String logonName, String password, String positionTitle){
        this.personID = personID;
        this.logonName = logonName;
        this.password = password;
        this.positionTitle = positionTitle;
    }
}