import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

public class ReverseUseStream {

    File fileIn;
    File fileOut;

    public ReverseUseStream(File fileIn, File fileOut) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
    }

    public void reversedUseStream() {
//        Чтение блоками по 1 млн. строк и запись в обратном порядке.
        long count = 0;
        try (PrintWriter temp =
                     new PrintWriter(new FileWriter(fileOut))) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(fileIn.toPath())) {
            count = lines.count();
            System.out.println("Кол-во строк к обработке: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis();
        long number = 1000000;
        try (
                PrintWriter writer = new PrintWriter(new FileWriter(fileOut))
        ) {
            List<String> array;
            int k = (int) (count / number);
            for (long j = 1; j <= k; j++) {
                try (Stream<String> lines = Files.lines(fileIn.toPath())) {
                    array = (lines.skip(count - number * j).limit(number).toList());
                    for (int i = 0; i < number; i++) {
                        writer.println(array.get((int) (number - 1 - i)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long intLines = count - number * k;
            Stream<String> lines = Files.lines(fileIn.toPath());
            array = lines.limit(intLines).toList();
            for (int i = 1; i <= intLines; i++) {
                writer.println(array.get((int) (intLines - i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Время работы реверсного чтения и записи: " +
                ((System.currentTimeMillis() - time)) + " ms");
    }
}
