using MediatR;
using se.clouds.javanet.app.domain.model;

namespace se.clouds.javanet.app.domain.feature.queries
{
    public class GetFeature : IRequest<SharedFeature[]>
    {
        public SharedFeature Feature { get; set; }
    }
}
