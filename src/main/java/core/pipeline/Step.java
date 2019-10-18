package core.pipeline;

public interface Step<I, O> {
    public static class StepException extends RuntimeException {
        private static final long serialVersionUID = 12344321L;

        public StepException(Throwable t) {
            super(t);
        }
    }
    public O Process(I input) throws StepException;
}
