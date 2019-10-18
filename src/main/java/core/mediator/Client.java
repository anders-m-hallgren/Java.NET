package core.mediator;

public class Client {
    public static void Gomain(String[] args) {
      ApplicationMediator mediator = new ApplicationMediator();
      ConcreteColleague desktop = new ConcreteColleague(mediator);
      //ConcreteColleague mobile = new MobileColleague(mediator);
      mediator.addColleague(desktop);
      //mediator.addColleague(mobile);
      desktop.send("Hello World");
      //mobile.send("Hello");
    }
  }
