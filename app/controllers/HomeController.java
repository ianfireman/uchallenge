package controllers;

import play.mvc.*;
import play.libs.Json;
import java.util.Map;
import java.util.HashMap;
import views.html.*;

public class HomeController extends Controller {

    public Result index() {
        String name = request().getQueryString("name");
        Map<String, String> helloWorld = new HashMap();
        helloWorld.put("id", "1");
        helloWorld.put("content", String.format("Hello %s!", name));
        return ok(Json.toJson(helloWorld));
    }

}
