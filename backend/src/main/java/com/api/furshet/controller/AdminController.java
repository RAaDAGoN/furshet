package com.api.furshet.controller;

import com.api.furshet.domain.entity.*;
import com.api.furshet.domain.enums.*;
import com.api.furshet.dto.*;
import com.api.furshet.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

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
    private final MenuService menuService;
    private final CateringService cateringService;
    private final PointService pointService;
    private final TelegramUsersService telegramUsersService;
    private final FeedbackService feedbackService;

    @GetMapping("")
    public String mainPage(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (token != null) {
            Cookie cookie = new  Cookie("XSRF-TOKEN", token.getToken());
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(false);
            cookie.setAttribute("SameSite", "None");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
        }

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
        dto.setTypeMenu(product.getTypeMenu());

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
        model.addAttribute("typeMenu", TypeMenu.values());
        model.addAttribute("labels", labelService.findAllActive());
        model.addAttribute("attributes", attributeService.getAttributes());


        return "products/product";
    }

    @PostMapping("/products/submitProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO,
                                @RequestParam(value = "newImages", required = false) List<MultipartFile> images,
                                @RequestParam(value = "deleteImageIds", required = false) List<Long> deleteId) {
        try {
            for (MultipartFile file : images) {
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
    public String createCategory(Model model, HttpServletRequest request) {

        CategoryDTO categoryDto = new CategoryDTO();
        if (categoryDto.getActive() == null) {
            categoryDto.setActive(true);
        }

        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        model.addAttribute("_csrf", token );
        model.addAttribute("categoryDto", categoryDto);

        return "categories/category";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
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
                                 @RequestParam("file") MultipartFile file) {
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
        OrderRequestDTO orderDTO = new OrderRequestDTO();

        model.addAttribute("orderDto", orderDTO);
        model.addAttribute("paymentMethod", PaymentMethods.values());
        model.addAttribute("typeDelivery", TypeDelivery.values());
        model.addAttribute("categories", categoryService.findAll());

        return "orders/order";
    }

    @GetMapping("/order/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
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
                    return itemDTO;
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


    @PostMapping("/attribute/delete/{id}")
    public String deleteAttribute(@PathVariable Long id) {
        attributeService.delete(id);
        return "redirect:/admin/attributes";
    }

    // Конец аттрибуты

    // Начало метки(labels)
    @GetMapping("/labels")
    public String labelsPage(Model model) {
        List<Label> labels = labelService.findAll();
        model.addAttribute("labels", labels);
        return "labels/labels";
    }

    @GetMapping("/label/new")
    public String createLabel(Model model) {
        Label label = new Label();
        model.addAttribute("labelDTO", label);

        return "labels/label";
    }

    @GetMapping("/label/edit/{id}")
    public String editLabel(@PathVariable Long id, Model model) {
        Label label = labelService.findById(id);

        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setId(label.getId());
        labelDTO.setCode(label.getCode());
        labelDTO.setName(label.getName());
        labelDTO.setColor(label.getColor());
        labelDTO.setActive(label.getActive());

        model.addAttribute("labelDTO", labelDTO);

        return "labels/label";
    }

    @PostMapping("/label/submitLabel")
    public String updateLabel(@ModelAttribute LabelDTO labelDTO) {
        labelService.update(labelDTO);
        return "redirect:/admin/labels";
    }


    @PostMapping("/label/delete/{id}")
    public String deleteLabel(@PathVariable Long id) {
        labelService.delete(id);
        return "redirect:/admin/labels";
    }


    // Конец метки(labels)

    // Начало меню
    @GetMapping("/menuList")
    public String menuPage(Model model) {
        List<Menu> menu = menuService.findAll();
        model.addAttribute("menuList", menu);
        return "menu/listMenu";
    }

    @GetMapping("/menu/new")
    public String createMenu(Model model) {
        Menu menu = new Menu();
        model.addAttribute("menuDTO", menu);

        model.addAttribute("typeMenu", TypeMenu.values());
        return "menu/menu";
    }

    @GetMapping("/menu/edit/{id}")
    public String editMenu(@PathVariable Long id, Model model) {

        Menu menu = menuService.findById(id);
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setName(menu.getName());
        menuDTO.setActive(menu.getActive());
        menuDTO.setFilename(menu.getFilename());
        menuDTO.setTranslate(menu.getTranslate());
        model.addAttribute("menuDTO", menuDTO);

        model.addAttribute("typeMenu", TypeMenu.values());

        return "menu/menu";
    }


    @PostMapping("/menu/submitMenu")
    public String updateMenu(@ModelAttribute MenuDTO menuDTO,
                             @RequestParam("file") MultipartFile file) {
        menuService.update(menuDTO, file);
        return "redirect:/admin/menuList";
    }

    // Конец меню

    // Начало кейтеринг
    @GetMapping("/caterings")
    public String cateringPage(Model model) {
        List<Catering> caterings = cateringService.findAll();
        model.addAttribute("caterings", caterings);
        return "catering/caterings";
    }

    @GetMapping("/catering/new")
    public String createCatering(Model model) {
        CateringDTO cateringDTO = new CateringDTO();
        model.addAttribute("cateringDTO", cateringDTO);
        model.addAttribute("typePhoto", TypePhoto.values());
        model.addAttribute("point", pointService.findAll());

        return "catering/catering";
    }

    @GetMapping("/catering/edit/{id}")
    public String editCatering(@PathVariable Long id, Model model) {
        Catering catering = cateringService.findById(id);
        CateringDTO cateringDTO = new CateringDTO();
        cateringDTO.setId(catering.getId());
        cateringDTO.setPointId(catering.getPoint().getId());
        cateringDTO.setTypePhoto(catering.getTypePhoto());
        cateringDTO.setFilename(catering.getFilename());

        model.addAttribute("cateringDTO", cateringDTO);
        model.addAttribute("typePhoto", TypePhoto.values());
        model.addAttribute("point", pointService.findAll());

        return "catering/catering";
    }

    @PostMapping("/catering/submitCatering")
    public String updateCatering(@ModelAttribute CateringDTO cateringDTO,
                                 @RequestParam("file") MultipartFile file) {
        cateringService.update(cateringDTO, file);
        return "redirect:/admin/caterings";
    }

    // Конец кейтеринг

    // Начало point

    @GetMapping("/points")
    public String cateringPoints(Model model) {
        List<Point> points = pointService.findAll();
        model.addAttribute("points", points);
        return "point/points";
    }

    @GetMapping("/point/new")
    public String createPoint(Model model) {
        Point point = new Point();
        model.addAttribute("pointDTO", point);
        return "point/point";
    }

    @GetMapping("/point/edit/{id}")
    public String editPoint(@PathVariable Long id, Model model) {
        Point point = pointService.findById(id);
        PointDTO pointDTO = new PointDTO();
        pointDTO.setId(point.getId());
        pointDTO.setName(point.getName());

        model.addAttribute("pointDTO", pointDTO);

        return "point/point";
    }

    @PostMapping("/point/submitPoint")
    public String updatePoint(@ModelAttribute PointDTO pointDTO) {
        pointService.update(pointDTO);
        return "redirect:/admin/points";
    }

    // Конец point

    // Начало Пользователи
    @GetMapping("/users")
    public String usersPage(Model model) {
        List<TelegramUsers> users = telegramUsersService.findAll();
        model.addAttribute("users", users);
        return "TelegramUsers/users";
    }

    @GetMapping("/users/new")
    public String createUser(Model model) {
        TelegramUsers telegramUsers = new TelegramUsers();
        model.addAttribute("usersDTO", telegramUsers);
        return "TelegramUsers/user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        TelegramUsers telegramUsers = telegramUsersService.findById(id);
        TelegramUsersDTO telegramUsersDTO = new TelegramUsersDTO();
        telegramUsersDTO.setId(telegramUsers.getId());
        telegramUsersDTO.setName(telegramUsers.getName());

        model.addAttribute("usersDTO", telegramUsersDTO);

        return "TelegramUsers/user";
    }

    @PostMapping("/user/submitUser")
    public String updateUser(@ModelAttribute TelegramUsersDTO telegramUsersDTO) {
        telegramUsersService.update(telegramUsersDTO);
        return "redirect:/admin/users";
    }

    // Конец Пользователи

    // Начало отзывы
    @GetMapping("/feedbacks")
    public String feedbacksPage(Model model) {
        List<Feedback> feedbacks = feedbackService.findAll();
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("typeFeedback", TypeFeedback.values());
        return "feedback/feedbacks";
    }

    @GetMapping("/feedback/new")
    public String createFeedback(Model model) {
        Feedback feedback = new Feedback();
        model.addAttribute("feedbackDTO", feedback);
        model.addAttribute("typeFeedback", TypeFeedback.values());
        return "feedback/feedback";
    }

    @GetMapping("/feedback/edit/{id}")
    public String editFeedback(@PathVariable Long id, Model model) {
        Feedback feedback = feedbackService.findById(id);
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setFI(feedback.getFI());
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setDate(feedback.getDate());
        feedbackDTO.setComment(feedback.getComment());
        feedbackDTO.setActive(feedback.getActive());
        feedbackDTO.setTypeFeedback(feedback.getTypeFeedback());

        model.addAttribute("feedbackDTO", feedbackDTO);
        model.addAttribute("typeFeedback", TypeFeedback.values());

        return "feedback/feedback";
    }

    @PostMapping("/feedback/submitFeedback")
    public String updateFeedback(@ModelAttribute FeedbackDTO feedbackDTO) {
        feedbackService.update(feedbackDTO);
        return "redirect:/admin/feedbacks";
    }



    @PostMapping("/feedback/{id}/publish")
    public String publishFeedback(@PathVariable Long id) {
        feedbackService.updateFeedbackStatus(id, TypeFeedback.ACCEPTED);

        return "redirect:/admin/feedbacks";
    }

    @PostMapping("/feedback/{id}/reject")
    public String rejectFeedback(@PathVariable Long id) {
        feedbackService.updateFeedbackStatus(id, TypeFeedback.REJECTED);

        return "redirect:/admin/feedbacks";
    }

    @PostMapping("/feedback/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.delete(id);
        return "redirect:/admin/feedbacks";
    }
}
