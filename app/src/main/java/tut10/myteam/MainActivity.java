package tut10.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnBack, btnNext;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String url = "https://jsonplaceholder.typicode.com/users/1";
//        RestLoader loader = new RestLoader();
//        loader.execute(url);
//
//        String url2 = "https://robohash.org/1?set=set2";
//        ImageLoader imageLoader = new ImageLoader();
//        imageLoader.execute(url2);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = new ProfileFragment();
        id=1;
        Bundle data = new Bundle();
        data.putInt("ID",id);
        fragment.setArguments(data);
        manager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id<11){
                   id+=1;
                   Bundle data = new Bundle();
                   data.putInt("ID", id);
                   ProfileFragment profileFragment = new ProfileFragment();
                   profileFragment.setArguments(data);
                   manager.beginTransaction()
                           .replace(R.id.fragmentContainer, profileFragment)
                           .commit();

                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id>0){
                    id-=1;
                    Bundle data = new Bundle();
                    data.putInt("ID", id);
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(data);
                    manager.beginTransaction()
                            .replace(R.id.fragmentContainer, profileFragment)
                            .commit();
                }
            }
        });
    }


}