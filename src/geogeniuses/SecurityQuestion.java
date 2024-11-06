package geogeniuses;

/**
 * The security question class contains the questionID and questionPrompt variables
 * which will be tied into the database's security questions.
 * @author David Bowen
 */
public class SecurityQuestion {
    
    int questionID;
    String questionPrompt;
    
    SecurityQuestion(int questionID, String questionPrompt) {
        this.questionID = questionID;
        this.questionPrompt = questionPrompt;
    }
}
