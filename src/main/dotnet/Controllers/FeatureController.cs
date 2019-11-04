using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using se.clouds.app.javanet.domain.feature;
using se.clouds.app.javanet.domain.feature.queries;

namespace se.clouds.app.javanet.Controllers
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
        public async Task<Feature[]> GetAsync()
        {
            return await _mediator.Send(new GetFeature(), new CancellationToken());
        }
    }
}
