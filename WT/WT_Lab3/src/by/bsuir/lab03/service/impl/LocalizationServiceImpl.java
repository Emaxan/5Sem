package by.bsuir.lab03.service.impl;

import by.bsuir.lab03.service.LocalizationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalizationServiceImpl implements LocalizationService {
    private final String LANG_COOKIE_NAME = "lang";
    private final String RUS_LANG = "rus";
    private final String ENG_LANG = "eng";

    @Override
    public String getLanguage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LANG_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return ENG_LANG;
    }

    @Override
    public void setRussianLanguage(HttpServletResponse response) {
        setLanguage(response, RUS_LANG);
    }

    @Override
    public void setEnglishLanguage(HttpServletResponse response) {
        setLanguage(response, ENG_LANG);
    }

    @Override
    public void setPageLanguage(HttpServletRequest request) {
        request.setAttribute("lang", getLanguage(request));
    }

    private void setLanguage(HttpServletResponse response, String lang) {
        Cookie cookie = new Cookie(LANG_COOKIE_NAME, lang);
        response.addCookie(cookie);
    }
}
