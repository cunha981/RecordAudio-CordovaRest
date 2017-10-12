package br.com.verity.recordaudio.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FtpBusiness {

	private static final String user = "teste";
	private static final String password = "teste";
	private static final int port = 21;
	private static final String server = "ftp.teste.hospedagemdesites.ws";
	private static final FTPClient ftpClient = new FTPClient();

	private static void connect() throws IOException {
		ftpClient.connect(server, port);
		ftpClient.login(user, password);
		ftpClient.enterLocalPassiveMode();

		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

	}

	private static void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}

	@SuppressWarnings("unused")
	private static void delete(String nameFile) throws IOException {
		ftpClient.deleteFile(nameFile);
	}

	private static void insert(MultipartFile file, String name) throws IOException {

		boolean done = ftpClient.storeFile(name, file.getInputStream());
		if (done) {
			System.out.println("Enviado com sucesso.");
		}
	}

	public static void enviarArquivo(MultipartFile file, String path, String name) throws IOException {
		try {
			connect();
			Boolean existe = caminhoExiste(path);
			if (existe)
				insert(file, name);
		} finally {
			disconnect();
		}
	}

	public static boolean caminhoExiste(String path) throws IOException {
		ArrayList<String> folders = new ArrayList<String>(Arrays.asList(path.split("/")));

		for (String folder : folders) {

			Boolean exist = ftpClient.changeWorkingDirectory(folder);
			if (!exist) {
				criarCaminho(folder);
				ftpClient.changeWorkingDirectory(folder);
			}
		}
		return true;
	}

	public static void criarCaminho(String path) throws IOException {
		ftpClient.makeDirectory(path);
	}
}
