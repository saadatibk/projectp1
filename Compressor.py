class Compressor:
    def __init__(self):
        self.word_to_num = {}
        self.num_to_word = {}
        self.next_id = 1

    def compress(self, text):
        words = text.split()
        compressed_words = []

        for word in words:
            if word not in self.word_to_num:
                self.word_to_num[word] = self.next_id
                self.num_to_word[self.next_id] = word
                self.next_id += 1

            compressed_words.append(str(self.word_to_num[word]))

        return ' '.join(compressed_words)

    def decompress(self, compressed_text):
        nums = compressed_text.split()
        decompressed_words = []

        for num in nums:
            word_id = int(num)
            if word_id in self.num_to_word:
                decompressed_words.append(self.num_to_word[word_id])
            else:
                raise ValueError(f"ID {word_id} not found in the decompression dictionary.")

        return ' '.join(decompressed_words)

    def compress_file(self, input_file, output_file):
        with open(input_file, 'r') as f_in:
            text = f_in.read()
        compressed_text = self.compress(text)
        with open(output_file, 'w') as f_out:
            f_out.write(compressed_text)
        print(f"File '{input_file}' compressed to '{output_file}'")

    def decompress_file(self, input_file, output_file):
        with open(input_file, 'r') as f_in:
            compressed_text = f_in.read()
        decompressed_text = self.decompress(compressed_text)
        with open(output_file, 'w') as f_out:
            f_out.write(decompressed_text)
        print(f"File '{input_file}' decompressed to '{output_file}'")

# Example usage:
if __name__ == "__main__":
    compressor = Compressor()
    
    # Compress a file
    compressor.compress_file('/Users/saadatibakova/Desktop/mytext.txt', 'compressed.txt')
    
    # Decompress the file
    compressor.decompress_file('compressed.txt', 'output.txt')


