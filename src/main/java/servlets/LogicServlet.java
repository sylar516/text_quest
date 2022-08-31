package servlets;

import entities.QuestPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String variant = req.getParameter("variant");

        HttpSession session = req.getSession();
        Map<Integer, QuestPage> pages = (Map<Integer, QuestPage>) session.getAttribute("pages");
        int currentPageNumber = (int) session.getAttribute("pageNumber") + 1;
        if (variant.equals("no") || currentPageNumber > pages.size()) {
            resp.sendRedirect("/gameOver.jsp?variant=" + variant);
            return;
        }
        session.setAttribute("pageNumber", currentPageNumber);
        resp.sendRedirect("/quest.jsp");
    }
}
