using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
#nullable enable
namespace se.clouds.javanet.core.domain
{
    public interface IAggregateRoot
    {
        string Name { get; set; }
    }

    public abstract class AggregateRoot<T> : IAggregateRoot where T : AggregateRoot<T>
    {
        public enum AggregateType
        {
            None,
            Month,
            Week,
            Year
        }
        public DateTime StopTime { get; set; }
        public string? Term { get; set; }
        [NotMapped]
        public IList<string>? Terms { get; set; }
        public bool IsAggregated { get; set; } = false;

        public string Name
        {
            get
            {
                var term = Terms?.Count > 1?
                    string.Join(" || ", Terms) + "-" :
                    string.IsNullOrEmpty(Term) ?
                        "" :
                        Term + "-";
                //var term = string.IsNullOrEmpty(Term) ? "" : Term + "-";
                var aggregate = (Aggregate.Equals(AggregateType.None)) ? "" : Aggregate.ToString() + "-";
                return $"{typeof(T).Name}-{term}{aggregate}{StopTime.ToString("s")}".Replace(":", "");
            }
            set { }
        }

        [NotMapped]
        public string? Source { get; set; }

        public AggregateType Aggregate { get; set; } = AggregateType.None;

        public override bool Equals(Object obj)
        {
            var valueObject = obj as T;
            if (ReferenceEquals(valueObject, null))
                return false;
            return EqualsCore(valueObject);
        }

        protected abstract bool EqualsCore(T other);

        public override int GetHashCode()
        {
            return GetHashCodeCore();
        }
        protected abstract int GetHashCodeCore();

        public static bool operator ==(AggregateRoot<T> a, AggregateRoot<T> b)
        {
            if (ReferenceEquals(a, null) && ReferenceEquals(b, null))
                return true;
            if (ReferenceEquals(a, null) || ReferenceEquals(b, null))
                return false;
            return a.Equals(b);
        }
        public static bool operator !=(AggregateRoot<T> a, AggregateRoot<T> b)
        {
            return !(a == b);
        }
    }
}
