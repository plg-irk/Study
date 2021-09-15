import java.io.*;
import java.util.ArrayList;

public class IntegerData {

    ArrayList<String> fileArrayList;
    boolean sortType;

    public IntegerData(ArrayList<String> fileArrayList, boolean sortType) {
        this.fileArrayList = fileArrayList;
        this.sortType = sortType;
    }

    public void readWriteWithSort() {

        ArrayList<Integer> arrayListToFile = new ArrayList<>();
        for (int i = 1; i < fileArrayList.size(); i++) {
            try (BufferedReader reader =
                         new BufferedReader(new FileReader(fileArrayList.get(i)))) {
                String strLine;
                Integer intData;
                int m = 0;
                while ((strLine = reader.readLine()) != null) {
                    try {
                        intData = Integer.parseInt(strLine);
                        if (arrayListToFile.size() != 0) {
                            for (int j = m; j < arrayListToFile.size(); j++) {
                                if (sortType && intData - arrayListToFile.get(j) <= 0) {
                                    arrayListToFile.add(j, intData);
                                    m = j;
                                    break;
                                } else if (!sortType && intData - arrayListToFile.get(j) >= 0) {
                                    arrayListToFile.add(j, intData);
                                    m = j;
                                    break;
                                } else if (j == (arrayListToFile.size() - 1)) {
                                    arrayListToFile.add(intData);
                                    m = j;
                                    break;
                                }
                            }
                        } else arrayListToFile.add(intData);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка данных в файле: " + fileArrayList.get(i));
                        System.out.println("Возможна потеря данных.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Не удается найти указанный файл: " + fileArrayList.get(i));
                System.out.println("Часть данных может быть утеряна.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileArrayList.get(0)))) {
            for (Integer s : arrayListToFile) {
                writer.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
