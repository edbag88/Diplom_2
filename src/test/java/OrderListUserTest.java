import Dto.UserCreateDTO;
import OrderSteps.OrderClientSteps;
import OrderSteps.OrderCreateSteps;
import UserSteps.UserGenerator;
import UserSteps.UserRequest;
import UserSteps.UserResponse;
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
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }
    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
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