package panel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by root on 26.01.17.
 */

@Entity
@Table(name = "Person", schema = "public", catalog = "ClientDB")
public class PersonEntity {

    public PersonEntity(){

    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name="person_id")
    private String personID;

    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @Column(name="e_mail")
    private String emailAdress;

    @Column(name="tel_number")
    private Integer telNumber;

    @Column(name="client_age")
    private Integer clientAge;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }


    public Integer getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(Integer telNumber) {
        this.telNumber = telNumber;
    }

    public Integer getClientAge() {
        return clientAge;
    }

    public void setClientAge(Integer clientAge) {
        this.clientAge = clientAge;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
