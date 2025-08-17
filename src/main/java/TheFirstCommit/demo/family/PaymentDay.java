package TheFirstCommit.demo.family;

public enum PaymentDay {
    DAY_15(15) ,
    DAY_30(30)
    ;
    PaymentDay(int day) {
        this.day = day;
    }
    private int day;
}
