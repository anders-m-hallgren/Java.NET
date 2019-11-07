using MediatR;

namespace se.clouds.javanet.app.domain.feature.queries
{
    public class GetFeature : IRequest<Feature[]>
    {
        public Feature Feature { get; set; }
    }
}
