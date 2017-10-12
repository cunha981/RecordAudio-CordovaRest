package br.com.verity.recordaudio.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.verity.recordaudio.business.AudioBusiness;

@RestController
public class AudioRestController {
	@Autowired
	private AudioBusiness audioBusiness;

	@PostMapping("record-audio")
	public ResponseEntity<String> gravar(@RequestParam MultipartFile audio, @RequestParam final String loja,
			@RequestParam final String categoria, HttpServletResponse response) {
		try {
			audioBusiness.salvar(audio, loja, categoria);
			//responseAudioSalvo(response, audioSalvo);

		} catch (IllegalStateException | IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar aúdio.");
		}
		return ResponseEntity.ok("Sucesso.");
	}
}
