package indiqueeganhe2019.com.indiqueeganhemobileag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PoliticaPrivacidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica_privacidade);

        WebView webView= (WebView)findViewById(R.id.wvpolitica);

        webView.loadUrl("https://politica588725227.wordpress.com/politica-de-privacidade/");

    }
}
