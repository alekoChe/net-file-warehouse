package ru.gb.net.file.warehouse.streamapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamApi {

    public static void orderCounting(String[] args) throws IOException {
        User ivan = new User("Ivan", new Order(1L), new Order(2L), new Order(3L));
        User andrey = new User("Andrey", new Order(4L), new Order(5L));
        User bogdan = new User("Bogdan", new Order(10L));

        long startMillis = System.currentTimeMillis();
        Optional<BigDecimal> totalAmount = List.of(ivan, andrey, bogdan)
                .stream()
                .map(User::getOrders)
                .flatMap(Collection::stream)// invoke DB
                .map(Order::getAmount)
                .peek(System.out::println)
                .reduce(BigDecimal::add);

        System.out.println(totalAmount);
        System.out.println("Total millis: " + (System.currentTimeMillis() - startMillis));

        Map<String, Integer> userOrders = List.of(ivan, andrey, bogdan)
                .stream()
                .collect(Collectors.toMap(user -> user.getLogin(), user -> user.getOrders().size()));
    }

    public static void main(String[] args) throws IOException {
        Files.walk(Path.of("/Users/bchervoniy/IdeaProjects/net-file-warehouse"))
                .map(Path::toFile)
                .filter(file -> !file.isDirectory())
                .filter(file -> file.getName().contains("java"))
                .forEach(item -> {
                    System.out.println(item.getName());
                });
    }
}
