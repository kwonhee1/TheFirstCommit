package TheFirstCommit.demo.family;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public enum PaymentDay {
    SECOND_SUNDAY,
    FOURTH_SUNDAY;

    public LocalDate beforeFeedDay() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        LocalDate date = feedDay(currentMonth);

        if (date.isBefore(now) || date.isEqual(now)) {
            return date;
        } else {
            return feedDay(currentMonth.minusMonths(1));
        }
    }

    public LocalDate nextFeedDay() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        LocalDate date = feedDay(currentMonth);

        if (date.isAfter(now)) {
            return date;
        } else {
            return feedDay(currentMonth.plusMonths(1));
        }
    }

    private LocalDate feedDay(YearMonth yearMonth) {
        if (this == SECOND_SUNDAY) {
            return yearMonth.atDay(1)
                .with(java.time.temporal.TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
        } else {
            return yearMonth.atDay(1)
                .with(java.time.temporal.TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.SUNDAY));
        }
    }
}
