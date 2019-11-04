using MediatR;
using se.clouds.app.javanet.domain.feature;

namespace se.clouds.app.javanet.domain.feature.queries
{
    public class GetFeature : IRequest<Feature[]>
    {
        public Feature Feature { get; set; }
    }
}
