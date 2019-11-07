using System;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.weatherforecast.queries;

namespace se.clouds.javanet.app.domain.weatherforecast.handlers
{
    public class GetWeatherForecastHandler : IRequestHandler<GetWeatherForecast, WeatherForecast[]>
    {
        private static readonly string[] Summaries = new[]
        {
            "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
        };
        private readonly ILogger<GetWeatherForecastHandler> _logger;
        private readonly IDistributedCache _cache;
        public TimeSpan CachedTime { get; set; } = TimeSpan.FromHours(12);
        public GetWeatherForecastHandler(ILogger<GetWeatherForecastHandler> logger, IDistributedCache cache)
        {
            _logger = logger;
            _cache =  cache;
        }
        public async Task<WeatherForecast[]> Handle(GetWeatherForecast request, CancellationToken cancellationToken)
        {
            WeatherForecast[] result;
            var keyId ="WeatherForecast-0";

            var cacheResp = await _cache.GetAsync(keyId, cancellationToken);
            if (cacheResp != null)
            {
                _logger.LogDebug($"--- Redis: {Encoding.UTF8.GetString(cacheResp)}");
                return JsonSerializer.Deserialize<WeatherForecast[]>(Encoding.UTF8.GetString(cacheResp));
            }
            else {
                _logger.LogDebug($"--- No cache entry could be found");
                var rng = new Random();

                result = Enumerable.Range(1, 5).Select(index => new WeatherForecast
                {
                    Date = DateTime.Now.AddDays(index),
                    TemperatureC = rng.Next(-20, 55),
                    Summary = Summaries[rng.Next(Summaries.Length)]
                })
                .ToArray();

                var serialized = JsonSerializer.Serialize<WeatherForecast[]>(result);
                var options = new DistributedCacheEntryOptions().SetSlidingExpiration(CachedTime);
                await _cache.SetAsync(keyId, Encoding.UTF8.GetBytes(serialized), options, cancellationToken);
            }

            return result;
        }

    }
}
