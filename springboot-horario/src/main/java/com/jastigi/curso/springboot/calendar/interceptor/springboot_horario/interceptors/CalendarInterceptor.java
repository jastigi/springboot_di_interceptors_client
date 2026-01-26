package com.jastigi.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component("calendarInterceptor")
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println("Hora actual: " + hour);

        if (hour < open || hour > close) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            StringBuilder message = new StringBuilder("Cerrado, fuera del horario de atención");
            message.append(" ");
            message.append("Horario de atención: ");
            message.append(open);
            message.append(" a ");
            message.append(close);
            message.append(" ");
            message.append("Hora actual: ");
            message.append(hour);
            message.append(" ");
            message.append("Gracias por usar nuestro sistema!");
            data.put("message", message.toString());
            data.put("date", new Date().toString());
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(data));
            response.setStatus(401);

            return false;
        } else {
            StringBuilder message = new StringBuilder("Bienvenidos al horario de atención");
            message.append(" ");
            message.append("Horario de atención: ");
            message.append(open);
            message.append(" a ");
            message.append(close);
            message.append(" ");
            message.append("Hora actual: ");
            message.append(hour);
            message.append(" ");
            message.append("Gracias por usar nuestro sistema!");
            request.setAttribute("message", message.toString());
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

}
