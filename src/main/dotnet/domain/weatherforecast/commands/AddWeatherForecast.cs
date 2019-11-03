using MediatR;
using System.Collections.Generic;

namespace se.clouds.app.javanet.domain.weatherforecast.commands
{
    public class AddWeatherForecast : IRequest
    {
        public IEnumerable<WeatherForecast> WeatherForecast { get; set; }

    }
}
