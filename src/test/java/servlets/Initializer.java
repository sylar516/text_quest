package servlets;

import org.mockito.Mockito;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//класс, созданный для оптимизации повторяющегося кода в тестах при инициализации глобальных переменных
public class Initializer {
    static HttpServlet servlet;
    static HttpServletRequest request;
     static HttpSession session;
     static Map<String, Object> sessionParams;
     static Enumeration<String> enumeration;
     static HttpServletResponse response;
     static String redirectURL;

     void initialize() throws IOException {
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
     }
}
