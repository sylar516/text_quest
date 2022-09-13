package servlets;

import entities.QuestPage;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//класс, созданный для оптимизации повторяющегося кода в тестах при инициализации глобальных переменных
public class Initializer {
    static HttpServletRequest request;
     static HttpSession session;
     static Map<String, Object> sessionParams;
    static Map<Integer, QuestPage> pages;
     static Enumeration<String> enumeration;
     static HttpServletResponse response;
     static String redirectURL;

     static void initTestParams() throws IOException {
         //инициализация полей и написание поведения для мок-объектов
         request = Mockito.mock(HttpServletRequest.class);
         session = Mockito.mock(HttpSession.class);
         sessionParams = new HashMap<>();
         enumeration = Mockito.mock(Enumeration.class);
         response = Mockito.mock(HttpServletResponse.class);

         Mockito.doReturn(session).when(request).getSession();

         Mockito.doAnswer(invocationOnMock -> {
             String key = invocationOnMock.getArgument(0);
             return sessionParams.get(key);
         }).when(session).getAttribute(Mockito.anyString());

         Mockito.doAnswer(invocationOnMock -> {
             String key = invocationOnMock.getArgument(0);
             Object value = invocationOnMock.getArgument(1);
             sessionParams.put(key, value);
             return null;
         }).when(session).setAttribute(Mockito.anyString(), Mockito.any());

         Mockito.doAnswer(invocationOnMock -> {
             redirectURL = invocationOnMock.getArgument(0);
             return redirectURL;
         }).when(response).sendRedirect(Mockito.anyString());
     }
}
