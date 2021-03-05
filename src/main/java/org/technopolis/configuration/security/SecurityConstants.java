package org.technopolis.configuration.security;

public final class SecurityConstants {
    public static final String SECRET = "myhoa-key";
    public static final long EXPIRATION_TIME = 86_400_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/services/controller/user";
}