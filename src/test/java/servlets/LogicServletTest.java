package servlets;

import entities.QuestPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

import static servlets.FieldsInitializer.*;

class LogicServletTest {
    private static LogicServlet servlet = new LogicServlet();
    @BeforeAll
    static void init() throws IOException {
        initTestParams();
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
    void doGetPositiveAnswerTest(int pageNumber) throws IOException {
        String variant = "yes";
        Mockito.doReturn(variant).when(request).getParameter("variant");

        session.setAttribute("pageNumber", pageNumber);

        servlet.doGet(request, response);

        int currentPageNumber = (int) session.getAttribute("pageNumber");

        if (pageNumber == pages.size()) {
            Assertions.assertEquals("/game_over.jsp?variant=" + variant, redirectURL);
        } else {
            Assertions.assertEquals(pageNumber + 1, currentPageNumber);
            Assertions.assertEquals("/quest.jsp", redirectURL);
        }
    }

    @ParameterizedTest
    @DisplayName("Тест метода doGet класса LogicServlet с отрицательным вариантом ответа")
    @ValueSource(ints = {1, 2, 3})
    void doGetNegativeAnswerTest(int pageNumber) throws IOException {
        String variant = "no";
        Mockito.doReturn(variant).when(request).getParameter("variant");

        session.setAttribute("pageNumber", pageNumber);

        servlet.doGet(request, response);

        int currentPageNumber = (int) session.getAttribute("pageNumber");

        Assertions.assertEquals(pageNumber, currentPageNumber);
        Assertions.assertEquals("/game_over.jsp?variant=" + variant, redirectURL);
    }
}