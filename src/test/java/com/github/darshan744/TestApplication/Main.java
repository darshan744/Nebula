package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Nebula;
import io.github.darshan744.nebula.Http.Constants.HttpMethod;
import io.github.darshan744.nebula.Route.Router;

public class Main {
    public static void main(String[] args) {
        Nebula server = new Nebula();
        UserController controller = new UserController();
        server.get("/users", controller);
        server.start();
    }
}
