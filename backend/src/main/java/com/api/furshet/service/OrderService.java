package com.api.furshet.service;

//import com.api.furshet.bot.UpdateConsumer;
import com.api.furshet.domain.entity.Order;
import com.api.furshet.domain.entity.OrderItem;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.domain.entity.TelegramUsers;
import com.api.furshet.dto.OrderRequestDTO;
import com.api.furshet.mail.OrderMail;
import com.api.furshet.repository.OrderRepository;
import com.api.furshet.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
//    private final UpdateConsumer updateConsumer;
    private final TelegramUsersService telegramUsersService;

    @Autowired
    private OrderMail emailService;


    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .FIO(orderRequestDTO.getFIO())
                .phone(orderRequestDTO.getPhone())
                .email(orderRequestDTO.getEmail())
                .delivery(orderRequestDTO.getDelivery())
                .city(orderRequestDTO.getCity())
                .address(orderRequestDTO.getAddress())
                .comment(orderRequestDTO.getComment())
                .paymentMethod(orderRequestDTO.getPaymentMethod())
                .build();

        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new RuntimeException("product not found"));


                    OrderItem item = OrderItem.builder()
                            .product(product)
                            .quantity(itemRequest.getQuantity())
                            .price(product.getPrice())
                            .build();

                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        double total = 0;
        for (OrderItem item : order.getOrderItems()) {
            double itemTotal = item.getPrice() * item.getQuantity();
            total += itemTotal;
        }

        List<TelegramUsers> users = telegramUsersService.findAll();

        // отправка email сообщения
//        for  (TelegramUsers user : users) {
////            updateConsumer.sendOrder(message, Long.parseLong(user.getName()));
//            emailService.sendEmail(user.getName(), "Новый заказ", message);
//        }

        List<String> emails = users.stream()
                .map(TelegramUsers::getName)
                .toList();

        emailService.sendOrderEmail(emails, order, order.getOrderItems(), total);

        return savedOrder;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order not found"));

    }

    public Order update(OrderRequestDTO dto) {
        if (dto.getId() != null) {
            Order order = findById(dto.getId());
            order.setFIO(dto.getFIO());
            order.setPhone(dto.getPhone());
            order.setEmail(dto.getEmail());
            order.setDelivery(dto.getDelivery());
            order.setCity(dto.getCity());
            order.setAddress(dto.getAddress());
            order.setComment(dto.getComment());
            order.setPaymentMethod(dto.getPaymentMethod());

            List<OrderItem> items = dto.getOrderItems().stream()
                    .filter(i -> i.getProductId() != null)
                    .map(itemDTO -> {

                        Product product = productRepository.findById(itemDTO.getProductId())
                                .orElseThrow(() ->
                                        new RuntimeException("Product not found: " + itemDTO.getProductId())
                                );

                        OrderItem item = new OrderItem();
                        item.setId(itemDTO.getId());
                        item.setProduct(product);
                        item.setQuantity(itemDTO.getQuantity());
                        item.setOrder(order);

                        return item;
                    })
                    .toList();

            // Чистим старые orderItems и добавляем новые
            order.getOrderItems().clear();
//            order.getOrderItems().addAll(items);
            for (OrderItem orderItem : items) {
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
            }

            return orderRepository.save(order);
        } else {
            return createOrder(dto);
        }
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
