package app;

import models.*;
import service.*;
import util.FileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final EventService eventService = new EventService();
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Event> loadedEvents = FileHandler.loadEvents();
        if (loadedEvents != null) {
            eventService.setEvents(loadedEvents);
        }

        while (true) {
            System.out.println("\nBem-vindo ao EventManager!");
            System.out.println("1. Registrar usuário");
            System.out.println("2. Cadastrar evento");
            System.out.println("3. Listar eventos");
            System.out.println("4. Participar de evento");
            System.out.println("5. Cancelar participação");
            System.out.println("6. Sair");

            int option = readInt("Escolha uma opção: ");

            switch (option) {
                case 1 -> registerUser();
                case 2 -> addEvent();
                case 3 -> listEvents();
                case 4 -> participateInEvent();
                case 5 -> cancelParticipation();
                case 6 -> {
                    FileHandler.saveEvents(eventService.getAllEvents());
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Cidade: ");
        String city = scanner.nextLine();

        userService.registerUser(name, email, city);
        System.out.println("Usuário registrado com sucesso!");
    }

    private static void addEvent() {
        System.out.print("Nome do evento: ");
        String name = scanner.nextLine();
        System.out.print("Endereço: ");
        String address = scanner.nextLine();
        System.out.print("Categoria (FESTA, SHOW, ESPORTE, OUTROS): ");
        Category category = Category.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Data e hora (dd-MM-yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        eventService.addEvent(new Event(name, address, category, dateTime, description));
        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listEvents() {
        System.out.println("1. Todos os eventos");
        System.out.println("2. Próximos eventos");
        System.out.println("3. Eventos passados");
        System.out.println("4. Eventos acontecendo agora");
        System.out.println("5. Meus eventos confirmados");

        int option = readInt("Escolha uma opção: ");

        List<Event> events = switch (option) {
            case 1 -> eventService.getAllEvents();
            case 2 -> eventService.getUpcomingEvents();
            case 3 -> eventService.getPastEvents();
            case 4 -> eventService.getOngoingEvents();
            case 5 -> userService.getUser().getConfirmedEvents();
            default -> {
                System.out.println("Opção inválida!");
                yield List.of();
            }
        };

        for (int i = 0; i < events.size(); i++) {
            System.out.println(i + ": " + events.get(i));
        }
    }

    private static void participateInEvent() {
        listEvents();
        int index = readInt("Digite o índice do evento: ");
        Event event = eventService.getEventByIndex(index);

        if (event != null) {
            userService.confirmParticipation(event);
            System.out.println("Participação confirmada!");
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    private static void cancelParticipation() {
        List<Event> confirmed = userService.getUser().getConfirmedEvents();
        for (int i = 0; i < confirmed.size(); i++) {
            System.out.println(i + ": " + confirmed.get(i));
        }

        int index = readInt("Digite o índice para cancelar: ");

        if (index >= 0 && index < confirmed.size()) {
            userService.cancelParticipation(confirmed.get(index));
            System.out.println("Participação cancelada!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    // Método auxiliar para ler inteiros com validação
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            }
            System.out.println("Entrada inválida. Digite um número.");
        }
    }
}