package com.example.insphiredapp.EmployerActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewerActivity extends AppCompatActivity  {
    PDFView pdfView;
    private String pdf, pdfUrl;
    private boolean shouldLoadHomeFragOnBackPress = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdf = getIntent().getStringExtra("data");
        Log.e("http", "" + pdf);
        pdfView = findViewById(R.id.pdfView);

        pdfUrl = ("https://itdevelopmentservices.com/insphire/public/image/admin/employee/" + pdf);

        // Attempt to open the PDF using an Intent
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If a PDF viewer app is not available, handle the exception
            Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
        }

    }


}