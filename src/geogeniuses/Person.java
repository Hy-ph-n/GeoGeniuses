package geogeniuses;

/**
* The person class contains all data associated with a person
* @author David Bowen
*/
public class Person {
    
    int personID;
    String title;
    String nameFirst;
    String nameMiddle;
    String nameLast;
    String suffix;
    String address1;
    String address2;
    String address3;
    String city;
    String zipCode;
    String state;
    String email;
    String phonePrimary;
    String phoneSecondary;
    byte[] image;
    int personDeleted;
    
    /**
    * Every person in the database, contained within the person table, has a lot
    * of fields of information associated with them. Many are required during
    * registration, while personID is given, and others aren't necessary.
    * @param personID The integer for the person's id [Given Automatically]
    * @param title The string for the person's title/prefix
    * @param nameFirst The string for the person's first name [REQUIRED]
    * @param nameMiddle The string for the person's middle name
    * @param nameLast The string for the person's last name [REQUIRED]
    * @param suffix The string for the person's suffix
    * @param address1 The string for the person's first email [REQUIRED]
    * @param address2 The string for the person's second email
    * @param address3 The string for the person's third email
    * @param city The string for the person's city [REQUIRED]
    * @param zipCode The string for the person's zip code [REQUIRED]
    * @param state The string for the person's state [REQUIRED]
    * @param email The string for the person's email [REQUIRED]
    * @param phonePrimary The string for the person's phone [REQUIRED]
    * @param phoneSecondary The string for the person's second phone
    * @param image The person's image
    * @param personDeleted An integer which denotes if the user has been deleted
    */
    public Person(int personID, String title, String nameFirst, String nameMiddle, String nameLast, String suffix, String address1, String address2, String address3, String city, String zipCode, String state, String email, String phonePrimary, String phoneSecondary, byte[] image, int personDeleted) {
        this.personID = personID;
        this.title = title;
        this.nameFirst = nameFirst;
        this.nameMiddle = nameMiddle;
        this.nameLast = nameLast;
        this.suffix = suffix;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.email = email;
        this.phonePrimary = phonePrimary;
        this.phoneSecondary = phoneSecondary;
        this.image = image;
        this.personDeleted = personDeleted;
    }
}