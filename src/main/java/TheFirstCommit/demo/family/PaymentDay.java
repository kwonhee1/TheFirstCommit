package TheFirstCommit.demo.family;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public enum PaymentDay {
    SECOND_SUNDAY,
    FOURTH_SUNDAY;

    public LocalDate getDay() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        LocalDate date = getDay(currentMonth);

        if(date.isAfter(now)) {
            return date;
        } else {
            return getDay(currentMonth.plusMonths(1));
        }
    }

    private LocalDate getDay(YearMonth yearMonth) {
        if(this == SECOND_SUNDAY)
            return yearMonth.atDay(1).with(java.time.temporal.TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
        else
            return yearMonth.atDay(1).with(java.time.temporal.TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.SUNDAY));
    }
}
