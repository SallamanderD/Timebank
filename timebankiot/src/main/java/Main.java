import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ua.dorotenko.timebank.Order;
import ua.dorotenko.timebank.User;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Александр Доротенко on 15.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String SERVER_URI = "http://localhost:6060/";
        RestTemplate restTemplate = new RestTemplate();
        List<Order> orders = restTemplate.exchange(SERVER_URI + "getOrders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {
                }).getBody();
        List<Order> ordersToDisplay = orders.stream()
                .filter(o -> o.isIot())
                .filter(o -> o.getIsCompletedByAuthor() == false
                        && o.getIsCompletedByExecuter() == false
                        && o.getExecuter() != null)
                .collect(Collectors.toList());
        for(Order o : ordersToDisplay){
            System.out.println("ID:" + o.getId());
            System.out.println("NAME:" + o.getName());
        }
        System.out.println("Введите ID заказа для эмуляции срабатывание датчика: ");
        int id = sc.nextInt();
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, id);
        o.setIsCompletedByAuthor(true);
        o.setIsCompletedByExecuter(true);
        o.setIsCompleted(true);
        User u = o.getExecuter();
        u.setBalance(u.getBalance() + o.getCount());
        restTemplate.postForEntity(SERVER_URI + "updateUser", u, Boolean.class).getBody();
        restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
        System.out.println("Операция выполнена успешно!");
    }


}
