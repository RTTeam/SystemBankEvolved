package panel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by root on 31.01.17.
 */

@Entity
@Table(name = "transaction", schema = "public", catalog = "ClientDB")
public class TransactionEntity {

        public TransactionEntity(){

        }
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name="increment", strategy="increment")
        private Integer id;

        @Column(name="sender_acc_num")
        private Integer senderAccountNum;

        @Column(name="recipient_acc_num")
        private Integer recipientAccountNum;

        @Column(name="transferred_money")
        private Integer transferredMoney;

        @Column(name = "currency_type")
        private String currencyType;

        @Column(name="transaction_header")
        private String transactionHeader;

        @Column(name="transfer_date")
        private String transferDate;

        @Column(name="transfer_time")
        private String transferTime;

    public Integer getSenderAccountNum() {
        return senderAccountNum;
    }

    public void setSenderAccountNum(Integer senderAccountNum) {
        this.senderAccountNum = senderAccountNum;
    }

    public Integer getRecipientAccountNum() {
        return recipientAccountNum;
    }

    public void setRecipientAccountNum(Integer recipentAccountNum) {
        this.recipientAccountNum = recipentAccountNum;
    }

    public Integer getTransferredMoney() {
        return transferredMoney;
    }

    public void setTransferredMoney(Integer transferredMoney) {
        this.transferredMoney = transferredMoney;
    }

    public String getTransactionHeader() {
        return transactionHeader;
    }

    public void setTransactionHeader(String transactionHeader) {
        this.transactionHeader = transactionHeader;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
