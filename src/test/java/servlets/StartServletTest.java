package servlets;

import entities.QuestPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static servlets.Initializer.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StartServletTest {
    private static StartServlet servlet = Mockito.spy(StartServlet.class);
    @BeforeAll
    static void init() throws IOException {
        initTestParams();
        Mockito.doReturn(enumeration).when(session).getAttributeNames();
    }

    @DisplayName("Тест метода doGet класса StartServlet")
    @Order(1)
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void doGetTest(boolean hasMoreElements) throws IOException, ServletException {
        String expectedName = "Игрок";
        Mockito.doReturn(expectedName).when(request).getParameter("name");

        Mockito.doReturn(hasMoreElements).when(enumeration).hasMoreElements();

        servlet.doGet(request, response);
        //Проверяем, вызывается ли внутри метод initSession при пустой сессии
        Mockito.verify(servlet, Mockito.times(1)).initSession(session);

        String actualName = (String) session.getAttribute("name");
        Assertions.assertEquals(expectedName, actualName);

        int pageNumber = (int) session.getAttribute("pageNumber");
        Assertions.assertEquals(1, pageNumber);

        Assertions.assertEquals("/quest.jsp", redirectURL);
    }

    @DisplayName("Тест метода initSession класса StartServlet")
    @Order(2)
    @Test
    void initSessionTest() throws UnknownHostException {
        servlet.initSession(session);

        Map<Integer, QuestPage> expectedPages = Map.ofEntries(
                Map.entry(1, new QuestPage("Ты потерял память. Принять вызов НЛО?", "Принять вызов", "Отклонить вызов", "Ты отклонил вызов. Поражение")),
                Map.entry(2, new QuestPage("Ты принял вызов. Подняться на мостик к капитану?", "Подняться на мостик", "Отказаться подниматься на мостик", "Ты не пошёл на переговоры. Поражение")),
                Map.entry(3, new QuestPage("Ты поднялся на мостик. Кто ты?", "Рассказать правду о себе", "Солгать о себе", "Твою ложь разоблачили. Поражение"))
        );
        Map<Integer, QuestPage> actualPages = (Map<Integer, QuestPage>) session.getAttribute("pages");
        Assertions.assertEquals(expectedPages.size(), actualPages.size());
        for (Integer key : expectedPages.keySet()) {
            Assertions.assertEquals(expectedPages.get(key), actualPages.get(key));
        }

        InetAddress localHost = InetAddress.getLocalHost();
        String expectedAdress = localHost.getHostAddress();
        String actualAdress = (String) session.getAttribute("adress");
        Assertions.assertEquals(expectedAdress, actualAdress);

        int numberGames = (int) session.getAttribute("numberGames");
        Assertions.assertEquals(0, numberGames);
    }
}
