import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {
    @FormUrlEncoded
    @POST("insert.php")
    Call<String> insertUser(
            @Field("name") String name,
            @Field("email") String email
    );

    @GET("fetch.php")
    Call<List<User>> getUsers();
}
