package exportkit.xd.View.Search;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.List;

import exportkit.xd.Controller.userController;
import exportkit.xd.DB.SessionManager;
import exportkit.xd.Model.User;
import exportkit.xd.R;
import exportkit.xd.View.IAppViews;
import exportkit.xd.View.Profile.profile_activity;
import exportkit.xd.View.homepage_activity;

public class SearchUser_activity extends Activity implements IAppViews {
    EditText username;
    Button done , recipesButton , usersButton ;
    ImageButton back, HomeButton;
    private CircularImageView ProfileIcon;
    int userID;

    userController UController;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_user);

        UController = new userController(this);

        //find views
        username = (EditText) findViewById(R.id.search);
        done = (Button) findViewById(R.id.ellipse_ek22);
        back = (ImageButton) findViewById(R.id.backk);
        HomeButton = (ImageButton) findViewById(R.id.home1);
        ProfileIcon = findViewById(R.id.profile1);
        recipesButton = (Button) findViewById(R.id.recipes);
        usersButton = (Button) findViewById(R.id.users) ;
        //display IProfile icon
        // get logged user
        SessionManager session = new SessionManager(this);
        long loggedUser= session.getUserFromSession();
        User user= UController.getUser((int)loggedUser);
        if(user.getAvatar() != null) {
            ProfileIcon.setImageURI(Uri.parse(user.getAvatar()));
        }

        //buttons functions
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txtName = username.getText().toString(), Username= txtName.replaceAll("\\s",""); //remove all spaces
                List<User> userinfo = UController.SearchUser(Username);
                if (userinfo.isEmpty()) {
                    onError("The username Doesn't exist");
                }
                else
                {
                    userID= userinfo.get(0).getId();
                    onSuccess("");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), homepage_activity.class);
                startActivity(nextScreen);
            }
        });
        HomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), homepage_activity.class);
                startActivity(nextScreen);
            }
        });
        recipesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), SearchRecipe_activity.class);
                startActivity(nextScreen);
                Toast.makeText(getApplication(),"Now You are On Search Recipe Mode",Toast.LENGTH_LONG).show();

            }
        });
        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), profile_activity.class);
                startActivity(nextScreen);
            }
        });
    }

    @Override
    public void onSuccess(String message) {
        Intent nextScreen = new Intent(getApplicationContext(), userprofile_Search.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", userID);
        nextScreen.putExtras(bundle);
        startActivity(nextScreen);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
    }
}