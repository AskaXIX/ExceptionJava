import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserDataProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
        String input = scanner.nextLine();

        String[] data = input.split(" ");

        if (data.length != 6) {
            System.err.println("Ошибка: введено " + data.length + " данных. Ожидается 6.");
            return;
        }

        try {
            String lastName = data[0];
            String firstName = data[1];
            String patronymic = data[2];
            String birthDate = data[3];
            long phoneNumber = Long.parseLong(data[4]);
            char gender = data[5].charAt(0);

            validateDate(birthDate);

            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Пол должен быть 'f' или 'm'. Введено: " + gender);
            }

            String fileName = lastName + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(String.join(" ", data));
                writer.newLine();
                System.out.println("Данные успешно сохранены в файл " + fileName);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: номер телефона должен быть целым числом. " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Ошибка: дата рождения должна быть в формате dd.mm.yyyy. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    private static void validateDate(String birthDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        dateFormat.parse(birthDate);
    }
}
