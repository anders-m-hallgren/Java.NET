package se.clouds.app.javanet.core.controller;

public interface IController {
    void init();
    //void service();
    String getRoutePath();
    boolean getIncludePipeProcessing();
    void postService();
    IActionResult Get();
}
