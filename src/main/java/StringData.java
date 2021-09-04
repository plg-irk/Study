import java.io.*;
import java.util.ArrayList;

public class StringData {

    ArrayList<String> fileArrayList;
    boolean sortType;

    public StringData(ArrayList<String> fileArrayList, boolean sortType) {
        this.fileArrayList = fileArrayList;
        this.sortType = sortType;
    }

    public void readWriteWithSort() {

        ArrayList<String> arrayListToFile = new ArrayList<>();
        for (int i = 1; i < fileArrayList.size(); i++) {
            try {
                BufferedReader reader =
                        new BufferedReader(new FileReader(fileArrayList.get(i)));
                String strLine;
                while ((strLine = reader.readLine()) != null) {
                    if (arrayListToFile.size() != 0) {
                        for (int j = 0; j < arrayListToFile.size(); j++) {
                            if (sortType && strLine.compareTo(arrayListToFile.get(j)) <= 0) {
                                arrayListToFile.add(j, strLine);
                                break;
                            } else if (!sortType && strLine.compareTo(arrayListToFile.get(j)) >= 0) {
                                arrayListToFile.add(j, strLine);
                                break;
                            } else if (j == (arrayListToFile.size() - 1)) {
                                arrayListToFile.add(strLine);
                                break;
                            }
                        }
                    } else arrayListToFile.add(strLine);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Не удается найти указанный файл: " + fileArrayList.get(i));
                System.out.println("Часть данных может быть утерянна.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileArrayList.get(0)))) {
            for (String s : arrayListToFile) {
                writer.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
