using MediatR;

namespace se.clouds.app.javanet.domain.weatherforecast.notifications
{
    public class AggregateWeatherForecast : INotification
    {
        public WeatherForecast WeatherForecast { get; set; }
    }
}
