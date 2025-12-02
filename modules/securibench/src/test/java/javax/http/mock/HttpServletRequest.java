package javax.servlet.http.mock;

import javax.servlet.http.Cookie;

public class HttpServletRequest {
    public String getParameter(String s) {
        return "secret";
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }
}
