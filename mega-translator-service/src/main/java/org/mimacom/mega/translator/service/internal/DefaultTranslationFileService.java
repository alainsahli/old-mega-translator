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
import java.util.*;

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
	public String addTranslationFile(String fileName, InputStream translationFileInputStream) throws IOException {
		Map<String, String> properties = analyzeAndStore(fileName, translationFileInputStream);
		String probablePrefix = findPrefix(properties);
		return probablePrefix;
	}

	private String findPrefix(Map<String, String> properties) {
		Map<String, Integer> prefixes = new HashMap<String, Integer>();
		for (String value : properties.values()) {
			if (value.length() > 3) {
				String testPrefix = value.substring(0, 3);
				if (prefixes.containsKey(testPrefix)) {
					Integer currentValue = prefixes.get(testPrefix);
					prefixes.put(testPrefix, currentValue + 1);
				} else {
					prefixes.put(testPrefix, 1);
				}
			}
		}
		String probablePrefix = biggestPrefix(prefixes);
		return searchEntirePrefix(probablePrefix, properties.values());
	}

	private String searchEntirePrefix(String probablePrefix, Collection<String> properties) {
		List<String> cleanedList = filterToPrefix(probablePrefix, properties);
		if (cleanedList.isEmpty()) {
			return null;
		}

		String entirePrefix = probablePrefix;
		boolean canBeMorePrecise = true;
		String anyPrefix = cleanedList.get(0);
		int prefixLength = 1;

		while (canBeMorePrecise) {
			if (prefixLength < anyPrefix.length()) {
				canBeMorePrecise = allStartsWith(anyPrefix.substring(0, prefixLength), cleanedList);
				if (canBeMorePrecise) {
					++prefixLength;
				}
			} else {
				canBeMorePrecise = false;
			}
		}
		return anyPrefix.substring(0, prefixLength - 1);
	}

	private boolean allStartsWith(String anyPrefix, List<String> elem) {
		for (String e : elem) {
			if (!e.startsWith(anyPrefix)) {
				return false;
			}
		}
		return true;
	}

	private List<String> filterToPrefix(String probablePrefix, Collection<String> properties) {
		List<String> result = new ArrayList<String>();
		for (String prefix : properties) {
			if (prefix.startsWith(probablePrefix)) {
				result.add(prefix);
			}
		}
		return result;
	}

	private String biggestPrefix(Map<String, Integer> prefixes) {
		String biggestPrefix = null;
		int maxPrefixOccurences = 0;
		for (String prefix : prefixes.keySet()) {
			if (prefixes.get(prefix) > maxPrefixOccurences) {
				biggestPrefix = prefix;
				maxPrefixOccurences = prefixes.get(prefix);
			}
		}
		return biggestPrefix;
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
