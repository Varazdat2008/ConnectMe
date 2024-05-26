package com.example.teachme;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AskQ extends AppCompatActivity {
    Button buttonQ;
    EditText askQuestion;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_q);

        buttonQ = findViewById(R.id.ButtonQuestion);
        askQuestion = findViewById(R.id.EditQuestion);

        firestore = FirebaseFirestore.getInstance();
        buttonQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String que = askQuestion.getText().toString().trim();
                if (que.isEmpty()) {
                    askQuestion.setError("EditText is Empty");
                } else if (que.length() < 6) {
                    askQuestion.setError("Question must be longer than 6 characters");
                } else {
                    // Add a new document with a generated ID
                    firestore.collection("questions")
                            .add(new Question(que))
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AskQ.this, "Question added to Firestore", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AskQ.this, "Failed to add question to Firestore", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
