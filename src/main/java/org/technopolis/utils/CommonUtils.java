package org.technopolis.utils;

import java.time.format.DateTimeFormatter;

public final class CommonUtils {

    public static final DateTimeFormatter LOCALDATE = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    public static final DateTimeFormatter LOCALDATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
