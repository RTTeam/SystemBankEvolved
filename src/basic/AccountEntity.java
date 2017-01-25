package basic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by root on 24.01.17.
 */
@Entity
@Table(name = "Account", schema = "public", catalog = "ClientDB")
public class AccountEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name="account_num")
    private String accountNum;

    @Column(name="account_password")
    private String accountPass;

    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        AccountEntity that = (AccountEntity) o;
//
//        if (id != null ? !id.equals(that.id) : that.id != null) return false;
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getsSecondName() {
        return secondName;
    }

    public void setsSecondName(String sName) {
        this.secondName = sName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }


    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountPass() {
        return accountPass;
    }

    public void setAccountPass(String accountPass) {
        this.accountPass = accountPass;
    }
}
