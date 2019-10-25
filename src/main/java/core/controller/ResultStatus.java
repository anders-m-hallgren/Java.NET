package core.controller;
import javax.servlet.http.HttpServletResponse;

public class ResultStatus {
    public static enum Status {
        MOVED_TEMPORARILY(HttpServletResponse.SC_MOVED_TEMPORARILY),
        UNPROCESSED(-1),
        STATIC(1),
        SWITCHING_PROTOCOL(101),
        OK(HttpServletResponse.SC_OK),
        NOT_FOUND(HttpServletResponse.SC_NOT_FOUND),
        BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST),
        NOT_IMPLEMENTED(HttpServletResponse.SC_NOT_IMPLEMENTED),
        METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED),
        SERVICE_UNAVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

        public final int status;

        private Status(int status) {
            this.status = status;
        }
    }
}
