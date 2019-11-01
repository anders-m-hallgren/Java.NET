package se.clouds.app.javanet.core.flow.pipeline;

public interface Step<In, Out> {
    public static class StepException extends RuntimeException {
        private static final long serialVersionUID = 12344321L;

        public StepException(Throwable t) {
            super(t);
        }
    }
    public Out Process(In input) throws StepException;
}
