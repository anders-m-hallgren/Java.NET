using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.feature.queries;
using se.clouds.javanet.app.domain.model;

namespace se.clouds.javanet.app.domain.feature.handlers
{
    public class GetFeatureHandler : IRequestHandler<GetFeature, SharedFeature[]>
    {
        private readonly IMediator _mediator;
        private readonly ILogger<GetFeatureHandler> _logger;
        //public TimeSpan CachedTime { get; set; } = TimeSpan.FromHours(12);
        public GetFeatureHandler(ILogger<GetFeatureHandler> logger, IMediator mediator)
        {
            _logger = logger;
            _mediator = mediator;
        }
        public async Task<SharedFeature[]> Handle(GetFeature request, CancellationToken cancellationToken) =>
            new SharedFeature[]{
                new SharedFeature() {
                    Name = "javaOrDotnet",
                    Activated = true,
                    Description = "Hello from .NET" + await _mediator.Send(new GetFromCache())
                },
                new SharedFeature() {
                    Name = "exampleFeature",
                    Activated = true,
                    Description = "controlls access to the example service."
                }
            };
    }
}
