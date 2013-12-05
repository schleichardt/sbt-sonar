package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        try {
        System.out.println("use logger!!!");
        } catch(Throwable e){}
        return ok(index.render("Your new application is ready."));
    }
  
}
