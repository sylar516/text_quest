package servlets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class RestartServletTest {
    private static RestartServlet servlet;
    private static HttpServletRequest request;
    private static HttpSession session;
    private static Map<String, Object> sessionParams;
    private static HttpServletResponse response;
    private static String redirectURL;
    @BeforeAll
    static void init() throws IOException {
        servlet = new RestartServlet();
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        sessionParams = new HashMap<>();
        response = Mockito.mock(HttpServletResponse.class);

        Mockito.doReturn(session).when(request).getSession();

        Mockito.doAnswer(invocationOnMock -> {
            String key = invocationOnMock.getArgument(0);
            Object value = invocationOnMock.getArgument(1);
            sessionParams.put(key, value);
            return null;
        }).when(session).setAttribute(Mockito.anyString(), Mockito.any());

        Mockito.doAnswer(invocationOnMock -> {
            String key = invocationOnMock.getArgument(0);
            return sessionParams.get(key);
        }).when(session).getAttribute(Mockito.anyString());

        Mockito.doAnswer(invocationOnMock -> {
            redirectURL = invocationOnMock.getArgument(0);
            return redirectURL;
        }).when(response).sendRedirect(Mockito.anyString());

        session.setAttribute("numberGames", 0);
    }

    @Test
    @DisplayName("Тест метода doGet класса RestartServlet")
    void doGetTest() throws IOException {
        servlet.doGet(request, response);

        int numberGames = (int) session.getAttribute("numberGames");
        Assertions.assertEquals(1, numberGames);

        Assertions.assertEquals("/prolog.jsp", redirectURL);
    }
}