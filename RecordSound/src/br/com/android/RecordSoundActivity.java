package br.com.android;

import java.io.File;
import java.io.IOException;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class RecordSoundActivity extends Activity {
	
	//variaveis estaticas para definir o formato do arquivo e a pasta onde ficarão os arquivos
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "Gravações";

	
	private MediaRecorder recorder = null;
	
	private int currentFormat = 0;
	//variavel do tipo inteira que selecionara o formato de saida
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,AUDIO_RECORDER_FILE_EXT_3GP };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//chamando o método que sensibiliza os botões
		setButtonHandlers();
		//chamando o método que habilita os botões quando clicados
		enableButtons(false);
		//chamando o método que muda o texto do botão de seleção de formato
		setFormatButtonCaption();
	}
	
	
	
	
	//método responsavel por sensibilizar os botões
	private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
	}
	//método responsavel por dehabilitar o botao stop
	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}
	//habilita o botão stop e desabilita os outros
	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
		enableButton(R.id.btnFormat, !isRecording);
		enableButton(R.id.btnStop, isRecording);
	}

	//muda o texto do botão format 
	private void setFormatButtonCaption() {
		((Button) findViewById(R.id.btnFormat)).setText(getString(R.string.audio_format) + " ("+ file_exts[currentFormat] + ")");
	}
	
	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
	}

	
	
	//método que inicia a gravação do som
	private void startRecording() {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());
		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//método que para a gravação 
	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();

			recorder = null;
		}
	}

	
	
	//método que cria um alertdialog para mostrar a seleção do formato
	private void displayFormatDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String formats[] = { "MPEG 4", "3GPP" };

		builder.setTitle(getString(R.string.)).setSingleChoiceItems(formats, currentFormat,
				new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {
								currentFormat = which;
								setFormatButtonCaption();
								dialog.dismiss();
							}
						}).show();
	}
	//em caso de erro
	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			AppLog.logString("Erro: " + what + ", " + extra);
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			AppLog.logString("Aviso: " + what + ", " + extra);
		}
	};
	
	
	
	
	//aqui tudos os métodos são chamados atravez dos botões
	private View.OnClickListener btnClick = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				AppLog.logString("Iniciando Gravação");

				enableButtons(true);
				startRecording();

				break;
			}
			case R.id.btnStop: {
				AppLog.logString("Iniciando Gravação");

				enableButtons(false);
				stopRecording();

				break;
			}
			case R.id.btnFormat: {
				displayFormatDialog();

				break;
			}
			}
		}
	};
}
