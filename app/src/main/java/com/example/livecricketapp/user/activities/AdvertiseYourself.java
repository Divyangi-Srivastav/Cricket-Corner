package com.example.livecricketapp.user.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityAdvertiseYourselfBinding;
import com.example.livecricketapp.model.AdBanner;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdvertiseYourself extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdvertiseYourselfBinding binding;
    private AdBanner banner;
    private Uri photoUri = null;
    private StorageReference photoRef , reference;
    private String photoUrl = "";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertiseYourselfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        banner = new AdBanner();
        reference = FirebaseStorage.getInstance().getReference("Ad Banner");

        user = FirebaseAuth.getInstance().getCurrentUser();

        binding.image.setOnClickListener(this::onClick);
        binding.uploadImage.setOnClickListener(this::onClick);
        binding.btnSubmit.setOnClickListener(this::onClick);

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(AdvertiseYourself.this, HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(AdvertiseYourself.this, SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(AdvertiseYourself.this, DashboardUser.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void clear(View view) {
        switch (view.getId()) {
            case R.id.message_ad_banner_cross:
                binding.messageAdBanner.getText().clear();
                break;
            case R.id.amount_cross:
                binding.amount.getText().clear();
                break;
        }
    }

    private void create_ad_banner() {
        banner.setUserId(user.getUid());
        banner.setAdMessage(binding.messageAdBanner.getText().toString());
        banner.setAdImageUrl(photoUrl);
        banner.setAmountPaid(Integer.parseInt(binding.amount.getText().toString()));
        banner.setAdTime(get_duration(banner.getAmountPaid()));
    }

    private int get_duration(int amount) {
        switch (amount) {
            case 25:
                return 5;
            case 45:
                return 10;
            case 65:
                return 15;
            default:
                return 0;
        }
    }

    private void upload_photo_to_firebase() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        photoRef.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photoUrl = uri.toString();
                        dialog.dismiss();
                        Toast.makeText(AdvertiseYourself.this, "Ad Banner Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image:

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
                break;

            case R.id.upload_image:

                if (photoUri == null) {
                    Toast.makeText(this, "First Select An Image", Toast.LENGTH_LONG).show();
                } else {
                    upload_photo_to_firebase();
                }

                break;

            case R.id.btn_submit:
                create_ad_banner();
                Intent intent1 = new Intent(this, PaymentActivity.class);
                intent1.putExtra("activity","advertise");
                intent1.putExtra("banner", banner);
                startActivity(intent1);
                clear_data();
                break;
        }

    }

    private void clear_data ()
    {
        binding.image.setImageResource(R.drawable.sample_pic);
        binding.messageAdBanner.getText().clear();
        binding.amount.getText().clear();
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getData() != null) {
                Intent data = result.getData();
                photoUri = data.getData();
                binding.image.setImageURI(photoUri);
                photoRef = reference.child(photoUri.getLastPathSegment());
            }
            else
                Toast.makeText(AdvertiseYourself.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    });
}