package com.example.teachme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;
public class Profile extends AppCompatActivity {
    TextView FullName, Email, mess;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userid, UserName, UserEmail;
    BottomNavigationView navigationView;

    Button buttonV, rePassword, ChangeProfile, AskQuestion;
    ImageView ProfileImage;
    private CircleImageView profileImageView;
    StorageReference storageReference;
    String CurrentUserUid;
    private static final int REQUEST_IMAGE_SELECT = 1000;
   // private ImageView profileImageView;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FullName = findViewById(R.id.nametextt);
        Email = findViewById(R.id.emailTextt);
//        rePassword = findViewById(R.id.respassword);
//        AskQuestion = findViewById(R.id.askQuestion);
//        ChangeProfile = findViewById(R.id.Cprofile);
        navigationView = findViewById(R.id.Navigation1);


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logoutm) { // Change to R.id.logoutm
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Profile.this, SingInActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.main) {
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }


                return false;
            }
        });




//        Toolbar toolbar = findViewById(R.id.toolbarChat);
//        setSupportActionBar(toolbar);

        // ProfileImage = findViewById(R.id.ProfileImage);
        CurrentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();



        profileImageView = findViewById(R.id.ProfileImage);
        profileImageView.setClickable(true);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        fstore.collection("users").document(CurrentUserUid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String profilePictureUrl = documentSnapshot.getString("profilePictureUrl");
                if (profilePictureUrl != null) {
                    // Display the image using Picasso
                    Picasso.get().load(profilePictureUrl).into(profileImageView);
                }
            }
        });


//        AskQuestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Profile.this, AskQ.class);
//                startActivity(intent);
//            }
//        });

        fstore.collection("users").document(CurrentUserUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserName = document.getString("userName");
                        UserEmail = document.getString("userEmail");

                        FullName.setText(UserName);
                        Email.setText(UserEmail);
                    } else {
                        Log.d("Profile", "No such document");
                    }
                } else {
                    Log.d("Profile", "get failed with ", task.getException());
                }
            }
        });


//        StorageReference profilrRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
//        profilrRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(ProfileImage);
//            }
//        });

       // buttonV = findViewById(R.id.Verify);
      //  mess = findViewById(R.id.verifytext);



//          if (!user.isEmailVerified()) {
//            buttonV.setVisibility(View.VISIBLE);
//            mess.setVisibility(View.VISIBLE);
//
//            buttonV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(Profile.this, "Verification Email Has been sent to your email", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
//                        }
//                    });
//
//                }
//            });
//        }

//        DocumentReference documentReference = fstore.collection("users").document(userid);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                FullName.setText(value.getString("fName"));
//                Email.setText(value.getString("email"));
//            }
//        });

        ////





//
//        rePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText resetPasswordEditText = new EditText(v.getContext());
//
//                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//                passwordResetDialog.setTitle("Reset Password?");
//                passwordResetDialog.setMessage("Enter Your New Password (must be more than 6 characters)");
//                passwordResetDialog.setView(resetPasswordEditText);
//
//                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String newPassword = resetPasswordEditText.getText().toString();
//
//                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                        if (firebaseUser != null) {
//                            // Update password in Firebase Authentication
//                            firebaseUser.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(Profile.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
//
//                                    // Update password in Realtime Database
//                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
//                                    userRef.child("userPassword").setValue(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(Profile.this, "Password Updated in Database", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                Toast.makeText(Profile.this, "Failed to Update Password in Database: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                Log.e("DatabaseUpdate", "Error updating password in database: " + task.getException().getMessage());
//                                            }
//                                        }
//                                    });
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(Profile.this, "Password Reset Failed", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                });
//
//                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Cancel password reset
//                    }
//                });
//
//                passwordResetDialog.create().show();
//            }
//        });
//


//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                onOptionsItemSelected(item);
//                return false;
//            }
//        });
//        ProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent OpengalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(OpengalleryIntent, 1000);
//            }
//        });
////        ChangeProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EditProfile.class);
//                intent.putExtra("Name", FullName.getText().toString());
//                intent.putExtra("Phone", Phone.getText().toString());
//                intent.putExtra("Email", Email.getText().toString());
//                startActivity(intent);
////                Intent OpenGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                startActivityForResult(OpenGalleryIntent,1000);
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri ImageUri = data.getData();
//                //ProfileImage.setImageURI(ImageUri);
//
//
//                uploadImageToFirebase(ImageUri);
//            }
//        }
//    }

//        private void uploadImageToFirebase (Uri imageUri){
//            final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
//            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Picasso.get().load(uri).into(ProfileImage);
//                            Toast.makeText(MainActivity.this, "Image uploading", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(MainActivity.this, "Image uploading is failed", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Update the Firestore document with the user's profile picture URL.
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(fAuth.getCurrentUser().getUid()).update("profilePictureUrl", uri.toString());

                        Picasso.get().load(uri).into(profileImageView);
                        Toast.makeText(Profile.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu,menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.logoutm){ // Change to R.id.logoutm
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(Profile.this, SingInActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        if(item.getItemId()==R.id.main){
//            Intent intent = new Intent(Profile.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}