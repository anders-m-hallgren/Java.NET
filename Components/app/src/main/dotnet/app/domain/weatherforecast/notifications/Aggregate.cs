using MediatR;

namespace se.clouds.javanet.app.domain.weatherforecast.notifications
{
    public class AggregateWeatherForecast : INotification
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
