package lesson6;

import java.io.IOException;
import java.util.Scanner;

public class UserInterfaceView {
    private final Controller controller = new Controller();

    public void runInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Введите имя города: ");
            String city = scanner.nextLine();

            System.out.println("Введите 5 для прогноза на 5 дней;" +
                    "Для выхода введите 0:");

            String command = scanner.nextLine();

            if ("o".equals(command)) break;
            //if (command.equals("0")) break;


            controller.getWeather(command, city);

            }





        }


        }


