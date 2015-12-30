package com.example.hanoidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText ev = (EditText) findViewById(R.id.editText);
		Button button = (Button) findViewById(R.id.button);
		final MySurfaceView surfaceView = (MySurfaceView) findViewById(R.id.surfaceview);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				String size = ev.getText().toString();
				int i_size = Integer.parseInt(size);
				surfaceView.setSize(i_size);
				}catch(Exception e){
					Toast.makeText(MainActivity.this, "输入的格式错误", 0).show();
				}
			}
		});
	}


}
