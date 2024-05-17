import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Class to handle text compression
public class Main {
    public static byte[] compress(String text) {
        Map<String, Integer> wordToNumber = new HashMap<>();
        int wordCount = 0;
        StringBuilder compressedText = new StringBuilder();

        String[] words = text.split("\\s+");

        for (String word : words) {
            if (!wordToNumber.containsKey(word)) {
                wordToNumber.put(word, ++wordCount);
            }

            int wordNumber = wordToNumber.get(word);

            // Add the word number to the compressed text
            compressedText.append((char) wordNumber);
            compressedText.append(" ");
        }

        // Convert compressed text to byte array
        byte[] compressedArray = new byte[compressedText.length()];
        for (int i = 0; i < compressedText.length(); i++) {
            compressedArray[i] = (byte) compressedText.charAt(i);
        }

        return compressedArray;
    }
}

// Class to handle text decompression
class Decompressor {
    public static String decompress(byte[] compressedData) {
        Map<Integer, String> numberToWord = new HashMap<>();
        int wordCount = 0;

        StringBuilder decompressedText = new StringBuilder();
        int i = 0;
        while (i < compressedData.length) {
            int count = compressedData[i++];
            int wordNumber = compressedData[i++];
            String word = numberToWord.get(wordNumber);
            if (word == null) {
                word = String.valueOf(++wordCount);
                numberToWord.put(wordNumber, word);
            }
            for (int j = 0; j < count; j++) {
                decompressedText.append(word);
                decompressedText.append(" ");
            }
        }
        return decompressedText.toString();
    }
}

public class Main {
    // Method to read text from file
    public static String readTextFromFile(String filename) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    // Method to write compressed data to file
    public static void writeCompressedDataToFile(byte[] compressedData, String filename) throws IOException {
        try (FileOutputStream result = new FileOutputStream(filename + ".sc")) {
            result.write(compressedData);
        }
    }

    // Method to write decompressed text to file
    public static void writeDecompressedTextToFile(String decompressedText, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(decompressedText);
        }
    }

    public static void main(String[] args) {
        try {
            // Read original text from file
            String originalText = readTextFromFile("/Users/saadatibakova/Desktop/mytext.txt");

            // Compress the text
            byte[] compressedData = TextCompressor.compress(originalText);

            // Write compressed data to file
            writeCompressedDataToFile(compressedData, "compressed");

            // Decompress the compressed data
            String decompressedText = Decompressor.decompress(compressedData);

            // Write decompressed text to file
            writeDecompressedTextToFile(decompressedText, "decompressed.txt");

            System.out.println("Text compression and decompression completed successfully.");
        } catch (IOException e) {
        }
    }
}


