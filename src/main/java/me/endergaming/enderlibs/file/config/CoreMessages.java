package me.endergaming.enderlibs.file.config;

public class CoreMessages {
    public static ErrorMessage INVALID_PLAYER = new ErrorMessage("&cThat player is not online or does not exist.");
    public static ErrorMessage INVALID_ARGUMENT = new ErrorMessage("&cInvalid argument(s).");
    public static ErrorMessage INVALID_PERMISSION = new ErrorMessage("&cInsufficient permissions.");
    public static ErrorMessage NON_PLAYER = new ErrorMessage("&cOnly players can run this command!");

    public static void changeErrorMessage(ErrorMessage e, final String s) {
        e.setMessage(s);
    }

    public static class ErrorMessage {
        String message;

        public ErrorMessage(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
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
