import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
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
            List<Byte> compressedList = new ArrayList<>();
            Map<String, Integer> wordToNumber = new HashMap<>();
            int wordCount = 0;

            String[] words = text.split("\\s+");

            for (String word : words) {
                if (!wordToNumber.containsKey(word)) {
                    wordToNumber.put(word, ++wordCount);
                }

                int wordNumber = wordToNumber.get(word);

                // Add count of the word
                compressedList.add((byte) word.length());
                // Add the word number to the compressed list
                compressedList.add((byte) wordNumber);
            }

            byte[] compressedArray = new byte[compressedList.size()];
            for (int i = 0; i < compressedList.size(); i++) {
                compressedArray[i] = compressedList.get(i);
            }

            return compressedArray;
        }
    }

    static class CompressedFileWriter {
        public static void writeCompressedFile(byte[] compressedData, String filename) throws IOException {
            try (FileOutputStream fos = new FileOutputStream(filename + ".sc")) {
                fos.write(compressedData);
            }
        }
    }

    static class Decompressor {
        public static String decompress(byte[] compressedData) {
            StringBuilder decompressedText = new StringBuilder();
            int i = 0;
            while (i < compressedData.length) {
                int count = compressedData[i++];
                int wordNumber = compressedData[i++];
                String word = getWordFromNumber(wordNumber);
                for (int j = 0; j < count; j++) {
                    decompressedText.append(word);
                }
            }
            return decompressedText.toString();
        }

        private static String getWordFromNumber(int wordNumber) {
            for (Map.Entry<String, Integer> entry : wordToNumber.entrySet()) {
                if (entry.getValue() == wordNumber) {
                    return entry.getKey();
                }
            }
            return null; // Handle if wordNumber is not found
        }
    }

    static Map<String, Integer> wordToNumber = new HashMap<>();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
