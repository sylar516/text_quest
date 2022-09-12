package servlets;

import entities.QuestPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        if (!session.getAttributeNames().hasMoreElements()) {
            initSession(session);
        }

        String name = req.getParameter("name");
        session.setAttribute("name", name);

        session.setAttribute("pageNumber", 1);

        resp.sendRedirect("/quest.jsp");
    }

    protected void initSession(HttpSession session) throws UnknownHostException {
        Map<Integer, QuestPage> pages = Map.ofEntries(
                Map.entry(1, new QuestPage("Ты потерял память. Принять вызов НЛО?", "Принять вызов", "Отклонить вызов", "Ты отклонил вызов. Поражение")),
                Map.entry(2, new QuestPage("Ты принял вызов. Подняться на мостик к капитану?", "Подняться на мостик", "Отказаться подниматься на мостик", "Ты не пошёл на переговоры. Поражение")),
                Map.entry(3, new QuestPage("Ты поднялся на мостик. Кто ты?", "Рассказать правду о себе", "Солгать о себе", "Твою ложь разоблачили. Поражение"))
        );
        session.setAttribute("pages", pages);

        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        session.setAttribute("adress", hostAddress);

        session.setAttribute("numberGames", 0);
    }
}
