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

public class OrderListUserTest {
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

    @DisplayName("Проверка получения списка заказов зарегистрированного пользователя")
    @Test
    public void testGetOrderListForRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser(accessToken);
        orderCreateSteps.getOrderListInLoginUser(response);
    }

    @DisplayName("Проверка невозможности получения списка заказов незарегистрированным пользователем")
    @Test
    public void testUnableToGetOrderListForNonRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser("1");
        orderCreateSteps.getOrderListNoLoginUser(response);
    }
}