import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

public class SetInputData {

    public static void main(String[] args) {

        int num;
        long count = 0;
        File fileIn = new File("in5.txt");
        File fileOut = new File("reversed.txt");

        try (
                PrintWriter temp = new PrintWriter(new FileWriter("reversed.txt"))) {
//            Обнуляем файл
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileIn))) {
            for (int i = 0; i < 10000; i++) {
                num = i + 1;
                writer.println(num);
                System.out.println(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(fileIn.toPath())) {
            count = lines.count();
            System.out.println("count= " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (RandomAccessFile fIn =
                     new RandomAccessFile(fileIn, "rw");
             RandomAccessFile fOut =
                     new RandomAccessFile(fileOut, "rw");
             PrintWriter result = new PrintWriter(new FileWriter(fileOut))) {
//            fOut.setLength(fIn.length());
            System.out.println("in length= " + fIn.length() + " out length= " + fOut.length());

            for (int j = 0; j < count; j++) {
                String str = null;
                long posCurrent = 0;
                long posReadIn = 0;
//                long posReadCurrent = 0;

                for (int i = 0; i < count - j; i++) {
                    fIn.seek(posCurrent);
                    posReadIn = fIn.getFilePointer();
//                    System.out.println("posReadIn= " + posReadIn);
                    str = fIn.readLine();
                    posCurrent = fIn.getFilePointer();
//                    System.out.println("str= " + str + " posCurrent= " + posCurrent);
                }
//                System.out.println("posCurrent= " + posReadIn);
                result.println(str);
                fIn.setLength(posReadIn);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
