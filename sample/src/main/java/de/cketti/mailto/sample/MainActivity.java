package de.cketti.mailto.sample;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import de.cketti.mailto.EmailIntentBuilder;


public class MainActivity extends AppCompatActivity {
    private View mainContent;
    private EditText emailTo;
    private EditText emailCc;
    private EditText emailBcc;
    private EditText emailSubject;
    private EditText emailBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContent = findViewById(R.id.main_content);
        emailTo = (EditText) findViewById(R.id.email_to);
        emailCc = (EditText) findViewById(R.id.email_cc);
        emailBcc = (EditText) findViewById(R.id.email_bcc);
        emailSubject = (EditText) findViewById(R.id.email_subject);
        emailBody = (EditText) findViewById(R.id.email_body);

        findViewById(R.id.button_send_email).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_feedback) {
            sendFeedback();
        }
        return true;
    }

    private void sendFeedback() {
        boolean success = EmailIntentBuilder.from(this)
                .to("cketti@gmail.com")
                .subject(getString(R.string.feedback_subject))
                .body(getString(R.string.feedback_body))
                .start();

        if (!success) {
            Snackbar.make(mainContent, R.string.error_no_email_app, Snackbar.LENGTH_LONG).show();
        }
    }

    void sendEmail() {
        String to = emailTo.getText().toString();
        String cc = emailCc.getText().toString();
        String bcc = emailBcc.getText().toString();
        String subject = emailSubject.getText().toString();
        String body = emailBody.getText().toString();

        EmailIntentBuilder builder = EmailIntentBuilder.from(this);

        try {
            if (!TextUtils.isEmpty(to)) {
                builder.to(to);
            }
            if (!TextUtils.isEmpty(cc)) {
                builder.cc(cc);
            }
            if (!TextUtils.isEmpty(bcc)) {
                builder.bcc(bcc);
            }
            if (!TextUtils.isEmpty(subject)) {
                builder.subject(subject);
            }
            if (!TextUtils.isEmpty(body)) {
                builder.body(body);
            }

            boolean success = builder.start();
            if (!success) {
                Snackbar.make(mainContent, R.string.error_no_email_app, Snackbar.LENGTH_LONG).show();
            }
        } catch (IllegalArgumentException e) {
            String errorMessage = getString(R.string.argument_error, e.getMessage());
            Snackbar.make(mainContent, errorMessage, Snackbar.LENGTH_LONG).show();
        }
    }
}
