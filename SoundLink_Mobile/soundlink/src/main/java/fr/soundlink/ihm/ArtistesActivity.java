package fr.soundlink.ihm;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import fr.soundlink.R;
import fr.soundlink.service.SoundlinkService;

@EActivity(R.layout.artistes_activiy)
public class ArtistesActivity extends Activity {
	
    @Bean
    protected SoundlinkService soundlinkService;
    
    

}
