using MediatR;
using System.Collections.Generic;

namespace se.clouds.javanet.app.domain.weatherforecast.commands
{
    public class AddWeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecast { get; set; }

    }
}
