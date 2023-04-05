import dto.OrderCreateDTO;
import dto.UserCreateDTO;
import order_steps.OrderClientSteps;
import order_steps.OrderCreateSteps;
import user_steps.UserGenerator;
import user_steps.UserRequest;
import user_steps.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderCreateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final OrderClientSteps orderClientSteps = new OrderClientSteps();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private final OrderCreateSteps orderCreateSteps = new OrderCreateSteps();
    private String accessToken;

    @Before
    public void setUpUser() {
        ValidatableResponse create = userRequest.createUser(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.deleteUser(accessToken);
            userResponse.assertDeleteSusses(response);
        }
    }

    @DisplayName("Проверка создания заказа зарегистрированным пользователем")
    @Test
    public void testOrderCreationByRegisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Проверка создания заказа незарегистрированным пользователем")
    @Test
    public void testOrderCreationByUnregisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders("1", orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Проверка на ошибку при попытке создать заказ без ингредиентов")
    @Test
    public void testOrderCreationErrorWithoutIngredients() {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderNoIngredients(response);
    }

    @DisplayName("Проверка на ошибку при попытке создать заказ с недопустимыми хэшами ингредиентов")
    @Test
    public void testOrderCreationErrorWithInvalidIngredientHashes() {
        List<String> ingredients = List.of("61c0c5a71d1f820dff01bdaaa6d", "61c0c5a71d1f82dfsdf001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderInvalidHashIngredients(response);
    }
}
