package com.example.looker;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class About extends Activity{
	TextView aboutText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		aboutText = (TextView) findViewById(R.id.textViewABout);
		aboutText.setText(Html.fromHtml(getString(R.string.aboutText)));
		
	}

}
