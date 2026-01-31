package com.api.furshet.controller;

import com.api.furshet.domain.entity.*;
import com.api.furshet.domain.enums.PaymentMethods;
import com.api.furshet.domain.enums.ProductLabelType;
import com.api.furshet.domain.enums.TypeCallbackRequest;
import com.api.furshet.domain.enums.TypeDelivery;
import com.api.furshet.dto.*;
import com.api.furshet.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final CallbackRequestService callbackRequestService;
    private final LabelService labelService;
    private final AttributeService attributeService;

    @GetMapping("")
    public String mainPage(){
        return "admin";
    }

    // Начало продукты
    @GetMapping("/products")
    public String getProductsPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/product/new")
    public String createProductForm(Model model) {

        ProductDTO productDto = new ProductDTO();

        model.addAttribute("canDelete", true);
        model.addAttribute("hasBlockedAttribute", null);

        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productImages", new ArrayList<>());
        model.addAttribute("labels", labelService.findAllActive());
        model.addAttribute("attributes", attributeService.getAttributes());


        return "products/product";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = productService.findById(id).orElseThrow();

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setAmount(product.getAmount());
        dto.setCategoryId(product.getCategory().getId());
        dto.setActive(product.getActive());
        dto.setDescription(product.getDescription());

        dto.setLabelIds(
                product.getProductLabel().stream()
                        .map(pl -> pl.getLabel().getId())
                        .toList()
        );

        dto.setAttributeIds(
                product.getProductAttribute().stream()
                        .map(attr -> attr.getAttribute().getId())
                        .toList()
        );


        boolean hasBlockedAttribute = product.getProductAttribute().stream()
                        .anyMatch(pa -> Boolean.TRUE.equals(pa.getAttribute().getBanOnDeletion()));

        model.addAttribute("canDelete", !hasBlockedAttribute);
        model.addAttribute("hasBlockedAttribute", hasBlockedAttribute ? "Невозможно удалить товар: присутствует атрибут с запретом на удаление" : null);


        model.addAttribute("productDto", dto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productImages", product.getProductImages());
        model.addAttribute("labels", labelService.findAllActive());
        model.addAttribute("attributes", attributeService.getAttributes());


        return "products/product";
    }

    @PostMapping("/products/submitProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO,
                                @RequestParam(value = "newImages", required = false) List<MultipartFile> images,
                                @RequestParam(value = "deleteImageIds", required = false) List<Long> deleteId) {
        try {
            for(MultipartFile file : images) {
                System.out.println(file.getOriginalFilename());
            }
            productService.update(productDTO, images, deleteId);
        } catch (Exception e) {
            System.out.println("Ошибка при попытке вызвать update " + e.getMessage());
        }

        return "redirect:/admin/products";
    }


    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
    // Конец продукты

    // Начало категории

    @GetMapping("/categories")
    public String categoryPage(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories/categories";
    }

    @GetMapping("/category/new")
    public String createCategory(Model model) {

        CategoryDTO categoryDto = new CategoryDTO();
        if (categoryDto.getActive() == null) {
            categoryDto.setActive(true);
        }

        model.addAttribute("categoryDto", categoryDto);

        return "categories/category";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setFilename(category.getFilename());
        dto.setActive(category.getActive());
        dto.setOutputToSearch(category.getOutputToSearch());

        model.addAttribute("categoryDto", dto);
        model.addAttribute("categories", categoryService.findAll());

        return "categories/category";
    }

    @PostMapping("/category/submitProduct")
    public String updateCategory(@ModelAttribute CategoryDTO categoryDTO,
                                 @RequestParam("file")MultipartFile file) {
        categoryService.update(categoryDTO, file);
        return "redirect:/admin/categories";
    }

    @PostMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }

    // Конец категории

    // Начало заказы

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders/orders";
    }

    @GetMapping("/order/new")
    public String createOrder(Model model) {
        OrderRequestDTO orderDTO = new  OrderRequestDTO();

        model.addAttribute("orderDto", orderDTO);
        model.addAttribute("paymentMethod", PaymentMethods.values());
        model.addAttribute("typeDelivery", TypeDelivery.values());
        model.addAttribute("categories", categoryService.findAll());

        return "orders/order";
    }

    @GetMapping("/order/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model){
        Order order = orderService.findById(id);

        OrderRequestDTO dto = new OrderRequestDTO();

        dto.setId(order.getId());
        dto.setFIO(order.getFIO());
        dto.setPhone(order.getPhone());
        dto.setEmail(order.getEmail());
        dto.setDelivery(order.getDelivery());
        dto.setCity(order.getCity());
        dto.setAddress(order.getAddress());
        dto.setComment(order.getComment());
        dto.setPaymentMethod(order.getPaymentMethod());

        List<OrderItemRequestDTO> items = order.getOrderItems().stream()
                        .map(item -> {
                            OrderItemRequestDTO itemDTO = new OrderItemRequestDTO();
                            itemDTO.setId(item.getId());
                            itemDTO.setProductId(item.getProduct().getId());
                            itemDTO.setProductName(item.getProduct().getName());
                            itemDTO.setQuantity(item.getQuantity());
                            return  itemDTO;
                        })
                        .toList();
        System.out.println(items);
        dto.setOrderItems(items);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);

        model.addAttribute("orderDto", dto);
        model.addAttribute("paymentMethod", PaymentMethods.values());
        model.addAttribute("typeDelivery", TypeDelivery.values());
        model.addAttribute("categories", categoryService.findAll());

        return "orders/order";
    }

    @PostMapping("/order/submitProduct")
    public String updateOrder(@ModelAttribute OrderRequestDTO orderDTO) {
        orderService.update(orderDTO);
        return "redirect:/admin/orders";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/admin/orders";
    }

    // Конец заказы

    // Начало заявки

    @GetMapping("/callbacks")
    public String callbackPage(Model model) {
        List<CallbackRequest> callback = callbackRequestService.findAll();
        model.addAttribute("callbacks", callback);
        return "CallbackRequests/CallbackRequests";
    }


    @GetMapping("/callback/new")
    public String createCallback(Model model) {
        CallbackRequestDto callbackDto = new CallbackRequestDto();

        model.addAttribute("callbackDTO", callbackDto);
        model.addAttribute("typeCallback", TypeCallbackRequest.values());

        return "CallbackRequests/CallbackRequest";
    }

    @GetMapping("/callbacks/edit/{id}")
    public String editCallback(@PathVariable Long id, Model model) {

        CallbackRequest callback = callbackRequestService.findById(id);

        CallbackRequestDto callbackDto = new CallbackRequestDto();
        callbackDto.setId(callback.getId());
        callbackDto.setFIO(callback.getFIO());
        callbackDto.setPhone(callback.getPhone());
        callbackDto.setEmail(callback.getEmail());
        callbackDto.setAmountPeople(callback.getAmountPeople());
        callbackDto.setData(callback.getData());
        callbackDto.setTypeCallbackRequest(callback.getTypeCallbackRequest());

        model.addAttribute("callbackDTO", callbackDto);
        model.addAttribute("typeCallback", TypeCallbackRequest.values());

        return "CallbackRequests/CallbackRequest";
    }

    @PostMapping("/callback/submitCallback")
    public String updateCallback(@ModelAttribute CallbackRequestDto callbackDto) {
        callbackRequestService.update(callbackDto);
        return "redirect:/admin/callbacks";
    }

    @PostMapping("/callback/delete/{id}")
    public String deleteCallback(@PathVariable Long id) {
        callbackRequestService.delete(id);
        return "redirect:/admin/callbacks";
    }

    // Конец заявки

    // Начало аттрибуты
    @GetMapping("/attributes")
    public String attributePage(Model model) {
        List<Attribute> attributes = attributeService.getAttributes();
        model.addAttribute("attributes", attributes);
        return "attributes/attributes";
    }

    @GetMapping("/attribute/new")
    public String createAttribute(Model model) {
        Attribute attribute = new Attribute();
        model.addAttribute("attributeDTO", attribute);

        return "attributes/attribute";
    }

    @GetMapping("/attribute/edit/{id}")
    public String editAttribute(@PathVariable Long id, Model model) {
        Attribute attribute = attributeService.findById(id);

        AttributeDTO attributeDTO = new AttributeDTO();
        attributeDTO.setId(attribute.getId());
        attributeDTO.setName(attribute.getName());
        attributeDTO.setBanOnDeletion(attribute.getBanOnDeletion());

        model.addAttribute("attributeDTO", attributeDTO);

        return "attributes/attribute";
    }

    @PostMapping("/attribute/submitAttribute")
    public String updateAttribute(@ModelAttribute AttributeDTO attributeDTO) {
        attributeService.update(attributeDTO);
        return "redirect:/admin/attributes";
    }

}
