package ru.job4j.dream.servlet;

import ru.job4j.dream.model.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    public void  doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        PsqlStore.instOf().saveUser(user);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
