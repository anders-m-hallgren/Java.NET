using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.feature;
using se.clouds.javanet.app.domain.feature.commands;
using se.clouds.javanet.app.domain.feature.queries;
using se.clouds.javanet.app.domain.model;

namespace se.clouds.javanet.app.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class HelloController : ControllerBase
    {
        private readonly IMediator _mediator;
        private readonly ILogger<HelloController> _logger;
        public HelloController(IMediator mediator, ILogger<HelloController> logger)
        {
            _mediator = mediator;
            _logger = logger;
        }
        [HttpGet]
        public async Task<Unit> GetAsync()
        {
            return await _mediator.Send(new AddStartupMessage(), new CancellationToken());
        }
    }
}
