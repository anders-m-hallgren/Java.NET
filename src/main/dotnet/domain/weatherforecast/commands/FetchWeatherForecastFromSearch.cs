using MediatR;

namespace se.clouds.app.javanet.domain.weatherforecast.commands
{
    public class FetchWeatherForecastFromSearch : IRequest<WeatherForecast>
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
