package com.test.upload_image_0802

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputBinding
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.test.upload_image_0802.databinding.ActivityStorageBinding
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityStorageBinding
    lateinit var ImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(LayoutInflater)
        setContentView((binding.root))

        binding.selectImageBtn.setOnClickListener {
        selectImage()
    }


        binding.uploadImageBtn.setOnClickListener{

            uploadImage()

    }






}
         private fun uploadImage() {
                        val progressDialog = ProgressDialog(this)
                        progressDialog.setCancelMessage("Uploading File ...")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                        val formatter =SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                        val now = Date()
                        val fileName = formatter.format(now)
                        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

                        storageReference.putFile(ImageUri).
                                  addOnSuccessListener{
                                        binding.firebaseimage.setImageURI(null)
                                        Toast.makeText(this@StorageActivity,"Successfuly uploaded",Toast.LENGTH_SHORT).show()

                                       if (progressDialog.isShowing) progressDialog.dismiss()


                                  }.addOnFailureListener{
                                       if (progressDialog.isShowing) progressDialog.dismiss()
                            Toast.makeText(this@StorageActivity,"Failed",Toast.LENGTH_SHORT).show()
                        }
             }

        private fun selectImage() {
            
              val intent = Intent()
              intent.type ="images/*"
              intent.action = Intent.ACTION_GET_CONTENT

             startActivityForResult(intent,100)


            
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){

            ImageUri = data?.data!!
            binding.firebaseimage.setImageURI(ImageUri)
        }


    }


}