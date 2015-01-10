package com.Avishek2014.vatcalculator;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.DialogInterface;
import android.widget.Toast;

import javax.sql.DataSource;

public class SavedDataActivity extends ListActivity{

    private VatCalculatorDataSource dataSource;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        dataSource = new VatCalculatorDataSource(this);
        dataSource.open();

        listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);

        List<VatCalculator> values = dataSource.getAllEntries(); //gets List from VatCalculatorDataSource

        ArrayAdapter<VatCalculator> adapter = new ArrayAdapter<VatCalculator>(this,
                android.R.layout.simple_list_item_multiple_choice, values);
        setListAdapter(adapter);
    }

    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saved_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_help:
                openHelp();
                return true;
            case R.id.action_delte:
                openDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openHelp(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Help");
        alertDialog.setMessage("Select item(s) on the list and press 'DELETE' to delete the selected item(s).");
        alertDialog.show();
    }

    private void openDelete(){

        // dialog box
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete")
                .setMessage("Delete selected item(s)?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayAdapter<VatCalculator> adapter = (ArrayAdapter<VatCalculator>) getListAdapter();
                        VatCalculator vc = null;

                        // delete operation
                        for (int i = 0, j = 0; i < selectedItemCount; i++, j++) {
                            if(checkedItems.get(i)) {
                                vc = (VatCalculator) getListAdapter().getItem(j);
                                dataSource.deleteEntry(vc);
                                adapter.remove(vc);
                                j--;
                            }
                        }
                        selectedItemCount = 0;
                        checkedItems.clear();
                        listView.clearChoices();

                        adapter.notifyDataSetChanged();
                        Toast toast = Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG);
                        toast.show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    int selectedItemCount = 0; //length of the selected items. i.e. 2 for 2 selected items
    SparseBooleanArray checkedItems;

    public void onListItemClick(ListView parent, View v, int position, long id){
        selectedItemCount = parent.getCount();
        checkedItems = parent.getCheckedItemPositions();
    }

    /*public void delete(View view){

        ArrayAdapter<VatCalculator> adapter = (ArrayAdapter<VatCalculator>) getListAdapter();
        VatCalculator vc = null;

        for (int i = 0, j = 0; i < selectedItemCount; i++, j++) {
            if(checkedItems.get(i)) {
                vc = (VatCalculator) getListAdapter().getItem(j);
                dataSource.deleteEntry(vc);
                adapter.remove(vc);
                j--;
            }
        }
        selectedItemCount = 0;
        checkedItems.clear();
        listView.clearChoices();

        adapter.notifyDataSetChanged();
    }*/

}
