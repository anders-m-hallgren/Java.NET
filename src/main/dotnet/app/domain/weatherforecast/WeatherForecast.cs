using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using se.clouds.javanet.core.domain;

namespace se.clouds.javanet.app.domain.weatherforecast
{
	public class WeatherForecast : AggregateRoot<WeatherForecast>
	{
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        [JsonIgnore]
        public Guid Id { get; set; }

        public DateTime Date { get; set; }

        public int TemperatureC { get; set; }

        public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);

        public string Summary { get; set; }

        [NotMapped]
		public ICollection<WeatherForecast> WeatherForecasts {get; set;}

        protected override bool EqualsCore(WeatherForecast obj)
        {
            return base.Equals(obj);
        }

        public override string ToString()
        {
			return WeatherForecasts != null? string.Join(Environment.NewLine, WeatherForecasts) : "";
        }

        protected override int GetHashCodeCore()
        {
            return base.GetHashCode();
        }
    }
}
