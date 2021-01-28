package ru.job4j.dream.servlet;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject json = new JSONObject();
        json.append("key", "Nice to meet you, " + name);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
