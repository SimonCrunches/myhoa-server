package org.technopolis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import org.technopolis.security.model.SecurityProperties;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class CookieService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;

    final SecurityProperties restSecProps;

    @Autowired
    public CookieService(@Nonnull final SecurityProperties restSecProps) {
        this.restSecProps = restSecProps;
    }

    public Cookie getCookie(@Nonnull final String name) {
        return WebUtils.getCookie(httpServletRequest, name);
    }

    public void setCookie(@Nonnull final String name,
                          @Nonnull final String value,
                          final int expiryInDays) {
        final int expiresInSeconds = (int) TimeUnit.DAYS.toSeconds(expiryInDays);
        final Cookie cookie = new Cookie(name, value);
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiresInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public void setSecureCookie(@Nonnull final String name,
                                @Nonnull final String value,
                                final int expiryInDays) {
        final int expiresInSeconds = (int) TimeUnit.DAYS.toSeconds(expiryInDays);
        final Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(restSecProps.getCookieProps().isHttpOnly());
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(expiresInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public void setSecureCookie(@Nonnull final String name,
                                @Nonnull final String value) {
        final int expiresInMinutes = restSecProps.getCookieProps().getMaxAgeInMinutes();
        setSecureCookie(name, value, expiresInMinutes);
    }

    public void deleteSecureCookie(@Nonnull final String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(restSecProps.getCookieProps().isHttpOnly());
        cookie.setSecure(restSecProps.getCookieProps().isSecure());
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public void deleteCookie(@Nonnull final String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(restSecProps.getCookieProps().getPath());
        cookie.setDomain(restSecProps.getCookieProps().getDomain());
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

}
