using System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using se.clouds.app.javanet.domain.weatherforecast.queries;

namespace se.clouds.app.javanet.domain.weatherforecast.handlers
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
        public Task<WeatherForecast[]> Handle(GetWeatherForecast request, CancellationToken cancellationToken)
        {
            var rng = new Random();

            var result = Enumerable.Range(1, 5).Select(index => new WeatherForecast
            {
                Date = DateTime.Now.AddDays(index),
                TemperatureC = rng.Next(-20, 55),
                Summary = Summaries[rng.Next(Summaries.Length)]
            })
            .ToArray();
            return Task.FromResult(result);
        }

    }
}
