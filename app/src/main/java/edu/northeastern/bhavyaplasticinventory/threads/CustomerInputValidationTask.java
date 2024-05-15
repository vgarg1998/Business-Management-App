package edu.northeastern.bhavyaplasticinventory.threads;

import android.os.AsyncTask;

public class CustomerInputValidationTask extends AsyncTask<String, Void, Boolean > {

    private ValidationListener validationListener;

    public CustomerInputValidationTask(ValidationListener listener) {
        this.validationListener = listener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        for (String input : strings) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        // Invoke the onValidationResult method of the ValidationListener
        if (validationListener != null) {
            validationListener.onValidationResult(aBoolean);
        }
    }

    @FunctionalInterface
    public interface ValidationListener {
        void onValidationResult(boolean isValid);
    }
}
