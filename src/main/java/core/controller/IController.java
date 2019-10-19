package core.controller;

public interface IController {
    void init();
    //void service();
    String getRoutePath();
    void postService();
    IActionResult Get();
}
