using System;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using RabbitMQ.Client;
using se.clouds.javanet.app.domain.feature.commands;

public class AddMessageHandler : IRequestHandler<AddStartupMessage, Unit>
{
//connection is thread safe, use one channel per thread
    private IConfiguration _conf {get; set;}
    private readonly ILogger<AddMessageHandler> _logger;
    private readonly IMediator _mediator;
    public AddMessageHandler(ILogger<AddMessageHandler> logger, IMediator mediator, IConfiguration conf)
    {
        _conf=conf;
        _logger = logger;
        _mediator = mediator;
    }
        //public TimeSpan CachedTime { get; set; } = TimeSpan.FromHours(12);
    public Task<Unit> Handle(AddStartupMessage request, CancellationToken cancellationToken)
    {
        var factory = new ConnectionFactory() { HostName = _conf["Rabbit:Host"] };
        using(var connection = factory.CreateConnection())
        using(var channel = connection.CreateModel())
        {
            channel.QueueDeclare(queue: "hello",
                                 durable: false,
                                 exclusive: false,
                                 autoDelete: false,
                                 arguments: null);

            string message = "Hello World!";
            var body = Encoding.UTF8.GetBytes(message);

            channel.BasicPublish(exchange: "",
                                 routingKey: "hello",
                                 basicProperties: null,
                                 body: body);
            Console.WriteLine(" [x] Sent {0}", message);
        }
        return Unit.Task;
        //Console.WriteLine(" Press [enter] to exit.");
        //Console.ReadLine();
    }
}
