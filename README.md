## TextCompressor

TextCompressor is a simple Python program that compresses and decompresses text files using a basic word-based compression technique.

## Features

- Compresses text files by replacing repeated words with numerical representations.
- Decompresses compressed text files to their original form.

## Usage

To use TextCompressor, follow these steps:

1. Clone this repository or download the TextCompressor.java file.
2. Compile the Java file using a Java compiler: javac TextCompressor.java
3. Run the compiled Java program with appropriate command-line arguments: java TextCompressor <inputFilePath> <compressedFilePath>

Replace `<inputFilePath>` with the path to the text file you want to compress, and `<compressedFilePath>` with the desired path for the compressed output file.

## Example

Suppose you have a text file named `mytext.txt` containing the following text:

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer ullamcorper magna nec tellus maximus, at consequat justo semper. Nullam facilisis magna ut ligula congue, in luctus ex pharetra. Nullam dignissim fermentum odio, eu vehicula justo commodo vitae. Vestibulum at varius justo. Duis ut malesuada nisi. Integer maximus diam in lorem fringilla, ut fringilla elit ultrices. Donec eget odio et nibh ullamcorper suscipit a id libero.

To compress this text file, you can run: 
java TextCompressor mytext.txt compressed.sc

This will compress the text file `mytext.txt` and save the compressed output to `compressed.sc`.

To decompress the compressed file, you can run: 
java TextCompressor compressed.sc decompressed.txt

This will decompress the compressed file `compressed.sc`.
