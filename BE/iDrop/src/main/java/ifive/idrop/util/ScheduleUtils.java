package ifive.idrop.util;

import ifive.idrop.exception.CommonException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleUtils {
    public static List<String> DAY_OF_WEEKS; //["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    public static final int EXPIRATION = 28; //4주의 유효 기간

    static {
        DAY_OF_WEEKS = Arrays.stream(DayOfWeek.values())
                .map(d -> d.getDisplayName(TextStyle.SHORT, Locale.US))
                .toList();
    }

    public static RequestSchedule parseToList(JSONObject schedule) {
        RequestSchedule requestSchedule = new RequestSchedule();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        int dayOfToday = now.getDayOfWeek().getValue();

        for (int i = 0; i < 7; i++) {
            Map<String, Integer> dayObject = (Map<String, Integer>) schedule.get(DAY_OF_WEEKS.get(i));
            if (dayObject != null) {
                Number hour = dayObject.get("hour");
                Number minute = dayObject.get("min");
                int difference = getDifferenceOfDayOfWeek(i + 1, dayOfToday);
                for (int d = difference; d <= EXPIRATION; d += 7) {
                    if (d == 0)
                        continue; //당일은 제외
                    requestSchedule.addSchedule(now.plusDays(d).withHour(hour.intValue()).withMinute(minute.intValue()));
                }
            }
        }
        return requestSchedule;
    }

    public static JSONObject toJSONObject(String schedule) {
        JSONParser parser = new JSONParser();
        JSONObject scheduleJSON = null;
        try {
            scheduleJSON = (JSONObject)parser.parse(schedule);
        } catch (ParseException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "");
        }
        return scheduleJSON;
    }

    private static int getDifferenceOfDayOfWeek(int from, int to) {
        return (from - to >= 0) ? from - to : from - to + 7;
    }

    public static LocalDate calculateStartDate(LocalDateTime modifiedDate) {
        if (modifiedDate == null) {
            return LocalDate.now().plusDays(1);
        }
        return modifiedDate.toLocalDate().plusDays(1);
    }

    public static LocalDate calculateEndDate(LocalDateTime modifiedDate) {
        if (modifiedDate == null) {
            return calculateStartDate(null).plusDays(EXPIRATION - 1);
        }
        return modifiedDate.toLocalDate().plusDays(EXPIRATION);
    }
}
