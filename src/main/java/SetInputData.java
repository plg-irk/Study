import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

public class SetInputData {

    public static void main(String[] args) {

        int num;
        long count = 0;
        File fileIn = new File("inBigData.txt");
        File fileOut = new File("reversed.txt");

        try (
                PrintWriter temp = new PrintWriter(new FileWriter("reversed.txt"))) {
//            Обнуляем файл
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileIn))) {
            for (int i = 0; i < 100000000; i++) {
                num = i + 1;
                writer.println(num);
//                System.out.println(num);
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

        long time = System.currentTimeMillis();
        try (RandomAccessFile fIn =
                     new RandomAccessFile(fileIn, "rw");
             RandomAccessFile fOut =
                     new RandomAccessFile(fileOut, "rw");
             PrintWriter result = new PrintWriter(new FileWriter(fileOut))) {
//            System.out.println("in length= " + fIn.length() + " out length= " + fOut.length());

            long posReadIn = fIn.length();
            long posReadOut = fIn.length();
            long pos;
            String str1 = null;
            String str2 = null;
            int numberLine = 0;
            for (int j = 0; j <= posReadOut; j++) {
                if (fIn.length() != 0) {
                    for (int i = 0; i <= posReadOut; i++) {
                        pos = posReadOut - i;
                        fIn.seek(pos);
                        if (pos != 0) {
                            str1 = fIn.readLine();
                            str2 = fIn.readLine();
//                            System.out.println("str1= " + str1 + " str2= " + str2);
//
                            if ((str1 == null || str1.length() == 0) && (str2 != null)) {
//                                System.out.println("posReadIn= " + (posReadIn - i));
                                result.println(str2);
                                fIn.setLength(posReadIn - i);
                                System.out.println("Time write line= " + numberLine + " " +
                                        ((System.currentTimeMillis() - time)) + " ms");
                                numberLine += 1;
//                                str2 = str1;
                                break;
                            }
//                            str2 = str1;
                        } else {
                            fIn.seek(0);
                            result.println(fIn.readLine());
                            fIn.setLength(0);
                            break;
                        }
                    }
                } else break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Время работы обратного чтения - записи: " +
                ((System.currentTimeMillis() - time)) + " ms");
    }
}
