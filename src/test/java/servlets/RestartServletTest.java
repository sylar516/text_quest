package servlets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static servlets.FieldsInitializer.*;

class RestartServletTest {
    private static RestartServlet servlet = new RestartServlet();
    @BeforeAll
    static void init() throws IOException {
        initTestParams();
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