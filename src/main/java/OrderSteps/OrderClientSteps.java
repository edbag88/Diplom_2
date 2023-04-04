package OrderSteps;
import Config.Config;
import Dto.OrderCreateDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClientSteps extends Config {

    @Step("Отправить запрос на создание заказа пользователем")
    public ValidatableResponse createOrders(String token, OrderCreateDTO orderCreateDTO) {
        return spec()
                .header("Authorization", token)
                .body(orderCreateDTO)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Отправить запрос на получение списка заказов")
    public ValidatableResponse getOrdersListUser(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .get("/orders")
                .then().log().all();
    }
}
