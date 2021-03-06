package br.com.fourlinux.playmp3;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayMP3Activity extends Activity {
    
	//objeto que controla a reprodução do audio
	MediaPlayer mediaPlayer;
	Button buttonPlayPause, buttonQuit;
	TextView textState;
	
	private int stateMediaPlayer;
	private final int stateMP_Error = 0;
	private final int stateMP_NotStarter = 1;
	private final int stateMP_Playing = 2;
	private final int stateMP_Pausing = 3;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonPlayPause = (Button)findViewById(R.id.playpause);
        buttonQuit = (Button)findViewById(R.id.quit);
        textState = (TextView)findViewById(R.id.state);
        
        buttonPlayPause.setOnClickListener(buttonPlayPauseOnClickListener);
        buttonQuit.setOnClickListener(buttonQuitOnClickListener);
        
        //chamando o método que inicia o player de musica
        initMediaPlayer();
        
    }
    
    
    private void initMediaPlayer(){
    	//caminho do arquivo mp3
    	String PATH_TO_FILE = "/sdcard/music.mp3";   	
    	//instanciando o objeto mediaPlayer
    	mediaPlayer = new  MediaPlayer();
    	
    	try {
    		
			mediaPlayer.setDataSource(PATH_TO_FILE);
			mediaPlayer.prepare();
			Toast.makeText(this, PATH_TO_FILE, Toast.LENGTH_LONG).show();
			stateMediaPlayer = stateMP_NotStarter;
	        textState.setText("- PARADO -");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			stateMediaPlayer = stateMP_Error;
	        textState.setText("- ERRO!!! -");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			stateMediaPlayer = stateMP_Error;
	        textState.setText("- ERRO!!! -");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			stateMediaPlayer = stateMP_Error;
	        textState.setText("- ERRO!!! -");
		}
    }
    
    
    
    //funções do botão play pausa
    Button.OnClickListener buttonPlayPauseOnClickListener = new Button.OnClickListener(){

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(stateMediaPlayer){
				case stateMP_Error:
					break;
				case stateMP_NotStarter:
					mediaPlayer.start();
					buttonPlayPause.setText("Pause");
					textState.setText("- TOCANDO -");
					stateMediaPlayer = stateMP_Playing;
					break;
				case stateMP_Playing:
					mediaPlayer.pause();
					buttonPlayPause.setText("Play");
					textState.setText("- PAUSANDO -");
					stateMediaPlayer = stateMP_Pausing;
					break;
				case stateMP_Pausing:
					mediaPlayer.start();
					buttonPlayPause.setText("Pause");
					textState.setText("- TOCANDO -");
					stateMediaPlayer = stateMP_Playing;
					break;
				}
				
			}
    };
    
    
    
    //funções do botão quit
    Button.OnClickListener buttonQuitOnClickListener = new Button.OnClickListener(){

		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mediaPlayer.stop();
			mediaPlayer.release();
			finish();
		}	
    };
}