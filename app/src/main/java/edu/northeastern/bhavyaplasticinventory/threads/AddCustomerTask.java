package edu.northeastern.bhavyaplasticinventory.threads;

import android.os.AsyncTask;

import edu.northeastern.bhavyaplasticinventory.doa.api_call.ClientApiCall;
import edu.northeastern.bhavyaplasticinventory.doa.entities.Client;

public class AddCustomerTask extends AsyncTask<String, Void, Boolean > {

    private ValidationListener validationListener;

    public AddCustomerTask(ValidationListener listener) {
        this.validationListener = listener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        for (String input : strings) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        //here I make the the api call to check whether adding the entry is possible or not
        //nothing is empty or null
        //verify the inputs regex

        //form the object and call
        String name = strings[0];
        String email = strings[1];
        String phone_number = strings[2];
        String gst_number = strings[3];
        Client client = new Client.ClientBuilder().name(name).email_id(email).phone_number(phone_number).gst_number(gst_number).build();
        ClientApiCall.addClientCall(client);

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
