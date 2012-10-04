package org.mimacom.mega.translator.service.internal;

import com.google.common.io.Closeables;
import com.google.common.io.Files;
import org.mimacom.mega.translator.service.PropertyKey;
import org.mimacom.mega.translator.service.PropertyService;
import org.mimacom.mega.translator.service.TranslationFileService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class DefaultTranslationFileService implements TranslationFileService {

	public static final String KEY_VALUE_SEPARATOR = "=";
	public static final String COMMENT_PREFIX = "#";
	private final PropertyService propertyService;

	@Inject
	public DefaultTranslationFileService(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@Override
	public void addTranslationFile(String fileName, InputStream translationFileInputStream) throws IOException {
		Map<String, String> properties = analyzeAndStore(fileName, translationFileInputStream);

	}

	private Map<String, String> analyzeAndStore(String fileName, InputStream translationFileInputStream) {
		File tempFile = null;
		BufferedWriter bw = null;
		Scanner scanner = null;
		try {
			tempFile = File.createTempFile("trans", ".tmp");
			bw = new BufferedWriter(new FileWriter(tempFile));
			scanner = new Scanner(translationFileInputStream);
			Map<String, String> properties = new HashMap<String, String>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				addProperty(properties, line);
				bw.write(line);
				bw.newLine();
			}
			Closeables.closeQuietly(bw);

			String keyHash = getKeyHash(properties);
			File keyHashFolder = new File(propertyService.getProperty(PropertyKey.TRANSLATION_FILE_STORE), keyHash);
			keyHashFolder.mkdirs();
			File translationFile = new File(keyHashFolder, fileName);
			boolean created = translationFile.createNewFile();

			if (created) {
				Files.copy(tempFile, translationFile);
			} else {
				// TODO handle this case
			}

			return properties;

		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("The hashkey of the given file couldn't be computed.", e);
		} catch (IOException e) {
			throw new IllegalStateException("An error occured with the given InputStream.", e);
		} finally {
			scanner.close();
			Closeables.closeQuietly(bw);
			tempFile.delete();
		}
	}

	public String getKeyHash(Map<String, String> properties) throws IOException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		for (String key : properties.keySet()) {
			md.update(key.getBytes());
		}

		byte[] digest = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	private void addProperty(Map<String, String> properties, String propertyLine) {
		// Ignore comments
		if (propertyLine.startsWith(COMMENT_PREFIX)) {
			// Go to next line
			return;
		}

		// Search the first '=' sign
		int equalsIndex = propertyLine.indexOf(KEY_VALUE_SEPARATOR);
		if (equalsIndex > 0) {
			String key = propertyLine.substring(0, equalsIndex).trim();
			String translation = propertyLine.substring(equalsIndex + 1).trim();
			if (!isNullOrEmpty(key)) {
				properties.put(key, translation);
			}
		}
	}
}
