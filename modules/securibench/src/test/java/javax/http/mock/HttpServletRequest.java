package javax.servlet.http.mock;

import javax.servlet.http.Cookie;


/**
 * IMPORTANT:
 *
 * Although this class was created to mock some methods
 * only one test (basic16) is using it.
 */
public class HttpServletRequest {
    public String getParameter(String s) {
        return "secret";
    }
}
