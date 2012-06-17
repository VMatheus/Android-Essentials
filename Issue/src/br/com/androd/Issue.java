package br.com.androd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Issue extends Activity {
	
	private Button btnFundo;
	private Button btnBrowser;
	private Button btnImagem;
	private Button btnSomaNumero;
	
	private LinearLayout lnrLayout;
	private AutoCompleteTextView edtLink;
	
	private TextView txtResultado;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnFundo = (Button) findViewById(R.id.btnFundo);
        lnrLayout = (LinearLayout) findViewById(R.id.linearLayout);
        btnBrowser = (Button) findViewById(R.id.btnBrowser);
        edtLink = (AutoCompleteTextView) findViewById(R.id.autoLink); 
        btnImagem = (Button) findViewById(R.id.btnImagem);
        btnSomaNumero = (Button) findViewById(R.id.btnSomaNumero);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
         
        btnFundo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				lnrLayout.setBackgroundColor(Color.BLUE);
//				if(lnrLayout.getBackground()){
//					lnrLayout.setBackgroundColor(Color.BLUE);
//				}else{
//					lnrLayout.setBackgroundColor(Color.BLACK);
//				}
			}
		});
        
        btnBrowser.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				String link = edtLink.getText().toString();
				
				final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link));
				startActivity(intent);
			}
		});
        
        btnImagem.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				startActivity(new Intent(Issue.this, Imagem.class));
			}
		});
     
        btnSomaNumero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int soma = 0;
				Intent it = new Intent(Issue.this, SomaNumero.class);
				startActivityForResult(it, soma);
			}
		});
  
    }
    
    @Override
    protected void onActivityResult(int codigo, int resultado, Intent it){
    	
    	Integer soma = it.getExtras().getInt("soma");
    	txtResultado.setText("Resultado: " + soma);
    	
    }
}