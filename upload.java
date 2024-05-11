import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        public static byte[] compress(String text) {
            List<Byte> compressedList = new ArrayList<>();
            
            int count = 1;
            for (int i = 1; i <= text.length(); i++) {
                if (i == text.length() || text.charAt(i) != text.charAt(i - 1)) {
                    compressedList.add((byte) text.charAt(i - 1));
                    compressedList.add((byte) count);
                    count = 1;
                } else {
                    count++;
                }
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
            for (int i = 0; i < compressedData.length; i += 2) {
                byte character = compressedData[i];
                int count = compressedData[i + 1] & 0xFF; // Convert byte to unsigned int
                for (int j = 0; j < count; j++) {
                    decompressedText.append((char) character);
                }
            }
            return decompressedText.toString();
        }
    }

    static class TextComparer {
        public static boolean isSame(String originalText, String decompressedText) {
            return originalText.equals(decompressedText);
        }
    }

    public static void main(String[] args) {
        try {
            String originalText = TxtReader.readTextFile("mytext.txt");
            byte[] compressedData = Compressor.compress(originalText);
            CompressedFileWriter.writeCompressedFile(compressedData, "compressed");
            String decompressedText = Decompressor.decompress(compressedData);
            boolean isSame = TextComparer.isSame(originalText, decompressedText);
            System.out.println("Is decompressed text same as original? " + isSame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
