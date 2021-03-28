package org.technopolis.utils;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class CommonUtils {

    public static final DateTimeFormatter LOCALDATE = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
    public static final DateTimeFormatter LOCALDATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
}
