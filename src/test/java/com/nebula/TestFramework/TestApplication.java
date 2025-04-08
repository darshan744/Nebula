package com.nebula.TestFramework;

import com.nebula.Nebula;
import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Route.Router;

public class TestApplication {
    public static void main(String[] args) {
        Nebula.start();
        Router router = Router.getRouter();
        UserController userController = new UserController();
        router.registerRoute(HttpMethod.GET, "/users", userController);
    }
}
