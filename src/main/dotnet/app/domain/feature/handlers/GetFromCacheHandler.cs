using System;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.feature.queries;

namespace se.clouds.javanet.app.domain.feature.handlers
{
    public class GetFromCacheHandler : IRequestHandler<GetFromCache, string>
    {
        private readonly ILogger<GetFromCacheHandler> _logger;
        private readonly IDistributedCache _cache;

        public GetFromCacheHandler(ILogger<GetFromCacheHandler> logger, IDistributedCache cache)
        {
            _logger = logger;
            _cache = cache;
        }
        public async Task<string> Handle(GetFromCache request, CancellationToken cancellationToken)
        {
            var cache = await _cache.GetAsync("shared-cache-0", cancellationToken);
            if (cache != null)
            {
                _logger.LogDebug($"--- Redis: {Encoding.UTF8.GetString(cache)}");
                return Encoding.UTF8.GetString(cache);
                //return JsonSerializer.Deserialize<Feature[]>(Encoding.UTF8.GetString(cache));
            }
            else
            {
                /*
            var serialized = JsonSerializer.Serialize<Feature[]>(result);
            var options = new DistributedCacheEntryOptions().SetSlidingExpiration(CachedTime);
            await _cache.SetAsync(keyId, Encoding.UTF8.GetBytes(serialized), options, cancellationToken);
             */
                TimeSpan CachedTime = TimeSpan.FromMinutes(10);
                var options = new DistributedCacheEntryOptions().SetSlidingExpiration(CachedTime);
                await _cache.SetAsync("shared-cache-0", Encoding.UTF8.GetBytes("hello from shared cache, set by .NET"), options, cancellationToken);
                return "hello from shared cache";
            }
        }
    }
}
