using System;
using Xunit;

namespace Java.NET.Test
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {
            var result = true;
            Assert.False(result, "1 should not be prime");
        }
    }
}
