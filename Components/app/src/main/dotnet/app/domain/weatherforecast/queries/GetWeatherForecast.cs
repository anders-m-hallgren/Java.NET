using MediatR;

namespace se.clouds.javanet.app.domain.weatherforecast.queries
{
    public class GetWeatherForecast : IRequest<WeatherForecast[]>
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
