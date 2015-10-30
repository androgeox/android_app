package com.georgiy.mapsearch;

        import android.net.Uri;
        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Intent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import com.georgiy.mapsearch.R;

/**
 * Any activity in this application.
 * TODO: put description of this activity.
 * */
public class MyActivity extends Activity implements OnClickListener {

    /* Private fields for store links to UI components */
    private Button btnFind = null;
    private EditText edFindQuery = null;

    /**
     * Private method that contain initialization code
     * for user interface (UI) components.
     * */
    private void initUI() {
        btnFind = (Button) this.findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);
        edFindQuery = (EditText) this.findViewById(R.id.edFindQuery);
    }

    /**
     * Called when the activity is starting (for more details,
     * please see description into super class).
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

		/* Invoke a parent method */
        super.onCreate(savedInstanceState);

		/* Load User Interface from resources */
        setContentView(R.layout.act_main);

		/* Initialize UI components (see initUI() method */
        this.initUI();

    }

    /**
     * Called when a view has been clicked.
     * 	@param v The view that was clicked
     */
    @Override
    public void onClick(View v) {

		/* Button Find */
        if (btnFind.equals(v)) {

			/* Define string for URI scheme */
            String geo = "geo:0,0?q=" +
                    edFindQuery.getText().toString().trim();

			/* Create intent object */
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,Uri.parse(geo));

			/* Starting Google Maps activity */
            this.startActivity(intent);

			/* Exit from method */
            return;

        }

    }

}
