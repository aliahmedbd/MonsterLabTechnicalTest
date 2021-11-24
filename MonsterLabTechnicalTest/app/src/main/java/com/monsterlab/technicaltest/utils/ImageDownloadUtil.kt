package com.monsterlab.technicaltest.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.BufferedOutputStream
import java.io.File
import java.io.OutputStream

class ImageDownloadUtil {

    public fun addImageToGallery(fileName: String, bitmap: Bitmap, ctx: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val fos: OutputStream
                val resolver = ctx.contentResolver
                val values = ContentValues()

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                values.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "EduImages"
                )

                val imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                fos = resolver.openOutputStream(imageUri!!) as OutputStream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
                Toast.makeText(
                    ctx, "Saved in gallery",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.Images.ImageColumns.TITLE, fileName)

                val uri: Uri? = ctx.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                uri?.let {
                    ctx.contentResolver.openOutputStream(uri)?.let { stream ->
                        val oStream =
                            BufferedOutputStream(stream)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream)
                        oStream.close()
                        Toast.makeText(ctx, "Saved in gallery", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}