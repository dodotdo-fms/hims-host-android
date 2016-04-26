package com.dodotdo.himsadmin.utill;

/**
 * Created by Omjoon on 16. 4. 22..
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
    static final long HOUR_TO_MILLISECOND = 3600000;
    static final long DAY_TO_MILLISECOND = 86400000;
    static final long MIN_TO_MILLISECOND = 60000;
    static final int RECENT_MIN = 5;

    public static long getCurrentUtcTime() {
        return System.currentTimeMillis();
    }

    public static String getTimeDiff(long targetUtcTime) {
        /**
         * 1분 이내 -> 조금 전
         * 한시간 이내 -> 몇분
         * 하루 이내 -> 몇시간
         * //TODO 다시 정리
         */
        long currentUtcTime = getCurrentUtcTime();
        long timeDiff = currentUtcTime - targetUtcTime;
        try {
        } catch (Exception e) {
        }
        if (timeDiff < MIN_TO_MILLISECOND * RECENT_MIN) {
            return "방금";
        }
        if (timeDiff < HOUR_TO_MILLISECOND) {
            return getMinDiff(timeDiff);
        }
        if (timeDiff < DAY_TO_MILLISECOND) {
            return getHourDiff(timeDiff);
        }
        if (timeDiff < DAY_TO_MILLISECOND * 2) {
            return "어제";
        }
        return getShortDate(targetUtcTime);
    }

    public static String getShortDate(long targetUtcTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(targetUtcTime);
        return simpleDateFormat.format(date);
    }

    private static String getHourDiff(long timeDiff) {
        return String.format("%d시간 전", (int) (timeDiff / HOUR_TO_MILLISECOND));
    }

    private static String getMinDiff(long timeDiff) {
        return String.format("%d분 전", (int) (timeDiff / MIN_TO_MILLISECOND));
    }

    public static String getTimeString(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        return simpleDateFormat.format(date);

    }

    public static String getTimeString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }

    public static boolean isSameDate(long time1, long time2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        if (simpleDateFormat.format(date1).equals(simpleDateFormat.format(date2))) {
            return true;
        }
        return false;
    }

    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(currentTime).toString();
    }
}
