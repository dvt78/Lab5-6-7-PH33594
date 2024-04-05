package code.name.monkey.lab6lab7.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import code.name.monkey.lab6lab7.R;
import code.name.monkey.lab6lab7.adapter.ImageAdapter;
import code.name.monkey.lab6lab7.databinding.ActivityAddFruitBinding;
import code.name.monkey.lab6lab7.model.FruitResponse;
import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.model.UserClient;
import code.name.monkey.lab6lab7.viewmodel.AddFruitViewModel;
import code.name.monkey.lab6lab7.viewmodel.LoginViewModel;

public class AddFruitActivity extends AppCompatActivity {
    private AddFruitViewModel viewModel;
    private ActivityAddFruitBinding binding;
    private static final int REQUEST_CODE_SELECT_IMAGES = 100;
    private List<String> imagePaths = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFruitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddFruitViewModel.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
        }

        String[] companyNames = getResources().getStringArray(R.array.company_names);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, companyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.company.setAdapter(adapter);

        binding.images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageAdapter = new ImageAdapter();
        binding.addImage.setOnClickListener(v -> {
            imagePaths.clear();
            selectImagesFromGallery();
        });

        binding.add.setOnClickListener(v -> {
            viewModel.addFruit(binding.name.getText().toString(), binding.quantity.getText().toString(), binding.price.getText().toString(), binding.description.getText().toString(),
                    UserClient.getInstance().get_id(),
                    String.valueOf(binding.company.getSelectedItemPosition()),
                    imagePaths
            );
        });

        viewModel.getFruitResponseMutableLiveData().observe(this, new Observer<FruitResponse>() {
            @Override
            public void onChanged(FruitResponse fruitResponse) {
                if (fruitResponse.getMessage().isStatus()) {
                    onBackPressed();
                }
                Toast.makeText(AddFruitActivity.this, fruitResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectImagesFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_SELECT_IMAGES);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGES && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        imagePaths.add(getRealPathFromURI2(data.getClipData().getItemAt(i).getUri()));
                    }
                } else {
                    imagePaths.add(getRealPathFromURI2(data.getData()));
                }
            }
            imageAdapter.setImagePaths(imagePaths);
            binding.images.setAdapter(imageAdapter);

        }
    }

    private String getRealPathFromURI2(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String realPath = cursor.getString(columnIndex);
            cursor.close();
            return realPath;
        }
        return null;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
}