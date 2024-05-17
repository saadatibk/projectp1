import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class compressor {

    static class TxtReader {
        public static String readTextFile(String filename) throws IOException {
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
    }

    static class Compressor {
        public byte[] compress(String text) {
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
            }

            // Convert compressed text to byte array
            byte[] compressedArray = new byte[compressedText.length()];
            for (int i = 0; i < compressedText.length(); i++) {
                compressedArray[i] = (byte) compressedText.charAt(i);
            }

            return compressedArray;
        }
    }

    static class CompressedFileWriter {
        public static void writeCompressedFile(byte[] compressedData, String filename) throws IOException {
            try (FileOutputStream result = new FileOutputStream(filename + ".sc")) {
                result.write(compressedData);
            }
        }
    }

    static class Decompressor {
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
                }
            }
            return decompressedText.toString();
        }
    }
    public class DecompressedFileWriter {
        public static void writeDecompressedFile(String decompressedText, String filename) throws IOException {
            try (BufferedWriter decompressed_result = new BufferedWriter(new FileWriter(filename))) {
                decompressed_result.write(decompressedText);
            }
        }
    }

    static class TextComparer {
        public static boolean isSame(String originalText, String decompressedText) {
            return originalText.equals(decompressedText);
        }
    }

    public static void main(String[] args) {
        try {
            String originalText = TxtReader.readTextFile("/Users/saadatibakova/Desktop/mytext.txt");
            Compressor compressor = new Compressor();
            byte[] compressedData = compressor.compress(originalText);
            CompressedFileWriter.writeCompressedFile(compressedData, "compressed");
            String decompressedText = Decompressor.decompress(compressedData);
            boolean isSame = TextComparer.isSame(originalText, decompressedText);
            System.out.println("Is decompressed text same as original? " + isSame);
            DecompressedFileWriter.writeDecompressedFile(decompressedText, "decompressed.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

