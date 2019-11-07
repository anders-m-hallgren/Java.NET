using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.feature.queries;

namespace se.clouds.javanet.app.domain.feature.handlers
{
    public class GetFeatureHandler : IRequestHandler<GetFeature, Feature[]>
    {
        private readonly IMediator _mediator;
        private readonly ILogger<GetFeatureHandler> _logger;
        //public TimeSpan CachedTime { get; set; } = TimeSpan.FromHours(12);
        public GetFeatureHandler(ILogger<GetFeatureHandler> logger, IMediator mediator)
        {
            _logger = logger;
            _mediator = mediator;
        }
        public async Task<Feature[]> Handle(GetFeature request, CancellationToken cancellationToken) =>
            new Feature[]{
                new Feature() {
                    Name = "javaOrDotnet",
                    Activated = true,
                    Description = "Hello from .NET, shared cache: " + await _mediator.Send(new GetFromCache())
                },
                new Feature() {
                    Name = "exampleFeature",
                    Activated = true,
                    Description = "controlls access to the example service."
                }
            };

        /*
        Feature[] result;
        var keyId ="Feature-0";

        var cacheResp = await _cache.GetAsync(keyId, cancellationToken);
        if (cacheResp != null)
        {
            _logger.LogDebug($"--- Redis: {Encoding.UTF8.GetString(cacheResp)}");
            return JsonSerializer.Deserialize<Feature[]>(Encoding.UTF8.GetString(cacheResp));
        }
        else {
            _logger.LogDebug($"--- No cache entry could be found");
            // data here

            var serialized = JsonSerializer.Serialize<Feature[]>(result);
            var options = new DistributedCacheEntryOptions().SetSlidingExpiration(CachedTime);
            await _cache.SetAsync(keyId, Encoding.UTF8.GetBytes(serialized), options, cancellationToken);
        }

        return result; */
    }
}
