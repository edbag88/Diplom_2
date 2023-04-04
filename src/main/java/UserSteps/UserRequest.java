package UserSteps;
import Config.Config;
import Dto.UserCreateDTO;
import Dto.UserLoginDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserRequest extends Config {
    private final String ROOT = "/auth";
    private final String REGISTER = ROOT + "/register";
    private final String USER = ROOT + "/user";
    private final String LOGIN = ROOT + "/login";

    @Step("Запрос на создание пользователя")
    public ValidatableResponse create(UserCreateDTO userCreateDTO) {
        return spec()
                .body(userCreateDTO)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Запрос на удаление пользователя")
    public ValidatableResponse delete(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Запрос на въезд курьера")
    public ValidatableResponse login(UserLoginDTO userLoginDTO) {
        return spec()
                .body(userLoginDTO)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Запрос на изменение данных пользователя")
    public ValidatableResponse update(String token, UserCreateDTO userCreateDTO) {
        return spec()
                .header("Authorization", token)
                .body(userCreateDTO)
                .when()
                .patch(USER)
                .then().log().all();
    }
}