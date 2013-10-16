package com.subha.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	private static final int REQUEST_CODE = 5;
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupSpinners();
		settings = new Settings();
	}
	
	private void setupSpinners() {
		setupImageSizeSpinner();
		setupColorFilterSpinner();
		setupImageTypeSpinner();
	}
	
	private void setupImageSizeSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spImageSize);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.image_sizes_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				settings.setImageSize((String) parent.getItemAtPosition(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void setupColorFilterSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spColorFilter);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.color_filter_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				settings.setColorFilter((String) parent.getItemAtPosition(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void setupImageTypeSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spImageType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.image_type_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				settings.setImageType((String) parent.getItemAtPosition(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	public void saveSettings(View v) {
		EditText etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		settings.setSiteFilter(etSiteFilter.getText().toString());
		Intent i = new Intent();
		i.putExtra("settings", this.settings);
		setResult(RESULT_OK, i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}
