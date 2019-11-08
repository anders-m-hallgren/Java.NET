package se.clouds.javanet.app.domain.feature.handler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import se.clouds.javanet.app.domain.feature.command.AddStartupMessage;
import se.clouds.javanet.core.configuration.Configuration;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.controller.ResultStatus;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;

public class AddMessageHandler implements IRequestHandler<AddStartupMessage, IActionResult>
{
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();

    public AddMessageHandler()
    {
        mediatr.addRequestHandler(new AddStartupMessage(), new HandlerTask());
    }

    public class HandlerTask implements Task<IActionResult>
    {
        private final static String QUEUE_NAME = "hello";
        @Override
        public IActionResult call() throws Exception {
            var result = new ActionResult();
            var host = Configuration.Get("Rabbit", "Host");

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                //System.out.println(" [x] Sent '" + message + "'");
            }
            result.SetStatus(ResultStatus.Status.OK);
            result.SetContent("");
            return result;
        }
    }
}
