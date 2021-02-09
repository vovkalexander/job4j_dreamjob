package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSONObjectConvertor;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class CityServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        Collection<City> cities = PsqlStore.instOf().findAllCities();
        resp.setHeader("Access-Control-Allow-Origin", "*");
       // Gson gson = new GsonBuilder().create();
        JSONArray jarray = new JSONArray(cities);
       // JsonObject jsonObject = new JsonObject();
       // jsonObject.add("cities", jarray);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(jarray);
        writer.flush();

    }

}
