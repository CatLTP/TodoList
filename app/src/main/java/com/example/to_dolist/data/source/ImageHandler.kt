package com.example.to_dolist.data.source

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageHandler @Inject constructor(
    context: Context,
) {
    private val directory: File = context.getDir("imageDir", Context.MODE_PRIVATE)
    private val contentResolver = context.contentResolver

    fun saveImage(uriList: List<Uri>) : List<String> {
        val nameList = mutableListOf<String>()
        uriList.forEach { uri ->
            val bitmap = convertToBitmap(uri)
            val imageName = getImageName(uri)
            saveBitmapToFile(
                bitmap = bitmap,
                fileName = imageName,
            )
            nameList.add(imageName)
        }
        return nameList
    }

    fun loadImage(fileName: List<String>): List<Bitmap> {
        val result = mutableListOf<Bitmap>()
        fileName.forEach {
            val imageFile = File(directory, it)
            if (imageFile.exists()) {
                result.add(BitmapFactory.decodeFile(imageFile.absolutePath))
            }
        }
        return result
    }

    fun deleteImage(fileName: String) : Boolean {
        val imageFile = File(directory, fileName)
        if (imageFile.exists()) {
            try {
                return imageFile.delete()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun convertToBitmap(uri: Uri): Bitmap {
        val source = ImageDecoder.createSource(contentResolver, uri)
        return ImageDecoder.decodeBitmap(source)
    }

    private fun saveBitmapToFile(bitmap: Bitmap, fileName: String) {
        val file = File(directory, fileName)
        if (!file.exists()) {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        }
    }

    private fun getImageName(uri: Uri) : String {
        var nameIndex = 0
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null,
        )
        if (cursor != null && cursor.moveToFirst()) {
            nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        }
        var result : String
        if (cursor != null) {
            result = cursor.getString(nameIndex)
        } else {
            result = uri.path!!
            val cut: Int = result.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        cursor?.close()
        return result
    }
}