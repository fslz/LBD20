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
    public static Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) { return (localDateTime == null ? null : Timestamp.valueOf(localDateTime)); }

    /**
     * Called internally,
     * safely converts a java.sql.Date to a LocalDate
     *
     * @return LocalDate
     */
    public static LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return (timestamp == null ? null : timestamp.toLocalDateTime());
    }

}
