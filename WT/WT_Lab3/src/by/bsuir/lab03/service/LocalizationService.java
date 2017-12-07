package by.bsuir.lab03.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LocalizationService {
    String getLanguage(HttpServletRequest request);
    void setRussianLanguage(HttpServletResponse response);
    void setEnglishLanguage(HttpServletResponse response);
    void setPageLanguage(HttpServletRequest request);
}
