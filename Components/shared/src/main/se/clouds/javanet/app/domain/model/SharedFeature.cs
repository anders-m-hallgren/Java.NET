using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;
using se.clouds.javanet.core.domain;

namespace se.clouds.javanet.app.domain.model
{
    public class SharedFeature : AggregateRoot<SharedFeature>
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        [JsonIgnore]
        public Guid Id { get; set; }

        public bool Activated { get; set; }

        public string Value { get; set; }
        public string Version { get; set; }

        public string Description { get; set; }


        public override string ToString()
        {
            return string.Format($"Feature (Name = {Name}, Activated = {Activated}, Value = {Value}, Description = {Description})");
        }

        protected override bool EqualsCore(SharedFeature other)
        {
            throw new NotImplementedException();
        }

        protected override int GetHashCodeCore()
        {
            throw new NotImplementedException();
        }
    }
}
