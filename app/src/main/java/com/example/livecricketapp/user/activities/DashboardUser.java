package com.example.livecricketapp.user.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityDashboardUserBinding;
import com.example.livecricketapp.model.UserHelper;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardUser extends AppCompatActivity {

    private ActivityDashboardUserBinding binding;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private FirebaseUser firebaseUser;
    private UserHelper user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new UserHelper();
        reference = FirebaseDatabase.getInstance().getReference("users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        get_user();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(DashboardUser.this, HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(DashboardUser.this, SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        break;

                }
                return true;
            }
        });
    }

    public void edit_content(View view) {
        switch (view.getId()) {
            case R.id.email_edit:
                binding.email.setEnabled(true);
                binding.email.setFocusable(true);
                binding.email.setClickable(true);
                binding.email.setFocusableInTouchMode(true);
                break;

            case R.id.phone_edit:
                binding.phone.setEnabled(true);
                binding.phone.setFocusable(true);
                binding.phone.setClickable(true);
                binding.phone.setFocusableInTouchMode(true);
                break;

            case R.id.address_edit:
                binding.address.setEnabled(true);
                binding.address.setFocusable(true);
                binding.address.setClickable(true);
                binding.address.setFocusableInTouchMode(true);
                break;
        }
    }

    private void get_user() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                user = snapshot.getValue(UserHelper.class);
                if (user.getUserKey().equalsIgnoreCase(firebaseUser.getUid()))
                    update_UI(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addChildEventListener(childEventListener);

    }

    private void update_UI(UserHelper userHelper) {
        binding.name.setText(userHelper.getUserName());
        binding.email.setText(userHelper.getUserEmail());
        binding.phone.setText(userHelper.getUserPhoneno());
        binding.address.setText(userHelper.getUserAddress());
    }

    public void update_on_firebase ( View view )
    {
        if ( check_empty() )
        {
            user.setUserEmail(binding.email.getText().toString());
            user.setUserPhoneno(binding.phone.getText().toString());
            user.setUserAddress(binding.address.getText().toString());

            reference.child(firebaseUser.getUid()).setValue(user);
            Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            binding.email.setFocusable(false);
            binding.name.setFocusable(false);
            binding.address.setFocusable(false);
        }
    }

    private Boolean check_empty() {
        if (binding.email.getText().toString().isEmpty()) {
            binding.email.setError("Required");
            return false;
        } else if (binding.phone.getText().toString().isEmpty()) {
            binding.phone.setError("Required");
            return false;
        } else if (binding.address.getText().toString().isEmpty()) {
            binding.address.setError("Required");
            return false;
        } else {
            return true;
        }
    }


}