package geogeniuses;

/**
* The login class contains all variables that correlate with a user
* @author David Bowen
*/
public class Logon {
    
    int personID;
    String logonName;
    String password;
    String firstChallengeAnswer;
    String secondChallengeAnswer;
    String thirdChallengeAnswer;
    String positionTitle;
    
    /**
    * The logon class, closely tied with the person class, completes the identity
    * of any registered user, manager or customer. It contains some of the most
    * widely used variables, like personID and logonName, which appear throughout
    * the views.
    * @param personID Ties the logon to a specific person, associating the details with the those in Person where personID is the same
    * @param logonName The logon name is unique to every user, and allows the user to reset their password if used alone on the logon screen
    * @param password The password is not unique, multiple users can have the same password. The password and the username are both required to log in as a customer or manager
    * @param firstChallengeAnswer The first challenge answer is stored upon registration/account being edited. It is required to reset the password
    * @param secondChallengeAnswer The second challenge answer is stored upon registration/account being edited. It is required to reset the password
    * @param thirdChallengeAnswer The third challenge answer is stored upon registration/account being edited. It is required to reset the password
    * @param positionTitle The position title is always either 'Manager' or 'Customer', and determines the view used upon successfully logging in
    */
    public Logon(int personID, String logonName, String password, String firstChallengeAnswer, String secondChallengeAnswer, String thirdChallengeAnswer, String positionTitle){
        this.personID = personID;
        this.logonName = logonName;
        this.password = password;
        this.firstChallengeAnswer = firstChallengeAnswer;
        this.secondChallengeAnswer = secondChallengeAnswer;
        this.thirdChallengeAnswer = thirdChallengeAnswer;
        this.positionTitle = positionTitle;
    }
}