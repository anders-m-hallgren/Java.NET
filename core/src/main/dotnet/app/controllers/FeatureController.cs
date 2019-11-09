using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.feature;
using se.clouds.javanet.app.domain.feature.queries;
using se.clouds.javanet.app.domain.model;

namespace se.clouds.javanet.app.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class FeatureController : ControllerBase
    {
        private readonly IMediator _mediator;
        private readonly ILogger<FeatureController> _logger;
        public FeatureController(IMediator mediator, ILogger<FeatureController> logger)
        {
            _mediator = mediator;
            _logger = logger;
        }
        [HttpGet]
        public async Task<SharedFeature[]> GetAsync()
        {
            return await _mediator.Send(new GetFeature(), new CancellationToken());
        }
    }
}
