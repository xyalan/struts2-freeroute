package org.example.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;

/**
 * @author bastengao
 * @date 13-1-12 20:21
 */
public class BookAction {
    @Route("/books")
    public String execute() {
        return Results.html("content");
    }
}