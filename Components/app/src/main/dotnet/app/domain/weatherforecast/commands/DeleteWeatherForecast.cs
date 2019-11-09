using MediatR;
using System.Collections.Generic;

namespace se.clouds.javanet.app.domain.weatherforecast.commands
{
    public class WeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecasts { get; set; }

    }
}
