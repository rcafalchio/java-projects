package br.com.leilaopecuario.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.leilaopecuario.constantes.Constantes;

public class LeilaoWebHelper {

	private final static Logger LOGGER = Logger.getLogger(LeilaoWebHelper.class);

	public static void gravarFotos(String localFotos, String localGravar) {

		try {

			final File diretorioGravarFotos = new File(localGravar);
			final File diretorioFotos = new File(localFotos);
			LeilaoWebHelper.copyAll(diretorioFotos, diretorioGravarFotos);

		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	public static String recuperaWorkSpace(String caminho) {

		try {
			final String[] diretorios = caminho.split("\\\\");
			// caminho = diretorios[0] + "/" + diretorios[1];
			caminho = diretorios[0];
		} catch (Exception e) {
			LOGGER.error("Não conseguiu recuperar o diretório", e);
		}

		return caminho;

	}

	public static void copyFile(File source, File destination) throws IOException {

		if (!source.exists()) {
			return;
		}

		if (destination.isDirectory() && !destination.exists()) {
			destination.mkdir();
		} else if (destination.exists() && !destination.isDirectory()) {
			destination.delete();
		}

		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;

		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		} finally {
			if (sourceChannel != null && sourceChannel.isOpen())
				sourceChannel.close();
			if (destinationChannel != null && destinationChannel.isOpen())
				destinationChannel.close();
		}
	}

	public static void copyAll(File origem, File destino) throws Exception {

		FileChannel oriChannel = null;
		FileChannel destChannel = null;

		try {

			if (!destino.exists())

				destino.mkdir();

			File[] listaDeArquivos = origem.listFiles();

			if (listaDeArquivos == null) {
				origem.setLastModified(origem.lastModified());

				// Cria channel na origem
				oriChannel = new FileInputStream(origem.getPath()).getChannel();

				// Cria channel no destino
				destChannel = new FileOutputStream(destino + "\\" + origem.getName()).getChannel();

				// Copia conteúdo da origem no destino
				destChannel.transferFrom(oriChannel, 0, oriChannel.size());

				// Fecha channels
				oriChannel.close();
				destChannel.close();
			} else {
				for (File file : listaDeArquivos) {

					if (file.isDirectory()) {
						String novoDiretorioString = destino.getAbsolutePath() + "\\\\" + file.getName();
						File novoDiretorio = new File(novoDiretorioString);
						novoDiretorio.mkdirs();
						copyAll(file, novoDiretorio);
						final FacesContext aFacesContext = FacesContext.getCurrentInstance();
						final ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
						// Insere imagem "sem_imagem.jpg" na pasta de fotos do
						// leilão
						String caminho = context.getRealPath("/") + "imagens\\" + Constantes.SEM_IMAGEM;
						File caminhoFotoSemImagem = new File(caminho);
						copyAll(caminhoFotoSemImagem, novoDiretorio);
					} else {
						file.setLastModified(origem.lastModified());

						// Cria channel na origem
						oriChannel = new FileInputStream(file.getPath()).getChannel();

						// Cria channel no destino
						destChannel = new FileOutputStream(destino + "\\" + file.getName()).getChannel();

						// Copia conteúdo da origem no destino
						destChannel.transferFrom(oriChannel, 0, oriChannel.size());

						// Fecha channels
						oriChannel.close();
						destChannel.close();
					}

				}
			}

			// Runtime.getRuntime().exec("explorer " +
			// destino.getAbsolutePath());

			LOGGER.info("Sincronização concluída com sucesso!");

		} catch (IOException e) {

			LOGGER.error(e.getMessage());

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			throw e;

		} finally {

			if (oriChannel != null && oriChannel.isOpen()) {

				oriChannel.close();
			}

			if (destChannel != null && destChannel.isOpen()) {

				destChannel.close();
			}

		}
	}
}
