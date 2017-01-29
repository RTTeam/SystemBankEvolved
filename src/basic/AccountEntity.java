package basic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by root on 24.01.17.
 */
@Entity
@Table(name = "Account", schema = "public", catalog = "ClientDB")
public class AccountEntity {

    public AccountEntity(){

    }
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name="account_num")
    private String accountNum;

    @Column(name="account_password")
    private String accountPass;

    @Column(name="money_value")
    private Integer moneyValue;

    @Column(name="acc_type")
    private String accountType;

    @Column(name="acc_exp_date")
    private String accountExpDate;

    @Column(name="acc_owner_id")
    private String accountOwnerId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

       if (id != null ? !id.equals(that.id) : that.id != null) return false;

       return true; }

   @Override
   public int hashCode() {
       return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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



    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountExpDate() {
        return accountExpDate;
    }

    public void setAccountExpDate(String accountExpDate) {
        this.accountExpDate = accountExpDate;
    }


    public String getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(String accountOwnerId) {
        this.accountOwnerId = accountOwnerId;
    }

    public Integer getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(Integer moneyValue) {
        this.moneyValue = moneyValue;
    }
}
