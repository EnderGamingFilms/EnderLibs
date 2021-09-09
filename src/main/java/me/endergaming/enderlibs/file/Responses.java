package me.endergaming.enderlibs.file;

public final class Responses {
    public static final ErrorMessage INVALID_PLAYER = new ErrorMessage("&cThat player is not online or does not exist.");
    public static final ErrorMessage INVALID_ARGUMENT = new ErrorMessage("&cInvalid argument(s).");
    public static final ErrorMessage INVALID_PERMISSION = new ErrorMessage("&cInsufficient permissions.");
    public static final ErrorMessage NON_PLAYER = new ErrorMessage("&cOnly players can run this command!");

    public static class ErrorMessage {
        String message;

        public ErrorMessage(String msg) {
            this.message = msg;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return this.message;
        }
    }
}
