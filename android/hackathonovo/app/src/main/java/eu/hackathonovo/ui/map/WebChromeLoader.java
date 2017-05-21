package eu.hackathonovo.ui.map;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

public class WebChromeLoader {

    public WebChromeLoader(final String url, final Context context) {
        new CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url));
    }
}
