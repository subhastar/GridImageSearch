package com.subha.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	
	private static final String BASE_URL =
			"https://ajax.googleapis.com/ajax/services/search/images";
	private static final int REQUEST_CODE = 5;
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultArrayAdapter imageAdapter;
	private Settings settings;
	private int start;
	private AsyncHttpClient client;
	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		
		query = "";
		start = 0;
		client = new AsyncHttpClient();
		
		imageResults = new ArrayList<ImageResult>();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				// do something
				Log.d("subhacounts", "calling load more");
				loadData();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			settings = (Settings) data.getSerializableExtra("settings");
		}
	}
	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_settings:
			showSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onImageSearch(View v) {
		loadData();	
	}
	
	public void loadData() {
		String query = etQuery.getText().toString();
		if (!this.query.equals(query)) {
			this.start = 0;
			imageResults.clear();
		}
		this.query = query;
				
		String settingsString = "&imgsz=" + settings.getImageSize() +
				"&imgcolor=" + settings.getColorFilter() +
				"&imgtype=" + settings.getImageType() +
				"&as_sitesearch=" + settings.getSiteFilter();
		client.get(BASE_URL + "?" + "rsz=8&start=" + start 
				+ settingsString
				+ "&v=1.0&q=" + Uri.encode(query),
		new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject(
							"responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
					
					start += 8;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showSettings() {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
	}
}
