package geogeniuses;

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