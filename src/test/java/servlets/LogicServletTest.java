package servlets;

import entities.QuestPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class LogicServletTest {
    private static LogicServlet servlet;
    private static HttpServletRequest request;
    private static HttpSession session;
    private static Map<String, Object> sessionParams;
    private static Map<Integer, QuestPage> pages;
    private static HttpServletResponse response;
    private static String redirectURL;

    @BeforeAll
    static void init() throws IOException {
        servlet = new LogicServlet();
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

        pages = Map.ofEntries(
                Map.entry(1, new QuestPage("Ты потерял память. Принять вызов НЛО?", "Принять вызов", "Отклонить вызов", "Ты отклонил вызов. Поражение")),
                Map.entry(2, new QuestPage("Ты принял вызов. Подняться на мостик к капитану?", "Подняться на мостик", "Отказаться подниматься на мостик", "Ты не пошёл на переговоры. Поражение")),
                Map.entry(3, new QuestPage("Ты поднялся на мостик. Кто ты?", "Рассказать правду о себе", "Солгать о себе", "Твою ложь разоблачили. Поражение"))
        );
        session.setAttribute("pages", pages);
    }

    @ParameterizedTest
    @DisplayName("Тест метода doGet класса LogicServlet с положительным вариантом ответа")
    @ValueSource(ints = {1, 2, 3})
    void doGetPositiveAnswerTest(int pageNumber) throws IOException, InterruptedException, ServletException {
        String variant = "yes";
        Mockito.doReturn(variant).when(request).getParameter("variant");

        session.setAttribute("pageNumber", pageNumber);

        servlet.doGet(request, response);

        int currentPageNumber = (int) session.getAttribute("pageNumber");

        if (pageNumber == pages.size()) {
            Assertions.assertEquals("/gameOver.jsp?variant=" + variant, redirectURL);
        } else {
            Assertions.assertEquals(pageNumber + 1, currentPageNumber);
            Assertions.assertEquals("/quest.jsp", redirectURL);
        }
    }

    @ParameterizedTest
    @DisplayName("Тест метода doGet класса LogicServlet с негативным вариантом ответа")
    @ValueSource(ints = {1, 2, 3})
    void doGetNegativeAnswerTest(int pageNumber) throws IOException, InterruptedException, ServletException {
        String variant = "no";
        Mockito.doReturn(variant).when(request).getParameter("variant");

        session.setAttribute("pageNumber", pageNumber);

        servlet.doGet(request, response);

        int currentPageNumber = (int) session.getAttribute("pageNumber");

        Assertions.assertEquals(pageNumber, currentPageNumber);
        Assertions.assertEquals("/gameOver.jsp?variant=" + variant, redirectURL);
    }
}