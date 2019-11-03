using MediatR;
using System.Collections.Generic;

namespace se.clouds.app.javanet.domain.weatherforecast.commands
{
    public class WeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecasts { get; set; }

    }
}
