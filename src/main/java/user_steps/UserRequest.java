package user_steps;
import сonfig.Config;
import dto.UserCreateDTO;
import dto.UserLoginDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserRequest extends Config {
    private final String ROOT = "/auth";
    private final String REGISTER = ROOT + "/register";
    private final String USER = ROOT + "/user";
    private final String LOGIN = ROOT + "/login";

    @Step("Запрос на создание пользователя")
    public ValidatableResponse createUser(UserCreateDTO userCreateDTO) {
        return spec()
                .body(userCreateDTO)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Запрос на удаление пользователя")
    public ValidatableResponse deleteUser(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Запрос на въезд курьера")
    public ValidatableResponse loginUser(UserLoginDTO userLoginDTO) {
        return spec()
                .body(userLoginDTO)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Запрос на изменение данных пользователя")
    public ValidatableResponse updateUser(String token, UserCreateDTO userCreateDTO) {
        return spec()
                .header("Authorization", token)
                .body(userCreateDTO)
                .when()
                .patch(USER)
                .then().log().all();
    }
}