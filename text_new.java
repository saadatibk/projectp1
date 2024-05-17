import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Compressor {
    private Map<String, Integer> wordToNum;
    private Map<Integer, String> numToWord;
    private int nextId;

    public Compressor() {
        this.wordToNum = new HashMap<>();
        this.numToWord = new HashMap<>();
        this.nextId = 1;
    }

    public String compress(String text) {
        String[] words = text.split("\\s+");
        StringBuilder compressedText = new StringBuilder();

        for (String word : words) {
            if (!wordToNum.containsKey(word)) {
                wordToNum.put(word, nextId);
                numToWord.put(nextId, word);
                nextId++;
            }
            compressedText.append(wordToNum.get(word)).append(" ");
        }

        return compressedText.toString().trim();
    }

    public String decompress(String compressedText) {
        String[] nums = compressedText.split("\\s+");
        StringBuilder decompressedText = new StringBuilder();

        for (String num : nums) {
            int wordId = Integer.parseInt(num);
            if (numToWord.containsKey(wordId)) {
                decompressedText.append(numToWord.get(wordId)).append(" ");
            } else {
                throw new IllegalArgumentException("ID " + wordId + " not found in the decompression dictionary.");
            }
        }

        return decompressedText.toString().trim();
    }

    public void compressFile(String inputFile, String outputFile) throws IOException {
        String text = readTextFromFile(inputFile);
        String compressedText = compress(text);
        writeTextToFile(compressedText, outputFile);
        System.out.println("File '" + inputFile + "' compressed to '" + outputFile + "'");
    }

    public void decompressFile(String inputFile, String outputFile) throws IOException {
        String compressedText = readTextFromFile(inputFile);
        String decompressedText = decompress(compressedText);
        writeTextToFile(decompressedText, outputFile);
        System.out.println("File '" + inputFile + "' decompressed to '" + outputFile + "'");
    }

    private String readTextFromFile(String filename) throws IOException {
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

    private void writeTextToFile(String text, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text);
        }
    }

    public static void main(String[] args) {
        Compressor compressor = new Compressor();

        try {
            // Compress a file
            compressor.compressFile("/Users/saadatibakova/Desktop/mytext.txt", "compressed.txt");

            // Decompress the file
            compressor.decompressFile("compressed.txt", "output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
