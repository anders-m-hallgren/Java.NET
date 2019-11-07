using MediatR;
using System.Collections.Generic;

namespace se.clouds.javanet.app.domain.weatherforecast.commands
{
    public class InvalidateCacheWeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecasts { get; set; }
    }
}
