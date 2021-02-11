package Util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Util {

    /**
     * Called internally,
     * safely converts a LocalDate to a java.sql.Date
     *
     * @return java.sql.Date
     */
    public static Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate == null ? null : Date.valueOf(localDate));
    }

    /**
     * Called internally,
     * safely converts a java.sql.Date to a LocalDate
     *
     * @return LocalDate
     */
    public static LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }

}
