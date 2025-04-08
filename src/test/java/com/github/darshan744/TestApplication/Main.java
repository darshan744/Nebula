package com.github.darshan744.TestApplication;

import com.github.darshan744.nebula.Nebula;
import com.github.darshan744.nebula.Http.Constants.HttpMethod;
import com.github.darshan744.nebula.Route.Router;

public class Main {
    public static void main(String[] args) {
        Nebula.start();
        UserController controller = new UserController();
        Router router = Router.getRouter();
        router.registerRoute(HttpMethod.POST, "/users", controller);
    }
}
