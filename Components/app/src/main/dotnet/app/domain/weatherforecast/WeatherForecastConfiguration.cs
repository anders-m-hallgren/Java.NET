//using Microsoft.EntityFrameworkCore;
//using Microsoft.EntityFrameworkCore.Metadata.Builders;
using System;

/* namespace workload._DomainCore.Entities._IndeedJob
{
    public class WeatherForecastConfiguration : IEntityTypeConfiguration<WeatherForecast>
    {
        public void Configure(EntityTypeBuilder<WeatherForecast> builder)
        {
            builder.HasData(new //Anonymous, will there be a fix?
            {
                Id = new Guid("00000000-1111-2222-3333-AAAABBBB0001"),
                Name = "cool",
                StartTime = DateTime.UtcNow,
                StopTime = DateTime.Now,
            });

            builder.HasAlternateKey(k => k.Name);
            builder.HasMany(j => j.Jobs).WithOne(j => j.IndeedJob)
                    .HasForeignKey("IndeedJobForeignKey")
                    .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
 */
