package TheFirstCommit.demo.payment;

public enum PaymentDate {
    DAY_15(15) ,
    DAY_30(30)
    ;
    PaymentDate(int day) {
        this.day = day;
    }
    private int day;
}
