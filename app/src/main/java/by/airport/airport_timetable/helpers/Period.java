package by.airport.airport_timetable.helpers;

public enum Period {
    NOW("now"),
    TODAY("today"),
    TOMORROW("tomorrow"),
    YESTERDAY("yesterday");

    private final String text;

    Period(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Period fromString(String text) {
        for (Period period : Period.values()) {
            if (period.text.equalsIgnoreCase(text)) {
                return period;
            }
        }
        return null;
    }
}

