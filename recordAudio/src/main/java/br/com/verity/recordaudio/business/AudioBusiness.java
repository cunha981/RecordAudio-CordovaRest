package br.com.verity.recordaudio.business;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudioBusiness {

	private static String marca = "ACOSTAMENTO";
	
	public MultipartFile salvar(MultipartFile audio, String loja, String categoria) throws IllegalStateException, IOException {
		String path = construirCaminho(loja, categoria);
		String name = LocalDateTime.now().toString().replaceAll(":", "-").replaceAll("\\.", "")+".mp3";
		
		FtpBusiness.enviarArquivo(audio,path,name);
		
		return audio;
	}
	private String construirCaminho(String loja, String categoria) {
		StringBuilder path = new StringBuilder(marca + "/");
		path.append(loja);
		path.append("/");
		path.append(categoria);
		path.append("/");
		path.append(LocalDate.now().toString());
		
		System.out.println(path);
		return path.toString();
	}
}
