package geogeniuses;

/**
 * The security question class contains the questionID and questionPrompt variables
 * which will be tied into the database's security questions.
 * @author David Bowen
 */
public class SecurityQuestion {
    
    int questionID;
    String questionPrompt;
    
    /**
     * The security question constructor contains the security question used when registering
     * or resetting password.
     * @param questionID References the question in the database using its specific id
     * @param questionPrompt The string that contains the actual text of the question
     */
    public SecurityQuestion(int questionID, String questionPrompt) {
        this.questionID = questionID;
        this.questionPrompt = questionPrompt;
    }
}
