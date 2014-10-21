package com.example.looker;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author jan.babel 
 * Application is monitoring Clipboard and checks, if the word is in the excel file, in column A.
 * If yes, it Toast its translation, which is in column B
 *         Excel should have a form 
 *       A			  B 
 *     Mutter 		Matka 
 *     Vater        Otec
 */

public class MainActivity extends Activity {

	static String TAG = "Vypis"; // use for filtering in logs
	String location = "/Download/Books/Dictionary";
	String fileName = "dictionary.xls";
	private static final String undefinedMessage = "UNDEFINED";
	HSSFWorkbook myWorkBook;
	ClipboardManager mClipboard;
	TextView txtClipboardContent;
	EditText editPilePath;

	
	ClipboardManager.OnPrimaryClipChangedListener mPrimaryChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
		public void onPrimaryClipChanged() {
			updateClipData();
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		mClipboard.addPrimaryClipChangedListener(mPrimaryChangeListener);
		txtClipboardContent = (TextView) findViewById(R.id.textView1);
		editPilePath = (EditText) findViewById(R.id.editTextPathToFile);
		editPilePath.setText(location + "/" + fileName);

		myWorkBook = readExcelFile(fileName);
	}

	private HSSFWorkbook readExcelFile(String filename) {
		HSSFWorkbook myWorkBook = null;
		if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
			Log.e(TAG, "Storage not available or read only");
			return null;
		}

		try {
			File sdCard = Environment.getExternalStorageDirectory();
			File directory = new File(sdCard.getAbsolutePath() + location);
			directory.mkdirs();
			File file = new File(directory, filename);
			FileInputStream myInput = new FileInputStream(file);
			// Create a POIFSFileSystem object
			POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
			// Create a workbook using the File System
			myWorkBook = new HSSFWorkbook(myFileSystem);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return myWorkBook;
	}

	private void searchExcelFile(Context context, HSSFWorkbook excelFile, String value) {
			// find all sheet in excel file
		for (int i = 0; i < excelFile.getNumberOfSheets(); i++) {
            HSSFSheet mySheet = excelFile.getSheetAt(i);
            // read a row
            Iterator<Row> rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				// if word exist in column A, show value in Column B
				if (myRow.getCell(0).toString().contains(value)) {
				Toast.makeText(context, myRow.getCell(1).toString(), Toast.LENGTH_SHORT).show();
				Log.i(TAG, "Search phrase: " + value + ",  translation : " +  myRow.getCell(1).toString() );
				return; // stop after finding first occurance
				}
            }
		}
		Toast.makeText(context,undefinedMessage, Toast.LENGTH_SHORT).show();

	}

	void updateClipData() {
		if ((mClipboard.hasPrimaryClip())) {
			ClipData.Item item = mClipboard.getPrimaryClip().getItemAt(0);
			String textInClip = (String) item.getText();
			if (item.getText() != null) {
				txtClipboardContent.setText(textInClip);
			}
			searchExcelFile(getBaseContext(), myWorkBook, textInClip);
		}
	}

	// --------------------- Utilities ------------------ //
	public static boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	public static boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            openAbout();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void openAbout() {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}
	

}
