using System;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using se.clouds.app.javanet.domain.feature.queries;

namespace se.clouds.app.javanet.domain.feature.handlers
{
    public class GetFeatureHandler : IRequestHandler<GetFeature, Feature[]>
    {
        private readonly ILogger<GetFeatureHandler> _logger;
        private readonly IDistributedCache _cache;
        //public TimeSpan CachedTime { get; set; } = TimeSpan.FromHours(12);
        public GetFeatureHandler(ILogger<GetFeatureHandler> logger, IDistributedCache cache)
        {
            _logger = logger;
            _cache = cache;
        }
        public async Task<Feature[]> Handle(GetFeature request, CancellationToken cancellationToken) =>
            new Feature[]{
                new Feature() {
                    Name = "javaOrDotnet",
                    Activated = true,
                    Description = "Hello from .NET"
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
