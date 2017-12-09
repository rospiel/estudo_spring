package com.estudo.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import static java.nio.file.FileSystems.getDefault;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.estudo.brewer.storage.FotoStorage;

public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(getDefault().getPath(System.getenv("USERPROFILE"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path local) {
		this.local = local;
		criarPastas();
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar fotos.");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
			
		} catch (IOException erro) {
			throw new RuntimeException("Erro criando pasta para salvar foto", erro);
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal) {
		StringBuilder novoNome = new StringBuilder().append(UUID.randomUUID().toString()).append("_").append(nomeOriginal);
		
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome.toString();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		
		if(files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = this.renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException erro) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária", erro);
			}
			
			return novoNome;
		}
		
		return novoNome;
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nomeFoto) {
		
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nomeFoto));
		} catch (IOException erro) {
			throw new RuntimeException("Erro lendo a foto temporária", erro);
		}
	}
	
	
	
}
