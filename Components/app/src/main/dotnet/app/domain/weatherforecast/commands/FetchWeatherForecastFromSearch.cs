using MediatR;

namespace se.clouds.javanet.app.domain.weatherforecast.commands
{
    public class FetchWeatherForecastFromSearch : IRequest<WeatherForecast>
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
