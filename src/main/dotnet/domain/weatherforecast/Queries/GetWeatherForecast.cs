using MediatR;

namespace se.clouds.app.javanet.domain.weatherforecast.queries
{
    public class GetWeatherForecast : IRequest<WeatherForecast[]>
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
