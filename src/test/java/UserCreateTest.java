import Dto.UserCreateDTO;
import UserSteps.UserGenerator;
import UserSteps.UserRequest;
import UserSteps.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {

    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
            userResponse.assertDeleteSusses(response);
        }
    }
    @Test
    @DisplayName("Проверка успешного создания пользователя")
    public void testSuccessfulUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }
    @Test
    @DisplayName("Проверка невозможности создания двойного пользователя")
    public void testFailedDuplicateUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);

        ValidatableResponse create2 = userRequest.create(uniqueUser);
        userResponse.assertCreationDoubleUserFailed(create2);
    }
    @Test
    @DisplayName("Проверка невозможности создания пользователя без e-mail")
    public void testFailedUserCreationWithoutEmail() {
        uniqueUser.setEmail(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }
    @Test
    @DisplayName("Проверка невозможности создания пользователя без пароля")
    public void testFailedUserCreationWithoutPassword() {
        uniqueUser.setPassword(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }
    @Test
    @DisplayName("Проверка невозможности создания пользователя без имени")
    public void testFailedUserCreationWithoutName() {
        uniqueUser.setName(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }
}
