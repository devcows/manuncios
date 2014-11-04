package com.devcows.manuncios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ContactActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String contact_url = intent.getStringExtra(Utils.CONTACT_URL);

        Bundle bundle = new Bundle();
        bundle.putString(Utils.CONTACT_URL, contact_url);

        Fragment fragment = new ContactFragment();
        fragment.setArguments(bundle);

        showFragment(fragment, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public static class ContactFragment extends FragmentReturn {

        public ContactFragment() {
        }

        @Override
        public String getReturnName() {
            return "a contactar";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.activity_contact, container, false);
            this.context = getActivity();

            String contact_url = getArguments().getString(Utils.CONTACT_URL);

            WebView myWebView = (WebView) rootView.findViewById(R.id.web_view);
            myWebView.loadUrl(contact_url);
            myWebView.setWebViewClient(new CustomWebViewClient());

            return rootView;
        }

        //web view client implementation
        private class CustomWebViewClient extends WebViewClient {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //do whatever you want with the url that is clicked inside the webview.
                //for example tell the webview to load that url.
                view.loadUrl(url);
                //return true if this method handled the link event
                //or false otherwise
                return true;
            }
        }
    }
}
