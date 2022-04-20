package lesson6;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        UserInterfaceView userInterfaceView = new UserInterfaceView();

        userInterfaceView.runInterface();
        ObjectMapper objectMapper = new ObjectMapper();

    }

}
