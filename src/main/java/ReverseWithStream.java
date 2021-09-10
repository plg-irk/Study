import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ReverseWithStream {
    public static void main(String[] args) {

        int num;
        long count = 0;
        File fileIn = new File("in5.txt");
        File fileOut = new File("reversed.txt");

        try (PrintWriter temp =
                     new PrintWriter(new FileWriter("reversed.txt"))) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileIn))) {
            for (int i = 0; i < 10000; i++) {
                num = i + 1;
                writer.println(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(fileIn.toPath())) {
            count = lines.count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis();
        try (
                PrintWriter writer = new PrintWriter(new FileWriter(fileOut))
        ) {
            for (long j = 1; j <= count; j++) {
                try (Stream<String> lines = Files.lines(fileIn.toPath())) {
                    writer.println(lines.skip(count - j).limit(1).toList().get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Время работы обратного чтения - записи: " +
                ((System.currentTimeMillis() - time)) + " ms");
    }
}


