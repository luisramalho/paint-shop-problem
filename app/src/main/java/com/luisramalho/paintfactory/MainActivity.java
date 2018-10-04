package com.luisramalho.paintfactory;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.luisramalho.pf.PaintFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.clearButton) Button clearButton;
    @BindView(R.id.computeButton) Button computeButton;
    @BindView(R.id.inputEditText) EditText inputEditText;
    @BindView(R.id.outputTextView) TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String fileURL = inputEditText.getText().toString();
                    new PerformComputationTask().execute(new URL(fileURL));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    outputTextView.setText(e.toString());
                }

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputEditText.setText("");
                outputTextView.setText("");
            }
        });
    }

    private class PerformComputationTask extends AsyncTask<URL, String,String> {

        PaintFactory.StatusCallback callback = new PaintFactory.StatusCallback() {
            @Override
            public void onStatusUpdate(String status) {
                publishProgress(status);
            }
        };

        @Override
        protected String doInBackground(URL... urls) {
            try {
                callback = new PaintFactory.StatusCallback() {
                    @Override
                    public void onStatusUpdate(String status) {
                        publishProgress(status);
                    }
                };
                PaintFactory paintFactory = new PaintFactory(urls[0], callback);
                return paintFactory.solve();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        protected void onPreExecute() {
            computeButton.setClickable(false);
        }

        protected void onProgressUpdate(String... item) {
            outputTextView.setText(item[0]);
        }

        protected void onPostExecute(String result) {
            outputTextView.setText(result);
            computeButton.setClickable(true);
        }
    }
}
