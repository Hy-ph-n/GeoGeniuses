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
    
    Logon(int personID, String logonName, String password, String firstChallengeAnswer, String secondChallengeAnswer, String thirdChallengeAnswer, String positionTitle){
        this.personID = personID;
        this.logonName = logonName;
        this.password = password;
        this.firstChallengeAnswer = firstChallengeAnswer;
        this.secondChallengeAnswer = secondChallengeAnswer;
        this.thirdChallengeAnswer = thirdChallengeAnswer;
        this.positionTitle = positionTitle;
    }
}