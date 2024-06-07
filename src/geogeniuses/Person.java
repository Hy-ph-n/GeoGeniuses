package geogeniuses;

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
    
    Person(int personID, String title, String nameFirst, String nameMiddle, String nameLast, String suffix, String address1, String address2, String address3, String city, String zipCode, String state, String email, String phonePrimary, String phoneSecondary, byte[] image, int personDeleted) {
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