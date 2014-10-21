package com.example.kalkulacka;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private double prvy;
	private double druhy;
	private double vysledok;
	EditText prvyET;
	EditText druhyET;
	EditText vysledokET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			prvy = 0.0;
			druhy = 0.0;
			vysledok = 0.0;

		}

		prvyET = (EditText) findViewById(R.id.prvyEditText);
		druhyET = (EditText) findViewById(R.id.druhyEditText);
		vysledokET = (EditText) findViewById(R.id.vysledokEditText);

		prvyET.addTextChangedListener(prvyListener);

	}

	private TextWatcher prvyListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				if (!s.toString().equals("") || !s.toString().equals("0") || !s.toString().equals("0."))
					prvy = Double.parseDouble(s.toString());

			} catch (NumberFormatException e) {
				prvy = 0.0;
			}
			updateVysledok();

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private void updateVysledok() {

		double prvyTemp = prvy;
		double druhyTemp = Double.parseDouble(druhyET.getText().toString());
		double vysledokTemp = prvyTemp * druhyTemp;
		vysledokET.setText(String.format("%.02f", vysledokTemp));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
