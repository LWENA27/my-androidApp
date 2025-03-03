import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.*;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nameInput, emailInput;
    Button addButton, fetchButton;
    ListView userListView;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        addButton = findViewById(R.id.addButton);
        fetchButton = findViewById(R.id.fetchButton);
        userListView = findViewById(R.id.userListView);

        apiService = ApiClient.getClient().create(ApiService.class);

        addButton.setOnClickListener(v -> insertUser());
        fetchButton.setOnClickListener(v -> fetchUsers());
    }

    private void insertUser() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();

        apiService.insertUser(name, email).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, "User Added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsers() {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        users.stream().map(u -> u.getName() + " - " + u.getEmail()).toArray(String[]::new)
                );
                userListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
