using System;
using System.Collections.Generic;

public class DslModel
{
    public DslModel()
    {
        MatchConditions = new List<MatchCondition>();
    }

    public DateRange DateRange { get; set; }
    public int? Limit { get; set; }
    public IList<MatchCondition> MatchConditions { get; set; }
}

public class DateRange
{
    public DateTime From { get; set; }
    public DateTime To { get; set; }
}

public class MatchCondition
{
    public DslObject Object { get; set; }
    public DslOperator Operator { get; set; }
    public string Value { get; set; }
    public List<string> Values { get; set; }

    public DslLogicalOperator LogOpToNextCondition { get; set; }
}

public enum DslObject
{
    Application,
    ExceptionType,
    Message,
    StackFrame,
    Fingerprint
}

public enum DslOperator
{
    NotDefined,
    Equals,
    NotEquals,
    Like,
    NotLike,
    In,
    NotIn
}

public enum DslLogicalOperator
{
    NotDefined,
    Or,
    And
}
