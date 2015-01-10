package com.Avishek2014.vatcalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;*/ // this commented out block may be needed in future

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {

	public float totalPrice = 0;
	public float price = 0;
	public float vat = 0;
    public float total = 0; //grand total
    public float totalVat = 0;

    private VatCalculatorDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        dataSource = new VatCalculatorDataSource(this);
        dataSource.open();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}


        // the following commented out block may be needed in future
        /*EditText cal = (EditText) findViewById(R.id.editText2);
        cal.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    calculate();
                }
                return false;
            }
        });
*/	}

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onResume();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
            // the following commented out block may be needed in future
		/*case R.id.action_settings:
			openSettings();
			return true;*/
		case R.id.action_help:
			openHelp();
			return true;
        case R.id.action_saved_data:
            Intent intent = new Intent(this, SavedDataActivity.class);
            startActivity(intent);
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	public void openHelp(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Help");
		alertDialog.setMessage("1. Provide the amount of VAT (e.g. 15) in the 'VAT' field.\n2. Insert price (e.g. 100) in the 'Price' field.\n3. Press 'Calculate' to"
				+ " find out the grand total (including VAT) and total VAT.\n4. Press 'Add more' to add further prices to the current one.\n"
				+ "5. Press 'Reset all' to start over.\n6. Press 'Save' to save the calculations.");
		alertDialog.show();
	}

    // the following commented out block may be needed in future
	/*public void openSettings() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Settings");
		alertDialog.setMessage("This action is not yet implemented. Sorry for your inconvenience.");
		alertDialog.show();
	}*/

	public void calculateVat(View view) {

		calculate();

	}

    private void calculate(){
        try {
            EditText editText = (EditText) findViewById(R.id.editText1);
            String vatstring = editText.getText().toString();
            vat = Float.parseFloat(vatstring); // this gets the value of the VAT

            EditText editTextPrice = (EditText) findViewById(R.id.editText2);
            String pricestring = editTextPrice.getText().toString();
            price = Float.parseFloat(pricestring); // this gets the value of the price

            price = price + totalPrice;

            total = price + (price * vat) / 100; // grand total
            totalVat = total - price; // total VAT

            TextView textViewGtotal = (TextView) findViewById(R.id.textView3);
            //String grandTotal = Float.toString(total);
            String grandTotal = String.format("%.2f", total); // rounding to two decimal places
            textViewGtotal.setText(grandTotal);

            TextView textViewTvat = (TextView) findViewById(R.id.textView4);
            //String totalVatString = Float.toString(totalVat);
            String totalVatString = String.format("%.2f", totalVat); // rounding to two decimal places
            textViewTvat.setText(totalVatString);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Please provide valid data");
            alertDialog.show();
        }
    }

	public void addMore(View view) {

		try {

			EditText editText = (EditText) findViewById(R.id.editText1);
			String vatstring = editText.getText().toString();
			vat = Float.parseFloat(vatstring); // this gets the value of the VAT

			EditText editTextPrice = (EditText) findViewById(R.id.editText2);
			String pricestring = editTextPrice.getText().toString();
			price = Float.parseFloat(pricestring); // this gets the value of the price

			totalPrice = totalPrice + price;

			total = totalPrice + (totalPrice * vat) / 100; // grand total
			totalVat = total - totalPrice; // total VAT

			TextView textViewGtotal = (TextView) findViewById(R.id.textView3);
			//String grandTotal = Float.toString(total);
            String grandTotal = String.format("%.2f", total); // rounding to two decimal places
			textViewGtotal.setText(grandTotal);

			TextView textViewTvat = (TextView) findViewById(R.id.textView4);
			//String totalVatString = Float.toString(totalVat);
            String totalVatString = String.format("%.2f", totalVat); // rounding to two decimal places
			textViewTvat.setText(totalVatString);

			EditText editTextAddPrice = (EditText) findViewById(R.id.editText2);
			editTextAddPrice.setText("");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error!");
			alertDialog.setMessage("Please provide valid data");
			alertDialog.show();
		}

	}

	public void reset(View view) {
        // the following commented out block won't be necessary
		// EditText resetEditTextVat = (EditText) findViewById(R.id.editText1);
		// resetEditTextVat.setText("");
        EditText resetVat = (EditText) findViewById(R.id.editText1);
        resetVat.setText(""); //this resets the initial VAT
		EditText resetPrice = (EditText) findViewById(R.id.editText2);
		resetPrice.setText(""); //this resets the price

		TextView resetGtotal = (TextView) findViewById(R.id.textView3);
		resetGtotal.setText(""); //resets grand total
		TextView resetTvat = (TextView) findViewById(R.id.textView4);
		resetTvat.setText(""); //resets total VAT

		totalPrice = 0;
		price = 0;

        resetVat.requestFocus();
	}

    public void save(View view){
        if(price != 0){
            dataSource.createVatCalculatorEntry(getDateTime(), total, totalVat);

            // the following commented out block won't be necessary
            /*AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Save");
            alertDialog.setMessage("Saved!");
            alertDialog.show();*/
            Toast toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Please make sure you've performed the calculation first!");
            alertDialog.show();
        }
    }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}


    // returns datetime in string format
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
