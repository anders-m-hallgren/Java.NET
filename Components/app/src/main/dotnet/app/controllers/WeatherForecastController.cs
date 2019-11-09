using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using se.clouds.javanet.app.domain.weatherforecast;
using se.clouds.javanet.app.domain.weatherforecast.queries;

namespace se.clouds.javanet.app.controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private readonly IMediator _mediator;
        private readonly ILogger<WeatherForecastController> _logger;
        public WeatherForecastController(IMediator mediator, ILogger<WeatherForecastController> logger)
        {
            _mediator = mediator;
            _logger = logger;
        }

        [HttpGet]
        public async Task<WeatherForecast[]> GetAsync()
        {
            return await _mediator.Send(new GetWeatherForecast(), new CancellationToken());
        }
    }
}
