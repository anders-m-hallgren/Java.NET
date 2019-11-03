using MediatR;
using System.Collections.Generic;

namespace se.clouds.app.javanet.domain.weatherforecast.commands
{
    public class InvalidateCacheWeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecasts { get; set; }
    }
}
