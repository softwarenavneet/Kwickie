package navneet.kwickie.com.kwickiefeed.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import navneet.kwickie.com.kwickiefeed.R;

public class KwickieVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kwickie_video);

        VideoView vidView = (VideoView) findViewById(R.id.videoView);
        String vidAddress = getIntent().getStringExtra("VIDEOURL");

        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);

        vidView.start();
    }
}


