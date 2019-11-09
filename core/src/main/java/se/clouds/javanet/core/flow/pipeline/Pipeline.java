package se.clouds.javanet.core.flow.pipeline;

public class Pipeline<In, Out> implements IPipeline{
    private final Step<In, Out> current;

    public Pipeline(Step<In, Out> current) {
        this.current = current;
    }

    public <NewOut> Pipeline<In, NewOut> pipe(Step<Out, NewOut> next) {
        return new Pipeline<>(input -> next.Process(current.Process(input)));
    }

    public Out execute(In input) throws Step.StepException {
        return current.Process(input);
    }
}
