package at.ac.tuwien.sepm.assignment.individual.universe.exception;
    public class DateOperationsException extends Exception{

        private static final long serialVersionUID = 1403901877318412951L;

        public DateOperationsException () {
        }

        public DateOperationsException(String message) {
            super(message);
        }

        public DateOperationsException(Throwable cause) {
            super(cause);
        }

        public DateOperationsException(String message, Throwable cause) {
            super(message, cause);
        }

        public DateOperationsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
}
