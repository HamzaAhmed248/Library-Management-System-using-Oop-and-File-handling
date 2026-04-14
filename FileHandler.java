package code;
import java.io.*;
public class FileHandler {

    private String path;
    private boolean append;

    public FileHandler(String path) {
        this.path = path;
        this.append = false;
    }

    public FileHandler(String path, boolean append) {
        this.path = path;
        this.append = append;
    }

    public void write(String data) throws IOException {
        try (FileWriter writer = new FileWriter(path, append)) {
            writer.write(data);
        }
    }

    public String[] readLines() throws IOException {
        try (FileReader reader = new FileReader(path)) {
            int ascii;
            StringBuilder data = new StringBuilder();
            while ((ascii = reader.read()) != -1) {
                data.append((char) ascii);
            }

            if (data.toString().trim().isEmpty()) return null;
            return data.toString().split("\n");
        }
    }
}
